package treenote.service.user;

import java.util.List;

import treenote.domain.User;

public interface UserDao {

	public void addUser(User user) throws Exception ;

	public User getUser(int userNo) throws Exception ;


	public int updateUser(User user) throws Exception ;
	
	public boolean checkDuplication(String email) throws Exception;

	public List<User> getFriendList(int userNo) throws Exception;
	
	public User getLoginUser(String email) throws Exception;
	
	//추가 !! 수정중인 트리 일련번호 업데이트 - by.Tahooki
	public void updateEditTreeNo(User user) throws Exception;
}