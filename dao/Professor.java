package br.com.dao;

/**
*
* @author CMarcelo
*/
public class Professor extends Pessoa{
	private String interesse,vinculo;

	public Professor(){
		super();
		setInteresse("");
		setVinculo("");
	}
	
	public Professor(String nome,String cpf,String mat,String interesse,String vinculo){
		super(nome,cpf,mat);
		setInteresse(interesse);
		setVinculo(vinculo);
	}
	
	public String getInteresse(){
		return interesse;
	}

	public void setInteresse(String interesse){
		this.interesse = interesse;
	}

	public String getVinculo(){
		return vinculo;
	}

	public void setVinculo(String vinculo){
		this.vinculo = vinculo;
	}

	public String toString() {
		
		return "Professor";
	}	
}
