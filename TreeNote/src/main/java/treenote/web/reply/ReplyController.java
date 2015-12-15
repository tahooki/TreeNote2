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
		
		if (reply.getParentReplyNo() == 0) {
			page.setReplyValueNo(reply.getContentNo());
			replyService.addReply(reply);	
			model.addAttribute("reply", replyService.listReply(page).get(0));
			
		}else{
			page.setReplyValueNo(reply.getParentReplyNo());
			replyService.addReply(reply);	
			model.addAttribute("reply", replyService.listReplyOfReply(page).get(0));			
		}
		
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
		
		Page page2 = new Page();
		for(int i=0; i<list.size(); i++){
			int totalReplyCount= replyService.replyOfReplytotalCount(list.get(i).getReplyNo());
			list.get(i).setTotalReplyCount(totalReplyCount);
			
			page2 = page;
			page2.setReplyValueNo(list.get(i).getReplyNo());
			
			for(int j=0; j<totalReplyCount; j++){
				List<Reply> listReplyOfReply;
				listReplyOfReply = replyService.listReplyOfReply(page2);
				list.get(i).setReplyOfReply(listReplyOfReply);
			}
			
		}		
		model.addAttribute("replyList",list);
		
	}
	//답글리스트
	@RequestMapping(value = "listReplyOfReply")
	public void listReplyOfReply(@RequestBody Page page, Model model) throws Exception{
		System.out.println("/listReplyOfReply");
			
		List<Reply> list = replyService.listReplyOfReply(page);

		model.addAttribute("replyOfReplyList",list);
	}
	
	//댓글 갯수
	@RequestMapping(value = "getTotalReply/{contentNo}")
	public void getTotalReply(@PathVariable int contentNo, Model model) throws Exception{
		System.out.println("/getTotalReply");
			
		model.addAttribute("totalReplyCount",replyService.replytotalCount(contentNo));
	}
	
	//답글 갯수
	@RequestMapping(value = "getTotalReplyOfReply/{parentReplyNo}")
	public void getTotalReplyOfReply(@PathVariable int parentReplyNo, Model model) throws Exception{
		System.out.println("/getTotalReplyOfReply");
		
		model.addAttribute("totalReplyOfReplyCount",replyService.replyOfReplytotalCount(parentReplyNo));
	}
}