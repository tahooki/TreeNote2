package treenote.service.tree.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import treenote.domain.Content;
import treenote.domain.Keyword;
import treenote.domain.Tree;
import treenote.service.content.ContentDao;
import treenote.service.keyword.KeywordDao;
import treenote.service.keyword.KeywordService;
import treenote.service.reply.ReplyDao;
import treenote.service.tree.TreeDao;
import treenote.service.tree.TreeService;;

@Service("treeServiceImpl")
public class TreeServiceImpl implements TreeService {

	/// Field
	@Autowired
	@Qualifier("treeDaoImpl")
	private TreeDao treeDao;

	@Autowired
	@Qualifier("keywordServiceImpl")
	private KeywordService keywordService;

	@Autowired
	@Qualifier("keywordDaoImpl")
	private KeywordDao keywordDao;

	public void setTreeDao(TreeDao treeDao) {
		this.treeDao = treeDao;
	}

	public void setKeywordService(KeywordService keywordService) {
		this.keywordService = keywordService;
	}

	public void setKeywordDao(KeywordDao keywordDao) {
		this.keywordDao = keywordDao;
	}

	/// Constructor
	public TreeServiceImpl() {
		System.out.println(this.getClass());
	}

	//추가 !! 키워드도 같이 생성 - by.Tahooki
	@Override
	public int addTree(Tree tree) throws Exception {
		System.out.println("Service.addTree::");
		
		int treeNo = treeDao.getTreeNo();
		tree.setTreeNo(treeNo);
		tree.setTitle("새 트리");
		tree.setRootkey(0);
		
		treeDao.addTree(tree);
		Keyword keyword = new Keyword();
		keyword.setKeyword("키워드");
		keyword.setParent(0);
		keyword.setTreeNo(treeNo);
		keyword.setColor("#2ECC71");
		keyword.setCollapse(0);
		
		tree.setRootkey(((Keyword)keywordService.newKeyword(keyword)).getKey());
		treeDao.updateTitle(tree);		
		return treeNo;
	}

	@Override
	public int updateTitle(Tree tree) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Service.updateTitle::");

		treeDao.updateTitle(tree);
		return 0;
	}

	@Override
	public void removeTree(int treeNo) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Service.removeTree::");
		treeDao.removeTree(treeNo);
	}

	@Override
	public String getTree(int treeNo) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Service.getTree::");

		List<Keyword> getKeyword = keywordDao.listTreeKeyword(treeNo);

		String jsonValue = new Gson().toJson(getKeyword);

		String jsonData = "{\"class\":\"go.TreeModel\", \"nodeDataArray\":" + jsonValue + "}";

		System.out.println("[[[[[[[[[[[[[[[[[[[[[" + jsonData);

		return jsonData;

	}

	@Override
	public List<Tree> listTree(int userNo) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Service.listTree::");

		return treeDao.listTree(userNo);

	}

}