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

import javax.swing.JOptionPane;

import br.com.cleomilson.n2.exceptions.ContaAtivaException;
import br.com.cleomilson.n2.exceptions.ContaIguaisException;
import br.com.cleomilson.n2.exceptions.ContaLimiteTransfNaoAceitaException;
import br.com.cleomilson.n2.exceptions.ContaNaoEncontradaException;
import br.com.cleomilson.n2.exceptions.ContaNaoPossoSaldoException;
import br.com.cleomilson.n2.util.Conta;
import br.com.cleomilson.n2.util.GerenciaContaDAO;
import br.com.cleomilson.n2.util.ControleExtrato;

public class ContaDAO implements GerenciaContaDAO{
	private double rSaldo;
	private double rLimite;
	private double rLimiteTransf;

	private Connection con;
	
	
	public ContaDAO() throws SQLException{
		new Conexao();
		this.con = Conexao.getConexao();
	}
	
	public void cadastrar(Conta c) throws SQLException{
		try{
			
		    //PREENCHER EXTRATO

		    ControleExtrato e1 = new ControleExtrato();
		    e1.ExtratoCreditoConta(c);
		    e1.ExtratoDebitoConta(c);

		    //--
			
			String sql = "INSERT INTO CONTA(nomeTitular,cpf,dataNascimento,sexo,endereco,limite,limiteTransferencia)values(?,?,?,?,?,?,?);";
			PreparedStatement state = con.prepareStatement(sql);
			
			state.setString(1, c.getNomeTitular());  
			state.setString(2, c.getCpf());  
		    Date vGetDataNascimento = new Date(c.getDataNascimento().getTime());  
		    state.setDate(3, vGetDataNascimento);  
		    state.setString(4, c.getSexo());
		    state.setString(5, c.jEndereco()); 
		    state.setDouble(6, c.getLimite()); 
		    state.setDouble(7, c.getLimiteTransferencia()); 
		    
			state.execute();
			state.close();
		    

		}finally{
			con.close();
		}
	}
	public void atualizar(Conta c) throws SQLException{
		try{

			String sql = "UPDATE CONTA SET nomeTitular=?, cpf=?,dataNascimento=?,sexo=?,endereco=?,limite=?,limiteTransferencia=? WHERE cod=?;";
			
			PreparedStatement state = con.prepareStatement(sql);
			
			state.setString(1, c.getNomeTitular());  
			state.setString(2, c.getCpf());  
		    Date vGetDataNascimento = new Date(c.getDataNascimento().getTime());  
		    state.setDate(3, vGetDataNascimento);  
		    state.setString(4, c.getSexo());
		    state.setString(5, c.jEndereco()); 
		    state.setDouble(6, c.getLimite()); 
		    state.setDouble(7, c.getLimiteTransferencia());
		    state.setInt(8, c.getCodigo());
		    state.execute();
			
			state.close();
		}finally{
			con.close();
		}
	}
	
	public void deletar(Conta c) throws SQLException,ContaAtivaException,ContaNaoEncontradaException{
		try{
			
			if (procurarConta(c)){
				if (ConsultaSaldo(c.getCpf()) == 0){
					//excluindo conta
					String sql3 = "DELETE FROM CONTA WHERE cpf=?;";
					PreparedStatement state3 = con.prepareStatement(sql3);
					state3.setString(1, c.getCpf());  
					state3.execute();
					state3.close();	
					//--
				}else{
					throw new ContaAtivaException("Conta ainda está ativa.");
				}
			}else{
				throw new ContaNaoEncontradaException("Conta não encontrada.");
			}
		}finally{
			con.close();
		}
	
	}	
	
	public boolean procurarConta(Conta c) throws SQLException{
		boolean retorno = false;
		
		String sql = "SELECT * FROM CONTA WHERE cod=? OR cpf=?";
		PreparedStatement state = con.prepareStatement(sql);
		state.setInt(1, c.getCodigo());
		state.setString(2, c.getCpf());  
		
		ResultSet Query = state.executeQuery();
		while (Query.next()) {
			if ((Query.getInt("cod") == (c.getCodigo())) || (Query.getString("cpf").equals(c.getCpf()))){ 
				retorno = true;
				c.setCodigo(Query.getInt("cod"));
				c.setCpf(Query.getString("cpf"));
				rLimite = Query.getDouble("Limite");
				rLimiteTransf = Query.getDouble("LimiteTransferencia");
			}
		}
		state.close();
		Query.close();

		return retorno;
	}
	
