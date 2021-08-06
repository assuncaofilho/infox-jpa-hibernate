package connection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class HibernateUtil {
	
	private static EntityManagerFactory entityManagerFactory = null;
	
	static {
		init();
	}
	
	private static void init() {
		
		try {
			
			if(entityManagerFactory == null) {
				entityManagerFactory = Persistence.createEntityManagerFactory("infox-hibernate");
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public synchronized static EntityManager getEntityManager() {
		return entityManagerFactory.createEntityManager();
	}
	
	public synchronized static Object getPrimaryKey(Object entity) { // retorna a chave primária de um Object passado;
		return entityManagerFactory.getPersistenceUnitUtil().getIdentifier(entity);
		
	}

}
