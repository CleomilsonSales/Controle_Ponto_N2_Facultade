package br.com.cleomilson.n2.util;

/* desenvolvido por Cleomilson Sales - email: cleomilsonsales@hotmail.com
Tecnologia java swing e awt (sem plugins);
Data: 27/05/14
*/

import java.sql.SQLException;

import br.com.cleomilson.n2.dao.ExtratosDAO;

public class ControleExtrato {
	private ExtratosDAO cDAO;
	
	public ControleExtrato() throws SQLException{
		cDAO = new ExtratosDAO();
	}
	
	public void ExtratoCreditoConta(Conta c) throws SQLException{
		cDAO.ExtratoCredito(c);
	};
	public void ExtratoDebitoConta(Conta c) throws SQLException{
		cDAO.ExtratoDebito(c);
	};
}
