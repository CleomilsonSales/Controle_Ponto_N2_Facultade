package br.com.cleomilson.n2.util;

/* desenvolvido por Cleomilson Sales - email: cleomilsonsales@hotmail.com
Tecnologia java swing e awt (sem plugins);
Data: 27/05/14
*/

import java.sql.SQLException;

import br.com.cleomilson.n2.dao.ContaDAO;
import br.com.cleomilson.n2.exceptions.ContaAtivaException;
import br.com.cleomilson.n2.exceptions.ContaIguaisException;
import br.com.cleomilson.n2.exceptions.ContaLimiteTransfNaoAceitaException;
import br.com.cleomilson.n2.exceptions.ContaNaoEncontradaException;
import br.com.cleomilson.n2.exceptions.ContaNaoPossoSaldoException;

public class Controle {
	private ContaDAO cDAO;
	
	public Controle() throws SQLException{
		cDAO = new ContaDAO();
	}
	
	public void cadastrarConta(Conta c) throws SQLException{
		cDAO.cadastrar(c);
	};
	public void atualizarConta(Conta c) throws SQLException{
		cDAO.atualizar(c);
	};
	public void deletarConta(Conta c) throws SQLException,ContaAtivaException,ContaNaoEncontradaException{
		cDAO.deletar(c);
	};
	public void depositarConta(Conta c,double valor) throws IllegalArgumentException,ContaNaoEncontradaException,SQLException{
		cDAO.depositar(c, valor);
	};
	public void sacarConta(Conta c,double valor) throws SQLException,IllegalArgumentException,ContaNaoEncontradaException,ContaNaoPossoSaldoException{
		cDAO.sacar(c, valor);
	};
	public void transferirConta(Conta cS,Conta cE,double valor) throws SQLException,IllegalArgumentException,ContaNaoEncontradaException,ContaNaoPossoSaldoException,ContaLimiteTransfNaoAceitaException,ContaIguaisException{
		cDAO.transferir(cS, cE, valor);
	};

}
