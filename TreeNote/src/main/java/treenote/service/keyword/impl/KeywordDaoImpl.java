package treenote.service.keyword.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import treenote.domain.Keyword;
import treenote.domain.Page;
import treenote.domain.Search;
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
		Map<String, Object> map=new HashMap<>();
		map.put("keyword", keyword);
		map.put("startNum", 1);
		map.put("endNum", 15);
		return sqlSession.selectList("keywordMapper.listSearchKeyword", map);
	}
	@Override
	public List<Keyword> listSearchKeyword(String keyword, int count) throws Exception {
		// TODO Auto-generated method stub
			int total=sqlSession.selectOne("keywordMapper.totalKeyword", keyword);			
			Map<String, Object> map=new HashMap<>();
			if(count<=total){
			map.put("keyword", keyword);
			map.put("startNum", count+1);
			map.put("endNum", count+15);
			}else{
				map.put("keyword", keyword);
				map.put("startNum", count+1);
				map.put("endNum", total);
			}
					
		return sqlSession.selectList("keywordMapper.listSearchKeyword", map);
	}

	@Override
	public List<Keyword> listChildKeyword(int keywordNo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList("keywordMapper.listChildKeyword", keywordNo);
	}
	
	@Override
	public List<Keyword> listTimeLineKeyword(int userNo, int count) throws Exception {
		// TODO Auto-generated method stub
		//int total=sqlSession.selectOne("keywordMapper.totalKeyword");

		Map<String, Integer> map=new HashMap<>();
		map.put("userNo", userNo);
		map.put("startNum", count+1);
		map.put("endNum", count+15);
				
		return sqlSession.selectList("keywordMapper.listTimelineKeyword", map);
	}
	
	@Override
	public List<Keyword> listTimeLineKeyword(int userNo) throws Exception {
		// TODO Auto-generated method stub
		//int total=sqlSession.selectOne("keywordMapper.totalKeyword");
		int total=sqlSession.selectOne("keywordMapper.totalKeyword", userNo);
		
		
		Map<String, Integer> map=new HashMap<>();
		map.put("userNo", userNo);
		map.put("startNum", 1);
		map.put("endNum", 15);
				
		return sqlSession.selectList("keywordMapper.listTimelineKeyword", map);
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
	
	@Override
	public int totalKeyword() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("keywordMapper.totalKeyword");
	}

}