package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import connection.HibernateUtil;

public class DaoGeneric<E> {
	
	private EntityManager entityManager = HibernateUtil.getEntityManager();
	
	public void salvar(E entity) {
		
		EntityTransaction transaction = entityManager.getTransaction();
		
		try {
		
		transaction.begin();
		entityManager.persist(entity);
		transaction.commit();
		entityManager.close();
		
		}catch(Exception e) {
			transaction.rollback();
			entityManager.close();
			e.printStackTrace();
		}
	}
	
	public E consultar(E entity) {
		
		Object id = HibernateUtil.getPrimaryKey(entity);
		
		E buscada = (E) entityManager.find(entity.getClass(), id);
		
		return buscada;

	}
	
	public E consultar(Integer id, Class<E> entity) {
		
		E buscada = (E) entityManager.find(entity, id);
		
		return buscada;
		
	}
	
	/*public void atualizar(E entity) {
		
		Object id = HibernateUtil.getPrimaryKey(entity);
		
		E toUpdate = (E) entityManager.find(entity.getClass(), id);
		
		toUpdate = entity;
		
		EntityTransaction transaction = entityManager.getTransaction();
		
		try {
			
		transaction.begin();
		entityManager.persist(toUpdate);
		transaction.commit();
		entityManager.close();
		
		}catch(Exception e) {
			transaction.rollback();
			entityManager.close();
			e.printStackTrace();
		}
		
	}*/
	
	public E updateMerge(E entity) { // salva ou atualiza
		
		EntityTransaction transaction = entityManager.getTransaction();
		
		E entidadeSalva = null;
		
		try {
		
		transaction.begin();
		entidadeSalva = entityManager.merge(entity);
		transaction.commit();
		entityManager.close();
		
		}catch(Exception e) {
			transaction.rollback();
			entityManager.close();
			e.printStackTrace();
		}
		
		return entidadeSalva;
	}
	
	public void deleteByID(E entity) {
		
		Object id = HibernateUtil.getPrimaryKey(entity);
		
		EntityTransaction transaction = entityManager.getTransaction();
		
		try {
			transaction.begin();
			
			entityManager.createNativeQuery("delete from " + 
			entity.getClass().getSimpleName().toLowerCase() + 
			" where id = " + id).executeUpdate();
			
			transaction.commit();
			entityManager.close();
			
		
		}catch(Exception e) {
			transaction.rollback();
			entityManager.close();
			e.printStackTrace();
		}
	}
	
	public void deleteByID(Integer id, Class<E> entity) {
		
		
		EntityTransaction transaction = entityManager.getTransaction();
		
		try {
			transaction.begin();
			/*Utilização de SQL nativo*/
			entityManager.createNativeQuery("delete from " + 
			entity.getSimpleName().toLowerCase() + 
			" where id = " + id).executeUpdate();
			
			transaction.commit();
			entityManager.close();
			
		
		}catch(Exception e) {
			transaction.rollback();
			entityManager.close();
			e.printStackTrace();
		}
	}
	
	
	public List<E> listar(E entity){
		
		List<E> lista = null;
	
			
			/*Utilização de JPQL*/
			lista = entityManager.createQuery("from " + entity.getClass().getSimpleName()).getResultList();
		
		
		return lista;
	}
	
	
	public List<E> listar(Class<E> entity){
		
		List<E> lista = null;

			
			lista = entityManager.createQuery("from " + entity.getSimpleName()).getResultList();
			
		
		return lista;
	}
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	
	
}
