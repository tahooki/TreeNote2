package treenote.service.keyword.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import treenote.domain.Keyword;
import treenote.service.keyword.KeywordDao;

@Repository("keywordDaoImpl")
public class KeywordDaoImpl implements KeywordDao {

	/// Field
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	/// Constructor
	public KeywordDaoImpl() {
		System.out.println(this.getClass());
	}

	@Override
	public void addKeyword(Keyword keyword) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert("keywordMapper.addKeyword", keyword);
	}

	@Override
	public int updateKeyword(Keyword keyword) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.update("keywordMapper.updateKeyword", keyword);
	}

	@Override
	public void removeKeyword(Keyword keyword) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete("keywordMapper.removeKeyword", keyword.getKey());
	}

	@Override
	public List<Keyword> listTreeKeyword(int treeNo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList("keywordMapper.listTreeKeyword", treeNo);
	}

	@Override
	public List<Keyword> listSearchKeyword(String keyword) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList("keywordMapper.listSearchKeyword", keyword);
	}

	@Override
	public List<Keyword> listChildKeyword(int keywordNo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList("keywordMapper.listChildKeyword", keywordNo);
	}
	
	@Override
	public List<Keyword> listTimeLineKeyword(int userNo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList("keywordMapper.listTimelineKeyword", userNo);
	}

	@Override
	public Keyword getKeyword(int keywordNo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("keywordMapper.getKeyword", keywordNo);
	}

	@Override
	public int getKeywrodNo() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("keywordMapper.getKeywordNo");
	}

	@Override
	public List<String> autoComplete() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList("keywordMapper.autoComplete");
	}

	@Override
	public List<Keyword> getMyKeyword(int userNo) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("keywordMapper.getMyKeyword", userNo);
	}

	@Override
	public int getUserNoKeyword(int keywordNo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("keywordMapper.getUserNoKeyword", keywordNo);
	}

}