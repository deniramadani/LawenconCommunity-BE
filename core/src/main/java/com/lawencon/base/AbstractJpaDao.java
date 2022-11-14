package com.lawencon.base;

import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;

import com.lawencon.model.SearchQuery;
import com.lawencon.security.principal.PrincipalService;

/**
 * 
 * @author Agung Damas Saputra
 * 
 */
public class AbstractJpaDao {
	
	@Autowired
	private PrincipalService principalService;
	
	public <T> T getById(Class<T> entityClass, final Object id) {
		T data = em().find(entityClass, id);
		return data;
	}
	
	public <T> T getByIdAndDetach(Class<T> entityClass, final Object id) {
		T data = em().find(entityClass, id);
		if (data != null) {
			em().detach(data);
		}
		return data;
	}

	public <T> Long countAll(Class<T> entityClass) {
		return countAll(entityClass, null, null, null);
	}
	
	public <T> Long countAll(Class<T> entityClass, 
			String whereClause, 
			String[] parameterNames, String[] parameterValues) {
		Query query = em().createQuery(
				"SELECT COUNT(*) FROM " + entityClass.getName() + (whereClause != null ? " " + whereClause : ""));

		if (parameterNames != null && parameterValues != null) {
			for (int i = 0; i < parameterValues.length; i++) {
				query.setParameter(parameterNames[i], parameterValues[i]);
			}
		}

		return (Long) query.getSingleResult();
	}
	
	public <T> List<T> getAll(Class<T> entityClass) {
		return getAll(entityClass, null, null);
	}

	public <T> List<T> getAll(Class<T> entityClass, 
			Integer startPosition, Integer limit) {		
		return getAll(entityClass, null, null, null, startPosition, limit);
	}
	
	public <T> List<T> getAll(Class<T> entityClass, 
			String whereClause, 
			String[] parameterNames, String[] parameterValues, 
			Integer startPosition, Integer limit) {
		TypedQuery<T> typedQuery = em().createQuery(
				"FROM " + entityClass.getName() + (whereClause != null ? " " + whereClause : ""), entityClass);

		if (parameterNames != null && parameterValues != null) {
			for (int i = 0; i < parameterValues.length; i++) {
				typedQuery.setParameter(parameterNames[i], parameterValues[i]);
			}
		}

		if (startPosition != null)
			typedQuery.setFirstResult(startPosition);
		if (limit != null)
			typedQuery.setMaxResults(limit);

		return typedQuery.getResultList();
	}
	
	public <T> SearchQuery<T> searchQuery(Class<T> entityClass, 
			String whereClause, String[] parameterNames, String[] parameterValues, 
			Integer startPosition, Integer limit) {
		List<T> resultData = getAll(entityClass, whereClause, parameterNames, parameterValues, startPosition, limit);
		Integer countData = countAll(entityClass, whereClause, parameterNames, parameterValues).intValue();
		
		SearchQuery<T> data = new SearchQuery<>();
		data.setData(resultData);
		data.setCount(countData);

		return data;
	}
	
	public <T> SearchQuery<T> searchQuery(Class<T> entityClass, 
			String textQuery, 
			int startPosition, int limit,
			String... fields) {

		String[] querySplit = extractQuery(textQuery);
		
		StringBuilder whereCriteriaBuilder = new StringBuilder("");
		
		for (String field : fields) {
			String fieldTrim = field.trim();
			for (String subQuery : querySplit) {				
				whereCriteriaBuilder.append("OR LOWER")
					.append("(")
					.append(fieldTrim)
					.append(") ")
					.append("LIKE CONCAT('%'")
					.append(",")
					.append("'")
					.append(subQuery.toLowerCase())
					.append("'")
					.append(",")
					.append("'%')");
			}
		}
		
		String whereCriteria = whereCriteriaBuilder.toString().replaceFirst("OR", "");
			
		Long resultCount = (Long) em()
				.createQuery("SELECT COUNT(*) FROM " + entityClass.getName() + (whereCriteria != null ? " WHERE " + whereCriteria : ""))
				.getSingleResult();
		
		List<T> resultData = em().createQuery("FROM " + entityClass.getName() + (whereCriteria != null ? " WHERE " + whereCriteria : ""), entityClass)
				.setFirstResult(startPosition)
				.setMaxResults(limit)
				.getResultList();
		
		SearchQuery<T> data = new SearchQuery<>();
		data.setData(resultData);
		data.setCount(resultCount.intValue());

		return data;
	}

