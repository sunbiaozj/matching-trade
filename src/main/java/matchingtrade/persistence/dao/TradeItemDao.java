package matchingtrade.persistence.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import matchingtrade.common.Criterion;
import matchingtrade.common.Pagination;
import matchingtrade.common.SearchCriteria;
import matchingtrade.common.SearchResult;
import matchingtrade.common.util.PersistenceUtil;
import matchingtrade.persistence.entity.TradeItemEntity;
import matchingtrade.persistence.entity.TradeListEntity;

@Component
public class TradeItemDao extends Dao<TradeItemEntity> {

	@Override
	protected Class<TradeItemEntity> getEntityClass() {
		return TradeItemEntity.class;
	}

	@Transactional
	public SearchResult<TradeItemEntity> search(SearchCriteria searchCriteria) {
		Criteria mainCriteria = buildSearchCriteria(searchCriteria, getCurrentSession());
		Criteria paginationCriteria = buildSearchCriteria(searchCriteria, getCurrentSession());
		// Get pagination from paginationCriteria
		Pagination resultPagination = PersistenceUtil.getPagination(searchCriteria.getPagination(), paginationCriteria);
		// Apply pagination parameters to the main criteria
		PersistenceUtil.applyPaginationToCriteria(resultPagination, mainCriteria);
		// Set Result Transformer
		mainCriteria.setResultTransformer(new AliasToBeanResultTransformer(TradeItemEntity.class));
		// List results
		@SuppressWarnings("unchecked")
		List<TradeItemEntity> resultList = mainCriteria.list();
		// Return results
		SearchResult<TradeItemEntity> result = new SearchResult<>(resultList, resultPagination);
		return result;
	}

	private Criteria buildSearchCriteria(SearchCriteria searchCriteria, Session session) {
		Criteria result = session.createCriteria(TradeListEntity.class);
		String tradeItemAlias = TradeListEntity.Field.tradeItems.toString();

		// Create Alias
		result.createAlias(TradeListEntity.Field.tradeItems.toString(), tradeItemAlias);

		// Projection List
		tradeItemAlias+=".";
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.property(tradeItemAlias + TradeItemEntity.Field.tradeItemId), TradeItemEntity.Field.tradeItemId.toString());
		pl.add(Projections.property(tradeItemAlias + TradeItemEntity.Field.name), TradeItemEntity.Field.name.toString());
		pl.add(Projections.property(tradeItemAlias + TradeItemEntity.Field.description), TradeItemEntity.Field.description.toString());
		result.setProjection(pl);

		// Add Criterion
		for (Criterion c : searchCriteria.getCriteria()) {
			if (c.getField().equals(TradeListEntity.Field.tradeListId)) {
				result.add(Restrictions.eq(TradeListEntity.Field.tradeListId.toString(), c.getValue()));
			}
		}
		return result;
	}

}
