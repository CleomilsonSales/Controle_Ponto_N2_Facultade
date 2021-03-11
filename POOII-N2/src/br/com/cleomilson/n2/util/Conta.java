package br.com.cleomilson.n2.util;

/* desenvolvido por Cleomilson Sales - email: cleomilsonsales@hotmail.com
Tecnologia java swing e awt (sem plugins);
Data: 27/05/14
*/

import java.util.Date;


public class Conta {
	
	protected int codigo;
	protected String nomeTitular;
	protected String cpf;
	protected Date dataNascimento;
	protected String sexo;
	protected Endereco endereco;
	protected double saldo;
	protected double limite;
	protected double limiteTransferencia;
	
	protected String EnderecoCompleto;

	public Conta(String nomeTitular, String cpf, Date dataNascimento, String sexo, Endereco endereco, double saldo, double limite,double limiteTransferencia) {
		this.nomeTitular = nomeTitular;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.sexo = sexo;
		this.endereco = endereco;
		this.saldo = saldo;
		this.limite = limite;
		this.limiteTransferencia = limiteTransferencia;
	}
	
	public Conta(int codigo,String nomeTitular, String cpf, Date dataNascimento, String sexo, Endereco endereco, double limite,double limiteTransferencia) {
		this.codigo = codigo;
		this.nomeTitular = nomeTitular;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.sexo = sexo;
		this.endereco = endereco;
		this.limite = limite;
		this.limiteTransferencia = limiteTransferencia;
	}
	
	public Conta() {
		
	}

	public String jEndereco(){
		String retorno = this.endereco.getTipoLogradouro()+" "+this.endereco.getLogradouro()+", "+this.endereco.getNumero()+" - CEP: "+this.endereco.getCep();
		return retorno;
	}
	
	public int getCodigo() {
		return codigo;
	}
	
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public String getNomeTitular() {
		return nomeTitular;
	}

	public void setNomeTitular(String nomeTitular) {
		this.nomeTitular = nomeTitular;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public double getLimite() {
		return limite;
	}

	public void setLimite(double limite) {
		this.limite = limite;
	}

	public double getLimiteTransferencia() {
		return limiteTransferencia;
	}

	public void setLimiteTransferencia(double limiteTransferencia) {
		this.limiteTransferencia = limiteTransferencia;
	}

	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
	public String getEnderecoCompleto() {
		return EnderecoCompleto;
	}

	public void setEnderecoCompleto(String enderecoCompleto) {
		EnderecoCompleto = enderecoCompleto;
	}

}
