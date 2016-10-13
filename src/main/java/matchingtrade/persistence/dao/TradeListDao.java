package matchingtrade.persistence.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import matchingtrade.common.Criterion;
import matchingtrade.common.SearchCriteria;
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

	@Override
	protected Criteria buildSearchCriteria(SearchCriteria searchCriteria) {
		Criteria result = getCurrentSession().createCriteria(UserEntity.class);
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
