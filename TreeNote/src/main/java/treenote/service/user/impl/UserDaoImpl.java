package treenote.service.user.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import treenote.domain.User;
import treenote.service.user.UserDao;

@Repository("userDaoImpl")
public class UserDaoImpl implements UserDao {

	/// Field
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	/// Constructor
	public UserDaoImpl() {
		System.out.println(this.getClass());
	}

	@Override
	public int addUser(User user) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("dao addUser");
		return sqlSession.insert("UserMapper.addUser", user);

	}

	@Override
	public User getUser(int userNo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("UserMapper.getUser", userNo);
	}

	@Override
	public List<User> getFriendList(Map<String, Object> userNo) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(userNo);
		return sqlSession.selectList("UserMapper.listFriend", userNo);
	}

	@Override
	public int updateUser(User user) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.update("UserMapper.updateUser",user);
	}

	@Override
	public boolean checkDuplication(String email) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	@Override
	public User getLoginUser(String email) throws Exception{
		return sqlSession.selectOne("UserMapper.getLoginUser", email);
	}

	//추가 !! 수정중인 트리 일련번호 업데이트 - by.Tahooki
	@Override
	public void updateEditTreeNo(User user) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update("UserMapper.updateEditTreeNo",user);
	}

	@Override
	public int requestFriend(Map<String, Object> userNo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.insert("UserMapper.requestFriend",userNo);
		
	}

	@Override
	public int acceptFriend(Map<String, Object> userNo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.update("UserMapper.acceptFriend", userNo);
	}
	
	@Override
	public int declineFriend(Map<String, Object> userNo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.update("UserMapper.declineFriend", userNo);
	}
	
	
	
	@Override
	public int snsSignup(User user) throws Exception{
		System.out.println("snsSignup userDao");
		return sqlSession.insert("UserMapper.snsSignup", user);
	}
	
	@Override
	public User fLogin(User user) {
		// TODO Auto-generated method stub
		return  sqlSession.selectOne("UserMapper.fLogin", user);
	}
	
//	정태가 추가한 것 
	@Override
	public int updateUserActivity(User user) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.update("UserMapper.updateUserActivity", user);
	}
	
	
	
}