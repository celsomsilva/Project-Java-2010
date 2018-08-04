package br.com.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.dao.Professor;
import br.com.dao.ProfessorDAO;

/**
*
* @author CMarcelo
*/
public class ProfessorControl {
	private ProfessorDAO dao;

	public ProfessorControl(Connection con)	{
		try {
            dao = new ProfessorDAO(con);
        } catch (SQLException ex) {
            Logger.getLogger(ProfessorControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProfessorControl.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
	
    public ProfessorControl(String login,char[] password) {
        try {
            dao = new ProfessorDAO(login,password);
        } catch (SQLException ex) {
            Logger.getLogger(ProfessorControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProfessorControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean addProfessor(Professor professor)throws SQLException{
        return dao.addProfessor(professor);
    }

    public boolean removeProfessor(String mat) throws SQLException{
        return dao.removeProfessor(mat);
    }

    public boolean updateProfessor(Professor professor) throws SQLException{
        return dao.updateProfessor(professor);
    }

    public List<Professor> getProfessor(String mat) throws SQLException{
        return dao.getListProfessores(mat);
    }
    
    public List<String> getProfessor(String mat,String columns) throws SQLException{
        return dao.getListProfessores(mat,columns);
    }
}
