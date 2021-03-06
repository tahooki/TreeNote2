function profilCall(){
	
	
	$.ajax({
		url:"/user/profil",
		contentType:"application/json",
		dataType:'json',
		success:function(data){
			$.get("resources/hbs/profil.hbs",function(hbs){
				Handlebars.registerHelper( "checkPhoto", function ( status ){
				    if (status !=null )
				    {
				        return status
				    }
				    else
				    {
				        return 'resources/img/profile.png';
				    }
				});
				
				Handlebars.registerHelper( "checkStatus2", function ( status ){
				    if (status != 2 )
				    {
				        return 'display: none"'
				    }
				    else
				    {
				        return ;
				    }
				});
				
				var tr;
				var template = Handlebars.compile(hbs);
				tr = template(data);
				$("#mypage").append(tr);
				
				

				$("#owl-demo").owlCarousel({
			        autoPlay: false,
			        items : 3,
			        itemsDesktop : [1199,3],
			        itemsDesktopSmall : [979,3],
			        navigation:true,
			        pagination:false,
			        mouseDrag:false
			      });
				
				
				$('#owl-demo .caption-content').on('mouseenter',function(){
					var tNo = $(this).parents('.col-sm-4.portfolio-item.item').find('input').val();
					
					var dName = $(this).attr('id')
					myTreelist(dName,tNo);
					$(this).unbind();
				})
				
				
				$('.keyword-list').on('click',function(){
					var keyno = $(this).find('input').val();
					sessionStorage.setItem("keywordNo",keyno);
					sessionStorage.setItem("keyword",$(this).text());
					$.getJSON("/content/getContent/" +keyno, function(data) {
						console.log("aaaaasaaasdfadsfsdf "+data.content);
						if(data.content==null){
							jQuery("#content").show("fade",300).find("iframe").attr("src","../../../contents/contents.html");
							console.log("1111111 "+data.content);
						}
						else{
							jQuery("#content").show("fade",300).find("iframe").attr("src","../../../contents/get.html");
							console.log(data.content);
						}						
					});					
				})
			
				$('.scroll-top').on("mouseenter",function(){
					$(this).find('.profilNavi').css("left","0px")
				})
				$('.scroll-top').on("mouseleave",function(){
					$(this).find('.profilNavi').css("left","-25px")
				})
				$('.scroll-left').on("mouseenter",function(){
					$(this).find('.profilNavi').css("left","-6px")
				})
				$('.scroll-left').on("mouseleave",function(){
					$(this).find('.profilNavi').css("left","-25px")
				})
				
				
				
				$('.scroll-top').on('click',function(){
					$('header').css('display','none')
					$('section').css('display','block')
					$(this).css('display','none')
					$('.scroll-left').css('display','block')
					return false;
				});
				
				$('.scroll-left').on('click',function(){
					$('section').css('display','none')
					$('header').css('display','block')
					$(this).css('display','none')
					$('.scroll-top').css('display','block')
					return false;
				})
				
				$('#profilImg').on('click',function(){
					$("input[name=file]").click()
					return false;
				})
				
				 $('input[type=file]').on('change',function(){
					 readURL(this)
					 var fd = new FormData($("#profilImage")[0]); 
					 console.log(fd)
					 $.ajax({
					     url: "/user/updateProfil",
					     type: "POST",
					         data: fd,
					         async: false,
					         cache: false,
					         contentType: false,
					         processData: false,
					         success:  function(data){
					             alert(data);
					         }
					     });
				 })
				 
					
				 
				
				
				$('.owl-item').on('click',function(){
					var myTreeNo = $(this).find('input').val();
					$("#myDiagram").remove();
					$("#timeline").before('<div id="myDiagram" style="position: relative; background: #E4E4E4; float: left; width: 100%; height: 100%"></div>');
					sessionStorage.setItem("isMyTree",true)
					goImpl(myTreeNo)
				})
				
				
				$('#profilFriend .pull-left .img-avatar').on('click',function(){
					var funo = $(this).prev().val();
					console.log(funo+"::::::")
					getUser(funo);
				})
			
			
			})
		}
	})
	
	
	
	
 


	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 
}


