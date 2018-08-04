package br.com.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import br.com.control.AlunoControl;
import br.com.control.BolsaControl;
import br.com.control.BolsistaControl;
import br.com.control.ProfessorControl;
import br.com.dao.Bolsista;

/**
*
* @author CMarcelo
*/
@SuppressWarnings("serial")
public class BolsistaWindow extends WindowFrame{
	
	private BolsistaControl bControl;
	private final String[] colName = {"Matrícula Aluno","Matrícula Orientador",
			"Matrícula Co-Orientador","Bolsa ID","Relatórios FAPERJ",
			"Data de Iníco","Data do Fim"};
	private JPanel pData;
	private JComboBox[] combs;
	private JTextField relFaperj,dataInicio,dataFim;
	private JLabel[] labels;
	private Connection con;
	private BolsaControl bolControl;
	private AlunoControl aControl;
	private ProfessorControl pControl;
	private Vector<Object[]> vec;
	private List<Bolsista> list;
	private DateFormat df;
	private Map<Integer,Integer> monthDay;
	private final int[] dayArray = {31,28,31,30,31,30,31,31,30,31,30,31};

	public BolsistaWindow(Connection con)	{
		
		this.con = con;
		bControl = new BolsistaControl(con);
		inicializar();	
		
	}
	
	public BolsistaWindow(String login,char[] password){

		bControl = new BolsistaControl(login,password);
		inicializar();
	}	
	
	public void inicializar(){
		String text;
		super.setTitle("Bolsistas");

		df = DateFormat.getDateInstance();
		
		pData = new JPanel();
		combs = new JComboBox[4];
		labels = new JLabel[colName.length];
		relFaperj = new JTextField();
		relFaperj.setEnabled(false);
		dataInicio = new JTextField();
		dataFim = new JTextField();
		dataInicio.setEnabled(false);
		dataFim.setEnabled(false);
		jComboData();
		
		for(int i=0;i<colName.length;i++){
			if(i<4){
				combs[i] = new JComboBox(vec.get(i));
				combs[i].insertItemAt("", 0);
				combs[i].setEnabled(false);
				combs[i].setSelectedIndex(0);
			}
			text = ((i==5||i==6) ?" (dd/mm/aaaa): " :": ");
			labels[i] = new JLabel(colName[i]+text);
			labels[i].setEnabled(false);
			
		}
						
		cont.add(pData,BorderLayout.CENTER);
		
		jModel.setColumnIdentifiers(colName);
		triggerTable();
		triggerMap();
		setGroupLayout();
		listenersImplementation();
	}
	
