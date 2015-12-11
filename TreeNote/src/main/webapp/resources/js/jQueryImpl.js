
$(function() {
	if (window.sessionStorage) {
        //sessionStorage.setItem('editTreeNo', data.user.userNo);
        //var position = sessionStorage.getItem('저장된 이름');
		//alert(sessionStorage.getItem('editTreeNo'));
    }

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
	        	$("#timelinec .keyword").remove();
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
	// circleLabel = JSON.parse(data);
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
			//$(#"timelinec").css("height",window.innerHeight-110);
			if (window.sessionStorage) {
		        sessionStorage.setItem('isTimeline', 'false');
				//alert(sessionStorage.getItem('editTreeNo'));
		    }
			$("#timelinec .keyword").remove();
			$("#timeLineTitleBox").hide();
			var searchList = JSONData.list;
			$("#keywordTop").after(setSearchList(JSONData));
			$("#searchBox").show("slide",{
				direction : "up",
				duration : 600
			});
			var showKeyword = $("#timelinec .keywordBox");
			for(var i = 0 ; i < showKeyword.length; i++){
				$(showKeyword[i]).delay(200*i+200).show("slide",{
					direction : "right",
					duration : 500
				});
			}
			/*$(".btn_add").click(function() {
				console.log($($(this).parent()).find("input").val());
				addKeyword($($(this).parent()).find("input").val());
			});
			$(".btn_copy").click(function() {
				console.log($($(this).parent()).find("input").val());
				changeKeyword($($(this).parent()).find("input").val());
			});
			setBtnVisible();*/
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
				//alert(sessionStorage.getItem('editTreeNo'));
		    }
			console.log(JSONData);
			$("#timelinec .keyword").remove();
			$("#searchBox").hide();
			$("#keywordTop").after(setSearchList(JSONData));
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
			/*$(".btn_add").click(function() {
				console.log($($(this).parent()).find("input").val());
				addKeyword($($(this).parent()).find("input").val());
			});
			$(".btn_copy").click(function() {
				console.log($($(this).parent()).find("input").val());
				changeKeyword($($(this).parent()).find("input").val());
			});*/
		}
	})
}

//트리 리스트 불러오기
$(function() {
		 $("#treeTitle").on("dblclick" , function() {
			 alert(  "수정 Start" );
			 var temp = $(this).text();
			 temp = temp.substring(6);
			 alert("왜 안되"+temp)
			 $('#treeTitle').remove();
			 $('#treeTitle').after('<input type="text" id="updateTitle" value="'+temp+'">');
			
		 $("#updateTitle").keyup(function(e){
		    if (e.keyCode == 13) { 
		    	$.ajax({
					url : "/tree/updateTitle",
					method : "POST",
					data:JSON.stringify({
						treeNo:$("#treeNo").val(),
						title:$("#updateTitle").val(),
						userNo:$("#userNo").val()
					}),
					dataType : "json",
					contentType : "application/json",
					success : function(JSONData, status) {
						alert(status);
						alert("JSONData : \n" + JSONData);
						$("#treeTitle").append(treeTitle);
						//alert("title :" + treeTitle);
						var count=0;
						var value=[];
						
						for(var i in JSONData.Tree){
							count++;
							//value=JSONData.Tree[i].title;
							value="<li>"+JSONData.Tree[i].title+"</li>";
							//alert("ttteesstt::: "+value);
							$("#list").append(value);
						}
						$("#count").append(count);
					}
					
				});
			}
			}); 
			
		 }); 
		 
		$("#userNo").on("click", function(e) {
			//if(e.keyCode==13 ){
			//var userNo = $("#userNo").val();
			$("#treeTitle").empty();
			$("#list").empty();
			$("#count").empty(); 
			
			alert("userNo :" + userNo);
			$.ajax({
				url : "/tree/listTree/"+1000000,
				method : "GET",
				dataType : "json",
				headers : {
					"Accept" : "application/json",
					"Content-Type" : "application/json"
				},
				success : function(JSONData, status) {
					//alert(status);
					//alert("JSONData : \n" + JSONData);
					var treeTitle=JSONData.Tree[0].title;
					$("#treeTitle").append(treeTitle+'<img src="resources/img/btn_add.png" id="addTitle"/>');
					//alert("title :" + treeTitle);
					var count=0;
					var value=[];
					
					for(var i in JSONData.Tree){
						count++;
						//value=JSONData.Tree[i].title;
						value="<li id='treeList"+i+"'  value='JSONData.Tree[i].title' class='listData'>"+JSONData.Tree[i].title+
								"<input type='hidden' id='treeNo' value="+JSONData.Tree[i].treeNo+">"+"</li>";
						//alert("ttteesstt::: "+value);
						$("#list").append(value);
						
					$('#treeList'+i+'').on("click" , function() {
						 var temp = $(this).text();
						 alert("test ::"+temp);
						 $("#treeTitle").text(temp).append('<img src="resources/img/btn_add.png" id="addTitle"/>'); 
					});
					}
					$("#count").append("TreeList ::"+count);
				}
			});
			
			
		});
	});

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
