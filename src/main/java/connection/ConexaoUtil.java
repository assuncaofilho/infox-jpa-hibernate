package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class ConexaoUtil {  //não permite instanciação da classe
	
	private static Connection conn;
	
	
	
	/*Os métodos static ou métodos da classe são funções que não dependem de nenhuma variável de instância, 
	 * quando invocados executam uma função sem a dependência do conteúdo de um objeto ou a execução da instância de uma classe*/
	public synchronized static Connection getConnection() { 
		
		if(conn == null) {
			
			
			String url = "jdbc:mysql://localhost:3306/dbinfox";
			String driver = "com.mysql.cj.jdbc.Driver";
			String user = "root";
			String senha = "root";
			
			try {
				Class.forName(driver);
				conn = DriverManager.getConnection(url, user, senha);
				conn.setAutoCommit(false);
				System.out.println("a conexão é:" + conn);
				
			}catch(Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
		
		return conn;
	}
	
	
    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }



}
