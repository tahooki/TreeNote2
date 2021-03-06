package treenote.service.user;

import java.util.List;
import java.util.Map;

import treenote.domain.User;

public interface UserDao {

	public int addUser(User user) throws Exception ;

	public User getUser(int userNo) throws Exception ;


	public int updateUser(User user) throws Exception ;
	
	public boolean checkDuplication(String email) throws Exception;

	public User getLoginUser(String email) throws Exception;
	
	//추가 !! 수정중인 트리 일련번호 업데이트 - by.Tahooki
	public void updateEditTreeNo(User user) throws Exception;
	
	public int requestFriend(Map<String, Object> userNo) throws Exception; 
	
	public int acceptFriend(Map<String, Object> userNo) throws Exception;

	public int declineFriend(Map<String, Object> userNo) throws Exception;

	public List<User> getFriendList(Map<String, Object> userNo) throws Exception;

	public int snsSignup(User user) throws Exception;

	public User fLogin(User user);


	void deleteFriend(Map<String, Object> map) throws Exception;

	
//	정태가 추가한 것 
	public int updateUserActivity(User user) throws Exception;

	}