package br.com.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;

import br.com.control.BolsaControl;
import br.com.dao.Bolsa;

/**
*
* @author CMarcelo
*/
@SuppressWarnings("serial")
public class BolsaWindow extends WindowFrame{
	
	private List<Bolsa> list;
	private BolsaControl bControl;
	private JPanel pData;
	private final String[] colName = {"ID","Orgão","Status","Data de Iníco","Expira em"};
	private final String[][] combNames = {{"","CAPES","CNPQ","FAPERJ","Outros"},{"","Ociosa","Definida"}};
	private JTextField textId,textOthers,dataInicio,dataFim;
	private JComboBox[] comb;
	private JLabel[] labels;
	private JLabel labOthers;
	private DateFormat df;
	private Map<Integer,Integer> monthDay;
	private final int[] dayArray = {31,28,31,30,31,30,31,31,30,31,30,31};

	public BolsaWindow(Connection con)	{
		
		bControl = new BolsaControl(con);
		inicializar();
		
	}
	
	public BolsaWindow(String login,char[] password){

		bControl = new BolsaControl(login,password);
		inicializar();
			
	}
	
	public void inicializar(){
		
		super.setTitle("BOLSAS");
		
		df = DateFormat.getDateInstance();
		
		pData = new JPanel();
		panelData();
		setGroupLayout();
		
		cont.add(pData,BorderLayout.CENTER);	
				
		jModel.setColumnIdentifiers(colName);
		triggerTable();
		triggerMap();
		
		listenersImplementation();
	}
	
