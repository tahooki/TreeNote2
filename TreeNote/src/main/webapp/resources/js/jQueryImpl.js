
function getUser(userNo){
	if($("#mypage").css("display") == "none"){
		$("#mypage").toggle("fade",500);
	}
	$("#mypage header").remove();
	$("#mypage section").remove();
	friendProfilCall(userNo)
	/*추가*/
	
}


window.onload = function(){
	$("#treeContainer").click(function(){
		console.log($("#mypage").css("display"));
		if($("#mypage").css("display") != "none"){
			$("#mypage").hide("fade",500);
		}
	})

	$("#btn_content").click(function() {
		$("#content").toggle("drop");
	});
	$("#btn_mypage").click(function() {
		if($("#mypage").css("display") == "none"){
			$("#mypage").toggle("fade",500);
		}
		$("#mypage header").remove();
		$("#mypage section").remove();
		profilCall();
		
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
	$("#btn_searchActive").click(function(){
		setListSearchKeyword($("#search").val());
	});
	$("#btn_logout").on('click', function(){
		$.ajax({
			url:"/user/logout",
			
			complete : function(){
				self.location="../index.html"
			}
		})
	})
	
	$("#btn_treeadd").click(function(){
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
					console.log("addTree : "+status);
					
					var temp=JSONData.tree.treeNo;
					console.log(temp);
					$("#myDiagram").remove();
					$("#timeline").before('<div id="myDiagram" style="position: relative; background: #E4E4E4; float: left; width: 100%; height: 100%"></div>');
					goImpl(temp);
					setTimeout("treeList()",1000);
				}
		 
			 });
	})	
	
	$("#btn_showtimeline").click(function(){
		$("#timeline").toggle("fade",300);
	});
	
	$("#btn_timeline").click(function(){
		if (sessionStorage.getItem('nowTimeline') != 'timeline'){
	    	setListTimeKeyword();
	    }
		return false;
	}).hover(function(){
		$(this).css("background","rgba(192,57,43,.7)");
	},function(){
		if (sessionStorage.getItem('nowTimeline') != 'timeline'){
			$(this).css("background","rgba(192,57,43,.2)");
		}
	});

	$("#btn_search").click(function(){
		if (sessionStorage.getItem('nowTimeline') != 'search') {
			setListSearchKeyword();
		}
		return false;
	}).hover(function(){
		$(this).css("background","rgba(40,128,185,.7)");
	},function(){
		if (sessionStorage.getItem('nowTimeline') != 'search'){
			$(this).css("background","rgba(40,128,185,.2)");
		}
	});

	$("#btn_clipboard").click(function(){
		if (sessionStorage.getItem('nowTimeline') != 'clipboard') {
			setListClipKeyword();
		}
		return false;
	}).hover(function(){
		$(this).css("background","rgba(230,126,34,.7)");
	},function(){
		if (sessionStorage.getItem('nowTimeline') != 'clipboard'){
			$(this).css("background","rgba(230,126,34,.2)");
		}
	});

	var clipBoardList = [];
	console.log(JSON.stringify(clipBoardList));
	sessionStorage.setItem('clipBoardList', JSON.stringify(clipBoardList));
	
	if (window.sessionStorage) {
		sessionStorage.setItem('isMyTree',true);
	}
	
	treeList();
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
	$("#timeline").show("fade",300);
	console.log(keyword);
	$("#btn_timeline").css("background","rgba(192,57,43,.2)");
	$("#btn_search").css("background","rgba(40,128,185,.7)");
	$("#btn_clipboard").css("background","rgba(230,126,34,.2)");
	
	$("#timeline").unbind();
	$("#search").val(keyword);
	$("#timeline .keywordBox").remove();
	$("#timeline .timeLineBtnBox").remove();
	$("#timeline .ui-effects-wrapper").remove();
	$("#timeLineTitleBox").hide();
	$("#clipBoardTitleBox").hide();
	
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
			
			
			var searchList = JSONData.list;
			$("#keywordTop").after(setSearchList(JSONData));
			$("#keywordBtnTop").after(setSearchBtnList(JSONData));
			console.log(setSearchBtnList(JSONData));
			console.log($("#keywordBtnTop").parent().html());
			$("#searchBox").show("fade",500);
			var showKeyword = $("#timelinec .keywordBox");
			for(var i = 0 ; i < showKeyword.length; i++){
				$(showKeyword[i]).delay(200*i+200).show("slide",{
					direction : "right",
					duration : 500
				});
			}
			setTimelineEvent();
			autoSearchListKeyword(keyword);
			setBtnVisible();
		}
	})
}

