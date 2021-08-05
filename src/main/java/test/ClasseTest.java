package test;

import org.junit.Test;

import connection.HibernateUtil;
import dao.DaoGeneric;
import model.Usuario;

public class ClasseTest {
	
	@Test
	public void testeSalvar() {
		
		DaoGeneric<Usuario> daoGeneric = new DaoGeneric<Usuario>();
		
		Usuario usuario = new Usuario();
		
		usuario.setLogin("carlosjj");
		usuario.setFone("9987-9988");
		usuario.setNome("Junqueira");
		usuario.setSenha("123jj");
		usuario.setPerfil("tecnico");
		
		daoGeneric.salvar(usuario);
		
	}
	
	

}
