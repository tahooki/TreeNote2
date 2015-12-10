package treenote.domain;

public class Keyword {
	private int key;
	private int treeNo;
	private int copyNo;
	private String keyword;
	private int collapse;
	private int parent;
	private String color;

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public int getTreeNo() {
		return treeNo;
	}

	public void setTreeNo(int treeNo) {
		this.treeNo = treeNo;
	}
	
	public int getCopyNo() {
		return copyNo;
	}

	public void setCopyNo(int copyNo) {
		this.copyNo = copyNo;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getCollapse() {
		return collapse;
	}

	public void setCollapse(int collapse) {
		this.collapse = collapse;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Keyword [key=" + key + ", treeNo=" + treeNo + ", copyNo=" + copyNo + ", keyword=" + keyword
				+ ", collapse=" + collapse + ", parent=" + parent + ", color=" + color + "]";
	}

}
