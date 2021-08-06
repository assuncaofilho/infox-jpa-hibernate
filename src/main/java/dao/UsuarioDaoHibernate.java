package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import connection.ConexaoUtil;
import connection.HibernateUtil;
import model.Usuario;

public class UsuarioDaoHibernate implements UsuarioDao {
	
	private EntityManager entityManager;
	
	private Connection connection; // remover após finalizar a impl
	
	private DaoGeneric<Usuario> daoGeneric = new DaoGeneric<Usuario>();
	
	
	public UsuarioDaoHibernate() {
		entityManager = HibernateUtil.getEntityManager();
	}
	
	
	public boolean validarAutenticacao(Usuario user) throws Exception {
		
		Usuario logado = (Usuario) entityManager.createNativeQuery("select * from tbusuarios where login = "
				+ "'"+user.getLogin()+"' and senha = '"+user.getSenha()+"'",Usuario.class).getSingleResult();
		
		
		if(logado != null) {
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
	
	
	
	
	public List<Usuario> consultaUsuarioList(String nome) throws Exception {
		
		List<Usuario> retorno = new ArrayList<Usuario>();
		
		String pesquisar = "select * from tbusuarios where nome like ?;";
		PreparedStatement statement = connection.prepareStatement(pesquisar);
		statement.setString(1, "%" + nome + "%");
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) { /*percorrer as linhas de resultado do SQL*/
			
			Usuario usuario = new Usuario();
			
			
			usuario.setId(resultado.getInt("iduser"));
			usuario.setNome(resultado.getString("nome"));
			usuario.setFone(resultado.getString("telefone"));
			usuario.setLogin(resultado.getString("login"));
			usuario.setSenha(resultado.getString("senha"));
			usuario.setPerfil(resultado.getString("perfil"));
			
			retorno.add(usuario);
		}
		
		
		return retorno;
	}
	
	public List<Usuario> listar() throws Exception {
		
		List<Usuario> list = daoGeneric.listar(Usuario.class);
		
		return list;
	}
	
	public Usuario consultaUsuario(String login) throws Exception  {
		
		Usuario usuario = new Usuario();
		
		usuario = (Usuario) entityManager.createNativeQuery("select * from tbusuarios where login = '"+login+"';", Usuario.class).getSingleResult();
		
		return usuario;
		
	}
	
	
	public Usuario consultaUsuarioID(String id) throws Exception  {
		
		Usuario usuario = new Usuario();
		
		String sql = "select * from tbusuarios where iduser = ?;";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, Integer.parseInt(id));
		
		ResultSet rs =  statement.executeQuery();
		
		if(rs.next()) /*Se tem resultado*/ {
			
			usuario.setId(rs.getInt("iduser"));
			usuario.setNome(rs.getString("nome"));
			usuario.setFone(rs.getString("telefone"));
			usuario.setLogin(rs.getString("login"));
			usuario.setSenha(rs.getString("senha"));
			usuario.setPerfil(rs.getString("perfil"));
			
		}
		
		
		return usuario;
		
	}
	
	
	
	public boolean loginExiste(String login) throws Exception {
		
		String select = "select count(1) > 0 as existe from tbusuarios where login = ?;";
		
		PreparedStatement pst = connection.prepareStatement(select);
		pst.setString(1, login);
		
		ResultSet rs = pst.executeQuery();
		
		rs.next(); //inicia o ponteiro apontando para a primeira ocorrencia do ResultSet;
		return rs.getBoolean("existe"); 
		
	}
	
	
	public void deletarUser(String idUser) throws Exception {
		String sql = "DELETE FROM tbusuarios WHERE iduser = ?;";
		
		PreparedStatement prepareSql = connection.prepareStatement(sql);
		
		prepareSql.setInt(1, Integer.parseInt(idUser));
		
		prepareSql.executeUpdate();
		
		connection.commit();
		
	}

}
