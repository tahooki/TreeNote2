package treenote.web.keyword;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import treenote.domain.Keyword;
import treenote.service.keyword.KeywordService;

@Controller
@RequestMapping("/keyword/*")
public class KeywordController {

	/// Field
	@Autowired
	@Qualifier("keywordServiceImpl")
	private KeywordService keywordService;

	public KeywordController() {
		System.out.println(this.getClass());
	}
	//새로만들기
	@RequestMapping(value = "newKeyword")
	public void newKeyword(@RequestBody Keyword keyword, Model model) throws Exception{
		System.out.println("/newKeyword");
		model.addAttribute("keyword", keywordService.newKeyword(keyword));
	}
	
	//추가
	@RequestMapping(value = "addKeyword")
	public void addKeyword(@RequestBody Keyword keyword, Model model) throws Exception {
		System.out.println("/addKeyword");
		model.addAttribute("keyword", keywordService.addKeyword(keyword));
	}
	
	//키워드 교체
	@RequestMapping(value = "changeKeyword")
	public void changeKeyword(@RequestBody Keyword keyword, Model model) throws Exception{
		System.out.println("/changeKeyword");
		model.addAttribute("keyword", keywordService.changeKeyword(keyword));
	}

	// 수정
	@RequestMapping(value = "updateKeyword")
	public void updateKeyword(@RequestBody Keyword keyword, Model model) throws Exception {
		System.out.println("/updateKeyword");
		keywordService.updateKeyword(keyword);
	}

	// 삭제
	@RequestMapping(value = "removeKeyword")
	public void removeKeyword(@RequestBody Keyword Keyword, Model model) throws Exception {
		System.out.println("/removeKeyword");
		keywordService.removeKeyword(Keyword);
	}

	// 해당 키워드 리스트 불러오기
	@RequestMapping(value = "listSearchKeyword")
	public void listSearchKeyword(@RequestBody Keyword keyword, Model model) throws Exception {
		System.out.println("/listSearchKeyword"+keyword);
		model.addAttribute("list", keywordService.listSearchKeyword(keyword.getKeyword()));
	}

	// 자식 키워드 불러오기
	@RequestMapping(value = "listOnwerChildKeyword")
	public void listOnwerChildKeyword(@RequestBody Keyword keyword, Model model) throws Exception {
		System.out.println("/listOnwerChildKeyword");
		List<Keyword> list = keywordService.listOnwerChildKeyword(keyword);
		model.addAttribute("list", list);
	}
	
	//자동완성
	@RequestMapping(value = "autoComplete")
	public void autoComplete(Model model) throws Exception{
		System.out.println("/autoComplete");
		model.addAttribute("autoComplete", keywordService.autoComplete());
	}
}