package treenote.web.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParser;
import com.google.gson.JsonObject;

import treenote.domain.User;
import treenote.service.user.UserService;

@Controller
@RequestMapping("/user/*")
public class UserController {

	/// Field
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	public UserController() {
		System.out.println(this.getClass());
	}

	//로그인
	@RequestMapping(value="login",method=RequestMethod.POST)
//	public void login(@PathVariable String email, @PathVariable String password, Model model, HttpSession session) throws Exception {
//	@ResponseBody
	public  void login(@RequestBody User user, Model model, HttpSession session) throws Exception {
		System.out.println("/login");
		User returnUser = userService.getUser2(user.getEmail());
//		System.out.println(password);
		if(user.getPassword().equals(returnUser.getPassword())){
			session.setAttribute("user", returnUser);
			System.out.println("success login");
			model.addAttribute("boolean",true);
			
			//추가됨 !! session정보 대신 데이터 가져가는 부분. - by.Tahooki
			model.addAttribute("user", returnUser);
		}else{
			model.addAttribute("boolean",false);
		}
		
		
				
	}

	//로그아웃
	@RequestMapping(value = "logout")
	public String logout(HttpSession session) throws Exception {
		System.out.println("/logout");
		session.invalidate();
		return "redirect:/index.jsp";
	}

	//가입체크
	@RequestMapping(value = "checkDuplication/{email}")
	public void checkDuplication(@PathVariable String email, Model model) throws Exception {
		System.out.println("/checkDuplication");
		User returnUser = userService.getUser2(email);
		System.out.println(returnUser);
		if(returnUser !=null){
			model.addAttribute("boolean",true);
		}else{
			model.addAttribute("boolean", false);
		}
		
	}

	//유저정보가지고 오기
	@RequestMapping(value = "getUser/{userNo}")
	public void getUser(@PathVariable int userNo, Model model) throws Exception {
		System.out.println("/getUser");
		User user = userService.getUser(userNo);
		
	}

	//유저 가입
	@RequestMapping(value = "addUser")
	public void addUser(@RequestBody User user, Model model, HttpSession session) throws Exception {
		System.out.println("/addUser");
		
		
		userService.addUser(user);
		model.addAttribute("user", user);
		
		//세션 정보 추가
		User returnUser = userService.getUser2(user.getEmail());
		session.setAttribute("user", returnUser);
		
	}

	//유저 정보 업데이트
	@RequestMapping(value = "updateUser")
	public void updateUser(@RequestBody User user, Model model) throws Exception {
		System.out.println("/updateUser");
		userService.updateUser(user);
		model.addAttribute("user", user);
	}

	//친구 목록
	@RequestMapping(value = "listFriend/{userNo}")
	public void listFriend(@PathVariable int userNo, Model model) throws Exception {
		System.out.println("/listFriend");
	}
	
	//추가 !! 수정중인 트리 일련번호 업데이트 - by.Tahooki
	@RequestMapping(value = "updateEditTreeNo/{editTreeNo}")
	public void updateEditTreeNo(@PathVariable int editTreeNo, Model model, HttpSession session) throws Exception {
		System.out.println("/updateEditTreeNo");
		User user = (User)session.getAttribute("user");
		user.setEditTreeNo(editTreeNo);
		userService.updateEditTreeNo(user);
	}
}