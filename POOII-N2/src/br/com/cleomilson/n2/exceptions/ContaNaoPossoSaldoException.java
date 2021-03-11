package br.com.cleomilson.n2.exceptions;

/* desenvolvido por Cleomilson Sales - email: cleomilsonsales@hotmail.com
Tecnologia java swing e awt (sem plugins);
Data: 27/05/14
*/

@SuppressWarnings("serial")
public class ContaNaoPossoSaldoException extends Exception {  
	  
	public ContaNaoPossoSaldoException(String message) {
	    super(message);
	 }
}