	public double ConsultaSaldo(String cpfConta) throws SQLException{
		String sql2 = "SELECT * FROM SALDO WHERE cpf=?";
		PreparedStatement state2 = con.prepareStatement(sql2);
		state2.setString(1, cpfConta);
		ResultSet Query2 = state2.executeQuery();
		
		while (Query2.next()) {
			rSaldo = Query2.getDouble("SALDO");
		}
		state2.close();
		Query2.close();

		return rSaldo;
	}

	public List<Conta> getListConta(String vSQL, String cpf) throws Exception {
		
		String sql = null;
		PreparedStatement state;
		
		ArrayList<Conta> contaArrayLista = new ArrayList<Conta>(); 
		try { 
			if (vSQL == null && cpf == null){
				sql = "SELECT FIRST 10 * FROM CONTA ORDER BY COD DESC;";
				state = con.prepareStatement(sql);
			}else{
				sql = "SELECT * FROM CONTA WHERE NOMETITULAR LIKE '%" + vSQL + "%' OR CPF=?;";
				state = con.prepareStatement(sql);
				state.setString(1, cpf);
			}
			
			ResultSet rs = state.executeQuery(); 
			while (rs.next()) { 
				Conta contaLista = new Conta();
				
				contaLista.setCodigo(rs.getInt("cod")); 
				contaLista.setNomeTitular(rs.getString("nomeTitular")); 
				contaLista.setCpf(rs.getString("cpf"));
				contaLista.setDataNascimento(rs.getDate("dataNascimento")); 
				contaLista.setSexo(rs.getString("sexo"));
				contaLista.setEnderecoCompleto(rs.getString("endereco"));
				contaLista.setLimite(rs.getDouble("limite"));
				contaLista.setLimiteTransferencia(rs.getDouble("limiteTransferencia"));
				
				contaArrayLista.add(contaLista); 
			} 
			rs.close();
			state.close();
			
		}finally{
			con.close();
		} 
		return contaArrayLista; 
	}	
	
	public List<Conta> getListConta(int vCod) throws Exception{
		
		String sql =  "SELECT * FROM CONTA WHERE COD=?;";
		 
		ArrayList<Conta> contaArrayLista = new ArrayList<Conta>(); 
		try { 
			PreparedStatement state = con.prepareStatement(sql);	
			state.setInt(1, vCod);
			
			ResultSet rs = state.executeQuery(); 
			while (rs.next()) { 
				Conta contaLista = new Conta();
				
				contaLista.setCodigo(rs.getInt("cod")); 
				contaLista.setNomeTitular(rs.getString("nomeTitular")); 
				contaLista.setCpf(rs.getString("cpf"));
				contaLista.setDataNascimento(rs.getDate("dataNascimento")); 
				contaLista.setSexo(rs.getString("sexo"));
				contaLista.setEnderecoCompleto(rs.getString("endereco"));
				contaLista.setLimite(rs.getDouble("limite"));
				contaLista.setLimiteTransferencia(rs.getDouble("limiteTransferencia"));
				
				contaArrayLista.add(contaLista); 
			} 
			rs.close();
			state.close();
			
		}finally{
			con.close();
		}  
		return contaArrayLista; 
	}	
	