function friendProfilCall(userNo){
	
	
	$.ajax({
		url:"/user/profil/"+userNo,
		contentType:"application/json",
		dataType:'json',
		success:function(data){
			$.get("resources/hbs/friendProfil.hbs",function(hbs){
				Handlebars.registerHelper( "checkPhoto", function ( status ){
					if (status !=null )
					{
						return status
					}
					else
					{
						return 'resources/img/profile.png';
					}
				});
				
				Handlebars.registerHelper( "checkStatus2", function ( status ){
					if (status != 2 )
					{
						return 'display: none"'
					}
					else
					{
						return ;
					}
				});
				
				var tr;
				var template = Handlebars.compile(hbs);
				tr = template(data);
				$("#mypage").append(tr);
				
				
				
				$("#owl-demo").owlCarousel({
					autoPlay: false,
					items : 3,
					itemsDesktop : [1199,3],
					itemsDesktopSmall : [979,3],
					navigation:true,
					pagination:false,
					mouseDrag:false
				});
				
				
				$('#owl-demo .caption-content').on('mouseenter',function(){
					var tNo = $(this).parents('.col-sm-4.portfolio-item.item').find('input').val();
					
					var dName = $(this).attr('id')
					myTreelist(dName,tNo);
					$(this).unbind();
				})
				
				
				$('.keyword-list').on('click',function(){
					var keyno = $(this).find('input').val();
					sessionStorage.setItem("keywordNo",keyno);
					sessionStorage.setItem("keyword",$(this).text());
					$.getJSON("/content/getContent/" +keyno, function(data) {
						console.log("aaaaasaaasdfadsfsdf "+data.content);
						if(data.content==null){
							jQuery("#content").show("fade",300).find("iframe").attr("src","../../../contents/contents.html");
							console.log("1111111 "+data.content);
						}
						else{
							jQuery("#content").show("fade",300).find("iframe").attr("src","../../../contents/get.html");
							console.log(data.content);
						}						
					});					
				})
				
				$('.scroll-top').on("mouseenter",function(){
					$(this).find('.profilNavi').css("left","0px")
				})
				$('.scroll-top').on("mouseleave",function(){
					$(this).find('.profilNavi').css("left","-25px")
				})
				$('.scroll-left').on("mouseenter",function(){
					$(this).find('.profilNavi').css("left","-6px")
				})
				$('.scroll-left').on("mouseleave",function(){
					$(this).find('.profilNavi').css("left","-25px")
				})
				
				
				
				$('.scroll-top').on('click',function(){
					$('header').css('display','none')
					$('section').css('display','block')
					$(this).css('display','none')
					$('.scroll-left').css('display','block')
					return false;
				});
				
				$('.scroll-left').on('click',function(){
					$('section').css('display','none')
					$('header').css('display','block')
					$(this).css('display','none')
					$('.scroll-top').css('display','block')
					return false;
				})
				
				$('#profilImg').on('click',function(){
					$("input[name=file]").click()
				})
				
				
				$('.owl-item').on('click',function(){
					var myTreeNo = $(this).find('input').val();
					$("#myDiagram").remove();
					$("#timeline").before('<div id="myDiagram" style="position: relative; background: #E4E4E4; float: left; width: 100%; height: 100%"></div>');
					sessionStorage.setItem("isMyTree",false)
					goImpl(myTreeNo)
					
					$("#otherTreeUser").text("by "+$("#proName").text());
					$("#otherTreeTitle").text($(this).find(".protreetitle").text());
				})
				
				$('#profilFriend .pull-left .img-avatar').on('click',function(){
					var funo = $(this).prev().val();
					console.log(funo+"::::::")
					getUser(funo);
				})
				
			})
		}
	})
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
}





function readURL(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();            
		reader.onload = function (e) {
			$('#profilImg').attr('src', e.target.result);
		}
		
		reader.readAsDataURL(input.files[0]);
	}
}
	 
