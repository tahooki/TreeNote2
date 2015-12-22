package treenote.domain;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class Content {
	private int contentNo;
	private int keywordNo;
	private String content;
	private int scrap;
	private Date regDate;
	private Timestamp regTime; //작성시간 추가 됨
	private String originContentList;
	

	public int getContentNo() {
		return contentNo;
	}

	public void setContentNo(int contentNo) {
		this.contentNo = contentNo;
	}

	public int getKeywordNo() {
		return keywordNo;
	}

	public void setKeywordNo(int keywordNo) {
		this.keywordNo = keywordNo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getScrap() {
		return scrap;
	}

	public void setScrap(int scrap) {
		this.scrap = scrap;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Timestamp getRegTime() {
		return regTime;
	}

	public void setRegTime(Timestamp regTime) {
		this.regTime = regTime;
	}

	public String getOriginContentList() {
		return originContentList;
	}

	public void setOriginContentList(String originContentList) {
		this.originContentList = originContentList;
	}

	@Override
	public String toString() {
		return "Content [contentNo=" + contentNo + ", keywordNo=" + keywordNo + ", content=" + content + ", scrap="
				+ scrap + ", regDate=" + regDate + ", regTime=" + regTime + ", originContentList=" + originContentList
				+ "]";
	}
}
	