function autoSearchListKeyword(keyword){
	$("#timeline").scroll(function(){
		var temp;
		if($("#timeline").prop("scrollHeight")< $(document).height()){
			temp=$("#timeline").prop("scrollHeight");
		}else{
			temp=$(document).height();
		}
		if ($("#timeline").prop("scrollHeight") < $("#timeline").scrollTop()+temp){
			var count=$(".keywordBox").length;
			$.ajax({
				type:'post',
				url:"/keyword/listSearchKeyword2/"+count,
				dataType:"json",
				data:JSON.stringify({
					keyword:keyword
				}),
				headers : {
					"Accept" : "application/json",
					"Content-Type" : "application/json"
				},
				success : function(JSONData, status) {
					console.log(JSONData);
					
					$(setSearchList(JSONData)).appendTo("#timelinec");
					$(setSearchBtnList(JSONData)).appendTo("#timelineb");
					var showKeyword = $("#timelinec .keywordBox");
					for(var i = 0 ; i < showKeyword.length; i++){
						$(showKeyword[i]).delay(200*i+200).show("slide",{
							direction : "right",
							duration : 500
						});
					}
					setTimelineEvent();
					setBtnVisible();
				}
			})
		}
	})
}

function setListTimeKeyword() {
	// circleLabel = JSON.parse(data);
	$("#timeline").show("fade",300);
	
	$("#btn_timeline").css("background","rgba(192,57,43,.7)");
	$("#btn_search").css("background","rgba(40,128,185,.2)");
	$("#btn_clipboard").css("background","rgba(230,126,34,.2)");
	
	$("#timeline").unbind();
	$("#search").val("");
	
	$("#timeline .keywordBox").remove();
	$("#timeline .timeLineBtnBox").remove();
	$("#timeline .ui-effects-wrapper").remove();
	$("#searchBox").hide();
	$("#clipBoardTitleBox").hide();
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
			
			$("#keywordTop").after(setSearchList(JSONData));
			$("#keywordBtnTop").after(setSearchBtnList(JSONData));
			$("#timeLineTitleBox").show("fade",500);
			var showKeyword = $("#timelinec .keywordBox");
			for(var i = 0 ; i < showKeyword.length; i++){
				$(showKeyword[i]).delay(200*i+200).show("slide",{
					direction : "right",
					duration : 500
				});
			}
			setTimelineEvent();
			autoListKeyword();
			setBtnVisible();
		}
		
	})
}
function autoListKeyword(){
	$("#timeline").scroll(function(){
		var temp;
		if($("#timeline").prop("scrollHeight")< $(document).height()){
			temp=$("#timeline").prop("scrollHeight");
		}else{
			temp=$(document).height();
		}
		if ($("#timeline").prop("scrollHeight") < $("#timeline").scrollTop()+temp){
			var count=$(".keywordBox").length;
			//alert("ggg")
			$.ajax({
				url:"/keyword/listTimeLineKeyword2?count="+count,
				dataType:"json",
				headers : {
					"Accept" : "application/json",
					"Content-Type" : "application/json"
				},
				success :  function(JSONData, status) {
					console.log(JSONData);
					
					$(setSearchList(JSONData)).appendTo("#timelinec");
					$(setSearchBtnList(JSONData)).appendTo("#timelineb");
					var showKeyword = $("#timelinec .keywordBox");
					for(var i = 0 ; i < showKeyword.length; i++){
						$(showKeyword[i]).delay(200*i+200).show("slide",{
							direction : "right",
							duration : 500
						});
					}
					setTimelineEvent();
					setBtnVisible();
				}
			})
		}
	})
}

