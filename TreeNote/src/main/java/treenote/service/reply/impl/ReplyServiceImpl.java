package treenote.service.reply.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import treenote.domain.Reply;
import treenote.domain.Page;
import treenote.service.reply.ReplyDao;
import treenote.service.reply.ReplyService;;

@Service("replyServiceImpl")
public class ReplyServiceImpl implements ReplyService {

	/// Field
	@Autowired
	@Qualifier("replyDaoImpl")
	private ReplyDao replyDao;

	public void setReplyDao(ReplyDao replyDao) {
		this.replyDao = replyDao;
	}

	/// Constructor
	public ReplyServiceImpl() {
		System.out.println(this.getClass());
	}

	@Override
	public void addReply(Reply reply) throws Exception {
		// TODO Auto-generated method stub
		replyDao.addReply(reply);
	}

	@Override
	public void updateReply(Reply reply) throws Exception {
		// TODO Auto-generated method stub
		replyDao.updateReply(reply);
	}

	@Override
	public void removeReply(Reply reply) throws Exception {
		// TODO Auto-generated method stub
		replyDao.removeReply(reply);
	}

	@Override
	public List<Reply> listReply(Page page) throws Exception {
		// TODO Auto-generated method stub
		return replyDao.listReply(page);
	}

	@Override
	public int replytotalCount(int contentNo) throws Exception {
		// TODO Auto-generated method stub
		return replyDao.ReplytotalCount(contentNo);
	}

	@Override
	public List<Reply> listReplyOfReply(Page page) throws Exception {
		// TODO Auto-generated method stub
		if(page.getCurrentPage() == 0){
			page.setCurrentPage(1);
		}
		return replyDao.listReplyOfReply(page);
	}

	@Override
	public int replyOfReplytotalCount(int parentReplyNo) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(";;");
		System.out.println(parentReplyNo);
		return replyDao.ReplyOfReplytotalCount(parentReplyNo);
	}
	
	
	
}