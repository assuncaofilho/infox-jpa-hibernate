package dao;

import java.util.List;

import model.Usuario;

public interface UsuarioDao {

	public boolean validarAutenticacao(Usuario u) throws Exception;
	
	public Usuario gravarUsuario(Usuario objeto) throws Exception;
	
	public List<Usuario> consultaUsuarioList(String nome) throws Exception;
	
	public List<Usuario> listar() throws Exception;
	
	public Usuario consultaUsuario(String login) throws Exception;
	
	public Usuario consultaUsuarioID(String id) throws Exception;
	
	public boolean loginExiste(String login) throws Exception;
	
	public void deletarUser(String idUser) throws Exception;

	
}