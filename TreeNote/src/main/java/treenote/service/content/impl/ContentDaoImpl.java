package treenote.service.content.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import treenote.domain.Content;
import treenote.service.content.ContentDao;

@Repository("contentDaoImpl")
public class ContentDaoImpl implements ContentDao {

	/// Field
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	/// Constructor
	public ContentDaoImpl() {
		System.out.println(this.getClass());
	}

	
	@Override
	public void addContent(Content content) throws Exception {
		sqlSession.insert("ContentMapper.addContent", content);
	}

	@Override
	public void removeContent(int contentNo) throws Exception {
		sqlSession.delete("ContentMapper.deleteContent", contentNo);
	}

	@Override
	public int updateContent(Content content) throws Exception {
		return sqlSession.update("ContentMapper.updateContent", content);
	}

	@Override
	public int updateScrapContent(int content) throws Exception {
		return sqlSession.update("contentMapper.updateScrapContent", content);
	}

	@Override
	public Content getContent(int keywordNo) throws Exception {
		return sqlSession.selectOne("ContentMapper.getContent", keywordNo);
	}

	@Override
	public Content copyContent(Content content) throws Exception {
		addContent(content);
		updateScrapContent(content.getScrap()+1);		
		
		return null;
	}
	
}