package treenote.web.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import treenote.domain.Content;
import treenote.service.content.ContentService;

@Controller
@RequestMapping("/content/*")
public class ContentController {

	/// Field
	@Autowired
	@Qualifier("contentServiceImpl")
	private ContentService contentService;

	public ContentController() {
		System.out.println(this.getClass());
	}

	//생성
	@RequestMapping(value = "addContent")
	public void addContent(@RequestBody Content content, Model model) throws Exception{
		System.out.println("/addContent");
		
		contentService.addContent(content);
		model.addAttribute("content", content);
	}
	
	//삭제
	@RequestMapping(value = "removeContent/{contentNo}")
	public void removeContent(@PathVariable int contentNo, Model model) throws Exception{
		System.out.println("/removeContent");
		
		contentService.removeContent(contentNo);
		//model.addAttribute("content", content);
		
	}
	
	//내용수정
	@RequestMapping(value = "updateContent")
	public void updateContent(@RequestBody Content content, Model model) throws Exception{
		System.out.println("/updateContent");
		
		contentService.updateContent(content);
		model.addAttribute("content", content);
	}
	
	//불러오기
	@RequestMapping(value = "getContent/{contentNo}")
	public void getContent(@PathVariable int contentNo, Model model) throws Exception{
		System.out.println("/getContent");
		
		Content content = contentService.getContent(contentNo);
		model.addAttribute("conent", content);
	}
	
}