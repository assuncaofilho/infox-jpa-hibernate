package test;

import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Table;

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
	
	
	@Test
	public void testeAnnotations() {
		
		//Annotation[] annotations = Usuario.class.getDeclaredAnnotations();  
        
		Table teste = Usuario.class.getAnnotation(Table.class);
		
		String tableName = (teste==null)? Usuario.class.getSimpleName().toLowerCase():teste.name();
		
		System.out.println(tableName);
		    
		}

		
	
	

	@Test
	public void testeGetTableName() {
		
		EntityManager entityManager = HibernateUtil.getEntityManager();
		
		
		String tableName = DaoGeneric.getTableName(entityManager, Usuario.class);
		
		System.out.println(tableName);
		
		
	}
	
	@Test
	public void testeGetColumnNameID() throws NoSuchFieldException, SecurityException {
		
		
		Field id = Usuario.class.getDeclaredField("id");
		
		Column c = id.getAnnotation(Column.class);
		
		String columnNameID = (c==null)? Usuario.class.getSimpleName().toLowerCase():c.name();
		
		System.out.println(columnNameID);
		
		
	}
}