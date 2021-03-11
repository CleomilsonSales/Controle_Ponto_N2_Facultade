package br.com.cleomilson.n2.util;

/* desenvolvido por Cleomilson Sales - email: cleomilsonsales@hotmail.com
Tecnologia java swing e awt (sem plugins);
Data: 27/05/14
*/

import java.sql.SQLException;

import br.com.cleomilson.n2.exceptions.ContaAtivaException;
import br.com.cleomilson.n2.exceptions.ContaIguaisException;
import br.com.cleomilson.n2.exceptions.ContaLimiteTransfNaoAceitaException;
import br.com.cleomilson.n2.exceptions.ContaNaoEncontradaException;
import br.com.cleomilson.n2.exceptions.ContaNaoPossoSaldoException;

public interface GerenciaContaDAO {
	public abstract void cadastrar(Conta c) throws SQLException;
	public abstract void atualizar(Conta c) throws SQLException;
	public abstract void deletar(Conta c) throws SQLException,ContaAtivaException,ContaNaoEncontradaException;
	public abstract void depositar(Conta c,double valor) throws IllegalArgumentException,ContaNaoEncontradaException,SQLException;
	public abstract void sacar(Conta c,double valor) throws SQLException,IllegalArgumentException,ContaNaoEncontradaException,ContaNaoPossoSaldoException;
	public abstract void transferir(Conta cS,Conta cE,double valor) throws SQLException,IllegalArgumentException,ContaNaoEncontradaException,ContaNaoPossoSaldoException,ContaLimiteTransfNaoAceitaException,ContaIguaisException;

}
