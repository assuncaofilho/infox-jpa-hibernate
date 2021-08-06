package test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

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
	

	@Test
	public void testeConsultaByLoginAndPassword() {
		
		EntityManager entityManager = HibernateUtil.getEntityManager();
		
		Query q = entityManager.createNativeQuery("select * from tbusuarios where login = 'admin' and senha = 'admin'", Usuario.class);
		
		Object obj = q.getSingleResult();
		
		Usuario u = (Usuario) obj;
		
		System.out.println(u);
		
		
	}
	
	
	@Test
	public void testeConsultaById() {
		
		DaoGeneric<Usuario> daoGeneric = new DaoGeneric<Usuario>();
		
		Usuario buscado = daoGeneric.consultar(1, Usuario.class);
		
		System.out.println(buscado);
		
		
	}
}