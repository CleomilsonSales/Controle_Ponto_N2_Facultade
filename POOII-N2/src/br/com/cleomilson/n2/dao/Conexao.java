package br.com.cleomilson.n2.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/* desenvolvido por Cleomilson Sales - email: cleomilsonsales@hotmail.com
   Tecnologia java swing e awt (sem plugins);
   Data: 27/05/14
 */

public class Conexao {
	/*private static final String dir = (ClassLoader.getSystemResource("").toString());
	private static final String diretorio = dir.substring((dir.lastIndexOf("file:")+6), dir.length());
	private static final String dir_atual = "jdbc:firebirdsql:127.0.0.1:"+diretorio+"br/com/cleomilson/n2/db/conta.fdb";*/
	
	private static final String dir_atual = "jdbc:firebirdsql:127.0.0.1:D:/Trabalhos Faculdades/JAVA/db/conta.fdb";
	
	public static Connection getConexao() throws SQLException{
		try{
		 	return DriverManager.getConnection(dir_atual,"sysdba","masterkey");
		}catch(SQLException se){
			throw new SQLException("Erro ao conectar o banco: "+se.getMessage());
		}
	}
}