function myTreelist(diagramName,treeNo) {
	// if (window.goSamples) goSamples();
	//json data를 서버에서 호출후 map 생성
	var jsondata = jQuery.getJSON( '/tree/getTree/'+treeNo,function(success){
		
		console.log(success);
		var gojs = go.GraphObject.make; // for conciseness in defining templates
		
		myDiagram = // 아직 분석 안됨.
			gojs(go.Diagram, diagramName, // div의 이름이 "myDiagram"인 것을 찾아 설정함.
					{
				"toolManager.mouseWheelBehavior" : go.ToolManager.WheelZoom,
				//마우스 속성 설정. whillzoom으로 설정해서 줌도 되게 만듬.
				initialAutoScale : go.Diagram.Uniform, // 화면 정렬 타입
				// contentAlignment: go.Spot.Center, // 화면을 가운데로 적용해서 움직이지 않게 함.
				initialContentAlignment : go.Spot.Center, // 안에 들어있는 노드를 가운데로 정렬시킴
				layout : gojs(go.ForceDirectedLayout), // layout 종류.
				// moving and copying nodes also moves and copies their subtrees
				"commandHandler.copiesTree" : true, // for the copy command
				"commandHandler.deletesTree" : true, // for the delete command
				"draggingTool.dragsTree" : true, // dragging for both move and copy
				hasHorizontalScrollbar : false,
				hasVerticalScrollbar : false,
				allowCopy : false,
				allowUndo : false,
				allowDelete : false,
				
				
				mouseOver:function(e){
					//잠시 폐쇠
					//console.log("canvase mouseOver :: "+e);
					//myDiagram.model.removeNodeData(myDiagram.model.findNodeDataForKey(0));
				},
				click:function(e){
					//서치 풀리면 show 하게 할 예정
					if (sessionStorage.getItem('isTimeline') == 'false') {
						//sessionStorage.setItem('isTimeline', 'false');
						setListTimeKeyword();
					}
					jQuery("#content").hide("fade",300);
				}
					});
		
		// Define the Node template.
		// This uses a Spot Panel to position a button relative
		// to the ellipse surrounding the text.
		myDiagram.nodeTemplate = gojs(go.Node, "Spot", {
			selectionObjectName : "PANEL", // ??
			isTreeExpanded : false, // 노드의 펼쳐지는 것을 설정하는 부분. 기본이ㅏ false로 해놓으면 펼쳐지지
			// 않는다.
			isTreeLeaf : false,
			// 자식노드를 생성하지 못하게 만든다.?
			click:function(e, obj) {
				console.log("node Data : "+JSON.stringify(obj.data));
			},
			selectionChanged:function(part){
				selectKeyword = part;
				
				part.findObject("button2").visible = part.isSelected;
				if(part.isSelected){
					setBtnVisible();
				}else{
					
					setBtnUnVisible();
				}
				
			},
			mouseEnter:function(e,obj){
				console.log("mouseEnter");
			},
			doubleClick:function(e,obj){
				showContent(obj.data.key, obj.data.keyword);		
				//jQuery("#content").show("fade",300);
			}
		},
		
		// the node's outer shape, which will surround the text
		gojs(go.Panel, "Auto", {
			name : "PANEL"
		}, 
		gojs(go.Shape, "Circle", // 노드의 모양을 정함.
				{
			fill : "whitesmoke",
			stroke : "black",
			strokeWidth : 0,
				}, // 기본색이 whitsmoke 인듯... stroke는
				new go.Binding("fill", "color")), gojs(go.TextBlock, {	
					font : "15pt Jeju Gothic",
					stroke : "black",
					editable : true,
					margin : 2,
					textAlign: "center",
					isMultiline : false,
					wrap: go.TextBlock.WrapFit
				}, // 폰트, margin, 텍스트 박스 수정가능을 설정.
				new go.Binding("text", "keyword"))// 노드에 표시되는 텍스트를 data에서 선택하는부분.. data의
				// property중 name이라는 것을 선택.
		),
		gojs(go.Picture, {
			name: "button2",
			alignment : go.Spot.TopRight,
			maxSize : new go.Size(20, 20),
			source : "resources/img/btn_expanded.png",
			visible : false,
			click : function(e, obj) {
				// openDialog();
				if(obj.part.isTreeExpanded){
					obj.source = "resources/img/btn_expanded.png";
					obj.part.isTreeExpanded = false;
					obj.part.data.collapse = 0;
					updateKeyword(obj.part.data);
				}else{
					var child = obj.part.findTreeChildrenNodes();
					//console.log("child count??"+ child);
					if(child.count == 0){
						listOnwerChildKeyword(obj.part);
						obj.part.data.copyNo = obj.part.data.key;
					}
					obj.source = "resources/img/btn_collapse.png";
					obj.part.isTreeExpanded = true;
					obj.part.data.collapse = 1;
					updateKeyword(obj.part.data);
				}
			}
		})
		
		); // end Node
		
		myDiagram.linkTemplate =
			gojs(go.Link,  // the whole link panel
					new go.Binding("points").makeTwoWay(),
					{ curve: go.Link.Bezier, toShortLength: 15 },
					new go.Binding("curviness", "curviness"),
					gojs(go.Shape,  // the link shape
							{ stroke: "#2F4F4F", strokeWidth: 2.5 }),
							gojs(go.Shape,  // the arrowhead
									{ toArrow: "kite", fill: "#2F4F4F", stroke: null, scale: 2 })
			);
		
		// create the model with a root node data
		myDiagram.model = new go.TreeModel([ // 트리모델로 설정
		                                     {
		                                    	 key : 0,
		                                    	 keyword : "로그아웃 되었습니다.",
		                                    	 color : "#2ECC71",
		                                    	 collapse : 0
		                                     } // 초기 토드 추가
		                                     ]);
		
		myDiagram.layoutDiagram(true);
		
		myDiagram.model = go.Model.fromJson(success.Tree)
		
		// console.log(myDiagram.model);
		allExpanded();
	})
	
}





