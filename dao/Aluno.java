package br.com.dao;

import java.sql.Date;

/**
*
* @author CMarcelo
*/
public class Aluno extends Pessoa{
	private String status;
	private Date dataInicio,dataFim;

	public Aluno(){	
		super();
		setStatus("");
		setDataInicio(null);
		setDataFim(null);
	}
	
	public Aluno(String nome, String cpf, String mat, String status, Date dataInicio, Date dataFim) {
		super(nome,cpf,mat);
		setStatus(status);
		setDataInicio(dataInicio);
		setDataFim(dataFim);
	}

	public String getStatus(){
		return status;
	}

	public void setStatus(String status){
		this.status = status;
	}
	
	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String toString() {
		
		return "Aluno";
	}

}
