package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import connection.ConexaoUtil;
import model.Cliente;




public class ClienteDaoJdbc implements ClienteDao {
	
	private Connection connection;
	
	public ClienteDaoJdbc() {
		connection = ConexaoUtil.getConnection();
	}

	
	public Cliente gravar(Cliente objeto) throws Exception {
		
		if (objeto.isNovo()) {/*Grava um novo*/
			
		String gravar = "insert into tbclientes (idcli, nomecli, endcli, telefonecli, emailcli) "
		        + "values (default, ?, ?, ?, ?)";
		PreparedStatement pst = connection.prepareStatement(gravar);
		
		 pst.setString(1, objeto.getNome());
         pst.setString(2, objeto.getEnd());
         pst.setString(3, objeto.getFone());
         pst.setString(4, objeto.getEmail());
      
		
		pst.execute();
		
		connection.commit();
		
		}else {
			String sql = "UPDATE tbclientes SET nomecli=?, endcli=?, telefonecli=?, emailcli=? WHERE idcli =  "+objeto.getId()+";";
			
			PreparedStatement pst = connection.prepareStatement(sql);
			
			 pst.setString(1, objeto.getNome());
	         pst.setString(2, objeto.getEnd());
	         pst.setString(3, objeto.getFone());
	         pst.setString(4, objeto.getEmail());
	         
			
			pst.executeUpdate();
			
			connection.commit();
			
		}
		
		
		return this.consulta(objeto.getEmail());
	}

	
	public List<Cliente> consultaListar(String nome) throws Exception {
		
		List<Cliente> clientesList = new ArrayList<Cliente>();
		
		String pesquisar = "select * from tbclientes where nomecli like ?;";
		PreparedStatement statement = connection.prepareStatement(pesquisar);
		statement.setString(1, "%" + nome + "%");
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) { 
			
			Cliente cliente = new Cliente();
			
			
			cliente.setId(resultado.getInt("idcli"));
			cliente.setNome(resultado.getString("nomecli"));
			cliente.setEnd(resultado.getString("endcli"));
			cliente.setFone(resultado.getString("telefonecli"));
			cliente.setEmail(resultado.getString("emailcli"));
			
			
			clientesList.add(cliente);
		}
		
		
		return clientesList;
	}

	
	public Cliente consulta(String email) throws Exception {
		
		Cliente cliente = new Cliente();
		
		String sql = "select * from tbclientes where emailcli = ?;";
		
		PreparedStatement pst = connection.prepareStatement(sql);
		
		pst.setString(1, email);
		ResultSet rs =  pst.executeQuery();
		
		while (rs.next()) /*Se tem resultado*/ {
			
			cliente.setId(rs.getInt("idcli"));
			cliente.setNome(rs.getString("nomecli"));
			cliente.setEnd(rs.getString("endcli"));
			cliente.setFone(rs.getString("telefonecli"));
			cliente.setEmail(rs.getString("emailcli"));
			
		}
		
		
		return cliente;
	}

	
	public Cliente consultaID(String id) throws Exception {

		
		Cliente cliente = new Cliente();
		
		String sql = "select * from tbclientes where idcli = ?;";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, Integer.parseInt(id));
		
		ResultSet rs =  statement.executeQuery();
		
		if(rs.next()) /*Se tem resultado*/ {
			
			cliente.setId(rs.getInt("idcli"));
			cliente.setNome(rs.getString("nomecli"));
			cliente.setEnd(rs.getString("endcli"));
			cliente.setFone(rs.getString("telefonecli"));
			cliente.setEmail(rs.getString("emailcli"));
			
		}
		
		
		return cliente;
	}

	
	public boolean emailExiste(String email) throws Exception {
		
		String select = "select count(1) > 0 as existe from tbclientes where emailcli = ?;";
		
		PreparedStatement pst = connection.prepareStatement(select);
		pst.setString(1, email);
		
		ResultSet rs = pst.executeQuery();
		
		rs.next(); //inicia o ponteiro apontando para a primeira ocorrencia do ResultSet;
		return rs.getBoolean("existe"); 
	}

	
	public void deletar(String id) throws Exception {
		
		
		String sql = "DELETE FROM tbclientes WHERE idcli = ?;";
		
		PreparedStatement prepareSql = connection.prepareStatement(sql);
		
		prepareSql.setInt(1, Integer.parseInt(id));
		
		prepareSql.executeUpdate();
		
		connection.commit();
		
	}


	
	public List<Cliente> listar() throws Exception {
		
		
		List<Cliente> clientesList = new ArrayList<Cliente>();
		
		String pesquisar = "select * from tbclientes;";
		PreparedStatement statement = connection.prepareStatement(pesquisar);
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) { 
			
			Cliente cliente = new Cliente();
			
			
			cliente.setId(resultado.getInt("idcli"));
			cliente.setNome(resultado.getString("nomecli"));
			cliente.setEnd(resultado.getString("endcli"));
			cliente.setFone(resultado.getString("telefonecli"));
			cliente.setEmail(resultado.getString("emailcli"));
			
			
			clientesList.add(cliente);
		}
		
		
		return clientesList;
		
	}

}
