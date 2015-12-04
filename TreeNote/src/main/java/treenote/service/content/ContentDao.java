package treenote.service.content;

import treenote.domain.Content;

public interface ContentDao {
	//생성
	public void addContent(Content content) throws Exception;
	//삭제
	public void removeContent(int contentNo) throws Exception;
	//수정
	public int updateContent(Content content) throws Exception;
	//스크랩 업데이트
	public int updateScrapContent(int content) throws Exception;
	//불러오기
	public Content getContent(int contentNo) throws Exception;
	//불러오기
	public Content getContentKeywordNo(int keywordNo) throws Exception;
	//복사
	public Content copyContent(Content content) throws Exception; 
}