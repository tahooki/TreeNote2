
package treenote.web.user;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
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

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
                                        import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.StringUtils;

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
			System.out.println(returnUser);
			model.addAttribute("boolean", true);

			// 추가됨 !! session정보 대신 데이터 가져가는 부분. - by.Tahooki
			model.addAttribute("user", returnUser);
		} else {
			model.addAttribute("boolean", false);
		}

	}

	// 로그아웃
	@RequestMapping(value = "logout")
	public void logout(HttpSession session) throws Exception {
		System.out.println("/logout");
		session.invalidate();
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
	@RequestMapping(value = "getUser")
	public void getUser( Model model, HttpSession session) throws Exception {
		System.out.println("/getUser");
		User user = (User)session.getAttribute("user");
		System.out.println(user);
		model.addAttribute("user", user);

	}
	
	
	// 유저 가입
	//////test
	@RequestMapping(value="/addUser", method=RequestMethod.POST)
    public @ResponseBody void addUser(@ModelAttribute User user, Model model,
            @RequestParam("file") MultipartFile file, HttpSession session){
		System.out.println(user);
		
//		String path = "https://s3-ap-northeast-1.amazonaws.com/treenote";
//		String fileName = path+"/"+file.getOriginalFilename();
//		s3client.putObject(new PutObjectRequest("treenote", file.getOriginalFilename(), ));		
		try {
			if (file != null && file.getOriginalFilename() != null
					&& !file.getOriginalFilename().equals("")) {
				// 파일이 존재하면
				String original_name = file.getOriginalFilename();
				String ext = original_name.substring(original_name.lastIndexOf(".") + 1);
				// 파일 기본경로
				String defaultPath = session.getServletContext().getRealPath("/");;
				// 파일 기본경로 _ 상세경로
				String path = defaultPath + "resource" + File.separator + "photo_upload" + File.separator;
				File filedir = new File(path);
				System.out.println("path:::::::::::" + path);
				// 디렉토리 존재하지 않을경우 디렉토리 생성
				if (!filedir.exists()) {
					filedir.mkdirs();
				}
				// 서버에 업로드 할 파일명(한글문제로 인해 원본파일은 올리지 않는것이 좋음)
				String realname = UUID.randomUUID().toString() + "." + ext;
				System.out.println("realname : "+realname);
				///////////////// 서버에 파일쓰기 /////////////////
				file.transferTo(new File(path + realname));
				user.setPhoto(path.replace(defaultPath, "/")+realname);
//				AWSCredentials credentials = new BasicAWSCredentials("AKIAJWYFFXN4HGRWKRTA", "nyWYOwJfC8kqOWM8FqvM47pQuTpi3NQBpihXekCv");
//
//				ClientConfiguration clientConfig = new ClientConfiguration();
//				clientConfig.setProtocol(Protocol.HTTP);
//
//				AmazonS3 conn = new AmazonS3Client(credentials, clientConfig);
//				List<Bucket> buckets = conn.listBuckets();
//				for (Bucket bucket : buckets){
//					System.out.println(":::::::::::::::::::"+bucket.getName()+"\t"+ StringUtils.fromDate(bucket.getCreationDate()));
//				}
//				
//				conn.putObject(new PutObjectRequest(path,  realname, filedata).withCannedAcl(CannedAccessControlList.PublicRead));
//				System.out.println("realname : "+realname);
//				///////////////// 서버에 파일쓰기 /////////////////
//				
//				System.out.println("여기");
				
				userService.addUser(user);
			} else {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	
	
//	
//	@RequestMapping(value = "addUser", headers = "content-type=multipart/form-data")
//	public void addUser(@RequestParam("filedata") MultipartFile filedata, 
//			@ModelAttribute User user, Model model, HttpSession session) throws Exception {
//		System.out.println("/addUser");
//		System.out.println(user);
//		String path ="/resources/upload/image";
//		File dir = new File(path);
//		if(!dir.isDirectory()){
//			dir.mkdirs();
//		}
//////////file upload/////////////////////////////////////////////////////////////////////////////////
//		if (!filedata.isEmpty()) {
//            try {
//            	UUID uuid = UUID.randomUUID();
//            	filedata.transferTo(new File(path,filedata.getOriginalFilename()));
////                byte[] bytes = filedata.getBytes();
////                BufferedOutputStream stream =
////                        new BufferedOutputStream(new FileOutputStream(new File(filedata.getOriginalFilename())));
//                System.out.println("::::::::::::::::::::::"+filedata.getOriginalFilename());
//                user.setPhoto(path.substring(path.lastIndexOf("/")+1)+"/"+filedata.getOriginalFilename());
//                System.out.println(user.getPhoto());
////                stream.write(bytes);
////                stream.close();
////                System.out.println("You successfully uploaded " + profil + "!");
//            } catch (Exception e) {
//            	System.out.println(e);
////                System.out.println("You failed to upload " + profil + " => " + e.getMessage());
//            }
//            
//        } else {
////            System.out.println("You failed to upload " + profil + " because the file was empty.");
//        }
//		
//		
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//		userService.addUser(user);
//		model.addAttribute("user", user);
//
//		// 세션 정보 추가
//		User returnUser = userService.getUser2(user.getEmail());
//		session.setAttribute("user", returnUser);
//
//	}

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
		System.out.println(userNo+":::::::::");
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> map2 = new HashMap<>();
		map.put("userNo", user.getUserNo()); //실제 코드
//		map.put("userNo", 1000000); // 테스트 코드
		map.put("userNo2", userNo);
//		map2.put("userNo1", user.getUserNo());
//		map2.put("userNo2", user.getUserNo());
		int result = userService.acceptFriend(map);
		if(result==2){
//			List<User> list = userService.ListFriend(map2);
//			model.addAttribute("friend",list);
			model.addAttribute("boolean",true);
		}else{
			model.addAttribute("boolean",false);
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
	
//sns signup
	@RequestMapping(value="facebookSignup")
	public void facebookSignup(@RequestBody User user, HttpSession session, Model model) throws Exception{
		System.out.println("facebookSignup");
		System.out.println(user);
		User sessionUser = (User)session.getAttribute("user");
		if(sessionUser!=null){
			if(sessionUser.getEmail()==user.getEmail()){
				model.addAttribute("boolean", false);
				return;
			}
		}
		
		if(userService.getUser2(user.getEmail())!=null){
			model.addAttribute("boolean", false);
			return;
		}
		UUID uuidPassword = UUID.randomUUID();
		user.setPassword(uuidPassword.toString());
		System.out.println(user+"::::::::uuid user");
		int result = userService.snsSignup(user);
		if(result==1){
			model.addAttribute("boolean", true);
		}else{
			model.addAttribute("boolean", false);
		}
		
		
	}
	
	@RequestMapping(value="fLogin")
	public void fLogin(@RequestBody User user, HttpSession session, Model model)throws Exception{
		System.out.println("/fLogin start");
		User fUser = userService.fLogin(user);
		if(fUser!=null){
			model.addAttribute( true);
			session.setAttribute("user", fUser);
		}else{
			model.addAttribute(false);
		}
	}
	
	
}
