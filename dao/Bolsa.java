package br.com.dao;

import java.sql.Date;

/**
*
* @author CMarcelo
*/
public class Bolsa {
	private int id;
	private String orgao,status;
	private Date dataInicio,dataFim;
	
	public Bolsa(){
		setId(0);
		setOrgao("");
		setStatus("");
		setDataInicio(null);
		setDataFim(null);
	}	

	public Bolsa( int id,String orgao, String status, Date dataInicio, Date dataFim) {
		setId(id);
		setOrgao(orgao);
		setStatus(status);
		setDataInicio(dataInicio);
		setDataFim(dataFim);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrgao(){
		return orgao;
	}

	public void setOrgao(String orgao){
		this.orgao = orgao;
	}

	public String getStatus(){
		return status;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public String toString() {
		
		return "Bolsa";
	}
}