	public void triggerTable(){

		list = new LinkedList<Bolsa>();
		try {
			list = bControl.getBolsas(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		Object[] objects = new Object[colName.length];
		for(Bolsa bolsa:list){
			objects[0] = bolsa.getId();
			objects[1] = bolsa.getOrgao();
			objects[2] = bolsa.getStatus();
			objects[3] = df.format(bolsa.getDataInicio());
			try{
			objects[4] = df.format(bolsa.getDataFim());
			}catch(NullPointerException e){
				objects[4] = "";
			}
			jModel.addRow(objects);
		}
		jt.setModel(jModel);
	}
	
	public void triggerMap(){
		int i = 0;
		monthDay = new HashMap<Integer,Integer>();
		while(i<12){
			monthDay.put((i+1),new Integer(dayArray[i++]));
		}
	}
	
	public void panelData(){
		String text;
		labOthers = new JLabel("Outros: ");
		labOthers.setEnabled(false);
		textOthers = new JTextField();
		textOthers.setEnabled(false);
		textId = new JTextField();
		textId.setEnabled(false);
		textId.setEditable(false);
		labels = new JLabel[colName.length];
		comb = new JComboBox[2];
		dataInicio = new JTextField();
		dataFim = new JTextField();
		dataInicio.setEnabled(false);
		dataFim.setEnabled(false);
		
		for(int i=0; i<colName.length;i++){
			text = ((i==3||i==4) ?" (dd/mm/aaaa): " :": ");
			labels[i] = new JLabel(colName[i]+text);
			labels[i].setEnabled(false);
			if(i<2){
				comb[i] = new JComboBox(combNames[i]);
				comb[i].setEnabled(false);
			}	
		}
	}

	public String currentDate(){
		Date date = new Date(System.currentTimeMillis());
		return df.format(date);
	}
	
	public void setGroupLayout(){
		
		
		   layout = new GroupLayout(pData);
		   pData.setLayout(layout);	
		   layout.setAutoCreateGaps(true);
		   layout.setAutoCreateContainerGaps(true);
		   		   
		   GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		 		  
		   hGroup.addGroup(layout.createParallelGroup().
		            addComponent(labels[0]).
		            addComponent(labels[1]).
		            addComponent(labels[2]).
		            addComponent(labels[3]).
		            addComponent(labels[4]));
		   hGroup.addGroup(layout.createParallelGroup().
		            addComponent(textId).
		            addComponent(comb[0]).
		            addComponent(comb[1]).
		            addComponent(dataInicio).
		            addComponent(dataFim));
		   layout.setHorizontalGroup(hGroup);
		   
		   GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		 	
		   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
		            addComponent(labels[0]).
		            addComponent(textId));
		   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
		            addComponent(labels[1]).
		            addComponent(comb[0]));
		   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
		            addComponent(labels[2]).
		            addComponent(comb[1]));
		   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
		            addComponent(labels[3]).
		            addComponent(dataInicio));
		   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
		            addComponent(labels[4]).
		            addComponent(dataFim));
		   layout.setVerticalGroup(vGroup);
	}
	
	public void setGroupLayoutOthers(){
		
	   GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
	   hGroup.addGroup(layout.createParallelGroup().
	            addComponent(labels[0]).
	            addComponent(labels[1]).
	            addComponent(labOthers).
	            addComponent(labels[2]).
	            addComponent(labels[3]).
	            addComponent(labels[4]));
	   hGroup.addGroup(layout.createParallelGroup().
	            addComponent(textId).
	            addComponent(comb[0]).
	            addComponent(textOthers).
	            addComponent(comb[1]).
	            addComponent(dataInicio).
	            addComponent(dataFim));
	   layout.setHorizontalGroup(hGroup);
	   
	   GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
	 
	   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
	            addComponent(labels[0]).
	            addComponent(textId));
	   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
	            addComponent(labels[1]).
	            addComponent(comb[0]));
	   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
	            addComponent(labOthers).
	            addComponent(textOthers));
	   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
	            addComponent(labels[2]).
	            addComponent(comb[1]));
	   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
	            addComponent(labels[3]).
	            addComponent(dataInicio));
	   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
	            addComponent(labels[4]).
	            addComponent(dataFim));
	   layout.setVerticalGroup(vGroup);
	}

	public void listenersImplementation(){
		pButtons.getButtonNovo().addActionListener(new NewCancelButton());
		pButtons.getButtonExcluir().addActionListener(new DeleteButton());
		pButtons.getButtonSalvar().addActionListener(new SaveButton());
		pButtons.getButtonCancelar().addActionListener(new NewCancelButton());
		jt.addMouseListener(new SelectRowMouse());
		jt.addKeyListener(new SelectRowKey());
		comb[0].addItemListener(new OthersOrgao());
	}
	
	public void enableComponents(int row){
		pButtons.getButtonExcluir().setEnabled(true);
		pButtons.getButtonSalvar().setEnabled(true);
		comb[0].setEnabled(true);
		comb[1].setEnabled(true);
		labels[0].setEnabled(true);
		labels[1].setEnabled(true);
		labels[2].setEnabled(true);
		labels[3].setEnabled(true);
		labels[4].setEnabled(true);
		dataInicio.setEnabled(true);
		dataFim.setEnabled(true);
		textId.setEnabled(true);
		textId.setText(String.valueOf(jt.getValueAt(row, 0)));
		Arrays.sort(combNames[0]);	
		
		if(Arrays.binarySearch(combNames[0], jt.getValueAt(row, 1))<0){
			labOthers.setEnabled(true);
			textOthers.setEnabled(true);
			setGroupLayoutOthers();
			comb[0].setSelectedItem("Outros");
			textOthers.setText((String)jt.getValueAt(row, 1));
		}
		else{
			comb[0].setSelectedItem(jt.getValueAt(row, 1));
		}
		comb[1].setSelectedItem(jt.getValueAt(row, 2));
		dataInicio.setText((String)jt.getValueAt(row, 3));
		dataFim.setText((String)jt.getValueAt(row, 4));
							
	}
	
	public void enableDisableComponents(boolean flag){
		pButtons.getButtonSalvar().setEnabled(flag);
		comb[0].setEnabled(flag);
		comb[1].setEnabled(flag);
		comb[0].setSelectedIndex(0);
		comb[1].setSelectedIndex(0);
		labels[0].setEnabled(false);
		labels[1].setEnabled(flag);
		labels[2].setEnabled(flag);
		labels[3].setEnabled(flag);
		labels[4].setEnabled(flag);
		dataInicio.setEnabled(flag);
		dataInicio.setText(flag==true?currentDate():"");
		dataFim.setEnabled(flag);
		dataFim.setText("");
		labOthers.setEnabled(flag);
		textOthers.setEnabled(flag);
		textId.setEnabled(false);
		textOthers.setText("");
		textId.setText("");
	}
	
	private class SelectRowKey extends KeyAdapter{
		
		public void keyPressed(KeyEvent ev) {
			
			if(ev.getSource()==jt && (ev.getKeyCode()== (KeyEvent.VK_DOWN)||ev.getKeyCode()== (KeyEvent.VK_UP))){
				int row = jt.getSelectedRow();
				int rows = jt.getRowCount();
				if((row < rows-1)&& ev.getKeyCode()==KeyEvent.VK_DOWN)
					row++;
				if((row > 0)&& ev.getKeyCode()==KeyEvent.VK_UP)
					row--;
								
				enableComponents(row);
			}
		}
	}
	
	private class SelectRowMouse extends MouseAdapter{
		
		public void mouseClicked(MouseEvent ev) {
			if(ev.getSource()==jt){
				int row = jt.getSelectedRow(); 
				
				enableComponents(row);				
			}			
		}

	}
	
	private class NewCancelButton implements ActionListener{

		public void actionPerformed(ActionEvent ev) {
			if(ev.getSource()== pButtons.getButtonNovo()){
												
				enableDisableComponents(true);
			}
			else{
				dispose();
			}
			
		}
	}
	
	private class DeleteButton implements ActionListener{

		public void actionPerformed(ActionEvent ev) {
			
			int row = jt.getSelectedRow(); 
			int op = JOptionPane.showConfirmDialog(null,"Tem certeza que deseja apagar a bolsa selecionada?"
				,"Apagar Bolsa",JOptionPane.YES_NO_OPTION);
			if(op==0){
				try {
					bControl.removeBolsa(Integer.parseInt(textId.getText()));
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				jModel.removeRow(row);
				pButtons.getButtonExcluir().setEnabled(false);
				enableDisableComponents(false);
			}			
		}
		
	}
	
	private class SaveButton implements ActionListener{

		public void actionPerformed(ActionEvent ev) {

			try {	
				int row;			
				String orgao = (String)comb[0].getSelectedItem();
				String status = (String)comb[1].getSelectedItem();
				String id = textId.getText();				
				String strInicio = dataInicio.getText();
				String strFim = dataFim.getText();
				String msg = "";
				String msg2 = "";
				List<Bolsa> lst;
				//prestar atenção que 'orgão' e 'status', que são combos, não podem 
				//ter mais de 20 caracteres, SEGUNDO A TABELA FEITA NO MYSQL
				if(orgao.equals(combNames[0][4])){
					orgao = textOthers.getText();
				}
				
				if(orgao.isEmpty()){
					msg = msg+"O CAMPO 'ORGÃO' NÃO PODE SER NULO.\n";
				}
				if(status.isEmpty()){
					msg = msg+"O CAMPO 'STATUS' NÃO PODE SER NULO.\n";
				}
				if(orgao.length()>20){
					msg = msg+"O CAMPO 'OUTROS' NÃO PODE TER MAIS QUE 20 CARACTERES.\nPREENCHA-O CORRETAMENTE";
					
				}
				if(strInicio.isEmpty()){
					msg = msg+"O CAMPO 'DATA DE INÍCIO' NÃO PODE SER NULO.\n";
				}else{
					msg2 = dateVerify(strInicio);
					if(!msg2.isEmpty())
						msg = msg+"CAMPO 'DATA DE INÍCIO': "+msg2+"\n";
				}
				
				if(!strFim.isEmpty()){
					msg2 = dateVerify(strFim);
					if(!msg2.isEmpty())
						msg = msg+"CAMPO 'EXPIRA EM': "+msg2+"\n";
					
				}
				
				if(!msg.isEmpty()){
					JOptionPane.showMessageDialog(BolsaWindow.this,msg, "ERRO", JOptionPane.ERROR_MESSAGE);
					return;
				}
								
				lst = new LinkedList<Bolsa>();
				Object[] obj = new Object[colName.length];
				if(id.isEmpty()){
					Bolsa bolsa = new Bolsa(0,orgao,status,toSqlDate(strInicio),
							strFim.equals("")?null:toSqlDate(strFim));					
					bControl.addBolsa(bolsa);
					lst = bControl.getBolsas(0);
					bolsa = lst.get(lst.size()-1);
					obj[0] = bolsa.getId();
					obj[1] = bolsa.getOrgao();
					obj[2] = bolsa.getStatus();
					obj[3] = bolsa.getDataInicio();
					obj[4] = bolsa.getDataFim();
					jModel.addRow(obj);
				}
				else{
					Bolsa bolsa = new Bolsa(Integer.parseInt(id),orgao,status,
							toSqlDate(strInicio),strFim.equals("")?null:toSqlDate(strFim));	
					obj[0] = bolsa.getId();
					obj[1] = bolsa.getOrgao();
					obj[2] = bolsa.getStatus();
					obj[3] = bolsa.getDataInicio();
					obj[4] = bolsa.getDataFim();
					row = jt.getSelectedRow();
					jModel.removeRow(row);
					jModel.insertRow(row,obj);
					bControl.updateBolsa(bolsa);
				}
			} catch (SQLException e) {				
				e.printStackTrace();
			}
			pButtons.getButtonExcluir().setEnabled(false);
			enableDisableComponents(false);
			setGroupLayout();
		}
		
		public String dateVerify(String date){
			int day,month,maxDay,year;
			String strDay,strMonth,strYear;
			if (date.charAt(2)!='/' || date.charAt(5)!='/'){
				return "FORMATO DE DATA INVÁLIDO. ESCREVA dd/mm/aaaa";
			}
			
			try{
				strDay = date.substring(0,2);
				strMonth = date.substring(3,5);
				strYear = date.substring(6,10);
				day = Integer.parseInt(strDay);
				month = Integer.parseInt(strMonth);
				year = Integer.parseInt(strYear);
			}catch(NumberFormatException e){
				return "FORMATO DE DATA INVÁLIDO. ESCREVA dd/mm/aaaa";
			}
			
			if(month>12){
				return "MÊS INVÁLIDO.";
			}
			
			maxDay = (monthDay.get(month));
			if(month==2){
				maxDay = (((year%4==0)&&(year%100!=0))||(year%400==0)? ++maxDay:maxDay);
			}
			if(day>maxDay){
				return "DIA INVÁLIDO.";
			}
			return "";
		}
		
		public Date toSqlDate(String strDate){
			java.util.Date myDate = new java.util.Date();
			Date mySqlDate;
			try {
				myDate = df.parse(strDate);
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
			mySqlDate = new Date(myDate.getTime());
			return mySqlDate; 
		}
		
	}

	private class OthersOrgao implements ItemListener{
		
		public void itemStateChanged(ItemEvent ev) {
			
			if(ev.getItem().toString().equals("Outros")){
				setGroupLayoutOthers();
			}	
			else{				
				setGroupLayout();
				pData.remove(labOthers);
				pData.remove(textOthers);
				pData.validate();		
			}
		}		
	}
}
