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
public class BolsaDAO extends DBConnection{
	private Connection con;

	public BolsaDAO(Connection con) throws SQLException, ClassNotFoundException{
		this.con = con;
	}

	public BolsaDAO(String login,char[] password) throws SQLException, ClassNotFoundException{
		this.con = getMyDBConnection(login,password);
	}
	
	public boolean addBolsa(Bolsa bolsa) throws SQLException{
		PreparedStatement pst = null;
		try	{
			pst = con.prepareStatement("INSERT INTO BOLSA VALUES(?,?,?,?,?)");
			pst.setString(1,null);
			pst.setString(2,bolsa.getOrgao());
			pst.setString(3,bolsa.getStatus());
			pst.setDate(4, bolsa.getDataInicio());
			pst.setDate(5, bolsa.getDataFim());
			return pst.executeUpdate()>0;
		} finally {
			pst.close();
		}
	}
	
	public boolean updateBolsa(Bolsa bolsa)throws SQLException{
        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement("UPDATE BOLSA SET ORGAO=?,STATUS=?,DATAINICIO=?,DATAFIM=? WHERE ID=?");
            pst.setString(1,bolsa.getOrgao());
            pst.setString(2,bolsa.getStatus());
			pst.setDate(3, bolsa.getDataInicio());
			pst.setDate(4, bolsa.getDataFim());
            pst.setInt(5,bolsa.getId());
            return pst.executeUpdate()>0;
        } finally {
            pst.close();
        }
    }

    public boolean removeBolsa(int id)throws SQLException{
        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement("DELETE FROM BOLSA WHERE ID=?");
            pst.setInt(1,id);
            return pst.executeUpdate()>0;
        } finally {
            pst.close();
        }
    }

    public List<String> getListBolsas(int id, String columns) throws SQLException{
    	//org -->  diferente de zero: um bolsa específica; zero: lista de todas as bolsas
    	//columns-->  configurado apenas para 'id'
        List<String> lst = new LinkedList<String>();
        PreparedStatement pst = null;
        ResultSet rs = null;
        if (columns.isEmpty()){
        	columns="*";
        }
        try{
            
        	if(id==0){
        		pst = con.prepareStatement("SELECT "+columns+" FROM BOLSA");
        	}
        	else{
        		pst = con.prepareStatement("SELECT "+columns+" FROM BOLSA WHERE ID=?");
        		pst.setInt(1,id);
        	}
            rs = pst.executeQuery();	            
        	while(rs.next()){
                lst.add(rs.getString("ID"));
            }
        }finally{
            pst.close();
            rs.close();
        }
        return lst;
    }
    
    public List<Bolsa> getListBolsas(int id) throws SQLException{
    	//org -->  diferente de zero: um bolsa específica; zero: lista de todas as bolsas
        List<Bolsa> lst = new LinkedList<Bolsa>();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            
        	if(id==0){
        		pst = con.prepareStatement("SELECT * FROM BOLSA");
        	}
        	else{
        		pst = con.prepareStatement("SELECT * FROM BOLSA WHERE ID=?");
        		pst.setInt(1,id);
        	}
            rs = pst.executeQuery();	            
        	while(rs.next()){
                lst.add(getBolsaFromSQL(rs));
            }
        }finally{
            pst.close();
            rs.close();
        }
        return lst;
    }    

    private Bolsa getBolsaFromSQL(ResultSet rs) throws SQLException{
        Bolsa bolsa = new Bolsa();
        bolsa.setId(rs.getInt("ID"));
        bolsa.setOrgao(rs.getString("ORGAO"));
        bolsa.setStatus(rs.getString("STATUS"));
    	bolsa.setDataInicio(rs.getDate("DATAINICIO"));
    	bolsa.setDataFim(rs.getDate("DATAFIM"));
        return bolsa;
    }
    
    public String getOrgao(int idBolsa) throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        String orgao="";
        try{  
        	pst = con.prepareStatement("SELECT ORGAO FROM BOLSA WHERE ID=?");
        	pst.setInt(1,idBolsa);        	
            rs = pst.executeQuery();	            
        	if(rs.next()){

            	orgao = rs.getString("ORGAO");
        	}
        }finally{
            pst.close();
            rs.close();
        }
    	return orgao;
    }
}
