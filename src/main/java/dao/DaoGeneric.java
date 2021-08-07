package dao;

import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Table;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import connection.HibernateUtil;

public class DaoGeneric<E> {
	
	private EntityManager entityManager = HibernateUtil.getEntityManager();
	
	public void salvar(E entity) {
		
		EntityTransaction transaction = entityManager.getTransaction();
		
		try {
		
		transaction.begin();
		entityManager.persist(entity);
		transaction.commit();
	
		
		}catch(Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public E consultar(E entity) {
		
		Object id = HibernateUtil.getPrimaryKey(entity);
		
		E buscada = (E) entityManager.find(entity.getClass(), id);
		
		return buscada;

	}
	
	public E consultar(Integer id, Class<E> entity) {
		
		E buscada = (E) entityManager.find(entity, id);
		
		return buscada;
		
	}
	
	
	

	public E updateMerge(E entity) { // salva ou atualiza
		
		EntityTransaction transaction = entityManager.getTransaction();
		
		E entidadeSalva = null;
		
		try {
		
		transaction.begin();
		entidadeSalva = entityManager.merge(entity);
		transaction.commit();
		
		}catch(Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}
		
		return entidadeSalva;
	}
	

	
	public void deleteByID(Integer id, Class<E> entity) {
		
		
		EntityTransaction transaction = entityManager.getTransaction();
		
		
		try {
			transaction.begin();
			/*Utilização de SQL nativo*/
			entityManager.createNativeQuery("delete from " + 
			DaoGeneric.getTableName(entityManager, entity) + 
			" where "+DaoGeneric.getColumnNameID(entityManager, entity)+" = "+id+"").executeUpdate();
			transaction.commit();
			
			
		
		}catch(Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List<E> listar(E entity){
		
		List<E> lista = null;
	
			
			/*Utilização de JPQL*/
			lista = entityManager.createQuery("from " + entity.getClass().getSimpleName()).getResultList();
		
		
		return lista;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<E> listar(Class<E> entity){
		
		List<E> lista = null;

			
			lista = entityManager.createQuery("from " + entity.getSimpleName()).getResultList();
			
		
		return lista;
	}
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	

	public static <T> String getTableName(EntityManager em, Class<T> entityClass) {
		
		Metamodel meta = em.getMetamodel();
		
	    EntityType<T> entityType = meta.entity(entityClass);

	    Table t = entityClass.getAnnotation(Table.class);

	    String tableName = (t == null)? entityType.getName().toLowerCase(): t.name();
	    
	    
	    return tableName;
	}
	
	public static <T> String getColumnNameID(EntityManager em, Class<T> entityClass) throws NoSuchFieldException, SecurityException {
		
		Metamodel meta = em.getMetamodel();
		
		EntityType<T> entityType = meta.entity(entityClass);
		
		Field id = entityClass.getDeclaredField("id");
		
		Column c = id.getAnnotation(Column.class);
		
		String columnNameID = (c==null)? entityType.getName().toLowerCase():c.name();
		
		return columnNameID;
	}
	
	
	
}
