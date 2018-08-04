package br.com.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.dao.Bolsista;
import br.com.dao.BolsistaDAO;


/**
*
* @author CMarcelo
*/
public class BolsistaControl {
	private BolsistaDAO dao;

	public BolsistaControl(Connection con)	{
		try {
            dao = new BolsistaDAO(con);
        } catch (SQLException ex) {
            Logger.getLogger(BolsistaControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BolsistaControl.class.getName()).log(Level.SEVERE, null, ex);
        }
	}

    public BolsistaControl(String login,char[] password) {
        try {
            dao = new BolsistaDAO(login,password);
        } catch (SQLException ex) {
            Logger.getLogger(BolsistaControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BolsistaControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean addBolsista(Bolsista bolsista)throws SQLException{
        return dao.addBolsista(bolsista);
    }

    public boolean removeBolsista(String matAluno) throws SQLException{
        return dao.removeBolsista(matAluno);
    }

    public boolean updateBolsista(Bolsista bolsista) throws SQLException{
        return dao.updateBolsista(bolsista);
    }

    public List<Bolsista> getBolsista(String matAluno) throws SQLException{
        return dao.getListBolsista(matAluno);
    }  
}

