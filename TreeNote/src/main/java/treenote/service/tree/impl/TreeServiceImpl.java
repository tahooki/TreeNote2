package treenote.service.tree.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import treenote.domain.Keyword;
import treenote.domain.Tree;
import treenote.service.content.ContentDao;
import treenote.service.keyword.KeywordDao;
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
	@Qualifier("keywordDaoImpl")
	private KeywordDao keywordDao;

	@Autowired
	@Qualifier("contentDaoImpl")
	private ContentDao contentDao;
	
	@Autowired
	@Qualifier("replyDaoImpl")
	private ReplyDao replyDao;
	
	

	public void setTreeDao(TreeDao treeDao) {
		this.treeDao = treeDao;
	}

	public void setKeywordDao(KeywordDao keywordDao) {
		this.keywordDao = keywordDao;
	}

	public void setContentDao(ContentDao contentDao) {
		this.contentDao = contentDao;
	}

	/// Constructor
	public TreeServiceImpl() {
		System.out.println(this.getClass());
	}

	@Override
	public void addTree(Tree tree) throws Exception {
		System.out.println("Service.addTree::");
		
		treeDao.addTree(tree);
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
		
		List<Keyword> getKeyword= keywordDao.listTreeKeyword(treeNo);
		
		String jsonValue=new Gson().toJson(getKeyword);
				
		String jsonData="{\"class\":\"go.TreeModel\", \"nodeDataArray\":"+jsonValue+"}";
		
		System.out.println("[[[[[[[[[[[[[[[[[[[[["+jsonData);
		
		return jsonData;

	}

	@Override
	public List<Tree> listTree(int userNo) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Service.listTree::");
		
		return treeDao.listTree(userNo);

	}

}