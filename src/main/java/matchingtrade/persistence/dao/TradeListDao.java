package matchingtrade.persistence.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import matchingtrade.common.Criterion;
import matchingtrade.common.Pagination;
import matchingtrade.common.SearchCriteria;
import matchingtrade.common.SearchResult;
import matchingtrade.common.util.PersistenceUtil;
import matchingtrade.persistence.entity.TradeListEntity;
import matchingtrade.persistence.entity.UserEntity;

@Component
public class TradeListDao extends Dao<TradeListEntity> {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	protected Class<TradeListEntity> getEntityClass() {
		return TradeListEntity.class;
	}
	
	@Transactional
	public void save(TradeListEntity tradeListEntity) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(tradeListEntity);
	}
	
	@Transactional
	public TradeListEntity get(Integer tradeListId) {
		Session session = sessionFactory.getCurrentSession();
		return (TradeListEntity) session.get(TradeListEntity.class, tradeListId);
	}

	@Transactional
	public SearchResult<TradeListEntity> search(SearchCriteria searchCriteria) {
		Criteria mainCriteria = buildSearchCriteria(searchCriteria, getCurrentSession());
		Criteria paginationCriteria = buildSearchCriteria(searchCriteria, getCurrentSession());

		// Get pagination from paginationCriteria
		Pagination resultPagination = PersistenceUtil.getPagination(searchCriteria.getPagination(), paginationCriteria);
		// Apply pagination parameters to the main criteria
		PersistenceUtil.applyPaginationToCriteria(resultPagination, mainCriteria);
		// Set Result Transformer
		mainCriteria.setResultTransformer(new AliasToBeanResultTransformer(TradeListEntity.class));
		// List results
		@SuppressWarnings("unchecked")
		List<TradeListEntity> resultList = mainCriteria.list();
		// Return results
		SearchResult<TradeListEntity> result = new SearchResult<>(resultList, resultPagination);
		return result;
	}

	private Criteria buildSearchCriteria(SearchCriteria searchCriteria, Session session) {
		Criteria result = session.createCriteria(UserEntity.class);
		String tradeListsAlias = UserEntity.Field.tradeLists.toString();

		// Create Alias
		result.createAlias(UserEntity.Field.tradeLists.toString(), tradeListsAlias);

		// Projection List
		tradeListsAlias += ".";
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.property(tradeListsAlias + TradeListEntity.Field.name), TradeListEntity.Field.name.toString());
		pl.add(Projections.property(tradeListsAlias + TradeListEntity.Field.tradeListId), TradeListEntity.Field.tradeListId.toString());
		pl.add(Projections.property(tradeListsAlias + TradeListEntity.Field.updatedDateTime), TradeListEntity.Field.updatedDateTime.toString());		
		result.setProjection(pl);

		// Add Criterion
		for (Criterion c : searchCriteria.getCriteria()) {
			if (c.getField().equals(UserEntity.Field.userId)) {
				result.add(Restrictions.eq(UserEntity.Field.userId.toString(), c.getValue()));
			}
		}
		return result;
	}
	
	
	
	
}
