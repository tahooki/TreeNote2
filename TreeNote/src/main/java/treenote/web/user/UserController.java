
package treenote.web.user;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.jws.soap.SOAPBinding.Use;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
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
//		정태가 변경한 곳  && returnUser.getActivity()==1
		System.out.println("returnUser.getActivity() : "+returnUser.getActivity());
		System.out.println("user.getPassword().equals(returnUser.getPassword()) : "+user.getPassword().equals(returnUser.getPassword()));
		if (user.getPassword().equals(returnUser.getPassword()) && returnUser.getActivity()==1) {
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
	
	@RequestMapping(value="index")
	public void index(HttpSession session,Model model)throws Exception{
		if(session.getAttribute("user")!=null){
			model.addAttribute(true);
		}else{
			model.addAttribute(false);
		}
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
	
	@RequestMapping(value = "getUser2")
	public void getUser2(@PathVariable int userNo, Model model, HttpSession session) throws Exception {
		System.out.println("/getUser");
		User user = userService.getUser(userNo);
		System.out.println(user);
		model.addAttribute("user", user);
		
	}
	
	
	// 유저 가입
	//////test
	@RequestMapping(value="/addUser", method=RequestMethod.POST)
    public @ResponseBody void addUser(@ModelAttribute User user, Model model,
            @RequestParam("file") MultipartFile file, HttpSession session){
		System.out.println(user);
		
		try {
			if (file != null && file.getOriginalFilename() != null
					&& !file.getOriginalFilename().equals("")) {
				// 파일이 존재하면
				String original_name = file.getOriginalFilename();
				System.out.println(original_name);
				String ext = original_name.substring(original_name.lastIndexOf(".") + 1);
				// 파일 기본경로
				String defaultPath = session.getServletContext().getRealPath("/");;
				System.out.println(defaultPath);
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
				///////////////// 아마존 서버에 파일쓰기 /////////////////
				String AWS_BUCKETNAME = "treenote";
		        String AWS_ACCESS_KEY = "AKIAIFT2OS6KFQXS3KDQ";
		        String AWS_SECRET_KEY = "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";
		        String file_path = "resources/;"; // 폴더명
		        String file_name = realname; // 파일명 


		        AWSCredentials credentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);
		        AmazonS3 s3 = new AmazonS3Client(credentials);
		       
		        try {
		        	// 파일 업로드 부분 파일 이름과 경로를 동시에 넣어줌.
		        	 PutObjectRequest putObjectRequest = new PutObjectRequest(AWS_BUCKETNAME, file_path+file_name,new File(path+realname));
		             putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead); // URL 접근시 권한 읽을수 있도록 설정.
		             s3.putObject(putObjectRequest);
		             System.out.println("Uploadinf OK");
		             
		             user.setPhoto("https://s3-ap-northeast-1.amazonaws.com/"+AWS_BUCKETNAME+"/"+file_path+file_name);
		             // 파일 다운로드 다운로드 경로와 파일이름 동시 필요. 
//		             System.out.println("Downloading an object");
//		             S3Object object = s3.getObject(new GetObjectRequest(AWS_BUCKETNAME, file_path+file_name));
//		             System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
		             
		             
		             
		             
		        } catch (AmazonServiceException ase) {
		            System.out.println("Caught an AmazonServiceException, which means your request made it "
		                    + "to Amazon S3, but was rejected with an error response for some reason.");
		            System.out.println("Error Message:    " + ase.getMessage());
		            System.out.println("HTTP Status Code: " + ase.getStatusCode());
		            System.out.println("AWS Error Code:   " + ase.getErrorCode());
		            System.out.println("Error Type:       " + ase.getErrorType());
		            System.out.println("Request ID:       " + ase.getRequestId());
		        } catch (AmazonClientException ace) {
		            System.out.println("Caught an AmazonClientException, which means the client encountered "
		                    + "a serious internal problem while trying to communicate with S3, "
		                    + "such as not being able to access the network.");
		            System.out.println("Error Message: " + ace.getMessage());
		        }
				
				
				
				
				
				userService.addUser(user);
			} else {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	
	@RequestMapping(value="/updateProfil")
	public void updateProfil(HttpSession session, @ModelAttribute MultipartFile file ){
//		User user = (User)session.getAttribute("user");
		
		try {
			if (file != null && file.getOriginalFilename() != null
					&& !file.getOriginalFilename().equals("")) {
				// 파일이 존재하면
				String original_name = file.getOriginalFilename();
				String ext = original_name.substring(original_name.lastIndexOf(".") + 1);
				// 파일 기본경로
				String defaultPath = session.getServletContext().getRealPath("/");;
				// 파일 기본경로 _ 상세경로
				String path = defaultPath + "user";
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
//				user.setPhoto(path.replace(defaultPath, "/")+realname);
			} else {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="listFriend")
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
	
	// 정태가 추가한 곳 
	//이메일 인증하기 위한 사용자에게 이메일 보내기
	@RequestMapping(value="sendEmali")
	public void sendEmali(@RequestBody Map<String, Object> sendMap, HttpSession session, Model model)throws Exception{
		System.out.println("/sendEmali start");
		
		String title = "Tree 계정인증 확인메일 입니다.";		
		String content = (String)sendMap.get("content");
		String receiver = (String)sendMap.get("reseiver");
		
		int activityNo = (int)sendMap.get("activityNo");
		
		System.out.println("content"+content);
		System.out.println("receiver"+receiver);
		System.out.println("activityNo"+activityNo);
		
		SendEmail sendEmail = new SendEmail();
		sendEmail.send(title, receiver, content);
		User user = userService.getUser2(receiver);
		user.setActivity(activityNo);
		userService.updateUserActivity(user);
	}
	
	// 정태가 추가한 곳 
	//계정인증요청으로 인한 Activity활성화
	@RequestMapping(value="sendEmailCheck")
	public String sendEmaliCheck(@RequestParam("reseiver") String userEmail, 
								 @RequestParam("activityNo") int activity, 
								 HttpSession session)throws Exception{
				
		System.out.println("/sendEmailCheck start");
		
		User user = userService.getUser2(userEmail);
		
		if(user.getActivity() == activity){
			user.setActivity(1);
			userService.updateUserActivity(user);
		}
		return "redirect:http://127.0.0.1:8080/index.html";
	}
	
	@RequestMapping(value="searchFriend/{email}")
	public void searchFriend(@PathVariable String email, HttpSession session, Model model) throws Exception{
		User user = (User)session.getAttribute("user");
		if(user!=null){
			User searchFriend = userService.getUser2(email);
			searchFriend.setPassword(null);
			model.addAttribute("searchFriend", searchFriend);
		}
	}
	
	
	//12월 21일 추가 by shin
	@RequestMapping(value="deleteFriend/{userNo}")
	public void deleteFriend(@PathVariable int userNo, HttpSession session) throws Exception{
		User user = (User)session.getAttribute("user");
		if(user!=null){
			Map<String, Object> map = new HashMap<>();
			map.put("user01", userNo);
			map.put("user02", user.getUserNo());
			userService.deleteFriend(map);
		}
	}
	
}
