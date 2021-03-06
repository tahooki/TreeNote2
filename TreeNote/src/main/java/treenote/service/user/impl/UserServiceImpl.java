package treenote.service.user.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import treenote.domain.User;
import treenote.service.user.UserDao;
import treenote.service.user.UserService;;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

	/// Field
	@Autowired
	@Qualifier("userDaoImpl")
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/// Constructor
	public UserServiceImpl() {
		System.out.println(this.getClass());
	}

	@Override
	public int addUser(User user) throws Exception {
		System.out.println("service adduser");
		return userDao.addUser(user);

	}

	@Override
	public User getUser(int userNo) throws Exception {
		// TODO Auto-generated method stub
		return userDao.getUser(userNo);
	}

	@Override
	public List<User> ListFriend(Map<String, Object> userNo) throws Exception {
		// TODO Auto-generated method stub
		return userDao.getFriendList(userNo);
	}

	@Override
	public int updateUser(User user) throws Exception {
		// TODO Auto-generated method stub
		return userDao.updateUser(user);
	}

	@Override
	public boolean checkDuplication(String email) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public User loginUser(User user) throws Exception {
		User returnUser = userDao.getLoginUser(user.getEmail());

		return returnUser;
	}

	@Override
	public User getUser2(String email) throws Exception {
		return userDao.getLoginUser(email);
	}

	//추가 !! 수정중인 트리 일련번호 업데이트 - by.Tahooki
	@Override
	public void updateEditTreeNo(User user) throws Exception {
		// TODO Auto-generated method stub
		userDao.updateEditTreeNo(user);
	}

	@Override
	public int requestFriend(Map<String, Object> userNo) throws Exception {
		// TODO Auto-generated method stub
		return userDao.requestFriend(userNo);
		
	}

	@Override
	public int acceptFriend(Map<String, Object> userNo) throws Exception {
		// TODO Auto-generated method stub
		
		return userDao.acceptFriend(userNo);
	}
	
	@Override
	public int declineFriend(Map<String, Object> userNo) throws Exception {
		// TODO Auto-generated method stub
		
		return userDao.declineFriend(userNo);
	}
	
	
	@Override
	public int snsSignup(User user) throws Exception{
		System.out.println("snsSignup:::service");
		return userDao.snsSignup(user);
	}
	
	
	@Override
	public User fLogin(User user)throws Exception{
		return userDao.fLogin(user);
	}

	
	
	@Override
	public void deleteFriend(Map< String, Object> map) throws Exception{
		userDao.deleteFriend(map);
	}

	
//	정태가 추가한 것 
	@Override
	public int updateUserActivity(User user) throws Exception {
		// TODO Auto-generated method stub
		return userDao.updateUserActivity(user);
	}
	
	

}