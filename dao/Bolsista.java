package br.com.dao;

import java.sql.Date;

/**
*
* @author CMarcelo
*/
public class Bolsista {

	private String matAluno,matOrientador,matCoOrientador;
	/*relFaperj: número de 	relatórios entregues de um bolsista da FAPERJ*/ 
	private int idBolsa,relFaperj;
	private Date dataInicio,dataFim;
	
	public Bolsista(){
		setMatAluno("");
		setMatOrientador("");
		setMatCoOrientador("");
		setIdBolsa(0);
		setRelFaperj(0);
		setDataInicio(null);
		setDataFim(null);
	}	
	
	public Bolsista(String matAluno,String matOrientador, String matCoOrientador, int idBolsa, int relFaperj, Date dataInicio, Date dataFim) {
		setMatAluno(matAluno);
		setMatOrientador(matOrientador);
		setMatCoOrientador(matCoOrientador);
		setIdBolsa(idBolsa);
		setRelFaperj(relFaperj);
		setDataInicio(dataInicio);
		setDataFim(dataFim);
	}

	public int getIdBolsa() {
		return idBolsa;
	}
	public void setIdBolsa(int idBolsa) {
		this.idBolsa = idBolsa;
	}
	public String getMatAluno() {
		return matAluno;
	}
	public void setMatAluno(String matAluno) {
		this.matAluno = matAluno;
	}
	public String getMatOrientador() {
		return matOrientador;
	}
	public void setMatOrientador(String matOrientador) {
		this.matOrientador = matOrientador;
	}
	public int getRelFaperj() {
		return relFaperj;
	}
	public void setRelFaperj(int relFaperj) {
		this.relFaperj = relFaperj;
	}

	public String getMatCoOrientador() {
		return matCoOrientador;
	}

	public void setMatCoOrientador(String matCoOrientador) {
		this.matCoOrientador = matCoOrientador;
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
		
		return "Bolsista";
	}

}
