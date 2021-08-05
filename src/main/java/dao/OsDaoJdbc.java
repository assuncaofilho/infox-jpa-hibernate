package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.ConexaoUtil;
import model.Os;

public class OsDaoJdbc implements OsDao {

	private Connection connection;

	
	public OsDaoJdbc() {
		connection = ConexaoUtil.getConnection();
	}
	
	public Os cadastrar(Os o) throws Exception{
		
		Os retorno = null;
		
		if (o.isNovo()) {/*Grava um novo*/
			
			String cadastrar = "insert into tbos (os, data_os, tipo, situacao, equipamento, defeito, servico, valor, idcli, idtec) "
	                + "values (default, default, ?, ?, ?, ?, ?, ?, ?, ?)";

	        
	               PreparedStatement pst = connection.prepareStatement(cadastrar);

	                pst.setString(1, o.getTipo());
	                pst.setString(2, o.getSituacao());
	                pst.setString(3, o.getEquipamento());
	                pst.setString(4, o.getDefeito());
	                pst.setString(5, o.getServico());
	                pst.setString(6, Double.toString(o.getValor()));
	                pst.setString(7, Integer.toString(o.getIdcli()));
	                pst.setString(8, Integer.toString(o.getIdtec()));

	                //ResultSet rs = pst.executeQuery(); somente para pesquisa
	                int rec = pst.executeUpdate(); //usado para DML (insert, update  e delete)
	                
	                if(rec > 0) {
	                
	                String getLastInsertId = "SELECT LAST_INSERT_ID();";
	                PreparedStatement pst2 = connection.prepareStatement(getLastInsertId);
	                ResultSet rs = pst2.executeQuery();
	                rs.next();	
	                retorno = this.pesquisar(rs.getString("LAST_INSERT_ID()"));
	                
	                connection.commit();
	                
	                }
	            
			
			
			
			}else {
				
				String editar = "update tbos set tipo=?, situacao=?, equipamento=?, defeito=?, servico=?, valor=?, idtec=? where os=?";
		     
						PreparedStatement pst;
		                pst = connection.prepareStatement(editar);
		                pst.setString(1, o.getTipo());
		                pst.setString(2, o.getSituacao());
		                pst.setString(3, o.getEquipamento());
		                pst.setString(4, o.getDefeito());
		                pst.setString(5, o.getServico());
		                pst.setString(6, Double.toString(o.getValor()));
		                pst.setString(7, Integer.toString(o.getIdtec()));
		                pst.setString(8, o.getId().toString());
		                
		                
		                int update = pst.executeUpdate();
		                if (update > 0) {
		                	
		                retorno = this.pesquisar(o.getId().toString());
				
		                connection.commit();
		                
		                }
			
			}
			
			
			return retorno;

		
	}

	
	public Os pesquisar(String id) throws Exception {
	
        
        String pesquisar = "select * from tbos where os=?";
        
            PreparedStatement pst = connection.prepareStatement(pesquisar);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            
            Os encontrada = new Os();
            
                if(rs.next()){
                	
                  
                  encontrada.setId(rs.getInt("os")); 
                  encontrada.setData(rs.getString("data_os"));
                  encontrada.setTipo(rs.getString("tipo"));
                  encontrada.setSituacao(rs.getString("situacao")); 
                  encontrada.setEquipamento(rs.getString("equipamento")); 
                  encontrada.setDefeito(rs.getString("defeito"));
                  encontrada.setServico(rs.getString("servico")); 
                  encontrada.setValor(rs.getDouble("valor"));
                  encontrada.setIdcli(rs.getInt("idcli"));
                  encontrada.setIdtec(rs.getInt("idtec"));
                    
                 }
            return encontrada;
	}
	
	public List<Os> listar() throws Exception {
	
        
        String pesquisar = "select * from tbos";
        
            PreparedStatement pst = connection.prepareStatement(pesquisar);
           
            ResultSet rs = pst.executeQuery();
            
            
            List<Os> listOs = new ArrayList<Os>();
            
            
            
                while(rs.next()){
                	
                  Os encontrada = new Os();
                	
                  encontrada.setId(rs.getInt("os")); 
                  encontrada.setData(rs.getString("data_os"));
                  encontrada.setTipo(rs.getString("tipo"));
                  encontrada.setSituacao(rs.getString("situacao")); 
                  encontrada.setEquipamento(rs.getString("equipamento")); 
                  encontrada.setDefeito(rs.getString("defeito"));
                  encontrada.setServico(rs.getString("servico")); 
                  encontrada.setValor(rs.getDouble("valor"));
                  encontrada.setIdcli(rs.getInt("idcli"));
                  encontrada.setIdtec(rs.getInt("idtec"));
                  
                  listOs.add(encontrada);
                    
                 }
            return listOs;
	}

	
	public void excluir(String id) throws Exception {
		
		String sql = "DELETE FROM tbos WHERE os = ?;";
		
		PreparedStatement prepareSql = connection.prepareStatement(sql);
		
		prepareSql.setInt(1, Integer.parseInt(id));
		
		prepareSql.executeUpdate();
		
		connection.commit();
		
		
	}

}
