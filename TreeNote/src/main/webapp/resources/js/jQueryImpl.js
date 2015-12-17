
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
	$("#btn_logout").on('click', function(){
		$.ajax({
			url:"/user/logout",
			
			complete : function(){
				self.location="../index.html"
			}
		})
	})
	
	$("#btn_timeline").click(function(){
		if (sessionStorage.getItem('nowTimeline') == 'timeline') {
	        sessionStorage.setItem('nowTimeline', '');
			//setListTimeKeyword();
			$("#timeline").hide();
			$("#timeLineTitleBox").hide();
			$(".keywordBox").remove();
			$(".timeLineBtnBox").remove();
			$(".ui-effects-wrapper").remove();
			
	    }else if(sessionStorage.getItem('nowTimeline') == 'search'){
	    	$("#timeline").show();
	    	$(".keywordBox").remove();
			$(".timeLineBtnBox").remove();
			$(".ui-effects-wrapper").remove();
	    	setListTimeKeyword();
	    }else{
	    	$("#timeline").show();
	    	$(".keywordBox").remove();
			$(".timeLineBtnBox").remove();
			$(".ui-effects-wrapper").remove();
	    	setListTimeKeyword();
	    }
	});
	$("#btn_search").click(function(){
		if (sessionStorage.getItem('nowTimeline') == 'search') {
			sessionStorage.setItem('nowTimeline', '');
			$("#timeline").hide();
			$("#searchBox").hide();
			$(".keywordBox").remove();
			$(".timeLineBtnBox").remove();
			$(".ui-effects-wrapper").remove();
		}else if(sessionStorage.getItem('nowTimeline') == 'timeline'){
			$("#timeline").show();
			$(".keywordBox").remove();
			$(".timeLineBtnBox").remove();
			$(".ui-effects-wrapper").remove();
			setListSearchKeyword();
		}else{
			$("#timeline").show();
			$(".keywordBox").remove();
			$(".timeLineBtnBox").remove();
			$(".ui-effects-wrapper").remove();
			setListSearchKeyword();
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
		        sessionStorage.setItem('nowTimeline', 'search');
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
			$(".childKeyword").click(function(){
				if($($(this).parent()).find("input[name='keyword']").val() == "없음"){
					showContent($($(this).parent()).find("input[name='key']").val(), $($(this).parent()).find("input[name='keyword']").val());
				}
				else if($($(this).parent()).find("input[name='keyword']").val() == "..."){
					showContent($($(this).parent()).find("input[name='key']").val(), $($(this).parent()).find("input[name='keyword']").val());
				}
				else{
					showContent($($(this).parent()).find("input[name='key']").val(), $($(this).parent()).find("input[name='keyword']").val());
				}
			});
			$(".parentKeyword").click(function(){
				showContent($($(this).parent()).find("input[name='key']").val(), $($(this).parent()).find("input[name='keyword']").val());
			});
			$(".keywordName").click(function(){
				showContent($($(this).parent()).find("input[name='key']").val(), $($(this).parent()).find("input[name='keyword']").val());
			});
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
		        sessionStorage.setItem('nowTimeline', 'timeline');
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
			$(".childKeyword").click(function(){
				if($($(this).parent()).find("input[name='keyword']").val() == "없음"){
					showContent($($(this).parent()).find("input[name='key']").val(), $($(this).parent()).find("input[name='keyword']").val());
				}
				else if($($(this).parent()).find("input[name='keyword']").val() == "..."){
					showContent($($(this).parent()).find("input[name='key']").val(), $($(this).parent()).find("input[name='keyword']").val());
				}
				else{
					showContent($($(this).parent()).find("input[name='key']").val(), $($(this).parent()).find("input[name='keyword']").val());
				}
			});
			$(".parentKeyword").click(function(){
				showContent($($(this).parent()).find("input[name='key']").val(), $($(this).parent()).find("input[name='keyword']").val());
			});
			$(".keywordName").click(function(){
				showContent($($(this).parent()).find("input[name='key']").val(), $($(this).parent()).find("input[name='keyword']").val());
			});
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

function showContent(keywordNo, keyword){
	console.log("key : "+keywordNo+"keyword : "+keyword)
	sessionStorage.setItem("keywordNo",keywordNo);
	sessionStorage.setItem("keyword",keyword);					

	$.getJSON("/content/getContent/" +keywordNo, function(data) {
		console.log("000000000 "+data.content);
		
	if(data.content==null){
		jQuery("#content").show("fade",300).find("iframe").attr("src","../../../contents/contents.html");
		console.log("1111111 "+data.content);
	}
	else{
		jQuery("#content").show("fade",300).find("iframe").attr("src","../../../contents/get.html");
		console.log("22222222 "+data.content);
	}						
	});	
}

function inputText() {
	var a = "<input type='text' class='form-control'>";
	return $(a);
} 

/*댓글 기능 함수 시작*/
var page = {
		addReplyCounting : 0,
		pageSize : 5,
		currentPage : 1,
		startRowNum : 0,
		endRowNum : 0,
		replyValueNo : 1000000
};

function reply(contentNo) {		
	page.replyValueNo = contentNo;
	page.startRowNum=(page.currentPage-1)*page.pageSize+1;
	page.endRowNum=page.currentPage*page.pageSize;
	
	listReply(page);
	addReply();	
	console.log("시작");
	$('.totalReplyCount').click(function(e) {
		page.currentPage++
		if(page.addReplyCounting !=0){			
			page.startRowNum=((page.currentPage-1)*page.pageSize+1)+page.addReplyCounting;
			page.endRowNum=(page.currentPage*page.pageSize)+page.addReplyCounting;
		}
		else{
			page.startRowNum=(page.currentPage-1)*page.pageSize+1;  
			page.endRowNum=page.currentPage*page.pageSize;
		}
		listReply(page);
	});
	$.getJSON("/reply/getTotalReply/"+page.replyValueNo, function(data){ 
		//console.log(data.totalReplyCount);
		$(".totalReplyCount").text(data.totalReplyCount+"개");
	});
}		
function DeleteReply(){
	$("#boardReply").children().remove();
	$(".totalReplyCount").text("");
	console.log("aa");
}
function listReply(page){
	$(function() {			
		$.ajax( 
				{
					url : "/reply/listReply/" ,
					method : "POST" ,
					dataType : "json" ,
					data: JSON.stringify({
						replyValueNo : page.replyValueNo,
						pageSize : page.pageSize,
						currentPage : page.currentPage,
						startRowNum : page.startRowNum,
						endRowNum : page.endRowNum							
					}),
					headers : {
						"Accept" : "application/json",
						"Content-Type" : "application/json"
					},							
					success : function(JSONData , status) {										
						$.get("../resources/hbs/reply/replyListTemplate.hbs", function(data){     
							var tr;	
							var template = Handlebars.compile(data);									
	
							var list = JSONData.replyList; 
							
							console.log(list);
							for(var i=0; i<list.length; i++){				
								JSONData.replyList[i].regTime = compareDate(JSONData.replyList[i].regTime);
								JSONData.replyList[i].reply = JSONData.replyList[i].reply.replace(" ","&nbsp;");
								var sizeList = JSONData.replyList[i].replyOfReply;
								if(sizeList != null){
									for(var j=0; j<sizeList.length; j++){
										JSONData.replyList[i].replyOfReply[j].regTime = 
											compareDate(JSONData.replyList[i].replyOfReply[j].regTime);
										JSONData.replyList[i].replyOfReply[j].reply = 
											JSONData.replyList[i].replyOfReply[j].reply.replace(" ","&nbsp;");
									}
								}									
							}	
							if(list.length>0){
								tr = template(JSONData);
								$("#boardReply").append(tr);  
								replyfunction();
								addReplyOfReply();
							}else{
								//alert("댓글이 없습니다.");
							} 
							
						});		
					}				
			});
	});	
}

function addReply(){
	$('input:text[name=reply]').keyup(function(e) {
		if (e.keyCode == 13) {
			var reply = $("input:text[name=reply]").val();
			var parentReplyNo = 0;
			//$("input:hidden[name=contentNo]").val();
			var contentNo = page.replyValueNo;
			//var userNo = $("input:hidden[name=userNo]").val();		
			var userNo = 1000003;
			
			$.ajax( 
					{
						url : "/reply/addReply/" ,
						method : "POST" ,
						dataType : "json" ,
						data: JSON.stringify({
							reply : reply,
							parentReplyNo : parentReplyNo,
							contentNo : contentNo,
							userNo : userNo
						}),
						headers : {
							"Accept" : "application/json",
							"Content-Type" : "application/json"
						},							
						success : function(JSONData , status) {
							JSONData.reply.regTime = compareDate(JSONData.reply.regTime);
							console.log(JSONData.reply);
							JSONData.reply.reply.replace(" ","&nbsp;");
							page.addReplyCounting++;
							
							$.get("../resources/hbs/reply/replyTemplate.hbs", function(data){     
								var tr;	
								var template = Handlebars.compile(data);	
								
								tr = template(JSONData.reply);
//								if($("#boardReply").find(".media").length == 1){
//									$("#boardReply").next().before(tr); 
//								}
								$("#boardReply").find(".media").first().before(tr); 
								replyfunction();
								$("#reply").val("");	
								addReplyOfReply()
							});	
						}							
				});			
		}if (e.keyCode == 27) {
			$("#reply").val("");
		}		
	});			
}

function addReplyOfReply(){
	$('.comentOfcomentinput').keyup(function(e) {
		var it = $(this);
		//console.log("AA");
		if (e.keyCode == 13) {
			var reply = it.val();
			//console.log(reply);
			var parentReplyNo = it.parent().find(".replyNo").val();
			var contentNo = 1000011;
			var userNo = 1000003;
			
			console.log(parentReplyNo);
			
			$.ajax( 
					{
						url : "/reply/addReply/" ,
						method : "POST" ,
						dataType : "json" ,
						data: JSON.stringify({
							reply : reply,
							parentReplyNo : parentReplyNo,
							contentNo : contentNo,
							userNo : userNo
						}),
						headers : {
							"Accept" : "application/json",
							"Content-Type" : "application/json"
						},							
						success : function(JSONData , status) {
							console.log("JSONData :: "+JSONData);
							console.log(JSONData.reply);
							JSONData.reply.regTime = compareDate(JSONData.reply.regTime);
							
							JSONData.reply.reply.replace(" ","&nbsp;");
							page.addReplyCounting++;
							
							$.get("../resources/hbs/reply/replyOfReplyTemplate.hbs", function(data){     
								var tr;	
								var template = Handlebars.compile(data);	
								
								tr = template(JSONData.reply);
								it.parents(".comentOfcomentinsert").find(".media").first().before(tr); 
								replyfunction();
								it.parents(".media").find(".comentOfcomentinsert").show();
								it.val("");		
								var co = $.trim(it.parents(".media").find(".comentPlusRe").text().replace("개"," "))
								if((co==0) || (co=="답글달기")){
									co=1;
								}else{
									co++;
								}
								it.parents(".media").find(".comentPlusRe").text(co+" 개");
							});	
						}							
				});			
		}
	});			
}
 

function replyfunction(){
	$(function(){
		$(".updateComment").hide();	
		$(".updateCommentOfComment").hide();
		$(".comentOfcomentinsert").hide();
//		$(".delete").hide();
//		$(".update").hide();
		
		$('.Comment').mouseenter(function(){
			$(this).css("white-space","initial"); 
		}).mouseleave(function() {
			$(this).css("white-space","nowrap"); 
		});
		
//		$('.delete').mouseenter(function(){
//			$(this).show(); 
//		}).mouseleave(function() {
//			//$(this).hide();
//		});
//		
//		$('.update').mouseenter(function(){
//			$(this).show(); 
//		}).mouseleave(function() {
//			//$(this).hide();
//		});
					
	});
	
	$('.delete').click(function(){
		var no= $(this).parent().find(".replyNo").val();
		console.log(no);
		var thi =$(this);
		$.getJSON('/reply/removeReply/'+no, function(){	
			thi.parents(".media").remove();	
		});
		$.getJSON("/reply/getTotalReply/"+page.replyValueNo, function(data){ 
			//console.log(data.totalReplyCount);
			$(".totalReplyCount").text(data.totalReplyCount+" 개")
		});
	});
	
	$('.deleteReplyOfReply').click(function(){
		var no= $(this).parent().find(".replyNo").val();
		console.log(no);
		var thi =$(this); 
		var it = thi.parents(".comentOfcomentinsert").prev(".media-body").find(".comentplus");
		var parentReplyNo = it.val();
		$.getJSON('/reply/removeReply/'+no, function(){	
			thi.parents(".media-m").remove();	
		});
		
		$.getJSON("/reply/getTotalReplyOfReply/"+parentReplyNo, function(data){ 
			//console.log(data.totalReplyCount);
			it.parent().text(data.totalReplyOfReplyCount+" 개");
		});
	});
	
	$('.update').click(function(){
		var no= $(this).parent().find(".replyNo").val();	
		console.log("no::"+no)
		var reply = $(this).parents(".media-body").find(".CommentFont").text();					
		console.log(reply);
		$(this).parents(".media-body").find(".updateComment").show();
		$(this).parents(".media-body").find(".updateComment").val(reply);
		$(this).parents(".media-body").find(".CommentFont").hide();
		$(this).parents(".media-body").find(".list-inline").hide();
		$(this).parents(".media-body").find(".updateComment").keyup(function(e) {
			if (e.keyCode == 27) {
				$(this).parents(".media-body").find(".updateComment").hide();
				$(this).parents(".media-body").find(".CommentFont").show();
				$(this).parents(".media-body").find(".list-inline").show();
			}
			if (e.keyCode == 13) {							
				var reply = $(this).parents(".media-body").find(".updateComment").val();
				var replyNo = no;
				console.log(reply);
				console.log(replyNo);
				var comment;
				var addressUpdate=$(this);
				$(this).parents(".media-body").find(".CommentFont").show();
				$(this).parents(".media-body").find(".list-inline").show();
				$(this).parents(".media-body").find(".updateComment").hide();							
				$.ajax( 
						{
							url : "/reply/updateReply/" ,
							method : "POST" ,
							dataType : "json" ,
							data: JSON.stringify({
								reply : reply,											
								replyNo : replyNo
							}),
							headers : {
								"Accept" : "application/json",
								"Content-Type" : "application/json"
							},							
							success : function(JSONData , status) {											
								addressUpdate.parent().find(".CommentFont").text(JSONData.reply);
							}				
				});		 
			}						
		});						
	});	
	
	$('.updateReplyOfReply').click(function(){
		var no= $(this).parent().find(".replyNo").val();	
		console.log("no::"+no)
		var reply = $(this).parents(".media-m").find(".replyofreply").text();					
		console.log(reply);
		$(this).parents(".media-m").find(".updateCommentOfComment").show();
		$(this).parents(".media-m").find(".updateCommentOfComment").val(reply);
		$(this).parents(".media-m").find(".Comment").hide();
		$(this).parents(".media-m").find(".list-inline").hide();
		$(this).parents(".media-m").find(".updateCommentOfComment").keyup(function(e) {
			if (e.keyCode == 27) {
				$(this).parents(".media-m").find(".updateCommentOfComment").hide();
				$(this).parents(".media-m").find(".Comment").show();
				$(this).parents(".media-m").find(".list-inline").show();
			}
			if (e.keyCode == 13) {							
				var reply = $(this).parents(".media-m").find(".updateCommentOfComment").val();
				var replyNo = no;
				console.log(reply);
				console.log(replyNo);
				var comment;
				var addressUpdate=$(this).parents(".media-m");
				$(this).parents(".media-m").find(".Comment").show();
				$(this).parents(".media-m").find(".list-inline").show();
				$(this).parents(".media-m").find(".updateCommentOfComment").hide();							
				$.ajax( 
						{
							url : "/reply/updateReply/" ,
							method : "POST" ,
							dataType : "json" ,
							data: JSON.stringify({
								reply : reply,											
								replyNo : replyNo
							}),
							headers : {
								"Accept" : "application/json",
								"Content-Type" : "application/json"
							},							
							success : function(JSONData , status) {											
								addressUpdate.find(".replyofreply").text(JSONData.reply);
							}				
				});		 
			}						
		});						
	});
	
	$('.comentplus').click(function(){				
		$(this).parents(".media").find(".comentOfcomentinsert").show();		
	});
	$('.comentplus').dblclick(function(){
		$(this).parents(".media").find(".comentOfcomentinsert").hide();
	});
 };
	 


function compareDate(regTime){
	var thisDate = getWorldTime(+9);
	var regDate = new Date();				
	regDate.setTime(regTime);
	
	var time = regDate.toTimeString().substring(0,8);
	var date = regDate.toISOString().substring(0,10);	
	
	var strFromDate = regDate.toISOString().substring(0,10).replace(/-/gi,"") + 
					  regDate.toTimeString().substring(0,8).replace(/:/gi,""); //시작시간						  
	var strToDate   = thisDate.substring(0,10).replace(/-/gi,"") + 
					  thisDate.substring(11,19).replace(/:/gi,"");; //종료시간
	var con;
	  var fromDate = new Date(strFromDate.substring(0,4),
	        strFromDate.substring(4,6)-1,
	        strFromDate.substring(6,8),
	        strFromDate.substring(8,10),
	        strFromDate.substring(10,12)
	       );
	       
	  var toDate = new Date(strToDate.substring(0,4),
	        strToDate.substring(4,6)-1,
	        strToDate.substring(6,8),
	        strToDate.substring(8,10),
	        strToDate.substring(10,12)
	       );
	    
	  daysAfter = (toDate.getTime() - fromDate.getTime()) / (1000*60*60*24);
	  hourAfter = (toDate.getTime() - fromDate.getTime()) / (1000*60*60);
	  minAfter = (toDate.getTime() - fromDate.getTime()) / (1000*60);
	  secAfter = (toDate.getTime() - fromDate.getTime()) / (1000);
	  
	  daysAfter = Math.round(daysAfter);
	  hourAfter = Math.round(hourAfter);
	  minAfter = Math.round(minAfter);
	  secAfter = Math.round(secAfter);	 
	 
	if(daysAfter==0){
		if(hourAfter==0){
			if(minAfter==0){
				if(secAfter==0){
					con = "지금"
				}
				if(secAfter!=0){
					con = secAfter+"초 전에" 
				}
			}
			if(minAfter!=0){
				con = minAfter+"분 전에"
			}
		}
		if(hourAfter!=0){
			con =hourAfter+"시간 전에"
		}
	}
	if(daysAfter!=0){
		con =daysAfter+"일 전에"
	}		 
	return con;
}

function getWorldTime(tzOffset) { // 24시간제
	  var now = new Date();
	  var tz = now.getTime() + (now.getTimezoneOffset() * 60000) + (tzOffset * 3600000);
	  now.setTime(tz);


	  var s =
	    leadingZeros(now.getFullYear(), 4) + '-' +
	    leadingZeros(now.getMonth() + 1, 2) + '-' +
	    leadingZeros(now.getDate(), 2) + ' ' +

	    leadingZeros(now.getHours(), 2) + ':' +
	    leadingZeros(now.getMinutes(), 2) + ':' +
	    leadingZeros(now.getSeconds(), 2);

	  return s;
	}


	function leadingZeros(n, digits) {
	  var zero = '';
	  n = n.toString();

	  if (n.length < digits) {
	    for (i = 0; i < digits - n.length; i++)
	      zero += '0';
	  }
	  return zero + n;
	}
/*댓글 기능 함수 끝*/
