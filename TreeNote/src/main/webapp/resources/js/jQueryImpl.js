
window.onload = function(){
	$("#btn_content").click(function() {
		$("#content").toggle("drop");
	});
	$("#btn_mypage").click(function() {
		$("#mypage").toggle("drop");
	});
	$("#btn_userpage").click(function() {
		$("#userpage").toggle("drop");
	});
	$("#btn_tree").click(function() {
		$("#treelist").toggle("drop");
	});
	$("#btn_reply").click(function() {
		$("#reply").toggle("drop");
	});
	$("#btn_friend").click(function() {
		$("#friendlist").toggle("drop");
	});
	
	$("#btn_search").click(function(){
		if (window.sessionStorage) {
			if(sessionStorage.getItem('isTimeline') == 'true'){
				$(".keywordBox").remove();
				$(".timeLineBtnBox").remove();
				$(".ui-effects-wrapper").remove();
	        	$("#timeLineTitleBox").hide();
	    		$("#searchBox").show("slide",{
	    			direction : "up",
	    			duration : 500
	    		});
	        }
	        sessionStorage.setItem('isTimeline', 'false');
	    }
	});

	autocom();
	setTimeout("setListTimeKeyword()",2000);
}


$(function() {
	if (window.sessionStorage) {
        // sessionStorage.setItem('editTreeNo', data.user.userNo);
        // var position = sessionStorage.getItem('저장된 이름');
		// alert(sessionStorage.getItem('editTreeNo'));
    }
})

// 페이스북 로그인 버트 클릭시 이벤
function facebookLogin() {

	$("#navbar").show("slide", {
		direction : "up"
	}, 1000);
	$("#loginContainer").css("display", "none");
	$('#treeContainer').css('display', 'block');
	$("#base").delay(1000).show("fade", 500);
	setTimeout("goImpl()", 2000);

}

function setListSearchKeyword(keyword) {
	console.log(keyword);
	$("#search").val(keyword);
	jQuery.ajax({
		url : "/keyword/listSearchKeyword",
		method : "POST",
		dataType : "json",
		data : JSON.stringify({
			keyword : keyword,
		}),
		headers : {
			"Accept" : "application/json",
			"Content-Type" : "application/json"
		},
		success : function(JSONData, status) {
			// $(#"timelinec").css("height",window.innerHeight-110);
			if (window.sessionStorage) {
		        sessionStorage.setItem('isTimeline', 'false');
				// alert(sessionStorage.getItem('editTreeNo'));
		    }
			$("#timelinec .keywordBox").remove();
			$(".timeLineBtnBox").remove();
			$(".ui-effects-wrapper").remove();
			$("#timeLineTitleBox").hide();
			var searchList = JSONData.list;
			$("#keywordTop").after(setSearchList(JSONData));
			$("#keywordBtnTop").after(setSearchBtnList(JSONData));
			console.log(setSearchBtnList(JSONData));
			console.log($("#keywordBtnTop").parent().html());
			$("#searchBox").show("slide",{
				direction : "up",
				duration : 600
			});
			var showKeyword = $("#timelinec .keywordBox");
			for(var i = 0 ; i < showKeyword.length; i++){
				$(showKeyword[i]).delay(200*i+200).show("slide",{
					direction : "right",
					duration : 500,
					complete : function(){
						setBtnVisible();
					}
				});
			}
			$(".timeLineAddButton").click(function() {
				console.log($($(this).parent()).find("input[name='key']").val());
				addKeyword($($(this).parent()).find("input[name='key']").val());
			});
			$(".timeLineCopyButton").click(function() {
				console.log($($(this).parent()).find("input[name='key']").val());
				changeKeyword($($(this).parent()).find("input[name='key']").val());
			});
		}
	})
}

