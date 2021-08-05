package dao;

import java.util.List;

import model.Os;

public interface OsDao {
	
	public Os cadastrar(Os o) throws Exception;
	
	public Os pesquisar(String id) throws Exception;
	
	public void excluir(String id) throws Exception;
	
	public List<Os> listar() throws Exception;
	

}
