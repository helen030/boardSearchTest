import demos.common.web.commons.paging.Criteria;
import demos.common.web.commons.paging.SearchCriteria;
import demos.common.web.domain.ArticleVO;
import demos.common.web.domain.MemberVO;
import demos.common.web.persistence.ArticleDAO;
import demos.common.web.persistence.MemberDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring-config/application-context.xml"})
public class ArticleDAOTest {

    private static final Logger logger = LoggerFactory.getLogger(ArticleDAOTest.class);

    @Inject
    private ArticleDAO articleDAO;

    @Test
    public void tesCreate() throws Exception {

        logger.info("start - logger msg");

        ArticleVO article = new ArticleVO();
        article.setTitle("새글 작성 테스트 제목");
        article.setContent("새글 작성 테스트 내용");
        article.setWriter("새글 작성자");

        logger.info("end - logger msg");

        articleDAO.create(article);
    }

    @Test
    public void testUpdate() throws Exception {
        ArticleVO article = new ArticleVO();
        article.setArticleNo(1);
        article.setTitle("글 수정 테스트 제목");
        article.setContent("글 수정 테스트 내용");
        article.setWriter("글 수정 작성자");
        articleDAO.update(article);
    }

    @Test
    public void testRead() throws Exception{
        logger.info(articleDAO.read(1).toString());
    }

    @Test
    public void testDelete() throws Exception {
        articleDAO.delete(1);
    }


    @Test
    public void tesTCreate() throws Exception {

        logger.info("start - logger msg");
        for(int a = 0 ; a < 1000 ; a++){
            ArticleVO article = new ArticleVO();
            article.setTitle(a + "번째 " + "제목");
            article.setContent(a + "번째 " + "내용");
            article.setWriter(a + "번째 " + "작성자");
            articleDAO.create(article);
        }
        logger.info("end - logger msg");

    }

    @Test
    public void testListPaging() throws Exception{
        int page = 3;
        List<ArticleVO> articles = articleDAO.listPaging(page);
        for(ArticleVO ar: articles){
          logger.info(ar.getArticleNo() + ":" + ar.getTitle());
        }
    }

    @Test
    public void testListCriteria() throws Exception{
        Criteria criteria = new Criteria();
        criteria.setPage(3);
        criteria.setPerPageNum(20);

        List<ArticleVO> articles = articleDAO.listCriteria(criteria);

        for(ArticleVO ar: articles){
            logger.info(ar.getArticleNo() + ":" + ar.getTitle());
        }
    }

    @Test
    public void testDynamic1() throws Exception {

        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setPage(1);
        searchCriteria.setKeyword("제목");
        searchCriteria.setSearchType("t");

        logger.info("======================");

        List<ArticleVO> articles = articleDAO.listSearch(searchCriteria);

        for (ArticleVO article : articles) {
            logger.info(article.getArticleNo() + " : " + article.getTitle());
        }

        logger.info("======================");

        logger.info("searched articles count : " + articleDAO.countSearchedArticles(searchCriteria));
    }

}