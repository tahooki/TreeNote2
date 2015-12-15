package treenote.web.tree;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import treenote.domain.Tree;
import treenote.domain.User;
import treenote.service.tree.TreeService;
import treenote.service.user.UserService;

@Controller
@RequestMapping("/tree/*")
public class TreeController {
	/// Field
	@Autowired
	@Qualifier("treeServiceImpl")
	private TreeService treeService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	public TreeController() {
		System.out.println(this.getClass());
		System.out.println("Tree.Controller Start:");
	}

	//생성
	@RequestMapping(value ="addTree", method=RequestMethod.POST)
	public void addTree(@RequestBody Tree tree, Model model) throws Exception {
		System.out.println("/addTree");
		
		treeService.addTree(tree);
		model.addAttribute("tree", tree);
		
	}
	
	//제목수정
	@RequestMapping(value = "updateTitle")
	public void updateTitle(@RequestBody Tree tree, Model model) throws Exception{
		System.out.println("/updateTitle");
		
		treeService.updateTitle(tree);
		model.addAttribute("tree", tree);
		
	}
	
	//삭제
	@RequestMapping(value = "removeTree/{treeNo}")
	public void removeTree(@PathVariable int treeNo, Model model) throws Exception{
		System.out.println("/removeTree");
		
		treeService.removeTree(treeNo);
		System.out.println("삭제 성공");
	}
	
	
	//불러오기
	@RequestMapping(value = "getTree", method=RequestMethod.GET )
	public void getTree(HttpSession session, Model model) throws Exception{
		System.out.println("/getTree");
		User user = (User)session.getAttribute("user");
		System.out.println(treeService.getTree(user.getEditTreeNo()));
		System.out.println(user.getEditTreeNo());
		if(user.getEditTreeNo() == 0){
			System.out.println("??");
			Tree tree = new Tree();
			tree.setUserNo(user.getUserNo());
			user.setEditTreeNo(treeService.addTree(tree));			
			userService.updateEditTreeNo(user);
			model.addAttribute("Tree", treeService.getTree(user.getEditTreeNo()));
		}else{
			model.addAttribute("Tree", treeService.getTree(((User)session.getAttribute("user")).getEditTreeNo()));
		}
	}
	
	@RequestMapping(value = "getTree/{treeNo}", method=RequestMethod.GET )
	public void getTree(@PathVariable int treeNo, HttpSession session, Model model) throws Exception{
		System.out.println("/getTree2"+treeNo);
		
		User user = (User)session.getAttribute("user");
		System.out.println(treeService.getTree(user.getEditTreeNo()));
		System.out.println(user.getEditTreeNo());
		if(user.getEditTreeNo() == 0){
			System.out.println("??");
			Tree tree = new Tree();
			tree.setUserNo(user.getUserNo());
			user.setEditTreeNo(treeService.addTree(tree));			
			userService.updateEditTreeNo(user);
			model.addAttribute("Tree", treeService.getTree(user.getEditTreeNo()));
		}else{
			model.addAttribute("Tree", treeService.getTree(treeNo));
		}
	}
	
	
	//트리 리스트를 불러온다.
	@RequestMapping(value = "listTree", method=RequestMethod.GET)
	public void listTree(HttpSession session , Model model) throws Exception{
		System.out.println("/listTree");
		User user=(User)session.getAttribute("user");
		System.out.println("UserNo ::::::::::::"+treeService.listTree(user.getUserNo()));
		List<Tree> listTree=treeService.listTree(user.getUserNo());
		List<Tree> subTree=listTree.subList(1, listTree.size());
		model.addAttribute("Tree", listTree);
		model.addAttribute("subTree", subTree);
	}
	
	//추가 !! login할때 불러오는 Tree - by.Tahooki
	@RequestMapping(value = "loginTree")
	public void loginTree() throws Exception{
		
	}
}