function setListClipKeyword() {
	// circleLabel = JSON.parse(data);
	$("#timeline").show("fade",300);
	
	$("#btn_timeline").css("background","rgba(192,57,43,.2)");
	$("#btn_search").css("background","rgba(40,128,185,.2)");
	$("#btn_clipboard").css("background","rgba(230,126,34,.7)");
	
	$("#timeline").unbind();
	//console.log(JSONData);

	/*if (window.sessionStorage) {
		var clipBoardList = JSON.parse(sessionStorage.getItem('clipBoardList'));
		clipBoardList.push(obj.part.data);8
		sessionStorage.setItem('clipBoardList', JSON.stringify(clipBoardList));
        // sessionStorage.setItem('editTreeNo', data.user.userNo);
        // var position = sessionStorage.getItem('저장된 이름');
		// alert(sessionStorage.getItem('editTreeNo'));
    }*/
	
	$("#timeline .keywordBox").remove();
	$("#timeline .timeLineBtnBox").remove();
	$("#timeline .ui-effects-wrapper").remove();
	$("#timeLineTitleBox").hide();
	$("#searchBox").hide();
	sessionStorage.setItem('nowTimeline', 'clipboard');
	
	keyList = sessionStorage.getItem('clipBoardList');
	console.log(keyList);
	jQuery.ajax({
		url : "/keyword/listClipBoardKeyword",
		method : "POST",
		dataType : "json",
		data:keyList,
		headers : {
			"Accept" : "application/json",
			"Content-Type" : "application/json"
		},
		success : function(JSONData, status) {
			console.log(JSONData);
			
			$("#keywordTop").after(setSearchList(JSONData));
			$("#keywordBtnTop").after(setSearchBtnList(JSONData));
			$("#clipBoardTitleBox").show("fade",500);
			var showKeyword = $("#timelinec .keywordBox");
			for(var i = 0 ; i < showKeyword.length; i++){
				$(showKeyword[i]).delay(200*i+200).show("slide",{
					direction : "right",
					duration : 500
				});
			}
			setTimelineEvent();
			setBtnVisible();
		}
	})
}

function setTimelineEvent(){
	$(".childKeyword").click(function(){
		if($($(this).parent()).find("input[name='key']").val() == "없음"){
			//showContent($($(this).parent()).find("input[name='key']").val(), $($(this).parent()).find("input[name='keyword']").val());
		}
		else if($($(this).parent()).find("input[name='key']").val() == "..."){
			//showContent($($(this).parent()).find("input[name='key']").val(), $($(this).parent()).find("input[name='keyword']").val());
			//더보게 만들어야하느니
		}
		else{
			showContent($($(this).parent()).find("input[name='key']").val(), $($(this).parent()).find("input[name='keyword']").val());
		}
		return false;
	});
	$(".keywordUser").click(function(){
		getUser($($(this).parent()).find("input[name='userNo']").val());
		return false;
	});
	$(".parentKeyword").click(function(){
		if($($(this).parent()).find("input[name='key']").val() != null){
			showContent($($(this).parent()).find("input[name='key']").val(), $($(this).parent()).find("input[name='keyword']").val());
		}
		return false;
	});
	$(".keyword").click(function(){
		showContent($($(this).parent()).find("input[name='key']").val(), $($(this).parent()).find("input[name='keyword']").val());
		return false;
	});
	$(".timeLineAddButton").click(function() {
		console.log($($(this).parent()).find("input[name='key']").val());
		addKeyword($($(this).parent()).find("input[name='key']").val());
		return false;
	});
	$(".timeLineCopyButton").click(function() {
		console.log($($(this).parent()).find("input[name='key']").val());
		changeKeyword($($(this).parent()).find("input[name='key']").val());
		return false;
	});
}

