package br.com.view;

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

import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;

import br.com.control.AlunoControl;
import br.com.dao.Aluno;

/**
*
* @author CMarcelo
*/
@SuppressWarnings("serial")
public class AlunoWindow extends PessoaWindow {
	
	private AlunoControl aControl;
	private final String[] colName = {"Matrícula","Nome","CPF","Status","Entrada","Conclusão"};
	private final String[] combNames = {"","Em Dissertação","Em Créditos","Concluinte","Trancado"};
	private JComboBox status;
	private JLabel lStatus,lInicio,lFim;
	private JTextField dataInicio,dataFim;
	private List<Aluno> list;
	private DateFormat df;
	private Map<Integer,Integer> monthDay;
	private final int[] dayArray = {31,28,31,30,31,30,31,31,30,31,30,31};
	
	public AlunoWindow(Connection con)	{
		
		aControl = new AlunoControl(con);
		inicializar();	
		
	}
	
	public AlunoWindow(String login,char[] password){

		aControl = new AlunoControl(login,password);
		inicializar();
			
	}
	
	public void inicializar(){
				
		super.setTitle("Alunos");
		df = DateFormat.getDateInstance();
		jModel.setColumnIdentifiers(colName);
		lStatus = new JLabel(colName[3]+": ");
	    lStatus.setEnabled(false);
		status = new JComboBox(combNames);
		status.setEnabled(false);
		lInicio = new JLabel(colName[4]+" (dd/mm/aaaa): ");
		lInicio.setEnabled(false);
		dataInicio = new JTextField();
		dataInicio.setEnabled(false);
		lFim = new JLabel(colName[5]+" (dd/mm/aaaa): ");
		lFim.setEnabled(false);
		dataFim = new JTextField();
		dataFim.setEnabled(false);		
		triggerTable();
		triggerMap();
		setGroupLayout();
		listenersImplementation();		
	}
	
	public void triggerTable(){

		list = new LinkedList<Aluno>();
		try {
			list = aControl.getAluno("");
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		Object[] objects = new Object[colName.length];
		for(Aluno aluno:list){
			objects[0] = aluno.getMat();
			objects[1] = aluno.getNome();
			objects[2] = aluno.getCpf();
			objects[3] = aluno.getStatus();
			objects[4] = df.format(aluno.getDataInicio());
			try{
			objects[5] = df.format(aluno.getDataFim());
			}catch(NullPointerException e){
				objects[5] = "";
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
		            addComponent(lMatricula).
		            addComponent(lNome).
		            addComponent(lCpf).
		            addComponent(lStatus).
		            addComponent(lInicio).
		            addComponent(lFim));
		   hGroup.addGroup(layout.createParallelGroup().
		            addComponent(matricula).
		            addComponent(nome).
		            addComponent(cpf).
		            addComponent(status).
		            addComponent(dataInicio).
		            addComponent(dataFim));
		   layout.setHorizontalGroup(hGroup);
		   
		   GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		 	
		   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
		            addComponent(lMatricula).
		            addComponent(matricula));
		   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
		            addComponent(lNome).
		            addComponent(nome));
		   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
		            addComponent(lCpf).
		            addComponent(cpf));		   
		   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
		            addComponent(lStatus).
		            addComponent(status));
		   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
		            addComponent(lInicio).
		            addComponent(dataInicio));
		   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
		            addComponent(lFim).
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
	}

	public void enableComponents(int row){
		pButtons.getButtonExcluir().setEnabled(true);
		pButtons.getButtonSalvar().setEnabled(true);
		nome.setEnabled(true);
		cpf.setEnabled(true);
		matricula.setEnabled(false);
		status.setEnabled(true);
		lNome.setEnabled(true);
		lCpf.setEnabled(true);
		lMatricula.setEnabled(true);
		lStatus.setEnabled(true);
		lInicio.setEnabled(true);
		dataInicio.setEnabled(true);
		lFim.setEnabled(true);
		dataFim.setEnabled(true);	
		
		matricula.setText((String)jt.getValueAt(row, 0));
		nome.setText((String)jt.getValueAt(row, 1));
		cpf.setText((String)jt.getValueAt(row, 2));
		status.setSelectedItem(jt.getValueAt(row, 3));
		dataInicio.setText((String)jt.getValueAt(row, 4));
		dataFim.setText((String)jt.getValueAt(row, 5));
	}
	
	public void enableDisableComponents(boolean flag){

		pButtons.getButtonSalvar().setEnabled(flag);
		status.setEnabled(flag);
		status.setSelectedIndex(0);
		lNome.setEnabled(flag);
		lCpf.setEnabled(flag);
		lMatricula.setEnabled(flag);
		lStatus.setEnabled(flag);
		nome.setEnabled(flag);
		nome.setText("");
		cpf.setEnabled(flag);
		cpf.setText("");
		matricula.setEnabled(flag);
		matricula.setText("");
		lInicio.setEnabled(flag);
		lFim.setEnabled(flag);
		dataInicio.setEnabled(flag);
		dataInicio.setText(flag==true?currentDate():"");
		dataFim.setEnabled(flag);
		dataFim.setText("");
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
			int op = JOptionPane.showConfirmDialog(null,"Tem certeza que deseja apagar o aluno selecionado?"
				,"Apagar Aluno",JOptionPane.YES_NO_OPTION);
			if(op==0){
				try {
					aControl.removeAluno(matricula.getText());
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
				String[] obj = {matricula.getText(),nome.getText(),cpf.getText(),
						(String)status.getSelectedItem(),dataInicio.getText(),
						dataFim.getText()};
				int[] max = {20,50,11,20,10,10};
				String msg = "",msg2 = "";
				List<Aluno> lst;
				lst = new LinkedList<Aluno>();
				lst = aControl.getAluno(obj[0]);
				
				//prestar atenção que o tamanho de caracteres de cada campo não pode passar
				//do tamanho de cada campo da TABELA FEITA NO MYSQL	
				for(int i=0;i<obj.length;i++){
					if(obj[i].length()>max[i]){
						msg = msg+"'"+colName[i]+"' NÃO PODE TER MAIS QUE "+max[i]+" CARACTERES\n";
					}
					if(obj[i].isEmpty() && i!=5){
						msg = msg+"'"+colName[i]+"' NÃO PODE SER NULO\n";
					}
					if((i==4 && !obj[i].isEmpty()) || (i==5 && !obj[5].isEmpty())){
						msg2 = dateVerify((String)obj[i]);
						if(!msg2.isEmpty())
							msg = msg+colName[i]+": "+msg2+"\n";
					}
				}
				if(!msg.isEmpty()){
					JOptionPane.showMessageDialog(AlunoWindow.this, "O(s) CAMPO(s):\n"+msg+
							"PREENCHA-O(s) CORRETAMENTE.", "ERRO", JOptionPane.ERROR_MESSAGE);
					return;
				}			
				
				Aluno aluno = new Aluno(obj[1],obj[2],obj[0],obj[3],
						toSqlDate(obj[4]),(obj[5].equals("")?null:toSqlDate(obj[5])));
				
				if(lst.isEmpty()){
					aControl.addAluno(aluno);
					jModel.addRow(obj);
				}
				else{
					int row = jt.getSelectedRow();
					jModel.removeRow(row);
					jModel.insertRow(row,obj);
					aControl.updateAluno(aluno);
				}
			} catch (SQLException e) {				
				e.printStackTrace();
			}
			pButtons.getButtonExcluir().setEnabled(false);
			enableDisableComponents(false);
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
}
