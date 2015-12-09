package treenote.service.user;

import java.util.List;
import java.util.Map;

import treenote.domain.User;
public interface UserService {
	
	public int addUser(User user) throws Exception ;

	public User getUser(int userNo) throws Exception ;

	public List<User> ListFriend(int userNo) throws Exception ;

	public int updateUser(User user) throws Exception ;
	
	public boolean checkDuplication(String email) throws Exception;
	
	public User loginUser(User user) throws Exception;
	
	public User getUser2(String email)throws Exception;
	
	public void updateEditTreeNo(User user) throws Exception;
	
	public int requestFriend(Map<String, Object> userNo) throws Exception; 
	
	public int acceptFriend(Map<String, Object> userNo) throws Exception;

	int declineFriend(Map<String, Object> userNo) throws Exception;

}