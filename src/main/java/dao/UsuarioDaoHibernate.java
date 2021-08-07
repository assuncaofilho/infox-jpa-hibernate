package dao;

import java.util.List;

import javax.persistence.EntityManager;

import connection.HibernateUtil;
import model.Usuario;

public class UsuarioDaoHibernate implements UsuarioDao {
	
	private EntityManager entityManager;
	
	//private Connection connection; NÃO ESTÁ SENDO UTILIZADO!!
	
	private DaoGeneric<Usuario> daoGeneric = new DaoGeneric<Usuario>();
	
	
	public UsuarioDaoHibernate() {
		entityManager = HibernateUtil.getEntityManager();
	}
	
	
	public boolean validarAutenticacao(Usuario user) throws Exception {
		
		@SuppressWarnings("unchecked")
		List<Usuario> logado = entityManager.createNativeQuery("select * from tbusuarios where login = "
				+ "'"+user.getLogin()+"' and senha = '"+user.getSenha()+"'",Usuario.class).getResultList();
		
		
		if(logado.size() == 1) {
			return true;
		}
		
		return false;
		
	}
	
	public Usuario gravarUsuario(Usuario objeto) throws Exception {
		
		if (objeto.isNovo()) {/*Grava um novo*/
			
			daoGeneric.salvar(objeto);

		
		}else {

			daoGeneric.updateMerge(objeto);
		}
		
		
		return this.consultaUsuario(objeto.getLogin());
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public List<Usuario> consultaUsuarioList(String nome) throws Exception {
		
			List<Usuario> listagem = entityManager.createNativeQuery("select * from tbusuarios where nome like '%"+nome+"%'", Usuario.class).getResultList();
			
			return listagem;
		}
		
		
	
	
	
	public List<Usuario> listar() throws Exception {
		
		List<Usuario> list = daoGeneric.listar(Usuario.class);
		
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	public Usuario consultaUsuario(String login) throws Exception  {
	
		
		List<Usuario> usuario = entityManager.createNativeQuery("select * from tbusuarios where login = '"+login+"';", Usuario.class).getResultList();
		
		return usuario.get(0);
		
	}
	
	
	public Usuario consultaUsuarioID(String id) throws Exception  {
		
		 return daoGeneric.consultar(Integer.parseInt(id), Usuario.class);
		
		
	}
	
	
	
	@SuppressWarnings("unchecked")
	public boolean loginExiste(String login) throws Exception {
		
		List<Usuario> usuarios = entityManager.createNativeQuery(
				"select * from tbusuarios where login = '"+login+"';", Usuario.class).getResultList();
		
		if (usuarios.size() > 0) {
			return true;
		}
		
		return false;
	}
	
	
	public void deletarUser(String idUser) throws Exception {
		
		daoGeneric.deleteByID(Integer.parseInt(idUser), Usuario.class);
		
		
	}

}