function setListTimeKeyword() {
	// circleLabel = JSON.parse(data);
	$("#search").val("");
	jQuery.ajax({
		url : "/keyword/listTimeLineKeyword",
		method : "GET",
		dataType : "json",
		headers : {
			"Accept" : "application/json",
			"Content-Type" : "application/json"
		},
		success : function(JSONData, status) {
			if (window.sessionStorage) {
		        sessionStorage.setItem('isTimeline', 'true');
				// alert(sessionStorage.getItem('editTreeNo'));
		    }
			console.log(JSONData);
			$("#timelinec .keywordBox").remove();
			$(".timeLineBtnBox").remove();
			$(".ui-effects-wrapper").remove();
			$("#searchBox").hide();
			$("#keywordTop").after(setSearchList(JSONData));
			$("#keywordBtnTop").after(setSearchBtnList(JSONData));
			$("#timeLineTitleBox").show("slide",{
				direction : "up",
				duration : 500
			});
			var showKeyword = $("#timelinec .keywordBox");
			for(var i = 0 ; i < showKeyword.length; i++){
				$(showKeyword[i]).delay(200*i+200).show("slide",{
					direction : "right",
					duration : 500
				});
			}
			$(".timeLineAddButton").click(function() {
				console.log($($(this).parent()).find("input[name='key']").val());
				addKeyword($($(this).parent()).find("input[name='key']").val());
			});
			$(".timeLineCopyButton").click(function() {
				console.log($($(this).parent()).find("input[name='key']").val());
				changeKeyword($($(this).parent()).find("input[name='key']").val());
			});
		}
	})
}
//트리 리스트 불러오기
$(function() {
	//alert("리스트");	
		$.ajax({
			url : '/tree/listTree',
			type : 'GET',
			dataType : 'json',
			ContentType : "application/json",
			success : function(list) {
				//console.log(list);
				$.ajax({
					url : "resources/hbs/treeList2.hbs",
					success : function(data) {
						var source = data;
						//console.log("gggggg ::"+source)
						var template = Handlebars.compile(source);

						var tr = template(list);
						$(".col-xs-1").remove();
						$(".col-xs-7").remove();
						$(tr).appendTo(".templist");
						
						$("h5 .editTitle").click(function(){
							var temp=$(".item.active").find('input[name=treeNo]').val();
							updateTitle(temp);
						})		

				/*해당트리 펼쳐짐*/
						$(".item h5").dblclick(function(){
							var temp=$(".item.active").find('input[name=treeNo]').val();
							alert(temp);
							$("#myDiagram").remove();
							$("#timeline").before('<div id="myDiagram" style="position: relative; background: #E4E4E4; float: left; width: 100%; height: 100%"></div>');
							goImpl(temp);
							
							}); 
					}
				});

			}

		}); 

/*트리추가*/	
	$(".col-xs-2").click(function(){
		var data=$(".item.active")
		//console.log(data)
		$.ajax({
			url : "/tree/addTree",
				method : "POST",
				data:JSON.stringify({
					userNo:data.find('input[name=userNo]').val()
				}),
				dataType : "json",
				contentType : "application/json",
				success : function(JSONData, status) {
					alert(status);
					
					var temp=JSONData.tree.treeNo;
					console.log(temp);
					$("#myDiagram").remove();
					$("#timeline").before('<div id="myDiagram" style="position: relative; background: #E4E4E4; float: left; width: 100%; height: 100%"></div>');
					goImpl(temp);
				}
		 
			 });
	})	

});


function updateTitle(temp) {
	/*트리타이틀 수정*/
	       var id=$(".item.active").find('h5').text();
	       alert(id);
	       
	      $('h5').remove();
	      $(".item.active").append('<input type="text" id="updateTitle" value="'+id+'">');

	      var data=$(".item.active");
	      $("#updateTitle").keydown(function(e){
			if (e.keyCode == 13) { 
			 $.ajax({
				url : "/tree/updateTitle",
				method : "POST",
				data:JSON.stringify({
					treeNo:data.find('input[name=treeNo]').val(),
					title:$("#updateTitle").val(),
					userNo:data.find('input[name=userNo]').val()
					}),
				dataType : "json",
				contentType : "application/json",
				success : function(JSONData, status) {
					alert(status);
					$("#carousel-example-generic").remove();
					List();
						}
					});
				}
	      })
}

function List() {
	$.ajax({
		url : '/tree/listTree',
		method : "GET",
		dataType : 'json',
		ContentType : "application/json",
		success : function(list) {
			$.ajax({
				url : "resources/hbs/treeList2.hbs",
				success : function(data) {
				var source = data;
				// console.log("gggggg ::"+source)
				var template = Handlebars.compile(source);
				var tr = template(list);
					$(".col-xs-1").remove();
					$(".col-xs-7").remove();
					$(tr).appendTo(".templist");
				/* 해당트리 펼쳐짐 */
					$(".item h5").dblclick(function() {
						var temp = $(".item.active").find('input[name=treeNo]').val();
						alert(temp);
						$("#myDiagram").remove();
						$("#timeline").before('<div id="myDiagram" style="position: relative; background: #E4E4E4; float: left; width: 100%; height: 100%"></div>');
						goImpl(temp);
						});
				}
			})
		}
	})
}


function openDialog() {
	$("#dialog").show().dialog();
}

function autocom() {
	var availableTags;
	jQuery.ajax({
		url : "/keyword/autoComplete",
		method : "GET",
		dataType : "json",
		headers : {
			"Accept" : "application/json",
			"Content-Type" : "application/json"
		},
		success : function(JSONData, status) {
			availableTags = JSONData.autoComplete;
			console.log(availableTags);
			$("#inputText").autocomplete({
				source : availableTags,
				select : function( event, ui ) {
					setListSearchKeyword(ui.item.value);
					$(this).blur();
				}
			});
			$("#search").autocomplete({
				source : availableTags,
				select: function( event, ui ) {
					setListSearchKeyword(ui.item.value);
					$(this).blur();
				}
			});
			$("#search").keydown(function(event) {
				if (event.which == 13) {
					setListSearchKeyword($("#search").val());
					$(this).blur();
				}
			});
		}
	})
}

function inputText() {
	var a = "<input type='text' class='form-control'>";
	return $(a);
}
