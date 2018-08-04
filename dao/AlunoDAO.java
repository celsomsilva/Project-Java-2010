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
public class AlunoDAO extends DBConnection {
	private Connection con;

	public AlunoDAO(Connection con)throws SQLException, ClassNotFoundException{
		this.con = con;
	}

	public AlunoDAO(String login,char[] password) throws SQLException, ClassNotFoundException{
		this.con = getMyDBConnection(login,password);
	}
	
	public boolean addAluno(Aluno aluno) throws SQLException {
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("INSERT INTO ALUNO VALUES(?,?,?,?,?,?)");
			pst.setString(1,aluno.getNome());
			pst.setString(2,aluno.getCpf());
			pst.setString(3,aluno.getMat());
			pst.setString(4,aluno.getStatus());
			pst.setDate(5, aluno.getDataInicio());
			pst.setDate(6, aluno.getDataFim());
			return pst.executeUpdate()>0;
		} finally {
			pst.close();
		}
	}
	
	public boolean updateAluno(Aluno aluno)throws SQLException{
        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement("UPDATE ALUNO SET NOME=?,CPF=?,MATRICULA=?,STATUS=?,DATAINICIO=?,DATAFIM=? WHERE MATRICULA=?");
            pst.setString(1,aluno.getNome());
            pst.setString(2,aluno.getCpf());
            pst.setString(3,aluno.getMat());
            pst.setString(4,aluno.getStatus());
			pst.setDate(5, aluno.getDataInicio());
			pst.setDate(6, aluno.getDataFim());
            pst.setString(7,aluno.getMat());
            return pst.executeUpdate()>0;
        } finally {
            pst.close();
        }
    }

    public boolean removeAluno(String mat)throws SQLException{
        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement("DELETE FROM ALUNO WHERE MATRICULA=?");
            pst.setString(1,mat);
            return pst.executeUpdate()>0;
        } finally {
            pst.close();
        }
    }

    public List<String> getListAlunos(String mat, String columns) throws SQLException{
    	//mat -->  não vazio: um aluno específico; "": lista de todos os alunos
    	//columns-->  configurado apenas para 'matricula'
        List<String> lst = new LinkedList<String>();
        PreparedStatement pst = null;
        ResultSet rs = null;
        if (columns.isEmpty()){
        	columns="*";
        }
        try{
            
        	if(mat.isEmpty()){
        		pst = con.prepareStatement("SELECT "+columns+" FROM ALUNO");
        	}
        	else{
        		pst = con.prepareStatement("SELECT "+columns+" FROM ALUNO WHERE MATRICULA=?");
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
    
    public List<Aluno> getListAlunos(String mat) throws SQLException{
    	//mat -->  não vazio: um aluno específico; "": lista de todos os alunos
        List<Aluno> lst = new LinkedList<Aluno>();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            
        	if(mat.isEmpty()){
        		pst = con.prepareStatement("SELECT * FROM ALUNO");
        	}
        	else{
        		pst = con.prepareStatement("SELECT * FROM ALUNO WHERE MATRICULA=?");
        		pst.setString(1,mat);
        	}
            rs = pst.executeQuery();	            
        	while(rs.next()){
                lst.add(getAlunoFromSQL(rs));
            }
        }finally{
            pst.close();
            rs.close();
        }
        return lst;
    }
    

    private Aluno getAlunoFromSQL(ResultSet rs) throws SQLException{
        Aluno aluno = new Aluno();
        aluno.setNome(rs.getString("NOME"));
        aluno.setCpf(rs.getString("CPF"));
        aluno.setMat(rs.getString("MATRICULA"));
        aluno.setStatus(rs.getString("STATUS"));
    	aluno.setDataInicio(rs.getDate("DATAINICIO"));
    	aluno.setDataFim(rs.getDate("DATAFIM"));
        return aluno;
    }	
}
