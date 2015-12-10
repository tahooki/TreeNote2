package treenote.domain;

import java.util.List;

public class Search {
	private User user;
	private Keyword keyword;
	private Keyword parentKeyword;
	private List<Keyword> childKeywordList;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Keyword getKeyword() {
		return keyword;
	}
	public void setKeyword(Keyword keyword) {
		this.keyword = keyword;
	}
	public Keyword getParentKeyword() {
		return parentKeyword;
	}
	public void setParentKeyword(Keyword parentKeyword) {
		this.parentKeyword = parentKeyword;
	}
	public List<Keyword> getChildKeywordList() {
		return childKeywordList;
	}
	public void setChildKeywordList(List<Keyword> childKeywordList) {
		this.childKeywordList = childKeywordList;
	}
	@Override
	public String toString() {
		return "Search [user=" + user + ", keyword=" + keyword + ", prentKeyword=" + parentKeyword
				+ ", childKeywordList=" + childKeywordList + "]";
	}
	
}
