package treenote.service.reply;

import java.util.List;

import treenote.domain.Reply;
import treenote.domain.Page;

public interface ReplyService {
	//추가
	public void addReply(Reply reply) throws Exception;	
	//수정
	public void updateReply(Reply reply) throws Exception;
	//삭제
	public void removeReply(Reply reply) throws Exception;
	//댓글리스트
	public List<Reply> listReply(Page page) throws Exception;
	//총 댓글 수 
	public int replytotalCount(int contentNo) throws Exception;
	//답글리스트
	public List<Reply> listReplyOfReply(Page page) throws Exception;
	//총 답글 수 
	public int replyOfReplytotalCount(int parentReplyNo) throws Exception;
}