package treenote.service.content;

import treenote.domain.Content;

public interface ContentService {
	//생성
	public void addContent(Content content) throws Exception;
	//삭제
	public void removeContent(int contentNo) throws Exception;
	//수정
	public int updateContent(Content content) throws Exception;
	//불러오기
	public Content getContent(int keywordNo) throws Exception;
	//불러오기
	public Content getContentContentNo(int contentNo) throws Exception;
}