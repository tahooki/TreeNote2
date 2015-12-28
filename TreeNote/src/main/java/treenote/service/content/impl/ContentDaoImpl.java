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
	public int removeContent(int contentNo) throws Exception {
		return sqlSession.delete("ContentMapper.deleteContent", contentNo);

	}

	@Override
	public int updateContent(Content content) throws Exception {
		return sqlSession.update("ContentMapper.updateContent", content);
	}

	@Override
	public int updateScrapContent(Content content) throws Exception {
		return sqlSession.update("ContentMapper.updateScrapContent", content);
	}

	@Override
	public Content getContent(int keywordNo) throws Exception {
		return sqlSession.selectOne("ContentMapper.getContent", keywordNo);
	}

	@Override
	public Content getContentContentNo(int contentNo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("ContentMapper.getContentContentNo", contentNo);
	}

}