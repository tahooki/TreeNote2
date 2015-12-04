package treenote.service.content.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import treenote.domain.Content;
import treenote.service.content.ContentDao;
import treenote.service.content.ContentService;;

@Service("contentServiceImpl")
public class ContentServiceImpl implements ContentService {

	/// Field
	@Autowired
	@Qualifier("contentDaoImpl")
	private ContentDao contentDao;

	public void setContentDao(ContentDao contentDao) {
		this.contentDao = contentDao;
	}

	/// Constructor
	public ContentServiceImpl() {
		System.out.println(this.getClass());
	}

	@Override
	public void addContent(Content content) throws Exception {
		contentDao.addContent(content);
	}

	@Override
	public void removeContent(int contentNo) throws Exception {
		contentDao.removeContent(contentNo);
	}

	@Override
	public int updateContent(Content content) throws Exception {
		return contentDao.updateContent(content);
	}	
	
	@Override
	public int updateScrapContent(int content) throws Exception {
		return contentDao.updateScrapContent(content);
	}

	@Override
	public Content getContent(int contentNo) throws Exception {
		return contentDao.getContent(contentNo);
	}

}