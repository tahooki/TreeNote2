package treenote.service.tree;

import java.util.List;

import treenote.domain.Tree;

public interface TreeDao {
	
	//생성
	public void addTree(Tree tree) throws Exception ;
	
	//제목수정
	public int updateTitle(Tree tree) throws Exception;
	
	//삭제
	public void removeTree(int treeNo) throws Exception;
	
	//트리 리스트 불러오기
	public List<Tree> listTree(int userNo) throws Exception;
}


