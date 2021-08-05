package dao;

import java.util.List;

import model.Cliente;

public interface ClienteDao {
	
	public Cliente gravar(Cliente objeto) throws Exception;
	
	public List<Cliente> consultaListar(String nome) throws Exception;
	
	public List<Cliente> listar() throws Exception;
	
	public Cliente consulta(String login) throws Exception;
	
	public Cliente consultaID(String id) throws Exception;
	
	public boolean emailExiste(String email) throws Exception;
	
	public void deletar(String id) throws Exception;

}
