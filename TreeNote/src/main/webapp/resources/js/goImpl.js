var selectKeyword;
function goImpl(treeNo) {
	// if (window.goSamples) goSamples();
	//json data를 서버에서 호출후 map 생성
	console.log(arguments[0]);
	
	if(arguments.length==1){
		var temp='/tree/getTree/'+arguments[0];
	}else{
		var temp='/tree/getTree';
	}
	console.log(":L:::"+temp);
	
		var jsondata = jQuery.getJSON(temp ,function(success){

		console.log(success);
		var gojs = go.GraphObject.make; // for conciseness in defining templates
		
		myDiagram = // 아직 분석 안됨.
			gojs(go.Diagram, "myDiagram", // div의 이름이 "myDiagram"인 것을 찾아 설정함.
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
				"undoManager.isEnabled" : true, // ctrl+z, ctrl+y 되게마는 것.
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
					
					part.findObject("button1").visible = part.isSelected;
					part.findObject("button2").visible = part.isSelected;
					part.findObject("button3").visible = part.isSelected;
					if(part.isSelected){
						setBtnVisible();
					}else{
						
						setBtnUnVisible();
					}
					jQuery("#content").hide("fade",300);
				},
				mouseEnter:function(e,obj){
					console.log("mouseEnter");
				},
				doubleClick:function(e,obj){
					console.log("doubleClick : "+obj.data);
					var keywordNo = JSON.stringify(obj.data.key);
					var keyword = JSON.stringify(obj.data.keyword);
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
										
					//jQuery("#content").show("fade",300);
				}
			},
			new go.Binding("isShadowed", "isSelected").ofObject(),
	        {
		          selectionAdorned: false,
		          shadowOffset: new go.Point(0, 0),
		          shadowBlur: 15,
		          shadowColor: "gray",
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
				name: "button1",
				alignment : go.Spot.TopRight,
				maxSize : new go.Size(20, 20),
				source : "resources/img/btn_add.png",
				visible : false,
				click : function(e, obj) {
					// openDialog();
					var node = obj.part; // 버튼 오브젝트가 있는 노드를 받는다.
					if (node === null)
						return; // 노드가 없으면 끝낸다.
					e.handled = true; // ??
					var diagram = node.diagram; // 노드의 다이어 그램을 받는다.
					diagram.startTransaction("	"); // 트랜젝션 시작... 중간에 오류가 나거나하면 롤백됨.
					// this behavior is specific to this incrementalTree sample:
					var data = node.data;
					
					newKeyword(data); // 트리생성

					if(!obj.part.isTreeExpanded){
						obj.part.findObject("button2").source = "resources/img/btn_collapse.png";
						obj.part.isTreeExpanded = true;
						obj.part.data.collapse = 1;
						updateKeyword(obj.part.data);
					}
					
					diagram.commitTransaction("CollapseExpandTree"); // startTransaction
				}
			}),
			gojs(go.Picture, {
				name: "button2",
				alignment : go.Spot.BottomRight,
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
			}),
			gojs(go.Picture, {
				name: "button3",
				alignment : go.Spot.BottomLeft,
				maxSize : new go.Size(20, 20),
				source : "resources/img/btn_delete.png",
				visible : false,
				click : function(e, obj) {
					// openDialog();
					removeKeyword(obj.part);
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
		myDiagram.toolManager.textEditingTool.defaultTextEditor = createInput();
	})
}

function newKeyword(parentdata) { // 노드를 생성하는 부분.
	var colors = ["#2ECC71","#3498DB","#9B59B6","#F1C40F","#E67E22","#E74C3C"];
	jQuery.ajax( 
	{
		url : "/keyword/newKeyword" ,
		method : "POST" ,
		dataType : "json" ,
		data: JSON.stringify({
			key : 0,
			treeNo : parentdata.treeNo,
			keyword : "키워드",
			copyNo : 0,
			parent : parentdata.key,
			collapse : 0,
			color : colors[Math.floor(Math.random() * 6)]
		}),
		headers : {
			"Accept" : "application/json",
			"Content-Type" : "application/json"
		},
		success : function(JSONData , status) {
			console.log(JSONData.keyword);
			var model = myDiagram.model;
			var parent = myDiagram.findNodeForData(parentdata);
			// add to model.nodeDataArray and create a Node
			model.addNodeData(JSONData.keyword);
			// position the new child node close to the parent
			var child = myDiagram.findNodeForData(JSONData.keyword);
			child.location = parent.location;
			
			parentdata.collapse = 1;
			updateKeyword(parentdata);
			setListTimeKeyword();
		}
	})
}

function addKeyword(key) { // 노드를 생성하는 부분.
	var selectedKeyword;
	var nodes = myDiagram.nodes;
	while(nodes.hasNext()){
		if(nodes.value.isSelected){
			selectedKeyword=nodes.value;
			console.log(selectedKeyword);
			console.log(selectedKeyword.data);
		}
	}
	jQuery.ajax( 
	{
		url : "/keyword/addKeyword" ,
		method : "POST" ,
		dataType : "json" ,
		data: JSON.stringify({
			key : key,
			treeNo : selectedKeyword.data.treeNo,
			parent : selectedKeyword.data.key,
			collapse : 0
		}),
		headers : {
			"Accept" : "application/json",
			"Content-Type" : "application/json"
		},
		success : function(JSONData , status) {
			console.log(JSONData.keyword);
			var model = myDiagram.model;
			var parent = myDiagram.findNodeForData(selectedKeyword.data);
			// add to model.nodeDataArray and create a Node
			model.addNodeData(JSONData.keyword);
			// position the new child node close to the parent
			var child = myDiagram.findNodeForData(JSONData.keyword);
			child.location = parent.location;

			selectedKeyword.findObject("button2").source = "resources/img/btn_collapse.png";
			 
			selectedKeyword.isTreeExpanded = true;
			selectedKeyword.data.collapse = 1;
			updateKeyword(selectedKeyword.data);
			setListTimeKeyword();
		}
	})
}

function changeKeyword(key) { // 노드를 생성하는 부분.
	var selectedKeyword;
	var nodes = myDiagram.nodes;
	while(nodes.hasNext()){
		if(nodes.value.isSelected){
			selectedKeyword=nodes.value;
			console.log(selectedKeyword);
			console.log(selectedKeyword.data);
		}
	}
	jQuery.ajax( 
	{
		url : "/keyword/changeKeyword" ,
		method : "POST" ,
		dataType : "json" ,
		data: JSON.stringify({
			key : selectedKeyword.data.key,
			treeNo : selectedKeyword.data.treeNo,
			copyNo : key,
			parent : selectedKeyword.data.parent,
			collapse : 0,
			color : selectedKeyword.data.color
		}),
		headers : {
			"Accept" : "application/json",
			"Content-Type" : "application/json"
		},
		success : function(JSONData , status) {
			//복사가 성공하면...
			//기존 데이터 삭제...
			//console.log(JSONData);
			var childList = selectedKeyword.findTreeChildrenNodes();
			while(childList.hasNext()){
				console.log(childList.value.data);
				removeKeyword(childList.value);
			}
			selectedKeyword.data.copyNo = key;
			selectedKeyword.data.collapse = 0;
			selectedKeyword.data.color = keyword.color;
			selectedKeyword.findObject("button2").source = "resources/img/btn_expanded.png";
			selectedKeyword.isTreeExpanded = false;
		}
	})
}

function updateKeyword(keyword) { // 노드를 생성하는 부분.
	jQuery.ajax( 
	{
		url : "/keyword/updateKeyword" ,
		method : "POST" ,
		dataType : "json" ,
		data: JSON.stringify({
			key : keyword.key,
			treeNo : keyword.treeNo,
			keyword : keyword.keyword,
			copyNo : keyword.copyNo,
			parent : keyword.parent,
			collapse : keyword.collapse,
			color : keyword.color
		}),
		headers : {
			"Accept" : "application/json",
			"Content-Type" : "application/json"
		},
		success : function(JSONData , status) {
		}
	})
}

function removeKeyword(node) { // 노드를 생성하는 부분.
	var childList = node.findTreeChildrenNodes();
	while(childList.hasNext()){
		console.log(childList.value.data);
		removeKeyword(childList.value);
	}
	var keyword = node.data;
	jQuery.ajax( 
	{
		url : "/keyword/removeKeyword" ,
		method : "POST" ,
		dataType : "json" ,
		data: JSON.stringify({
			key : keyword.key,
			treeNo : keyword.treeNo,
			keyword : keyword.keyword,
			copyNo : keyword.copyNo,
			parent : keyword.parent,
			collapse : keyword.collapse,
			color : keyword.color
		}),
		headers : {
			"Accept" : "application/json",
			"Content-Type" : "application/json"
		},
		success : function(JSONData , status) {
			console.log("remove data :: "+keyword);
			myDiagram.model.removeNodeData(keyword);
			setListTimeKeyword();
		}
	})
}

function allExpanded() {
	var nodes = myDiagram.nodes;
	while (nodes.hasNext()) {
		nodes.value.isTreeExpanded = true;
		if (nodes.value.data.collapse == 1) {
			nodes.value.findObject("button2").source = "resources/img/btn_collapse.png";
		}
	}
	var nodes = myDiagram.nodes;
	while (nodes.hasNext()) {
		if (nodes.value.data.collapse == 0) {
			nodes.value.isTreeExpanded = false;
			nodes.value.findObject("button2").source = "resources/img/btn_expanded.png";
		}
	}
}

function createInput() {
	var customText = document.getElementById("inputText");
	customText.onActivate = function() {
		customText.style.visibility = "";
		var startingValue = customText.textEditingTool.textBlock.text;
		customText.value = startingValue;
		
		var loc = customText.textEditingTool.textBlock.getDocumentPoint(go.Spot.TopLeft);
		var loc2 = customText.textEditingTool.textBlock.getDocumentPoint(go.Spot.BottomRight);
		var pos = myDiagram.transformDocToView(loc);
		var pos2 = myDiagram.transformDocToView(loc2);
		customText.style.left = pos.x + "px";
		customText.style.top = pos.y + "px";
		customText.style.width =  (5 + pos2.x - pos.x) + "px";
		customText.style.height = (5 + pos2.y - pos.y) + "px";
		customText.style.padding = 1;
		customText.style.fontSize = (( pos2.y - pos.y)*0.6) + "pt";
		customText.style.fontSizeAdjust="0.58";
		customText.style.display ="block";
	}
	
	customText.addEventListener("keydown", function(e) {
		var keynum = e.which;
		var tool = customText.textEditingTool;
		if (tool === null)
			return;
		if (keynum == 13) { // Accept on Enter
			tool.acceptText(go.TextEditingTool.Enter);
			//console.log(tool.diagram.nodeTemplate.findMainElement().data);
			selectKeyword.data.keyword = customText.value;
			setListSearchKeyword(selectKeyword.data.keyword);
			updateKeyword(selectKeyword.data);
			e.preventDefault();
			return false;
		} else if (keynum == 9) { // Accept on Tab
			tool.acceptText(go.TextEditingTool.Tab);
			e.preventDefault();
			return false;
		} else if (keynum === 27) { // Cancel on Esc
			tool.doCancel();
			if (tool.diagram)
				tool.diagram.focus();
		}
	}, false);
	return customText;
}

function listOnwerChildKeyword(node){
	//find owner User keyword (user + tree + parentkey)
	//있나? copy.
	//node의 owner를 현제 유저로 set
	console.log("what"+JSON.stringify(node.data));
	jQuery.ajax( 
	{
		url : "/keyword/listOnwerChildKeyword" ,
		method : "POST" ,
		dataType : "json" ,
		data: JSON.stringify({
			key : node.data.key,
			treeNo : node.data.treeNo,
			copyNo : node.data.copyNo,
			parent : node.data.key
		}),
		headers : {
			"Accept" : "application/json",
			"Content-Type" : "application/json"
		},
		success : function(JSONData , status) {
			console.log(JSONData);
			console.log(JSON.stringify(JSONData));
			console.log(JSONData.list);
			var keywordList = JSONData.list;
			for(var i= 0 ; i < keywordList.length ; i++){
				console.log("copying...");
				//JSON.stringify(keywordList[i]));
				model = myDiagram.model;
				var parent = myDiagram.findNodeForData(node.data);
				// add to model.nodeDataArray and create a Node
				model.addNodeData(keywordList[i]);
				// position the new child node close to the parent
				var child = myDiagram.findNodeForData(keywordList[i]);
				child.location = parent.location;
			}
			/*var model = myDiagram.model;
			var parent = myDiagram.findNodeForData(selectedKeyword.data);
			// add to model.nodeDataArray and create a Node
			model.addNodeData(JSONData.keyword);
			// position the new child node close to the parent
			var child = myDiagram.findNodeForData(JSONData.keyword);
			child.location = parent.location;

			selectedKeyword.findObject("button2").source = "resources/img/btn_collapse.png";
			selectedKeyword.isTreeExpanded = true;
			selectedKeyword.data.collapse = 1;
			updateKeyword(selectedKeyword.data);*/
		}
	})
}

function setBtnVisible(){
	var nodes = myDiagram.nodes;
	var selectedKeyword;
	console.log(nodes);
	while(nodes.hasNext()){
		if(nodes.value.isSelected){
			selectedKeyword=nodes.value.data;
			console.log(selectedKeyword);
			console.log(selectedKeyword.data);
		}
	}
	
	var viewList = jQuery(".timeLineBtnBox");
	console.log(viewList.length);
	for(var i = 0; i < viewList.length ; i++){
		console.log("select : " + selectedKeyword + " keyword : " +jQuery(viewList[i]).find("input[name='keyword']").val());
		console.log(JSON.stringify(jQuery(viewList[i]).find("input[name='keyword']").val()));
		//if(selectedKeyword.keyword == jQuery(viewList[i]).find("input[name='treeNo']").val())
		if(selectedKeyword.keyword == jQuery(viewList[i]).find("input[name='keyword']").val()){
			console.log(jQuery(viewList[i]).find(".timeLineAddButton").hide()); 
			console.log(jQuery(viewList[i]).find(".timeLineCopyButton").show());
		}
		else{
			console.log(jQuery(viewList[i]).find(".timeLineCopyButton").hide());
			console.log(jQuery(viewList[i]).find(".timeLineAddButton").show());
		}
	}
	var timeLineBtnList = $(".timeLineBtnBox");
	console.log(timeLineBtnList);
	for(var i = 0; i < timeLineBtnList.length ; i++){
		$(timeLineBtnList[i]).delay(200*i).show("slide",{
			direction : "right",
			duration : 500
		});
	}
}

function setBtnUnVisible(){
	var viewList = jQuery(".timeLineBtnBox");
	console.log(viewList.length);
	for(var i = 0; i < viewList.length ; i++){
		console.log(jQuery(viewList[i]).dequeue().hide());
	}
}