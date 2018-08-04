package br.com.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.dao.Bolsa;
import br.com.dao.BolsaDAO;

/**
*
* @author CMarcelo
*/
public class BolsaControl {
	private BolsaDAO dao;

	public BolsaControl(Connection con)	{
		try {
            dao = new BolsaDAO(con);
        } catch (SQLException ex) {
            Logger.getLogger(BolsaControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BolsaControl.class.getName()).log(Level.SEVERE, null, ex);
        }
	}

    public BolsaControl(String login,char[] password) {
        try {
            dao = new BolsaDAO(login,password);
        } catch (SQLException ex) {
            Logger.getLogger(BolsaControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BolsaControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean addBolsa(Bolsa bolsa)throws SQLException{
        return dao.addBolsa(bolsa);
    }

    public boolean removeBolsa(int id) throws SQLException{
        return dao.removeBolsa(id);
    }

    public boolean updateBolsa(Bolsa bolsa) throws SQLException{
        return dao.updateBolsa(bolsa);
    }

    public List<Bolsa> getBolsas(int id) throws SQLException{
        return dao.getListBolsas(id);
    }

    public List<String> getBolsas(int id,String columns) throws SQLException{
        return dao.getListBolsas(id,columns);
    }

    public String getOrgao(int idBolsa) throws SQLException{
    	return dao.getOrgao(idBolsa);
    }
}
