package br.com.cleomilson.n2.dao;

/* desenvolvido por Cleomilson Sales - email: cleomilsonsales@hotmail.com
Tecnologia java swing e awt (sem plugins);
Data: 27/05/14
*/

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {
	
	private Connection con;

	public LoginDAO() throws SQLException{
		new Conexao();
		this.con = Conexao.getConexao();
	}
	
	public boolean logar(String user,String pass) throws SQLException{
		Boolean retorno = false;
		try{
			String sql = "SELECT * FROM USUARIO";
			PreparedStatement state = con.prepareStatement(sql);
			ResultSet Query  = state.executeQuery();
	
			 while (Query.next()) {
				if ((Query.getString("nomeUsuario").equals(user))&& (Query.getString("Senha").equals(pass))){ 
					 retorno = true;
					 break;
				}
			}
			state.close();
			
		}finally{
			con.close();
		}
		return retorno;
		
	}
}