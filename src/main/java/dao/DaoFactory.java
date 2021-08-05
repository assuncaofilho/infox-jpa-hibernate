package dao;

public abstract class DaoFactory { // classe modelo; não pode ser instanciada diretamente;
	
	public static UsuarioDao createUsuarioDao() { // não depende de variável de instância;
		return new UsuarioDaoJdbc();
	}
	
	public static ClienteDao createClienteDao() { // não depende de variável de instância;
		return new ClienteDaoJdbc();
	}
	
	public static OsDao createOsDao() { // não depende de variável de instância;
		return new OsDaoJdbc();
	}
	


}
