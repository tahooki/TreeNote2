package treenote.service.keyword;

import java.util.List;

import treenote.domain.Keyword;

public interface KeywordDao {
	//추가
	public void addKeyword(Keyword keyword) throws Exception;	
	//수정
	public int updateKeyword(Keyword keyword) throws Exception;
	//삭제
	public void removeKeyword(Keyword keyword) throws Exception;
	//해당 트리 리스트 불러오기
	public List<Keyword> listTreeKeyword(int treeNo) throws Exception;
	//해당 키워드 리스트 불러오기
	public List<Keyword> listTimeLineKeyword(int userNo) throws Exception;
	//해당 키워드 리스트 불러오기
	public List<Keyword> listSearchKeyword(String keyword) throws Exception;
	//자식 키워드 불러오기
	public List<Keyword> listChildKeyword(int keywordNo) throws Exception;
	
	public Keyword getKeyword(int keywordNo) throws Exception;
	
	public int getKeywrodNo() throws Exception;
	
	//자동완성
	public List<String> autoComplete() throws Exception;
	//프로필 키워드 리스트
	public List<Keyword> getMyKeyword(int userNo); 
}