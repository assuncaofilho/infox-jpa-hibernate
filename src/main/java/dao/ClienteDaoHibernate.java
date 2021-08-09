package dao;

import java.util.List;

import javax.persistence.EntityManager;

import connection.HibernateUtil;
import model.Cliente;




public class ClienteDaoHibernate implements ClienteDao {
	
	//private Connection connection;
	
	private EntityManager entityManager;
	
	private DaoGeneric<Cliente> daoGeneric = new DaoGeneric<Cliente>(); 
	
	public ClienteDaoHibernate() {
		entityManager = HibernateUtil.getEntityManager();
	}

	
	public Cliente gravar(Cliente objeto) throws Exception {
		
		if (objeto.isNovo()) {/*Grava um novo*/
			
			daoGeneric.salvar(objeto);

		
		}else {

			daoGeneric.updateMerge(objeto);
		}
		
		
		return this.consulta(objeto.getEmail());
	}

	
	@SuppressWarnings("unchecked")
	public List<Cliente> consultaListar(String nome) throws Exception {
		
		List<Cliente> listagem = entityManager.createNativeQuery(
				"select * from tbclientes where nomecli like '%"+nome+"%'", Cliente.class).getResultList();
		
		return listagem;
	}

	
	@SuppressWarnings("unchecked")
	public Cliente consulta(String email) throws Exception {
		
		
		List<Cliente> clientes = entityManager.createNativeQuery("select * from tbclientes where emailcli = '"+email+"';", Cliente.class).getResultList();
		
		return clientes.get(0);
	}

	
	public Cliente consultaID(String id) throws Exception {

		return daoGeneric.consultar(Integer.parseInt(id), Cliente.class);

	}

	
	@SuppressWarnings("unchecked")
	public boolean emailExiste(String email) throws Exception {
		
		List<Cliente> clientes = entityManager.createNativeQuery(
				"select * from tbclientes where emailcli = '"+email+"';", Cliente.class).getResultList();
		
		if (clientes.size() > 0) {
			return true;
		}
		
		return false;
	}

	
	public void deletar(String id) throws Exception {
		
		daoGeneric.deleteByID(Integer.parseInt(id), Cliente.class);
		
	}


	
	public List<Cliente> listar() throws Exception {
		
		
		List<Cliente> clientesList = daoGeneric.listar(new Cliente());
		return clientesList;

		}
		
		
		
	}