function updateTitle(temp) {
	/*트리타이틀 수정*/
	       var id=$(".item.active").find('h5').text();
	       //alert(id);
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
					console.log("updateTitle : "+status);
					$("#carousel-example-generic").remove();
					treeList();
						}
					});
				}
			else if (e.keyCode == 27) {
				//esc 누르면 취소
				$("#carousel-example-generic").remove();
				treeList();
			}
	      });
	      $("#updateTitle").focusout(function() {
	    	  //포커스 아웃되면 취소
	    	  $("#carousel-example-generic").remove();
	    	  treeList();
	      });
}

function treeList() {
	$.ajax({
		url : '/tree/listTree',
		type : 'GET',
		dataType : 'json',
		ContentType : "application/json",
		success : function(list) {
			console.log(list);
			$.ajax({
				url : "resources/hbs/treeList2.hbs",
				success : function(data) {
					var source = data;
					console.log("gggggg ::"+source)
					var template = Handlebars.compile(source);

					var tr = template(list);
					$(".col-xs-1").remove();
					$(".col-xs-7").remove();
					$("#carousel-example-generic").remove();
					$(tr).appendTo(".templist");
					
					$("h5 .editTitle").click(function(){
						var temp=$(".item.active").find('input[name=treeNo]').val();
						updateTitle(temp);
					})
					$("h5").hover(function(){
						$("h5 .editTitle").show();
					},function(){
						$("h5 .editTitle").hide();
					})

			/*해당트리 펼쳐짐*/
					$(".right").click(function() {
						var temp=$(".item.active").next().find('input[name=treeNo]').val();
						if(temp == null){
							temp = $($(".item")[0]).find('input[name=treeNo]').val();
						}
						//alert(temp)
						$("#myDiagram").remove();
						$("#timeline").before('<div id="myDiagram" style="position: relative; background: #E4E4E4; float: left; width: 100%; height: 100%"></div>');
						setTimeout("goImpl("+temp+")",1000);
					});
					$(".left").click(function() {
						var temp=$(".item.active").prev().find('input[name=treeNo]').val();
						if(temp == null){
							var count = $("#carousel-example-generic .item").length;
							alert(count)
							temp=$($(".item")[count-1]).find('input[name=treeNo]').val();
						}
						$("#myDiagram").remove();
						$("#timeline").before('<div id="myDiagram" style="position: relative; background: #E4E4E4; float: left; width: 100%; height: 100%"></div>');
						setTimeout("goImpl("+temp+")",1000);
					});
				}
			});

		}

	}); 
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
		console.log(data);

		jQuery("#content").show("fade",300).find("iframe").attr("src","../../../contents/get.html");
		console.log("22222222 "+data.content);
		
		$(".replyIn").children().show();
		DeleteReply();
		
		sessionStorage.setItem('addReplyCounting', 0);
		sessionStorage.setItem('pageSize', 4);
		sessionStorage.setItem('currentPage', 1);
		sessionStorage.setItem('startRowNum', 1);
		sessionStorage.setItem('endRowNum', 4);
		sessionStorage.setItem('contentNo', data.content.contentNo);
		sessionStorage.setItem('replyUserNo', data.user.userNo);
		
		$(".userPhoto").attr("src",data.user.photo);
		getTotalReply();
		listReply();
							
	});	
}

function inputText() {
	var a = "<input type='text' class='form-control'>";
	return $(a);
} 

/*댓글 기능 함수 시작*/
function getTotalReply() {	
	$.getJSON("/reply/getTotalReply/"+sessionStorage.getItem('contentNo'), function(data){ 
		//console.log(data.totalReplyCount);
		$(".totalReplyCount").text(data.totalReplyCount+"개");
	});
}		

function DeleteReply(){
	$("#boardReply").children().remove();
	$(".totalReplyCount").text("");
	console.log("aa");
}