	public <T extends BaseEntity> SearchQuery<T> getAll(Class<T> entityClass, 
			String textQuery, 
			Integer startPosition, Integer limit,
			String... fields) throws Exception {
		SearchQuery<T> sq = new SearchQuery<>();
		List<T> data = null;

		if (startPosition == null || limit == null) {
			data = getAll(entityClass);
			sq.setData(data);
		} else {
			if (textQuery == null) {
				data = getAll(entityClass, startPosition, limit);
				Integer count = countAll(entityClass).intValue();

				sq.setData(data);
				sq.setCount(count);
			} else {
				return searchQuery(entityClass, textQuery, startPosition, limit, fields);
			}
		}

		return sq;
	}

	public <T extends BaseEntity> T save(T entity) throws Exception {
		if (entity.getId() != null) {
			entity.setUpdatedBy(principalService.getAuthPrincipal());
			entity = em().merge(entity);
		} else {
			entity.setCreatedBy(principalService.getAuthPrincipal());
			em().persist(entity);
		}

		return entity;
	}
	
	public <T extends BaseEntity> T saveAndFlush(T entity) throws Exception {
		if (entity.getId() != null) {
			entity.setUpdatedBy(principalService.getAuthPrincipal());
			entity = em().merge(entity);
		} else {
			entity.setCreatedBy(principalService.getAuthPrincipal());
			em().persist(entity);
		}
		em().flush();

		return entity;
	}
	
	public <T extends BaseEntity> T saveNoLogin(T entity, 
			Supplier<String> getIdFunc) throws Exception {
		if (getIdFunc == null)
			throw new Exception("You must supply the String ID");
		
		if (entity.getId() != null) {
			if (getIdFunc.get() == null)
				throw new Exception("Updated By is NULL");
			entity.setUpdatedBy(getIdFunc.get());
			entity = em().merge(entity);
		} else {
			if (getIdFunc.get() == null)
				throw new Exception("Created By is NULL");
			entity.setCreatedBy(getIdFunc.get());
			em().persist(entity);
		}

		return entity;
	}
	
	public <T extends BaseEntity> T saveNoLoginAndFlush(T entity, 
			Supplier<String> getIdFunc) throws Exception {
		if (getIdFunc == null)
			throw new Exception("You must supply the String ID");
		
		if (entity.getId() != null) {
			if (getIdFunc.get() == null)
				throw new Exception("Updated By is NULL");
			entity.setUpdatedBy(getIdFunc.get());
			entity = em().merge(entity);
		} else {
			if (getIdFunc.get() == null)
				throw new Exception("Created By is NULL");
			entity.setCreatedBy(getIdFunc.get());
			em().persist(entity);
		}
		em().flush();

		return entity;
	}

	public <T> void delete(final T entity) throws Exception {
		em().remove(entity);
	}

	public <T> boolean deleteById(Class<T> entityClass, 
			final Object entityId) throws Exception {
		T entity = null;
		if (entityId != null && entityId instanceof String) {
			entity = em().find(entityClass, entityId);
		}

		if (entity != null) {
			delete(entity);
			return true;
		}

		return false;
	}

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	protected <C> TypedQuery<C> createQuery(Class<C> clazz, 
			String sql) {
		return createQuery(clazz, sql, null, null);
	}
	
	protected <C> TypedQuery<C> createQuery(Class<C> clazz, 
			String sql, 
			Integer startPosition, Integer limit) {
		TypedQuery<C> typedQuery = em().createQuery(sql, clazz);
		
		if (startPosition != null)
			typedQuery.setFirstResult(startPosition);
		if (limit != null)
			typedQuery.setMaxResults(limit);
		
		return typedQuery;
	}

	protected Query createNativeQuery(String sql) {
		return createNativeQuery(sql, null, null);
	}
	
	protected Query createNativeQuery(String sql, 
			Integer startPosition, Integer limit) {
		Query query = em().createNativeQuery(sql);
		
		if (startPosition != null)
			query.setFirstResult(startPosition);
		if (limit != null)
			query.setMaxResults(limit);
		
		return query;
	}
	
	private String[] extractQuery(String textQuery) {
		Pattern pattern = Pattern.compile("\\s{2,}");
		Matcher matcher = pattern.matcher(textQuery);
		
		String textQueryFinal = matcher.replaceAll(" ").trim();
		String[] result = textQueryFinal.split(" ");
		
		return result;
	}

}