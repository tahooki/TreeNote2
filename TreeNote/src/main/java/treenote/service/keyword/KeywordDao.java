package treenote.service.keyword;

import java.util.List;

import treenote.domain.Keyword;
import treenote.domain.Search;

public interface KeywordDao {
	// 추가
	public void addKeyword(Keyword keyword) throws Exception;

	// 수정
	public int updateKeyword(Keyword keyword) throws Exception;

	// 삭제
	public void removeKeyword(Keyword keyword) throws Exception;

	// 해당 트리 리스트 불러오기
	public List<Keyword> listTreeKeyword(int treeNo) throws Exception;

	// 해당 키워드 리스트 불러오기
	public List<Keyword> listTimeLineKeyword(int userNo) throws Exception;

	public List<Keyword> listTimeLineKeyword(int userNo, int count) throws Exception;

	// 해당 키워드 리스트 불러오기
	public List<Keyword> listSearchKeyword(String keyword) throws Exception;

	public List<Keyword> listSearchKeyword(String keyword, int count) throws Exception;

	// 자식 키워드 불러오기
	public List<Keyword> listChildKeyword(int keywordNo) throws Exception;

	public Keyword getKeyword(int keywordNo) throws Exception;

	public int getKeywrodNo() throws Exception;

	// 자동완성
	public List<String> autoComplete() throws Exception;

	// 프로필 키워드 리스트
	public List<Keyword> getMyKeyword(int userNo);

	// 키워드로 유저 넘버 가지고오기
	public int getUserNoKeyword(int keywordNo) throws Exception;

	// 전체키워드 수
	public int totalKeyword() throws Exception;

}