	public void depositar(Conta c,double valor) throws IllegalArgumentException,ContaNaoEncontradaException,SQLException{
		try{
			Date vDataHoje = new Date(System.currentTimeMillis());
			
			if (valor < 0) {
				throw new IllegalArgumentException("Valor não pode ser inferior a zero.");
			}else{
		
				if (procurarConta(c)){
					String sql = "INSERT INTO EXTRATO (CPF,TIPOEXTRATO,TRANSACAO,DATATRANSACAO,VALOR) VALUES (?,'C','DEPOSITO',?,?);";
					PreparedStatement state = con.prepareStatement(sql);
					
					state.setString(1, c.getCpf());  
					state.setDate(2, vDataHoje);  
				    state.setDouble(3, valor); 
				    
					state.execute();
					state.close();
				    
				    JOptionPane.showMessageDialog(null, "Deposito realizado com sucesso." ,"Ação aceita",JOptionPane.INFORMATION_MESSAGE);
		
				}else{
					throw new ContaNaoEncontradaException("Conta não encontrada.");
				}
				
			}
		}finally{
			con.close();
		} 
	}
	public void sacar(Conta c,double valor) throws SQLException,IllegalArgumentException,ContaNaoEncontradaException,ContaNaoPossoSaldoException{
		try{
			Date vDataHoje = new Date(System.currentTimeMillis());
			
			if (valor < 0) {
				throw new IllegalArgumentException("Valor não pode ser inferior a zero.");
			}else{
		
				if (procurarConta(c)){
					ConsultaSaldo(c.getCpf());
					double rPermitido = rSaldo + rLimite; 
					
					if (rPermitido >= valor) {
						String sql = "INSERT INTO EXTRATO (CPF,TIPOEXTRATO,TRANSACAO,DATATRANSACAO,VALOR) VALUES (?,'D','SAQUE',?,?);";
						PreparedStatement state = con.prepareStatement(sql);
						
						state.setString(1, c.getCpf());  
						state.setDate(2, vDataHoje);  
					    state.setDouble(3, valor); 
					    
						state.execute();
						state.close();
					    
					    JOptionPane.showMessageDialog(null, "Saque realizado com sucesso." ,"Ação aceita",JOptionPane.INFORMATION_MESSAGE);
			
					}else{
						throw new ContaNaoPossoSaldoException("Conta não posso saldo e/ou limite para saque.");
					}
				}else{
					throw new ContaNaoEncontradaException("Conta não encontrada.");
				}
				
			}
		}finally{
			con.close();
		} 
	}
	public void transferir(Conta cS,Conta cE,double valor) throws SQLException,IllegalArgumentException,ContaNaoEncontradaException,ContaNaoPossoSaldoException,ContaLimiteTransfNaoAceitaException,ContaIguaisException{
		try{
			Date vDataHoje = new Date(System.currentTimeMillis());
			
			if (valor < 0) {
				throw new IllegalArgumentException("Valor não pode ser inferior a zero.");
			}else{
					if (procurarConta(cE)){
						if (procurarConta(cS)){
							if (cE.getCpf() != cS.getCpf()){
								ConsultaSaldo(cS.getCpf());
								double rPermitido = rSaldo + rLimite; 
								
								if (rPermitido >= valor) {
									if (rLimiteTransf >= valor){
										String sqlS = "INSERT INTO EXTRATO (CPF,TIPOEXTRATO,TRANSACAO,DATATRANSACAO,VALOR) VALUES (?,'D','TRANSF PARA CPF:"+cE.getCpf()+"',?,?);";
										String sqlE = "INSERT INTO EXTRATO (CPF,TIPOEXTRATO,TRANSACAO,DATATRANSACAO,VALOR) VALUES (?,'C','TRANSF DE CPF:"+cS.getCpf()+"',?,?);";
										PreparedStatement state = con.prepareStatement(sqlS);
										state.setString(1, cS.getCpf());  
										state.setDate(2, vDataHoje);  
									    state.setDouble(3, valor); 
										state.execute();
										state.close();
		
										PreparedStatement state2 = con.prepareStatement(sqlE);
										state2.setString(1,  cE.getCpf());  
										state2.setDate(2, vDataHoje);  
									    state2.setDouble(3, valor); 
										state2.execute();
										state2.close();
									}else{
										throw new ContaLimiteTransfNaoAceitaException("Limite de Transferência não é suficiente para transferência.");
									}
								}else{
									throw new ContaNaoPossoSaldoException("Conta não possue saldo e/ou limite para transferência.");
								}
							}else{
								throw new ContaIguaisException("Contas iguais não permitido.");
							}
						}else{
							throw new ContaNaoEncontradaException("Conta da transferência não encontrada.");
						}
					}else{
						throw new ContaNaoEncontradaException("Conta para transferência não encontrada.");
					}
			}
		}finally{
			con.close();
		} 
	}
}
