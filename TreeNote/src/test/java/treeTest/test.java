package treeTest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import treenote.domain.Tree;
import treenote.domain.User;
import treenote.service.tree.TreeService;
import treenote.service.user.UserService;

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations = {	"classpath:config/context-common.xml",
												"classpath:config/context-aspect.xml",
		"classpath:config/context-mybatis.xml",
		"classpath:config/context-transaction.xml" })
public class test {
	
	@Autowired
	@Qualifier("treeServiceImpl")
	private TreeService treeService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	//@Test
	public void testAddTree() throws Exception {
		Tree tree=new Tree();
		User user=new User();
		user.setUserNo(1000000);
		int userNo=user.getUserNo();
		
		tree.setTitle("정태또가출햇네");
		tree.setUserNo(userNo);

		System.out.println("JunitTest: addTree"+tree);
		
		treeService.addTree(tree);
		
		Assert.assertEquals("정태또가출햇네", tree.getTitle());
		Assert.assertEquals(userNo, tree.getUserNo());
	}
	
	//@Test
	public void testUpdateTitle() throws Exception {
		Tree tree=new Tree();
		String title="정태야정신차려";
		
		tree.setTreeNo(1000004);
		tree.setTitle(title);

		System.out.println("JunitTest: updatetitle"+tree);
		
		treeService.updateTitle(tree);
		
				
		Assert.assertEquals("정태야정신차려", tree.getTitle());
		Assert.assertEquals(1000004, tree.getTreeNo());
		
	}
	
	//@Test
	public void testRemoveTree() throws Exception {
		Tree tree=new Tree();
				
		int treeNo=1000004;
	
		System.out.println("JunitTest: testRemoveTree"+tree);
		
		treeService.removeTree(treeNo);
						
	}
	
	//@Test
	public void testGetTree() throws Exception {
		Tree tree=new Tree();
				
		int treeNo=1000000;
	
		System.out.println("JunitTest: testGetTree"+tree);
		
		//tree = treeService.getTree(treeNo);
		
		Assert.assertNotNull(tree);
						
	}
	
	//@Test
	public void testListTree() throws Exception {
		Tree tree=new Tree();
				
		int userNo=1000000;
	
		
		treeService.listTree(userNo);
		
						
	}
	
	

}
