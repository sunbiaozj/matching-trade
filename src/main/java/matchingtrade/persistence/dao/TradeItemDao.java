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

import matchingtrade.common.SearchCriteria;
import matchingtrade.common.SearchResult;
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
		Session session = getCurrentSession();
		Criteria ct = session.createCriteria(TradeListEntity.class);

//		Pagination resultPagination = PersistenceUtil.getPagination(searchCriteria.getPagination(), ct);
//		PersistenceUtil.applyOrderBy(searchCriteria, ct);

		ct.createAlias("tradeItems", "ti");
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.property("ti.tradeItemId"), "tradeItemId");
		pl.add(Projections.property("ti.name"), "name");
		
		ct.add(Restrictions.eq("tradeListId", 1));
		
		ct.setProjection(pl);
		
		ct.setResultTransformer(new AliasToBeanResultTransformer(TradeItemEntity.class));
		
		
		
		List<TradeItemEntity> resultList = ct.list();

//		SearchResult<TradeItemEntity> result = new SearchResult<>(resultList, resultPagination);
		SearchResult<TradeItemEntity> result = new SearchResult<>(resultList, searchCriteria.getPagination());
		return result;
	}

}
