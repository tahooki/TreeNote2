package treenote.service.keyword;

import java.util.List;

import treenote.domain.Keyword;
import treenote.domain.Search;

public interface KeywordService {
	//새 키워드
	public Keyword newKeyword(Keyword keyword) throws Exception;	
	//키워드 추가
	public Keyword addKeyword(Keyword keyword) throws Exception;
	//키워드 교체
	public int changeKeyword(Keyword keyword) throws Exception;
	//수정
	public int updateKeyword(Keyword keyword) throws Exception;
	//삭제
	public void removeKeyword(Keyword keyword) throws Exception;
	//해당 유저 타임라인 키워드 리스트 불러오기
	public List<Search> listTimeLineKeyword(int userNo) throws Exception;
	//해당 검색 키워드 리스트 불러오기
	public List<Search> listSearchKeyword(String keyword) throws Exception;
	//자식 키워드 불러오기
	public List<Keyword> listOnwerChildKeyword(Keyword keyword) throws Exception;
	//자동완성
	public List<String> autoComplete() throws Exception; 
}