package br.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
*
* @author CMarcelo
*/
public class BolsistaDAO extends DBConnection{
	private Connection con;

	public BolsistaDAO(Connection con) throws SQLException, ClassNotFoundException{
		this.con = con;
	}

	public BolsistaDAO(String login,char[] password) throws SQLException, ClassNotFoundException{
		this.con = getMyDBConnection(login,password);
	}
	
	public boolean addBolsista(Bolsista bolsista) throws SQLException{
		PreparedStatement pst = null;
		int i;
		try	{
			pst = con.prepareStatement("INSERT INTO BOLSISTA VALUES(?,?,?,?,?,?,?)");
			pst.setString(1,bolsista.getMatAluno());
			pst.setString(2,bolsista.getMatOrientador());
			pst.setString(3,bolsista.getMatCoOrientador());
			pst.setInt(4,bolsista.getIdBolsa());
			i = bolsista.getRelFaperj();
			if(i==0){
				pst.setString(5,null);
			}else{
				pst.setInt(5,i);
			}
			pst.setDate(6, bolsista.getDataInicio());
			pst.setDate(7, bolsista.getDataFim());
			return pst.executeUpdate()>0;
		} finally {
			pst.close();
		}
	}
	
	public boolean updateBolsista(Bolsista bolsista)throws SQLException{
        PreparedStatement pst = null;
		int i;
        try {
            pst = con.prepareStatement("UPDATE BOLSISTA SET MATORIENTADOR=?,MATCOORIENTADOR=?,IDBOLSA=?,RELFAPERJ=?,DATAINICIO=?,DATAFIM=? WHERE MATALUNO=?");
			pst.setString(1,bolsista.getMatOrientador());
			pst.setString(2,bolsista.getMatCoOrientador());
			pst.setInt(3,bolsista.getIdBolsa());
			i = bolsista.getRelFaperj();
			if(i==0){
				pst.setString(4,null);
			}else{
				pst.setInt(4,i);
			}
			pst.setDate(5, bolsista.getDataInicio());
			pst.setDate(6, bolsista.getDataFim());
			pst.setString(7,bolsista.getMatAluno());
            return pst.executeUpdate()>0;
        } finally {
            pst.close();
        }
    }

    public boolean removeBolsista(String matAluno)throws SQLException{
        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement("DELETE FROM BOLSISTA WHERE MATALUNO=?");
            pst.setString(1,matAluno);
            return pst.executeUpdate()>0;
        } finally {
            pst.close();
        }
    }

    public List<Bolsista> getListBolsista(String matAluno) throws SQLException{
    	//matAluno -->  não vazio: um aluno específico; "": lista de todos os alunos
        List<Bolsista> lst = new LinkedList<Bolsista>();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            
        	if(matAluno.isEmpty()){
        		pst = con.prepareStatement("SELECT * FROM BOLSISTA");
        	}
        	else{
        		pst = con.prepareStatement("SELECT * FROM BOLSISTA WHERE MATALUNO=?");
        		pst.setString(1,matAluno);
        	}
            rs = pst.executeQuery();	            
        	while(rs.next()){
                lst.add(getBolsistaFromSQL(rs));
            }
        }finally{
            pst.close();
            rs.close();
        }
        return lst;
    }
    

    private Bolsista getBolsistaFromSQL(ResultSet rs) throws SQLException{
    	Bolsista bolsista = new Bolsista();
    	bolsista.setMatAluno(rs.getString("MATALUNO"));
    	bolsista.setMatOrientador(rs.getString("MATORIENTADOR"));
    	bolsista.setMatCoOrientador(rs.getString("MATCOORIENTADOR"));
    	bolsista.setIdBolsa(rs.getInt("IDBOLSA"));
    	bolsista.setRelFaperj(rs.getInt("RELFAPERJ"));
    	bolsista.setDataInicio(rs.getDate("DATAINICIO"));
    	bolsista.setDataFim(rs.getDate("DATAFIM"));
    	
        return bolsista;
    }    
}
