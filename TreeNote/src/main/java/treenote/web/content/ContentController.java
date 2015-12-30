package treenote.web.content;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import treenote.domain.Content;
import treenote.domain.Keyword;
import treenote.domain.Photo;
import treenote.domain.Tree;
import treenote.domain.User;
import treenote.service.content.ContentDao;
import treenote.service.content.ContentService;
import treenote.service.keyword.KeywordDao;
import treenote.service.keyword.KeywordService;
import treenote.service.keyword.impl.KeywordDaoImpl;
import treenote.service.keyword.impl.KeywordServiceImpl;
import treenote.service.user.UserService;

@Controller
@RequestMapping("/content/*")
public class ContentController {

	/// Field
	@Autowired
	@Qualifier("contentServiceImpl")
	private ContentService contentService;
	
	@Autowired
	@Qualifier("contentDaoImpl")
	private ContentDao contentDao;

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	@Autowired
	@Qualifier("keywordDaoImpl")
	private KeywordDao keywordDao;
	
	public ContentController() {
		System.out.println(this.getClass());
	}

	//생성
	@RequestMapping(value = "addContent")
	public void addContent(@RequestBody Content content, Model model) throws Exception{
		System.out.println("/addContent");
		System.out.println("aaa");
		
		contentService.addContent(content);
		model.addAttribute("content", content);		
		System.out.println("000 " +content);
	}
	
	//삭제
	@RequestMapping(value = "removeContent/{keywordNo}")
	public void removeContent(@PathVariable int keywordNo, Model model) throws Exception{
		System.out.println("/removeContent");
		System.out.println("remove No: "+keywordNo);
		contentService.removeContent(keywordNo);
		System.out.println("remove success!!!!!!");
		
		Content content = new Content();
		content.setKeywordNo(keywordNo);
		content.setScrap(0);
		content.setContent("내용을 입력해주세요 !");
		contentDao.addContent(content);
		System.out.println("contentNo "+content);
	}
	
	//내용수정
	@RequestMapping(value = "updateContent")
	public void updateContent(@RequestBody Content content, Model model) throws Exception{
		System.out.println("/updateContent");
		
		contentService.updateContent(content);
		model.addAttribute("content", content);
		System.out.println("updateeeeeee "+content);
	}
	
	//불러오기
	@RequestMapping(value = "getContent/{keywordNo}")
	public void getContent(@PathVariable int keywordNo, Model model, HttpSession session) throws Exception{
		System.out.println("/getContent");
				
		Content content = new Content();
		content = contentService.getContent(keywordNo);
		System.out.println("111111111111 "+content);
				
		User user2 = (User)session.getAttribute("user");
		
		User user = userService.getUser(keywordDao.getUserNoKeyword(keywordNo));
		System.out.println("22222 "+user);
		//int userNo= keywordDao.getUserNoKeyword(keywordNo);				
		//User user = userService.getUser(userNo);
		
		List<User> originUserList = new ArrayList<User>();
		List<Content> originContentList = new ArrayList<Content>();
		if(content.getOriginContentList() != null){
			String contentNoList[] = content.getOriginContentList().split(",");
			
			for(int i = 0; i < contentNoList.length ; i++){
				
				if(contentService.getContentContentNo(Integer.parseInt(contentNoList[i])) == null){
					originContentList.add(null);
					originUserList.add(null);
				}else{
					Content temp = contentService.getContentContentNo(Integer.parseInt(contentNoList[i]));
					originContentList.add(temp);
					originUserList.add(userService.getUser(keywordDao.getUserNoKeyword(temp.getKeywordNo())));
				}
			}
			System.out.println("originContentList : "+originContentList);
			System.out.println("originUserList : "+originUserList);
		}
		
		model.addAttribute("content", content);
		model.addAttribute("user", user);
		model.addAttribute("originUserList", originUserList);
		model.addAttribute("originContentList", originContentList);
		model.addAttribute("userNo", user2.getUserNo());
	}
	
	
	@RequestMapping(value = "upload")
	public String upload(MultipartHttpServletRequest request, Photo vo) throws Exception {
		System.out.println("/contentupload");
		String callback = vo.getCallback();
		String callback_func = vo.getCallback_func();
		String file_result = "";
		try {
			if (vo.getFiledata() != null && vo.getFiledata().getOriginalFilename() != null
					&& !vo.getFiledata().getOriginalFilename().equals("")) {
				// 파일이 존재하면
				String original_name = vo.getFiledata().getOriginalFilename();
				String ext = original_name.substring(original_name.lastIndexOf(".") + 1);
				// 파일 기본경로
				String defaultPath = request.getSession().getServletContext().getRealPath("/");
				// 파일 기본경로 _ 상세경로
				String path = defaultPath + "resource" + File.separator + "photo_upload" + File.separator;
				File file = new File(path);
				System.out.println("path:::::::::::" + path);
				// 디렉토리 존재하지 않을경우 디렉토리 생성
				if (!file.exists()) {
					file.mkdirs();
				}
				// 서버에 업로드 할 파일명(한글문제로 인해 원본파일은 올리지 않는것이 좋음)
				String realname = UUID.randomUUID().toString() + "." + ext;
				System.out.println("realname : "+realname);
				///////////////// 서버에 파일쓰기 /////////////////
				vo.getFiledata().transferTo(new File(path + realname));
				System.out.println("original : " +original_name);
				file_result += "&bNewLine=true&sFileName===" + original_name + "&sFileURL=/resource/photo_upload/"
						+ realname;
				System.out.println("여기");
			} else {
				file_result += "&errstr=error";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("끝???"+callback+" :::::: "+callback_func +" :::::: "+ file_result);
		return "redirect:" + callback + "?callback_func=" + callback_func + file_result;
		
	}
	
}