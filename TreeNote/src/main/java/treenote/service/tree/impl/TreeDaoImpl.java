package treenote.service.tree.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import treenote.domain.Tree;
import treenote.service.tree.TreeDao;

@Repository("treeDaoImpl")
public class TreeDaoImpl implements TreeDao {

	/// Field
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;

	}

	/// Constructor
	public TreeDaoImpl() {
		System.out.println(this.getClass());

	}

	@Override
	public void addTree(Tree tree) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Dao.addTree::");

		sqlSession.insert("TreeMapper.addTree", tree);
	}

	@Override
	public int updateTitle(Tree tree) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Dao.updateTitle::");

		return sqlSession.update("TreeMapper.updateTitle", tree);
	}

	@Override
	public void removeTree(int treeNo) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Dao.removeTree::");

		sqlSession.delete("TreeMapper.removeTree", treeNo);
	}

	@Override
	public List<Tree> listTree(int userNo) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Dao.listTree::");

		return sqlSession.selectList("TreeMapper.listTree", userNo);
	}

	@Override
	public int getTreeNo() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("TreeMapper.getTreeNo");
	}

}