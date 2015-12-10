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
		//int keywordNo = keyword.getKeywordNo();
		keyword.setKey(keywordNo);
		keywordDao.addKeyword(keyword);
		//content 복사
		return keywordDao.getKeyword(keywordNo);
	}
	
	@Override
	public int changeKeyword(Keyword keyword) throws Exception {
		// TODO Auto-generated method stub 
		// content 복사
		contentDao.removeContent(contentDao.getContent(keyword.getKey()).getContentNo());
		Content content = contentDao.getContent(keyword.getCopyNo());
		if(content != null){
			content.setKeywordNo(keyword.getKey());
			content.setScrap(0);
			contentDao.copyContent(content);
		}
		return keywordDao.updateKeyword(keyword);
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
	public List<Keyword> listOnwerChildKeyword(Keyword keyword) throws Exception {
		// TODO Auto-generated method stub

		List<Keyword> OnwerList = keywordDao.listChildKeyword(keyword.getCopyNo());
		for (Keyword onwerKeyword : OnwerList) {
			onwerKeyword.setKey(keywordDao.getKeywrodNo());
			onwerKeyword.setTreeNo(keyword.getTreeNo());
			onwerKeyword.setParent(keyword.getKey());
			keywordDao.addKeyword(onwerKeyword);
			Content content = contentDao.getContent(onwerKeyword.getKey());
			if(content != null){
				content.setKeywordNo(keyword.getKey());
				content.setScrap(0);
				contentDao.addContent(content);
			}
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
	public List<String> autoComplete() throws Exception {
		// TODO Auto-generated method stub
		return keywordDao.autoComplete();
	}
}