
package treenote.web.user;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


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

	// 로그인
	@RequestMapping(value = "login", method = RequestMethod.POST)
	// public void login(@PathVariable String email, @PathVariable String
	// password, Model model, HttpSession session) throws Exception {
	// @ResponseBody
	public void login(@RequestBody User user, Model model, HttpSession session) throws Exception {
		System.out.println("/login");
		User returnUser = userService.getUser2(user.getEmail());
		// System.out.println(password);
		if (user.getPassword().equals(returnUser.getPassword())) {
			session.setAttribute("user", returnUser);
			System.out.println("success login");
			model.addAttribute("boolean", true);

			// 추가됨 !! session정보 대신 데이터 가져가는 부분. - by.Tahooki
			model.addAttribute("user", returnUser);
		} else {
			model.addAttribute("boolean", false);
		}

	}

	// 로그아웃
	@RequestMapping(value = "logout")
	public String logout(HttpSession session) throws Exception {
		System.out.println("/logout");
		session.invalidate();
		return "redirect:/index.jsp";
	}

	// 가입체크
	@RequestMapping(value = "checkDuplication")
	public void checkDuplication(@RequestBody String email, Model model) throws Exception {
		System.out.println("/checkDuplication");
		User returnUser = userService.getUser2(email);
		System.out.println(returnUser);
		if (returnUser != null) {
			model.addAttribute("boolean", true);
		} else {
			model.addAttribute("boolean", false);
		}

	}

	// 유저정보가지고 오기
	@RequestMapping(value = "getUser/{userNo}")
	public void getUser(@PathVariable int userNo, Model model) throws Exception {
		System.out.println("/getUser");
		User user = userService.getUser(userNo);

	}

	// 유저 가입
	@RequestMapping(value = "addUser", headers = "content-type=multipart/form-data")
	public void addUser(@RequestParam("filedata") MultipartFile filedata, 
			@ModelAttribute User user, Model model, HttpSession session) throws Exception {
		System.out.println("/addUser");
		System.out.println(user);
		String path ="/resources/upload/image";
		File dir = new File(path);
		if(!dir.isDirectory()){
			dir.mkdirs();
		}
////////file upload/////////////////////////////////////////////////////////////////////////////////
		if (!filedata.isEmpty()) {
            try {
            	UUID uuid = UUID.randomUUID();
            	filedata.transferTo(new File(path,filedata.getOriginalFilename()));
//                byte[] bytes = filedata.getBytes();
//                BufferedOutputStream stream =
//                        new BufferedOutputStream(new FileOutputStream(new File(filedata.getOriginalFilename())));
                System.out.println("::::::::::::::::::::::"+filedata.getOriginalFilename());
                user.setPhoto(path.substring(path.lastIndexOf("/")+1)+"/"+filedata.getOriginalFilename());
//                stream.write(bytes);
//                stream.close();
//                System.out.println("You successfully uploaded " + profil + "!");
            } catch (Exception e) {
            	System.out.println(e);
//                System.out.println("You failed to upload " + profil + " => " + e.getMessage());
            }
            
        } else {
//            System.out.println("You failed to upload " + profil + " because the file was empty.");
        }
		
		
//////////////////////////////////////////////////////////////////////////////////////////////////////////////

		userService.addUser(user);
		model.addAttribute("user", user);

		// 세션 정보 추가
		User returnUser = userService.getUser2(user.getEmail());
		session.setAttribute("user", returnUser);

	}

	// 유저 정보 업데이트
	@RequestMapping(value = "updateUser")
	public void updateUser(@RequestBody User user, Model model) throws Exception {
		System.out.println("/updateUser");
		userService.updateUser(user);
		model.addAttribute("user", user);
	}

	// 친구 목록
	@RequestMapping(value = "listFriend")
	public void listFriend( Model model, HttpSession session) throws Exception {
		System.out.println("/listFriend");
		User user = (User)session.getAttribute("user");
		Map<String, Object> map = new HashMap<>();
		map.put("userNo1", user.getUserNo());
		map.put("userNo2", user.getUserNo());
//		map.put("userNo1", 1000000);
//		map.put("userNo2", 1000000);
		List<User> list = userService.ListFriend(map);
//		List<User> list = userService.ListFriend(1000000);
		model.addAttribute("friend",list);
	}
	
	

	// 추가 !! 수정중인 트리 일련번호 업데이트 - by.Tahooki
	@RequestMapping(value = "updateEditTreeNo/{editTreeNo}")
	public void updateEditTreeNo(@PathVariable int editTreeNo, Model model, HttpSession session) throws Exception {
		System.out.println("/updateEditTreeNo");
		User user = (User) session.getAttribute("user");
		user.setEditTreeNo(editTreeNo);
		userService.updateEditTreeNo(user);
		session.setAttribute("user", user);
	}
	
	
	
	// 친구 요청
	@RequestMapping(value = "requestFriend/{userNo}")
	public void requestFriend(@PathVariable int userNo, HttpSession session, Model model ) throws Exception{
		System.out.println(userNo);
		User user = (User) session.getAttribute("user");
		Map<String, Object> userNoMap = new HashMap<>();
		userNoMap.put("userNo", user.getUserNo()); // 실제 코드 
//		userNoMap.put("userNo", 1000000); // 테스트 코드
		userNoMap.put("userNo2", userNo);
		Map<String, Object> map2 = new HashMap<>();
		map2.put("userNo1", user.getUserNo());
		map2.put("userNo2", user.getUserNo());
		int result = userService.requestFriend(userNoMap);
		System.out.println(":::::::"+result);
		if(result==2){
			List<User> list = userService.ListFriend(map2);
			model.addAttribute("friend",list);
		}
		
	}
	
	
	// 친구 요청 수락
	@RequestMapping(value="acceptFriend/{userNo}")
	public void acceptFriend(@PathVariable int userNo, HttpSession session, Model model) throws Exception{
		User user = (User)session.getAttribute("user");
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> map2 = new HashMap<>();
		map.put("userNo", user.getUserNo()); //실제 코드
//		map.put("userNo", 1000000); // 테스트 코드
		map.put("userNo2", userNo);
		map2.put("userNo1", user.getUserNo());
		map2.put("userNo2", user.getUserNo());
		int result = userService.acceptFriend(map);
		if(result==2){
			List<User> list = userService.ListFriend(map2);
			model.addAttribute("friend",list);
		}
		
	}
	
	
	//친구 거절 
	@RequestMapping(value="declineFriend/{userNo}")
	public void declineFriend(@PathVariable int userNo, HttpSession session, Model model) throws Exception{
		User user = (User)session.getAttribute("user");
		Map<String, Object> map = new HashMap<>();
		map.put("userNo", user.getUserNo()); //실제 코드
//		map.put("userNo", 1000000); // 테스트 코드
		map.put("userNo2", userNo);
		int result = userService.declineFriend(map);
		Map<String, Object> map2 = new HashMap<>();
		map2.put("userNo1", user.getUserNo());
		map2.put("userNo2", user.getUserNo());
		if(result==2){
			List<User> list = userService.ListFriend(map2);
//			List<User> list = userService.ListFriend(1000000);
			model.addAttribute("friend",list);
		}
	}
	
	
	
	//친구 거절후 확
}
