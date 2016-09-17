package matchingtrade.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import matchingtrade.common.SearchCriteria;
import matchingtrade.persistence.dao.TradeItemDao;
import matchingtrade.persistence.entity.TradeItemEntity;
import matchingtrade.service.json.TradeItemJson;
import matchingtrade.test.random.TradeItemRandom;
import matchingtrade.transformer.TradeItemTransformer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/application-context-web.xml"})
public class PaginationTest {
	
	@Autowired
	private TradeItemDao tradeItemDao;
	
	@Before
	public void before() {
		// Create five TradeItems to test pagination
		TradeItemTransformer transformer = new TradeItemTransformer();
		TradeItemRandom random = new TradeItemRandom();
		for (int i = 0; i < 5; i++) {
			TradeItemJson tij = random.get();
			TradeItemEntity tie = transformer.transform(tij);
			tradeItemDao.save(tie);
		}
	}

	@Test
	@Rollback(false)
	public void page1Limit3() {
		SearchCriteria sc = new SearchCriteria(1, 3);
		List<TradeItemEntity> result = tradeItemDao.get(sc).getResultList();
		assertNotNull(result);
		assertEquals(3, result.size());
		for (int i=0; i<result.size(); i++) {
			TradeItemEntity tie = result.get(i);
			assertEquals(i+1, tie.getTradeItemId().intValue());
		}
	}

	@Test
	@Rollback(false)
	public void page2Limit1() {
		SearchCriteria sc = new SearchCriteria(2, 1);
		List<TradeItemEntity> result = tradeItemDao.get(sc).getResultList();
		assertNotNull(result);
		assertEquals(1, result.size());
		TradeItemEntity tie = result.get(0);
		assertEquals(2, tie.getTradeItemId().intValue());
	}

	@Test
	@Rollback(false)
	public void page2Limit2() {
		SearchCriteria sc = new SearchCriteria(2, 2);
		List<TradeItemEntity> result = tradeItemDao.get(sc).getResultList();
		assertNotNull(result);
		assertEquals(2, result.size());
		for (int i=0; i<result.size(); i++) {
			TradeItemEntity tie = result.get(i);
			assertEquals(i+3, tie.getTradeItemId().intValue());
		}
	}
}
