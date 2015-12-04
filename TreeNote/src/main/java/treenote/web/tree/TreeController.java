package treenote.web.tree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import treenote.domain.Tree;
import treenote.service.tree.TreeService;

@Controller
@RequestMapping("/tree/*")
public class TreeController {
	/// Field
	@Autowired
	@Qualifier("treeServiceImpl")
	private TreeService treeService;

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
	@RequestMapping(value = "getTree/{treeNo}", method=RequestMethod.GET )
	public void getTree(@PathVariable int treeNo, Model model) throws Exception{
		System.out.println("/getTree");
		model.addAttribute("Tree", treeService.getTree(treeNo));
	}
	
	
	//트리 리스트를 불러온다.
	@RequestMapping(value = "listTree/{userNo}")
	public void listTree(int userNo, Model model) throws Exception{
		System.out.println("/listTree");
	}
}
