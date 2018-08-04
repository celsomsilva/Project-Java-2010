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
public class ProfessorDAO extends DBConnection{
	private Connection con;

	public ProfessorDAO(Connection con)throws SQLException, ClassNotFoundException{
		this.con = con;
	}

	public ProfessorDAO(String login,char[] password) throws SQLException, ClassNotFoundException{
		this.con = getMyDBConnection(login,password);
	}
	
	public boolean addProfessor(Professor prof) throws SQLException{
		PreparedStatement pst = null;
		try	{
			pst = con.prepareStatement("INSERT INTO PROFESSOR VALUES(?,?,?,?,?)");
			pst.setString(1,prof.getNome());
			pst.setString(2,prof.getCpf());
			pst.setString(3,prof.getMat());
			pst.setString(4,prof.getInteresse());
			pst.setString(5,prof.getVinculo());
			return pst.executeUpdate()>0;
		} finally {
			pst.close();
		}
	}
	
	public boolean updateProfessor(Professor prof)throws SQLException{
        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement("UPDATE PROFESSOR SET NOME=?,CPF=?,MATRICULA=?,INTERESSE=?,VINCULO=? WHERE MATRICULA=?");
            pst.setString(1,prof.getNome());
            pst.setString(2,prof.getCpf());
            pst.setString(3,prof.getMat());
            pst.setString(4,prof.getInteresse());
            pst.setString(5,prof.getVinculo());
            pst.setString(6,prof.getMat());
            return pst.executeUpdate()>0;
        } finally {
            pst.close();
        }
    }

    public boolean removeProfessor(String mat)throws SQLException{
        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement("DELETE FROM PROFESSOR WHERE MATRICULA=?");
            pst.setString(1,mat);
            return pst.executeUpdate()>0;
        } finally {
            pst.close();
        }
    }

    public List<String> getListProfessores(String mat, String columns) throws SQLException{
    	//mat -->  não vazio: um professor específico; "": lista de todos os professores
    	//columns-->  configurado apenas para 'matricula'
        List<String> lst = new LinkedList<String>();
        PreparedStatement pst = null;
        ResultSet rs = null;
        if (columns.isEmpty()){
        	columns="*";
        }
        try{
            
        	if(mat.isEmpty()){
        		pst = con.prepareStatement("SELECT "+columns+" FROM PROFESSOR");
        	}
        	else{
        		pst = con.prepareStatement("SELECT "+columns+" FROM PROFESSOR WHERE MATRICULA=?");
        		pst.setString(1,mat);
        	}
            rs = pst.executeQuery();	            
        	while(rs.next()){
                lst.add(rs.getString("MATRICULA"));
            }
        }finally{
            pst.close();
            rs.close();
        }
        return lst;
    }
    
    public List<Professor> getListProfessores(String mat) throws SQLException{
    	//mat -->  não vazio: um professor específico; "": lista de todos os professores
        List<Professor> lst = new LinkedList<Professor>();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            
        	if(mat.isEmpty()){
        		pst = con.prepareStatement("SELECT * FROM PROFESSOR");
        	}
        	else{
        		pst = con.prepareStatement("SELECT * FROM PROFESSOR WHERE MATRICULA=?");
        		pst.setString(1,mat);
        	}
            rs = pst.executeQuery();	            
        	while(rs.next()){
                lst.add(getProfessorFromSQL(rs));
            }
        }finally{
            pst.close();
            rs.close();
        }
        return lst;
    }    

    private Professor getProfessorFromSQL(ResultSet rs) throws SQLException{
        Professor prof = new Professor();
        prof.setNome(rs.getString("NOME"));
        prof.setCpf(rs.getString("CPF"));
        prof.setMat(rs.getString("MATRICULA"));
        prof.setInteresse(rs.getString("INTERESSE"));
        prof.setVinculo(rs.getString("VINCULO"));
        return prof;
    }
    
   
}
