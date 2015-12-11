package treenote.web.content;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import treenote.domain.Content;
import treenote.domain.Keyword;
import treenote.domain.Photo;
import treenote.domain.User;
import treenote.service.content.ContentService;

@Controller
@RequestMapping("/content/*")
public class ContentController {

	/// Field
	@Autowired
	@Qualifier("contentServiceImpl")
	private ContentService contentService;

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
		
		contentService.removeContent(keywordNo);
		System.out.println("remove success!!!!!!");
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
	public void getContent(@PathVariable int keywordNo, HttpSession session, Model model) throws Exception{
		System.out.println("/getContent");
		
		User user = (User)session.getAttribute("user");
				
		Content content = new Content();
		content = contentService.getContent(keywordNo);
		
		System.out.println("111111111111 "+content);
		
		model.addAttribute("content", content);
		model.addAttribute("user", user);
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