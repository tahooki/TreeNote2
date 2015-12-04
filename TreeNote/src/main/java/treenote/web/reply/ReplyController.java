package treenote.web.reply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
	@RequestMapping(value = "addReply")
	public void addReply(@RequestBody Reply reply, Model model) throws Exception{
		System.out.println("/addReply");
		System.out.println("Reply :: "+reply);
		
		replyService.addReply(reply);
		model.addAttribute("reply", reply);
	}
	//수정
	@RequestMapping(value = "updateReply")
	public void updateReply(Reply reply, Model model) throws Exception{
		System.out.println("/updateReply");
	}
	//삭제
	@RequestMapping(value = "removeReply/{replyNo}")
	public void removeReply(@PathVariable int replyNo, Model model) throws Exception{
		System.out.println("/removeReply");
	}
	//댓글리스트
	@RequestMapping(value = "listReply/{contentNo}")
	public void listReply(@PathVariable int contentNo, Model model) throws Exception{
		System.out.println("/listReply");
	}
}