package br.com.cleomilson.n2.util;

/* desenvolvido por Cleomilson Sales - email: cleomilsonsales@hotmail.com
Tecnologia java swing e awt (sem plugins);
Data: 27/05/14
*/

public class Endereco {

	private String tipoLogradouro;
	private String logradouro;
	private int numero;
	private String cep;

	public Endereco (String tipoLogradouro, String logradouro, int numero, String cep){
		this.tipoLogradouro = tipoLogradouro;
		this.logradouro = logradouro;
		this.numero = numero;
		this.cep = cep;
	}
	
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getTipoLogradouro(){
		return this.tipoLogradouro;
	}
	public void setTipoLogradouro(String tipoLogradouro){
		this.tipoLogradouro = tipoLogradouro;
	}
	public String getLogradouro(){
		return this.logradouro;
	}
	public void setLogradouro(String logradouro){
		this.logradouro = logradouro;
	}
	public int getNumero(){
		return this.numero;
	}
	public void setNumero(int numero){
		this.numero = numero;
	}

}
