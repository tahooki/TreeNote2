package treeTest;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import treenote.domain.Keyword;
import treenote.service.keyword.KeywordDao;
import treenote.service.keyword.KeywordService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/context-common.xml", "classpath:config/context-aspect.xml",
		"classpath:config/context-mybatis.xml", "classpath:config/context-transaction.xml" })
public class testKeyword {

	@Autowired
	@Qualifier("keywordServiceImpl")
	private KeywordService keywordService;

	@Autowired
	@Qualifier("keywordDaoImpl")
	private KeywordDao keywordDao;

	// 추가
	//@Test
	public void testAddKeyword() throws Exception {
		Keyword	keyword = new Keyword();
		keyword.setKey(1000020);
		keyword.setKeyword("임시데이터");
		keyword.setCollapse(0);
		keyword.setCopyNo(1000000);
		keyword.setParent(1000000);
		keyword.setTreeNo(1000000);
		
		Keyword reKeyword = keywordService.addKeyword(keyword);
		
		Assert.assertEquals("임시데이터", reKeyword.getKeyword());
		Assert.assertEquals(0, reKeyword.getCollapse());
		Assert.assertEquals(1000000, reKeyword.getCopyNo());
		Assert.assertEquals(1000000, reKeyword.getParent());
		Assert.assertEquals(1000000, reKeyword.getTreeNo());
	}

	// 수정
	//@Test
	public void testUpdateKeyword() throws Exception {
		Keyword	keyword = new Keyword();
		keyword.setKey(1000017);
		keyword.setKeyword("임시데이터2");
		keyword.setCollapse(0);
		keyword.setCopyNo(1000001);
		keyword.setParent(1000001);
		keyword.setTreeNo(1000001);
		
		keywordService.updateKeyword(keyword);
		Keyword reKeyword = keywordDao.getKeyword(1000017);
		Assert.assertEquals("임시데이터2", reKeyword.getKeyword());
		Assert.assertEquals(0, reKeyword.getCollapse());
		Assert.assertEquals(1000001, reKeyword.getCopyNo());
		Assert.assertEquals(1000001, reKeyword.getParent());
		Assert.assertEquals(1000001, reKeyword.getTreeNo());
	}

	// 삭제
	//@Test
	public void testRemoveKeyword() throws Exception {
		Keyword	keyword = new Keyword();
		keyword.setKey(1000017);
		keyword.setKeyword("임시데이터2");
		keyword.setCollapse(0);
		keyword.setCopyNo(1000001);
		keyword.setParent(1000001);
		keyword.setTreeNo(1000001);
		
		keywordService.removeKeyword(keyword);
	}

	// 해당 키워드 리스트 불러오기
	//@Test
	public void testListTimeLineKeyword() throws Exception {
		List<Keyword> list = keywordDao.listTimeLineKeyword(1000000);
		for (Keyword keyword : list) {
			System.out.println(keyword);
		}
	}

	// 복사
	@Test
	public void testCopyKeyword() throws Exception {

	}

	// 해당 트리 리스트 불러오기
	//@Test
	public void testListTreeKeyword() throws Exception {
		List<Keyword> list = keywordDao.listTreeKeyword(1000000);
		for (Keyword keyword : list) {
			System.out.println(keyword);
		}
	}
 
	//자식 키워드 불러오기???
	public void listChildKeyword() throws Exception{
		
	}
	
	//@Test
	public void autoComplete() throws Exception{
		List<String> list = keywordService.autoComplete();
		for (String keyword : list) {
			System.out.println(keyword);
		}
	}
}
