package treenote.web.reply;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import treenote.domain.Page;
import treenote.domain.Reply;
import treenote.service.reply.ReplyService;

@Controller
@RequestMapping("/reply/*")
public class ReplyController {

	/// Field
	@Autowired
	@Qualifier("replyServiceImpl")
	private ReplyService replyService;

	public ReplyController() {
		System.out.println(this.getClass());
	}

	//추가
	@RequestMapping(value = "addReply", method = RequestMethod.POST)
	public void addReply(@RequestBody Reply reply, Model model) throws Exception{
		System.out.println("/addReply");
		System.out.println("Reply :: "+reply);
		
		Page page = new Page();
		page.setCurrentPage(1);
		page.setPageSize(5);
		page.setStartRowNum(1);
		page.setEndRowNum(5);
		page.setReplyValueNo(reply.getContentNo());
		
		replyService.addReply(reply);		
		model.addAttribute("reply", replyService.listReply(page).get(0));
	}
	//수정
	@RequestMapping(value = "updateReply", method=RequestMethod.POST)
	public void updateReply(@RequestBody Reply reply, Model model) throws Exception{
		System.out.println("/updateReply");
		
		replyService.updateReply(reply);
		model.addAttribute("reply", reply.getReply());		
	}
	//삭제
	@RequestMapping(value = "removeReply/{replyNo}")
	public void removeReply(@PathVariable int replyNo, Model model) throws Exception{
		System.out.println("/removeReply");
		Reply reply = new Reply();
		reply.setReplyNo(replyNo);
		reply.setParentReplyNo(0);
		
		replyService.removeReply(reply);
	}
	//댓글리스트
	@RequestMapping(value = "listReply")
	public void listReply(@RequestBody Page page, Model model) throws Exception{
		System.out.println("/listReply");
				
		List<Reply> list = replyService.listReply(page);
    	//JSONArray jsonArray = JSONArray.fromObject(list);
		
		System.out.println("date :: "+list.get(0).getRegDate());
		System.out.println("time :: "+list.get(0).getRegTime());
		model.addAttribute("replyList",list);
	}
}