function listReply(){
	$(function() {			
		$.ajax( 
				{
					url : "/reply/listReply/" ,
					method : "POST" ,
					dataType : "json" ,
					data: JSON.stringify({
						replyValueNo : sessionStorage.getItem('contentNo'),
						pageSize : sessionStorage.getItem('pageSize'),
						currentPage : sessionStorage.getItem('currentPage'),
						startRowNum : sessionStorage.getItem('startRowNum'),
						endRowNum : sessionStorage.getItem('endRowNum')							
					}),
					headers : {
						"Accept" : "application/json",
						"Content-Type" : "application/json"
					},							
					success : function(JSONData , status) {										
						$.get("../resources/hbs/reply/replyListTemplate.hbs", function(data){     
							var tr;	
							var template = Handlebars.compile(data);									
							
							console.log(JSONData);
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
							}else{
								//alert("댓글이 없습니다.");
							} 
							
						});		
					}				
			});
	});	
}


$(document).on('keyup','input:text[name=reply]',function(e) {
	if (e.keyCode == 13) {
		var reply = $(this).val();
		var parentReplyNo = 0;
		//$("input:hidden[name=contentNo]").val();
//		var contentNo = page.replyValueNo;
		//var userNo = $("input:hidden[name=userNo]").val();		
//		var userNo = page.replyUserNo;
		
		$.ajax( 
				{
					url : "/reply/addReply/" ,
					method : "POST" ,
					dataType : "json" ,
					data: JSON.stringify({
						reply : reply,
						parentReplyNo : parentReplyNo,
						contentNo : sessionStorage.getItem('contentNo'),
						userNo : sessionStorage.getItem('userNo')	
					}),
					headers : {
						"Accept" : "application/json",
						"Content-Type" : "application/json"
					},							
					success : function(JSONData , status) {
						console.log(JSONData);
						JSONData.reply.regTime = compareDate(JSONData.reply.regTime);
						
						JSONData.reply.reply.replace(" ","&nbsp;");
						var addReplyCounting = sessionStorage.getItem("addReplyCounting");
						addReplyCounting++;
						sessionStorage.setItem("addReplyCounting",addReplyCounting);
						
						$.get("../resources/hbs/reply/replyTemplate.hbs", function(data){
							 var template = Handlebars.compile(data);	
							 var tr = template(JSONData.reply);
								if($("#boardReply").has(".media").length !=0){
									$("#boardReply").find(".media").first().before(tr);									
								}if($("#boardReply").has(".media").length==0){
									$(tr).appendTo("#boardReply"); 
								}								
								replyfunction();
								$("#reply").val("");
								$.getJSON("/reply/getTotalReply/"+sessionStorage.getItem('contentNo'), function(data){ 
									//console.log(data.totalReplyCount);
									$(".totalReplyCount").text(data.totalReplyCount+" 개")
								});
						});	
					}							
				});			
	}if (e.keyCode == 27) {
		$("#reply").val("");
	}		
});			

$(document).on('click','.totalReplyCount',function(e){
	var currentPage = sessionStorage.getItem('currentPage');
	var startRowNum = sessionStorage.getItem('startRowNum');
	var endRowNum = sessionStorage.getItem('endRowNum');
	var pageSize = sessionStorage.getItem('pageSize');
	var addReplyCounting =sessionStorage.getItem('addReplyCounting');
	
	currentPage++
	
	if(addReplyCounting !=0){			
		startRowNum=((currentPage-1)*pageSize+1)+addReplyCounting;
		endRowNum=(currentPage*pageSize)+addReplyCounting;
	}
	else{
		startRowNum=(currentPage-1)*pageSize+1;  
		endRowNum=currentPage*pageSize;
	}
	
	sessionStorage.setItem('currentPage', currentPage);
	sessionStorage.setItem('startRowNum', startRowNum);
	sessionStorage.setItem('endRowNum', endRowNum);
	sessionStorage.setItem('addReplyCounting', addReplyCounting);
	listReply();
});

