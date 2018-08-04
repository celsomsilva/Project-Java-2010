package br.com.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.dao.AlunoDAO;
import br.com.dao.Aluno;

/**
*
* @author CMarcelo
*/
public class AlunoControl {
	private AlunoDAO dao;

	public AlunoControl(Connection con)	{
		try {
            dao = new AlunoDAO(con);
        } catch (SQLException ex) {
            Logger.getLogger(AlunoControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AlunoControl.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
	
    public AlunoControl(String login,char[] password) {
        try {
            dao = new AlunoDAO(login,password);
        } catch (SQLException ex) {
            Logger.getLogger(AlunoControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AlunoControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean addAluno(Aluno aluno)throws SQLException{
        return dao.addAluno(aluno);
    }

    public boolean removeAluno(String mat) throws SQLException{
        return dao.removeAluno(mat);
    }

    public boolean updateAluno(Aluno aluno) throws SQLException{
        return dao.updateAluno(aluno);
    }

    public List<Aluno> getAluno(String mat) throws SQLException{
        return dao.getListAlunos(mat);
    }
    
    public List<String> getAluno(String mat,String columns) throws SQLException{
        return dao.getListAlunos(mat,columns);
    }
}
