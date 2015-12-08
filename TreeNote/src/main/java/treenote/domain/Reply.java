package treenote.domain;

import java.sql.Date;
import java.sql.Timestamp;

public class Reply {
	private int replyNo;
	private int userNo;
	private int contentNo;
	private int parentReplyNo;
	private String reply;
	private Date regDate;
	private Timestamp regTime; 	
	private boolean deleteChildReply;
	
	
	public int getReplyNo() {
		return replyNo;
	}

	public void setReplyNo(int replyNo) {
		this.replyNo = replyNo;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public int getContentNo() {
		return contentNo;
	}

	public void setContentNo(int contentNo) {
		this.contentNo = contentNo;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public int getParentReplyNo() {
		return parentReplyNo;
	}

	public void setParentReplyNo(int parentReplyNo) {
		this.parentReplyNo = parentReplyNo;
	}

	public boolean isDeleteChildReply() {
		return deleteChildReply;
	}

	public void setDeleteChildReply(boolean deleteChildReply) {
		this.deleteChildReply = deleteChildReply;
	}

	public Timestamp getRegTime() {
		return regTime;
	}

	public void setRegTime(Timestamp regTime) {
		this.regTime = regTime;
	}

	@Override
	public String toString() {
		return "Reply [replyNo=" + replyNo + ", userNo=" + userNo + ", contentNo=" + contentNo + ", parentReplyNo="
				+ parentReplyNo + ", reply=" + reply + ", regDate=" + regDate + ", regTime=" + regTime
				+ ", deleteChildReply=" + deleteChildReply + "]";
	}

}
