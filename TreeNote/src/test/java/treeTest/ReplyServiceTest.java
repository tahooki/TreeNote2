package treeTest;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import treenote.domain.Reply;
import treenote.domain.Page;
import treenote.service.reply.ReplyService;

@RunWith(SpringJUnit4ClassRunner.class)

//@ContextConfiguration(locations = { "classpath:config/context-*.xml" })
@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
										"classpath:config/context-aspect.xml",
										"classpath:config/context-mybatis.xml",
										"classpath:config/context-transaction.xml" })
//@ContextConfiguration(locations = { "classpath:config/context-common.xml" })
public class ReplyServiceTest {

	@Autowired
	@Qualifier("replyServiceImpl")
	private ReplyService replyService;

	//@Test
	public void testAddReply() throws Exception {

		Reply reply = new Reply();
		reply.setContentNo(1000000);
		reply.setReply("안녕하세요");
		reply.setUserNo(1000000);
		reply.setParentReplyNo(0);

		replyService.addReply(reply);

		Page search = new Page();
		search.setCurrentPage(1);
		search.setPageSize(5);
		search.setReplyValueNo(1000000);

		List<Reply> list = replyService.listReply(search);
		
		for(Reply replys : list){
			System.out.println("testListReply() Result :: "+replys);
		}
	}

	//@Test
	public void testUpdateReply() throws Exception {

		Reply reply = new Reply();
		reply.setReplyNo(1000004);
		reply.setReply("감사해요");

		replyService.updateReply(reply);

		Page search = new Page();
		search.setCurrentPage(1);
		search.setPageSize(5);
		search.setReplyValueNo(1000000);

		List<Reply> list = replyService.listReply(search);
		
		for(Reply replys : list){
			System.out.println("testListReply() Result :: "+replys);
		}
	}

	@Test
	public void testRemoveReply() throws Exception {

		Reply reply = new Reply();
		reply.setReplyNo(1000010);
		reply.setParentReplyNo(reply.getReplyNo());
		reply.setDeleteChildReply(true);
		replyService.removeReply(reply);

	}

	// @Test
	public void testListReply() throws Exception {

		Page search = new Page();
		search.setCurrentPage(1);
		search.setPageSize(5);
		search.setReplyValueNo(1000041);
		
		List<Reply> list = replyService.listReply(search);
		
		for(Reply reply : list){
			System.out.println("testListReply() Result :: "+reply);
		}
	}

	// @Test
	public void testListReplyOfReply() throws Exception {

		Page search = new Page();
		search.setCurrentPage(1);
		search.setPageSize(5);
		search.setReplyValueNo(1000041);
		
		List<Reply> list = replyService.listReplyOfReply(search);
		
		for(Reply reply : list){
			System.out.println("testListReplyOfReply() Result :: "+reply);
		}
	}

	// @Test
	public void testGetReplyListTotalCount() throws Exception {

		int contentNo = 1000000;
		int totalCount = 0;

		totalCount = replyService.replytotalCount(contentNo);

		System.out.println("totalCount :: " + totalCount);

	}

	// @Test
	public void testGetReplyOfReplyListTotalCount() throws Exception {

		int parentReplyNo = 1000007;
		int totalCount = 0;

		totalCount = replyService.replyOfReplytotalCount(parentReplyNo);

		System.out.println("totalCount :: " + totalCount);

	}
}