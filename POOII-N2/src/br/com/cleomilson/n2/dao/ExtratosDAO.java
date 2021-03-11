package br.com.cleomilson.n2.dao;

/* desenvolvido por Cleomilson Sales - email: cleomilsonsales@hotmail.com
Tecnologia java swing e awt (sem plugins);
Data: 27/05/14
*/

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.cleomilson.n2.util.Conta;
import br.com.cleomilson.n2.util.Extrato;

public class ExtratosDAO {
	
	private Connection con;

	public ExtratosDAO() throws SQLException{
		new Conexao();
		this.con = Conexao.getConexao();
	}
	
	public void ExtratoCredito(Conta c) throws SQLException  {
		
		String sql2 = "INSERT INTO EXTRATO (CPF,TIPOEXTRATO,TRANSACAO,DATATRANSACAO,VALOR) VALUES (?,'C','DEPOSITO',?,?);";
		Date vDataHoje = new Date(System.currentTimeMillis());

		PreparedStatement state2 = con.prepareStatement(sql2);
		
		state2.setString(1, c.getCpf());  
	    state2.setDate(2, vDataHoje);
	    state2.setDouble(3, c.getSaldo());
		state2.execute();
		state2.close();

	}
	
	public void ExtratoDebito(Conta c) throws SQLException{
		String sql3 = "INSERT INTO EXTRATO (CPF,TIPOEXTRATO,TRANSACAO,DATATRANSACAO,VALOR) VALUES (?,'D','CRIACAO CONTA',?,0);";

			Date vDataHoje = new Date(System.currentTimeMillis());
			PreparedStatement state3 = con.prepareStatement(sql3);
			state3.setString(1, c.getCpf());  
		    state3.setDate(2, vDataHoje);
			state3.execute();
			state3.close();

	}
	
	public List<Extrato> getListContaExtrato(int vCod, String vCpf ) throws Exception {

		String sql = null;
		if ((vCod == 0) & (vCpf.equals(""))){
			sql = "SELECT FIRST 10 * FROM VEXTRATO ORDER BY COD DESC;";
		}else{
			sql = "SELECT * FROM VEXTRATO WHERE cod=? OR cpf=?";
		}
		
		ArrayList<Extrato> contaArrayLista = new ArrayList<Extrato>(); 
		try{
			PreparedStatement state = con.prepareStatement(sql);	
			if ((vCod != 0) || (!vCpf.equals(""))){
				state.setInt(1, vCod);
				state.setString(2, vCpf);
			}
			
			ResultSet rs = state.executeQuery(); 
			
			while (rs.next()) { 
				Extrato contaLista = new Extrato();
				
				contaLista.setCodigo(rs.getInt("cod")); 
				contaLista.setNomeTitular(rs.getString("nomeTitular")); 
				contaLista.setCpf(rs.getString("cpf"));
				contaLista.setDataTransacao(rs.getDate("dataTransacao"));
				contaLista.setTipoExtrato(rs.getString("tipoExtrato"));
				contaLista.setTransacao(rs.getString("transacao"));
				contaLista.setValor(rs.getDouble("Valor"));
			
				contaArrayLista.add(contaLista); 
			} 
			rs.close();
			state.close();
			
			return contaArrayLista;
			
		}finally{
			con.close();
		}
	
	}	

}
