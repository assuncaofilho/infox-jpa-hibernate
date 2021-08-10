package dao;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;

import connection.HibernateUtil;
import model.Os;

public class OsDaoHibernate implements OsDao {

	//private Connection connection;
	
	private EntityManager entityManager;
	
	private DaoGeneric<Os> daoGeneric = new DaoGeneric<Os>();

	
	public OsDaoHibernate() {
		entityManager = HibernateUtil.getEntityManager();
	}
	

	@SuppressWarnings("unchecked")
	public Os cadastrar(Os o) throws Exception {

		Os retorno = null;

		if (o.isNovo()) {/* Grava um novo */

			daoGeneric.salvar(o);

			String getLastInsertId = "SELECT LAST_INSERT_ID();";
			List<BigInteger> idAdd = entityManager.createNativeQuery(getLastInsertId).getResultList();
			String idAux = idAdd.get(0).toString();
			retorno = entityManager.find(Os.class, Integer.parseInt(idAux));

		} else {

			daoGeneric.updateMerge(o);
			Integer idUpdate = (Integer) HibernateUtil.getPrimaryKey(o); 
			retorno = this.pesquisar(idUpdate.toString());

		}

		return retorno;

	}

	
	public Os pesquisar(String id) throws Exception {
	
		return daoGeneric.consultar(Integer.parseInt(id), Os.class);
	}
	
	public List<Os> listar() throws Exception {
	
		List<Os> osList = daoGeneric.listar(Os.class);
		return osList;

	}

	
	public void excluir(String id) throws Exception {
		
		daoGeneric.deleteByID(Integer.parseInt(id), Os.class);
		
		
	}

}