$(document).on('keyup','.comentOfcomentinput',function(e) {
	var it = $(this);
	//console.log("AA");
	if (e.keyCode == 13) {
		var reply = it.val();
		//console.log(reply);
		var parentReplyNo = it.parent().find(".replyNo").val();
//		var contentNo = page.replyValueNo;
//		var userNo = page.replyUserNo;
//		console.log(contentNo);
//		console.log(parentReplyNo);
		
		$.ajax( 
				{
					url : "/reply/addReply/" ,
					method : "POST" ,
					dataType : "json" ,
					data: JSON.stringify({
						reply : reply,
						parentReplyNo : parentReplyNo,
						contentNo : sessionStorage.getItem('contentNo'),
						userNo : sessionStorage.getItem('userNo')
					}),
					headers : {
						"Accept" : "application/json",
						"Content-Type" : "application/json"
					},							
					success : function(JSONData , status) {
						console.log(JSONData);
						console.log(JSONData.reply);
						JSONData.reply.regTime = compareDate(JSONData.reply.regTime);
						
						JSONData.reply.reply.replace(" ","&nbsp;");
						
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
 

function replyfunction(){
	$(".updateComment").hide();	
	$(".updateCommentOfComment").hide();
	$(".comentOfcomentinsert").hide();
	$(".delete").animate({opacity:"0"}, 0);
	$(".update").animate({opacity:"0"}, 0);
	$(".deleteReplyOfReply").animate({opacity:"0"}, 0);
	$(".updateReplyOfReply").animate({opacity:"0"}, 0);
};

$(document).on('mouseenter','.media-body',function(){
	$(this).find(".update, .delete").animate({opacity:"1"}, 0);
}).on('mouseleave','.media-body',function(){
	$(this).find(".update, .delete").animate({opacity:"0"}, 0);
}); 

$(document).on('mouseenter','.media-body',function(){
	$(this).find(".updateReplyOfReply, .deleteReplyOfReply").animate({opacity:"1"}, 0);
}).on('mouseleave','.media-body',function(){
	$(this).find(".updateReplyOfReply, .deleteReplyOfReply").animate({opacity:"0"}, 0);
}); 
 
$(document).on('click','.Comment',function(){
	$(this).css("white-space","initial");  
});
 
$(document).on('click','.replyofreply',function(){
	$(this).css("white-space","initial"); 
	$(this).children().css("white-space","initial");	 
});

 $(document).on('click','.delete',function(){
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
 
 $(document).on('click','.deleteReplyOfReply',function() {
	var no= $(this).parent().find(".replyNo").val();
	console.log(no);
	var thi =$(this); 
	var it = thi.parents(".media").find(".comentPlusRe");
	var parentReplyNo = thi.parents(".media").find(".replyNo").val();
	$.getJSON('/reply/removeReply/'+no, function(){	
		thi.parents(".media-m").remove();	
	});
	
	$.getJSON("/reply/getTotalReplyOfReply/"+parentReplyNo, function(data){ 
		//console.log(data.totalReplyCount);
		it.text(data.totalReplyOfReplyCount+" 개");
	});
});
		 
$(document).on('click','.update',function() {
	var no= $(this).parent().find(".replyNo").val();	
	console.log("no::"+no)
	var reply = $(this).parents(".media-body").find(".CommentFont").text();					
	console.log(reply);
	$(this).parents(".media-body").find(".updateComment").show();
	$(this).parents(".media-body").find(".updateComment").val(reply);
	$(this).parents(".media-body").find(".Comment").hide();
	$(this).parents(".media-body").find(".list-inline").hide();
	$(this).parents(".media-body").find(".updateComment").keyup(function(e) {
		if (e.keyCode == 27) {
			$(this).parents(".media-body").find(".updateComment").hide();
			$(this).parents(".media-body").find(".Comment").show();
			$(this).parents(".media-body").find(".list-inline").show();
		}
		if (e.keyCode == 13) {							
			var reply = $(this).parents(".media-body").find(".updateComment").val();
			var replyNo = no;
			console.log(reply);
			console.log(replyNo);
			var comment;
			var addressUpdate=$(this);
			$(this).parents(".media-body").find(".Comment").show();
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
$(document).on('click','.updateReplyOfReply',function() {
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
 
$(document).on('click','.comentplus',function(){	
	$(this).parents(".media").find(".comentOfcomentinsert").toggle();		
});

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


	
