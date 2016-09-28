package matchingtrade.persistence.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import matchingtrade.common.Criterion;
import matchingtrade.common.Pagination;
import matchingtrade.common.SearchCriteria;
import matchingtrade.common.SearchResult;
import matchingtrade.common.util.PersistenceUtil;
import matchingtrade.persistence.entity.TradeItemEntity;
import matchingtrade.persistence.entity.TradeListEntity;
import matchingtrade.persistence.entity.UserEntity;

@Component
public class TradeItemDao extends Dao<TradeItemEntity> {

	@Override
	protected Class<TradeItemEntity> getEntityClass() {
		return TradeItemEntity.class;
	}

	@Transactional
	public SearchResult<TradeItemEntity> search(SearchCriteria searchCriteria) {
		Session session = getCurrentSession();
		Criteria ct = session.createCriteria(UserEntity.class);

		// Create Alias
		ct.createAlias(UserEntity.Field.tradeLists.toString(), "tl");
		ct.createAlias(UserEntity.Field.tradeLists.toString() + "." + TradeListEntity.Field.tradeItems.toString(), "ti");

		// Projection List
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.property("ti."+TradeItemEntity.Field.tradeItemId.toString()), TradeItemEntity.Field.tradeItemId.toString());
		pl.add(Projections.property("ti."+TradeItemEntity.Field.name.toString()), TradeItemEntity.Field.name.toString());
		pl.add(Projections.property("ti."+TradeItemEntity.Field.description.toString()), TradeItemEntity.Field.description.toString());
		ct.setProjection(pl);

		// Add Criterion
		for (Criterion c : searchCriteria.getCriteria()) {
			if (c.getField().equals(UserEntity.Field.userId)) {
				ct.add(Restrictions.eq(UserEntity.Field.userId.toString(), c.getValue()));
			}
		}

		// Set Result Transformer
		ct.setResultTransformer(new AliasToBeanResultTransformer(TradeItemEntity.class));

		// Apply Pagination 
//		Pagination resultPagination = PersistenceUtil.getPagination(searchCriteria.getPagination(), ct);

		// Apply Order By
//		PersistenceUtil.applyOrderBy(searchCriteria, ct);
		
		// List results
		@SuppressWarnings("unchecked")
		List<TradeItemEntity> resultList = ct.list();
		
		// Return results
		SearchResult<TradeItemEntity> result = new SearchResult<>(resultList, searchCriteria.getPagination());
		return result;
	}

}
