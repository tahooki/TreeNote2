package treenote.service.keyword.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import treenote.domain.Content;
import treenote.domain.Keyword;
import treenote.domain.Search;
import treenote.service.content.ContentDao;
import treenote.service.keyword.KeywordDao;
import treenote.service.keyword.KeywordService;
import treenote.service.tree.TreeDao;
import treenote.service.user.UserDao;;

@Service("keywordServiceImpl")
public class KeywordServiceImpl implements KeywordService {

	/// Field
	@Autowired
	@Qualifier("keywordDaoImpl")
	private KeywordDao keywordDao;

	@Autowired
	@Qualifier("contentDaoImpl")
	private ContentDao contentDao;
	
	@Autowired
	@Qualifier("userDaoImpl")
	private UserDao userDao;

	@Autowired
	@Qualifier("treeDaoImpl")
	private TreeDao treeDao;

	public void setKeywordDao(KeywordDao keywordDao) {
		this.keywordDao = keywordDao;
	}

	public void setContentDao(ContentDao contentDao) {
		this.contentDao = contentDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setTreeDao(TreeDao treeDao) {
		this.treeDao = treeDao;
	}

	/// Constructor
	public KeywordServiceImpl() {
		System.out.println(this.getClass());
	}

	@Override
	public Keyword newKeyword(Keyword keyword) throws Exception {
		// TODO Auto-generated method stub
		int keywordNo = keywordDao.getKeywrodNo();
		//int keywordNo = keyword.getKeywordNo();
		keyword.setKey(keywordNo);
		keyword.setCopyNo(keywordNo);
		keywordDao.addKeyword(keyword);
		//content 생성?
		Content content = new Content();
		content.setKeywordNo(keywordNo);
		content.setScrap(0);
		content.setContent("내용을 입력해주세요 !");
		contentDao.addContent(content);
		return keywordDao.getKeyword(keywordNo);
	}
	
	@Override
	public Keyword addKeyword(Keyword keyword) throws Exception {
		// TODO Auto-generated method stub
		int keywordNo = keywordDao.getKeywrodNo();
		Keyword addKeyword = keywordDao.getKeyword(keyword.getKey());
		addKeyword.setKey(keywordNo);
		addKeyword.setParent(keyword.getParent());
		addKeyword.setTreeNo(keyword.getTreeNo());
		addKeyword.setCollapse(0);
		keywordDao.addKeyword(addKeyword);
		//content 복사
		Content content = contentDao.getContent(keyword.getKey());
		if(keywordDao.getUserNoKeyword(keywordNo) != keywordDao.getUserNoKeyword(keyword.getKey())){
			content.setScrap(content.getScrap()+1);
			contentDao.updateScrapContent(content);
			System.out.println("content : "+ content);
			if(content.getOriginContentList() == null){
				content.setOriginContentList(""+((Content)contentDao.getContent(keyword.getKey())).getContentNo());
				System.out.println("null "+((Content)contentDao.getContent(keyword.getKey())).getContentNo());
			}else{
				//출처 담는 부분
				content.setOriginContentList(content.getOriginContentList()+","+((Content)contentDao.getContent(keyword.getKey())).getContentNo());
			}
		}

		content.setKeywordNo(keywordNo);
		content.setScrap(0);
		contentDao.addContent(content);
		return keywordDao.getKeyword(keywordNo);
	}
	
	@Override
	public int changeKeyword(Keyword keyword) throws Exception {
		// TODO Auto-generated method stub 
		// content 복사
		
		Content content = contentDao.getContent(keyword.getCopyNo());
		Keyword copyKeyword = keywordDao.getKeyword(keyword.getCopyNo()); 
		
		if(keywordDao.getUserNoKeyword(keyword.getKey()) != keywordDao.getUserNoKeyword(keyword.getCopyNo())){
			contentDao.removeContent(keyword.getKey());
			content.setScrap(content.getScrap()+1);
			contentDao.updateScrapContent(content);
			
			if(content.getOriginContentList() == null){
				content.setOriginContentList(""+((Content)contentDao.getContent(keyword.getCopyNo())).getContentNo());
			}else{
				//출처 담는 부분
				content.setOriginContentList(content.getOriginContentList()+","+((Content)contentDao.getContent(keyword.getCopyNo())).getContentNo());
			}
		}
		content.setKeywordNo(keyword.getKey());
		content.setScrap(0);
		contentDao.addContent(content);
		
		copyKeyword.setKey(keyword.getKey());
		copyKeyword.setParent(keyword.getParent());
		copyKeyword.setTreeNo((keyword.getTreeNo()));
		return keywordDao.updateKeyword(copyKeyword);
	}

	@Override
	public int updateKeyword(Keyword keyword) throws Exception {
		// TODO Auto-generated method stub
		return keywordDao.updateKeyword(keyword);
	}

	@Override
	public void removeKeyword(Keyword keyword) throws Exception {
		keywordDao.removeKeyword(keyword);
	}

	@Override
	public List<Search> listSearchKeyword(String keyword) throws Exception {
		// TODO Auto-generated method stub
		List<Search> searchList = new ArrayList<Search>();
		
		List<Keyword> keywordList = keywordDao.listSearchKeyword(keyword);
		for (Keyword temp : keywordList){
			Search search = new Search();
			search.setKeyword(keywordDao.getKeyword(temp.getKey()));
			search.setParentKeyword(keywordDao.getKeyword(temp.getParent()));
			search.setChildKeywordList(keywordDao.listChildKeyword(temp.getKey()));
			search.setUser(userDao.getUser(treeDao.getTree(temp.getTreeNo()).getUserNo()));
			searchList.add(search);
		}
		
		return searchList;
	}
	@Override
	public List<Search> listSearchKeyword(String keyword, int count) throws Exception {
		// TODO Auto-generated method stub
		List<Search> searchList = new ArrayList<Search>();
		
		List<Keyword> keywordList = keywordDao.listSearchKeyword(keyword, count);
		for (Keyword temp : keywordList){
			Search search = new Search();
			search.setKeyword(keywordDao.getKeyword(temp.getKey()));
			search.setParentKeyword(keywordDao.getKeyword(temp.getParent()));
			search.setChildKeywordList(keywordDao.listChildKeyword(temp.getKey()));
			search.setUser(userDao.getUser(treeDao.getTree(temp.getTreeNo()).getUserNo()));
			searchList.add(search);
		}
		
		return searchList;
	}
	
	@Override
	public List<Search> listClipBoardKeyword(String keyList) throws Exception {
		// TODO Auto-generated method stub
		List<Search> searchList = new ArrayList<Search>();
		String keywordNoList[]= keyList.split(",");
		
		for(int i = 0 ; i < keywordNoList.length ; i++){
			Search search = new Search();
			Keyword temp = keywordDao.getKeyword(Integer.parseInt(keywordNoList[i]));
			search.setKeyword(keywordDao.getKeyword(temp.getKey()));
			search.setParentKeyword(keywordDao.getKeyword(temp.getParent()));
			search.setChildKeywordList(keywordDao.listChildKeyword(temp.getKey()));
			search.setUser(userDao.getUser(treeDao.getTree(temp.getTreeNo()).getUserNo()));
			searchList.add(search);
		}
		return searchList;
	}

	@Override
	public List<Keyword> listOnwerChildKeyword(Keyword keyword) throws Exception {
		// TODO Auto-generated method stub

		List<Keyword> OnwerList = keywordDao.listChildKeyword(keyword.getCopyNo());
		System.out.println("으아아"+OnwerList);
		for (Keyword onwerKeyword : OnwerList) {
			Content content = contentDao.getContent(onwerKeyword.getKey());
			
			content.setScrap(content.getScrap()+1);
			contentDao.updateScrapContent(content);
			
			if(content.getOriginContentList() == null){
				content.setOriginContentList(""+((Content)contentDao.getContent(onwerKeyword.getKey())).getContentNo());
			}else{
				//출처 담는 부분
				content.setOriginContentList(content.getOriginContentList()+","+((Content)contentDao.getContent(onwerKeyword.getKey())).getContentNo());
			}
			
			onwerKeyword.setKey(keywordDao.getKeywrodNo());
			onwerKeyword.setTreeNo(keyword.getTreeNo());
			onwerKeyword.setParent(keyword.getKey());
			keywordDao.addKeyword(onwerKeyword);
			
			
			if(content != null){
				content.setKeywordNo(onwerKeyword.getKey());
				content.setScrap(0);
				contentDao.addContent(content);
			}
			System.out.println("listOnwerChildKeyword : "+content);
		}
		return keywordDao.listChildKeyword(keyword.getKey());
	}

	@Override
	public List<Search> listTimeLineKeyword(int userNo) throws Exception {
		// TODO Auto-generated method stub
		List<Search> searchList = new ArrayList<Search>();
		
		List<Keyword> keywordList = keywordDao.listTimeLineKeyword(userNo);
		for (Keyword temp : keywordList){
			Search search = new Search();
			search.setKeyword(keywordDao.getKeyword(temp.getKey()));
			search.setParentKeyword(keywordDao.getKeyword(temp.getParent()));
			search.setChildKeywordList(keywordDao.listChildKeyword(temp.getKey()));
			search.setUser(userDao.getUser(treeDao.getTree(temp.getTreeNo()).getUserNo()));
			searchList.add(search);
		}
		return searchList;
	}
	@Override
	public List<Search> listTimeLineKeyword(int userNo, int count) throws Exception {
		// TODO Auto-generated method stub
		List<Search> searchList = new ArrayList<Search>();
		
		List<Keyword> keywordList = keywordDao.listTimeLineKeyword(userNo, count);
		for (Keyword temp : keywordList){
			Search search = new Search();
			search.setKeyword(keywordDao.getKeyword(temp.getKey()));
			search.setParentKeyword(keywordDao.getKeyword(temp.getParent()));
			search.setChildKeywordList(keywordDao.listChildKeyword(temp.getKey()));
			search.setUser(userDao.getUser(treeDao.getTree(temp.getTreeNo()).getUserNo()));
			searchList.add(search);
		}
		return searchList;
	}
	

	@Override
	public List<String> autoComplete() throws Exception {
		// TODO Auto-generated method stub
		return keywordDao.autoComplete();
	}

	@Override
	public List<Keyword> getMyKeyword(int userNo) {
		// TODO Auto-generated method stub
		return keywordDao.getMyKeyword(userNo);
	}
	
	@Override
	public int totalKeyword() throws Exception {
		// TODO Auto-generated method stub
		return keywordDao.totalKeyword();
	}
}