package treenote.domain;


public class Page {
	
	///Field
	private int currentPage;
	private String searchCondition;
	private String searchKeyword;
	private int replyValueNo;
	private int pageSize;
	private int endRowNum;
	private int startRowNum;
	
	///Constructor
	public Page() {
	}
	
	///Method
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int paseSize) {
		this.pageSize = paseSize;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getSearchCondition() {
		return searchCondition;
	}
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}
	
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	
	public int getReplyValueNo() {
		return replyValueNo;
	}

	public void setReplyValueNo(int replyValueNo) {
		this.replyValueNo = replyValueNo;
	}

	public int getEndRowNum() {
		//return getCurrentPage()*getPageSize();
		return endRowNum;		
	}
	
	public int getStartRowNum() {
		//return (getCurrentPage()-1)*getPageSize()+1;
		return startRowNum;
	}

	public void setEndRowNum(int endRowNum) {
		this.endRowNum = endRowNum;
	}

	public void setStartRowNum(int startRowNum) {
		this.startRowNum = startRowNum;
	}

	@Override
	public String toString() {
		return "Page [currentPage=" + currentPage + ", searchCondition=" + searchCondition + ", searchKeyword="
				+ searchKeyword + ", replyValueNo=" + replyValueNo + ", pageSize=" + pageSize + ", endRowNum="
				+ endRowNum + ", startRowNum=" + startRowNum + "]";
	}
	
}