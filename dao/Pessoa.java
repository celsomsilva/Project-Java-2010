package br.com.dao;

/**
*
* @author CMarcelo
*/
public class Pessoa {

	private String mat,nome,cpf;
	
	public Pessoa(){
		
		setNome("");
		setCpf("");
		setMat("");
	}
	
	public Pessoa(String nome,String cpf,String mat){
		setNome(nome);
		setCpf(cpf);
		setMat(mat);
	}
	
	public String getNome(){
		return nome;
	}

	public void setNome(String nome){
		this.nome = nome;
	}
	
	public String getCpf(){
		return cpf;
	}

	public void setCpf(String cpf){
		this.cpf = cpf;
	}

	public String getMat(){
		return mat;
	}

	public void setMat(String mat){
		this.mat = mat;
	}
	
	public String toString() {
		
		return "Pessoa";
	}	
}