	public void jComboData(){
		aControl = new AlunoControl(con);
		List<String> lst;
		lst = new LinkedList<String>();
		vec = new Vector<Object[]>();
		try {
			lst = aControl.getAluno("","matricula");
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		Object[] array = lst.toArray();
		vec.add(array);
		
		pControl = new ProfessorControl(con);
		try {
			lst = pControl.getProfessor("","matricula");
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		array = lst.toArray();
		vec.add(array);
		vec.add(array);
		
		bolControl = new BolsaControl(con);
		try {
			lst = bolControl.getBolsas(0,"id");
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		array = lst.toArray();
		vec.add(array);
	}
	
	public void triggerTable(){

		list = new LinkedList<Bolsista>();
		try {
			list = bControl.getBolsista("");
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		Object[] objects = new Object[colName.length];
		for(Bolsista bolsista:list){
			objects[0] = bolsista.getMatAluno();
			objects[1] = bolsista.getMatOrientador();
			objects[2] = bolsista.getMatCoOrientador();
			objects[3] = bolsista.getIdBolsa();
			objects[4] = bolsista.getRelFaperj();
			objects[5] = df.format(bolsista.getDataInicio());
			try{
			objects[6] = df.format(bolsista.getDataFim());
			}catch(NullPointerException e){
				objects[6] = "";
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
		            addComponent(labels[4]).
		            addComponent(labels[5]).
		            addComponent(labels[6]));
		   hGroup.addGroup(layout.createParallelGroup().
		            addComponent(combs[0]).
		            addComponent(combs[1]).
		            addComponent(combs[2]).
		            addComponent(combs[3]).
		            addComponent(relFaperj).
		            addComponent(dataInicio).
		            addComponent(dataFim));
		   layout.setHorizontalGroup(hGroup);
		   
		   GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		 	
		   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
		            addComponent(labels[0]).
		            addComponent(combs[0]));
		   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
		            addComponent(labels[1]).
		            addComponent(combs[1]));
		   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
		            addComponent(labels[2]).
		            addComponent(combs[2]));
		   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
		            addComponent(labels[3]).
		            addComponent(combs[3]));
		   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
		            addComponent(labels[4]).
		            addComponent(relFaperj));
		   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
		            addComponent(labels[5]).
		            addComponent(dataInicio));
		   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
		            addComponent(labels[6]).
		            addComponent(dataFim));
		   layout.setVerticalGroup(vGroup);
	}

	public void listenersImplementation(){
		pButtons.getButtonNovo().addActionListener(new NewCancelButton());
		pButtons.getButtonExcluir().addActionListener(new DeleteButton());
		pButtons.getButtonSalvar().addActionListener(new SaveButton());
		pButtons.getButtonCancelar().addActionListener(new NewCancelButton());
		jt.addMouseListener(new SelectTableMouse());
		jt.addKeyListener(new SelectTableKey());
		combs[3].addPopupMenuListener(new RelFaperj());
	}
	
	public void enableComponents(int row){
		Object obj;
		String str;
		pButtons.getButtonExcluir().setEnabled(true);
		pButtons.getButtonSalvar().setEnabled(true);
		combs[0].setEnabled(true);
		combs[1].setEnabled(true);
		combs[2].setEnabled(true);
		combs[3].setEnabled(true);
		labels[0].setEnabled(true);
		labels[1].setEnabled(true);
		labels[2].setEnabled(true);
		labels[3].setEnabled(true);
		labels[5].setEnabled(true);
		dataInicio.setEnabled(true);
		labels[6].setEnabled(true);
		dataFim.setEnabled(true);	

		combs[0].setSelectedItem(jt.getValueAt(row, 0));
		combs[1].setSelectedItem(jt.getValueAt(row, 1));
		combs[2].setSelectedItem(jt.getValueAt(row, 2));
		obj = jt.getValueAt(row, 3);
		str = String.valueOf(obj);
		combs[3].setSelectedItem(str);
		dataInicio.setText((String)jt.getValueAt(row, 5));
		dataFim.setText((String)jt.getValueAt(row, 6));
	}
	
	public void enableDisableComponents(boolean flag){
		pButtons.getButtonSalvar().setEnabled(flag);
		combs[0].setSelectedIndex(0);
		combs[1].setSelectedIndex(0);
		combs[2].setSelectedIndex(0);
		combs[3].setSelectedIndex(0);
		combs[0].setEnabled(flag);
		combs[1].setEnabled(flag);
		combs[2].setEnabled(flag);
		combs[3].setEnabled(flag);
		labels[0].setEnabled(flag);
		labels[1].setEnabled(flag);
		labels[2].setEnabled(flag);
		labels[3].setEnabled(flag);
		labels[4].setEnabled(false);
		labels[5].setEnabled(flag);
		labels[6].setEnabled(flag);
		relFaperj.setEnabled(false);
		relFaperj.setText("");
		dataInicio.setEnabled(flag);
		dataInicio.setText(flag==true?currentDate():"");
		dataFim.setEnabled(flag);
		dataFim.setText("");
	}
	
	public void relFaperjEnable(int row){
		int idBol; 
		String faperj="";		
		
		faperj = (row!=-1 ? String.valueOf(jt.getValueAt(row, 3)) : String.valueOf(combs[3].getSelectedItem()));
		if(!faperj.isEmpty()){
			try {
				idBol = Integer.parseInt(faperj);
				faperj = bolControl.getOrgao(idBol);
			} catch (SQLException e) {
				
				e.printStackTrace();
			}	
		}

		if(faperj.equals("FAPERJ")){
			relFaperj.setEnabled(true);
			labels[4].setEnabled(true);
			if(row!=-1)
				relFaperj.setText(String.valueOf(jt.getValueAt(row, 4)));
		}
		else{
			relFaperj.setEnabled(false);
			labels[4].setEnabled(false);
		}				
	}
	
	private class SelectTableKey extends KeyAdapter{
		
		public void keyPressed(KeyEvent ev) {
			
			if(ev.getSource()==jt && (ev.getKeyCode()== (KeyEvent.VK_DOWN)||ev.getKeyCode()== (KeyEvent.VK_UP))){
				int row = jt.getSelectedRow();
				int rows = jt.getRowCount();
				if((row < rows-1)&& ev.getKeyCode()==KeyEvent.VK_DOWN)
					row++;
				if((row > 0)&& ev.getKeyCode()==KeyEvent.VK_UP)
					row--;
								
				enableComponents(row);				
				relFaperjEnable(row);				
			}
		}
	}
	
	private class SelectTableMouse extends MouseAdapter{
		
		public void mousePressed(MouseEvent ev) {
			if(ev.getSource()==jt){
				int row = jt.getSelectedRow();
				
				enableComponents(row);				
				relFaperjEnable(row);				
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
			int op = JOptionPane.showConfirmDialog(null,"Tem certeza que deseja apagar o bolsista selecionado?"
				,"Apagar Bolsista",JOptionPane.YES_NO_OPTION);
			if(op==0){
				try {
					bControl.removeBolsista((String)combs[0].getSelectedItem());
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
				Bolsista bolsista;
				List<Bolsista> lst;
				int faperj = 0;
				String str;
				String msg = "",msg2 = "";
				str = (String)combs[3].getSelectedItem();
				int idBolsa = 0;
				//!relFaperj.getText().isEmpty(): impede que o campo relFaperj ganhe um valor "",
				//caso o usuário não coloque valor e tb não é necessario testar se o campo se envontra vazio
				if(relFaperj.isEnabled()&& !relFaperj.getText().isEmpty()){
					faperj = Integer.parseInt(relFaperj.getText());
				}
				Object[] obj = {combs[0].getSelectedItem(),combs[1].getSelectedItem(),combs[2].
						getSelectedItem(),str,faperj,dataInicio.getText(),dataFim.getText()};
				
				lst = new LinkedList<Bolsista>();
				lst = bControl.getBolsista((String)combs[0].getSelectedItem());
				
				msg = (obj[1].equals(obj[2])?"'"+colName[1]+"' e '"+colName[2]+"' NÃO PODEM SER IGUAIS\n":"");
				for(int i=0;i<colName.length;i++){
					
					if(i!=2 && i!=4 && i!=6 &&((String)obj[i]).isEmpty()){
						msg = msg+"'"+colName[i]+"' NÃO PODE SER NULO\n";
					}
					if((i==5 && !((String)obj[i]).isEmpty()) || (i==6 && !((String)obj[i]).isEmpty())){
						msg2 = dateVerify((String)obj[i]);
						if(!msg2.isEmpty())
							msg = msg+colName[i]+": "+msg2+"\n";
					}
				}
				
				if(!msg.isEmpty()){
					JOptionPane.showMessageDialog(BolsistaWindow.this, "O(s) CAMPO(s):\n"+msg+
							"PREENCHA-O(s) CORRETAMENTE.", "ERRO", JOptionPane.ERROR_MESSAGE);
					return;
				}			
				
				idBolsa = Integer.parseInt(str);
				bolsista = new Bolsista((String)obj[0],(String)obj[1],(String)obj[2],idBolsa,faperj,
						toSqlDate((String)obj[5]),(obj[6].equals("")?null:toSqlDate((String)obj[6])));
				
				if(lst.isEmpty()){
					bControl.addBolsista(bolsista);
					jModel.addRow(obj);
				}
				else{
					int row = jt.getSelectedRow();
					jModel.removeRow(row);
					jModel.insertRow(row,obj);
					bControl.updateBolsista(bolsista);
				}
				
				pButtons.getButtonExcluir().setEnabled(false);
				enableDisableComponents(false);
			} catch (SQLException e) {				
				e.printStackTrace();
			} catch (ArrayIndexOutOfBoundsException e){
				JOptionPane.showMessageDialog(BolsistaWindow.this,"ATENÇÃO: NÃO É PERMITIDO REPETIR A MATRÍCULA DE UM ALUNO JÁ CADASTRADO COMO BOLSISTA"
						,"ERRO",JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
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
		
	public class RelFaperj implements PopupMenuListener{

		public void popupMenuWillBecomeInvisible(PopupMenuEvent ev) {
			relFaperjEnable(-1);			
		}
		
		public void popupMenuWillBecomeVisible(PopupMenuEvent ev) {			
		}
		
		public void popupMenuCanceled(PopupMenuEvent ev) {			
		}		
	}
}
