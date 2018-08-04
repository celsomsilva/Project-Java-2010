package br.com.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.GroupLayout.Alignment;

import br.com.control.ProfessorControl;
import br.com.dao.Professor;

/**
*
* @author CMarcelo
*/
@SuppressWarnings("serial")
public class ProfessorWindow extends PessoaWindow {
	
	private ProfessorControl pControl;
	private JScrollPane scrollText;
	private final String[] colName = {"Matrícula","Nome","CPF","Áreas de Interesse","Vínculo"};
	private JTextField vinculo;
	private JLabel lAreas,lVinculo;
	private JTextPane jtPane;
	
	public ProfessorWindow(Connection con)	{
		
		pControl = new ProfessorControl(con);
		inicializar();	
		
	}
	
	public ProfessorWindow(String login,char[] password){

		pControl = new ProfessorControl(login,password);
		inicializar();
			
	}
	
	public void inicializar(){
								
		super.setTitle("Professores");
		jModel.setColumnIdentifiers(colName);
		lAreas = new JLabel(colName[3]);
		lAreas.setEnabled(false);
	    lVinculo = new JLabel(colName[4]);
	    lVinculo.setEnabled(false);
	    jtPane = new JTextPane();
		scrollText = new JScrollPane(jtPane);
	    jtPane.setEnabled(false);
		vinculo = new JTextField();
	    vinculo.setEnabled(false);
		triggerTable();
		setGroupLayout();
		listenersImplementation();
		
	}
	
	public void triggerTable(){

		List<Professor> list;
		list = new LinkedList<Professor>();
		try {
			list = pControl.getProfessor("");
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		Object[] objects = new Object[5];
		for(Professor professor:list){
			objects[0] = professor.getMat();
			objects[1] = professor.getNome();
			objects[2] = professor.getCpf();
			objects[3] = professor.getInteresse();
			objects[4] = professor.getVinculo();
			jModel.addRow(objects);
		}
		jt.setModel(jModel);
	}
	
	public void setGroupLayout(){
		
		
		   layout = new GroupLayout(pData);
		   pData.setLayout(layout);	
		   layout.setAutoCreateGaps(true);
		   layout.setAutoCreateContainerGaps(true);
		   		   
		   GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		 		  
		   hGroup.addGroup(layout.createParallelGroup().
		            addComponent(lMatricula).addComponent(lNome).
		            addComponent(lCpf).
		            addComponent(lAreas).
		            addComponent(lVinculo));
		   hGroup.addGroup(layout.createParallelGroup().
		            addComponent(matricula).addComponent(nome).
		            addComponent(cpf).
		            addComponent(scrollText).
		            addComponent(vinculo));
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
		            addComponent(lAreas).
		            addComponent(scrollText));
		   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
		            addComponent(lVinculo).
		            addComponent(vinculo));
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
		jtPane.setEnabled(true);
		vinculo.setEnabled(true);
		lNome.setEnabled(true);
		lCpf.setEnabled(true);
		lMatricula.setEnabled(true);
		lAreas.setEnabled(true);
		lVinculo.setEnabled(true);

		matricula.setText((String)jt.getValueAt(row, 0));
		nome.setText((String)jt.getValueAt(row, 1));
		cpf.setText((String)jt.getValueAt(row, 2));
		jtPane.setText((String)jt.getValueAt(row, 3));
		vinculo.setText((String)jt.getValueAt(row, 4));
	}
	
	public void enableDisableComponents(boolean flag){

		pButtons.getButtonSalvar().setEnabled(flag);
		lNome.setEnabled(flag);
		lMatricula.setEnabled(flag);
		lCpf.setEnabled(flag);
		lAreas.setEnabled(flag);
		lVinculo.setEnabled(flag);
		nome.setEnabled(flag);
		cpf.setEnabled(flag);
		matricula.setEnabled(flag);
		jtPane.setEnabled(flag);
		vinculo.setEnabled(flag);
		nome.setText("");
		cpf.setText("");
		matricula.setText("");
		jtPane.setText("");
		vinculo.setText("");
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
			int op = JOptionPane.showConfirmDialog(null,"Tem certeza que deseja apagar o professor selecionado?"
				,"Apagar Aluno",JOptionPane.YES_NO_OPTION);
			if(op==0){
				try {
					pControl.removeProfessor(matricula.getText());
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
				String[] obj = {matricula.getText(),nome.getText(),cpf.getText(),jtPane.getText(),vinculo.getText()};
				int[] max = {20,50,11,200,30};
				String msg = "";
				List<Professor> lst;
				lst = new LinkedList<Professor>();
				lst = pControl.getProfessor(obj[0]);
				
				//prestar atenção que o tamanho de caracteres de cada campo não pode passar
				//do tamanho de cada campo da TABELA FEITA NO MYSQL	
				for(int i=0;i<obj.length;i++){
					if(obj[i].length()>max[i]){
						msg = msg+"'"+colName[i]+"' NÃO PODE TER MAIS QUE "+max[i]+" CARACTERES\n";
					}
					if(obj[i].isEmpty()){
						msg = msg+"'"+colName[i]+"' NÃO PODE SER NULO\n";
					}
				}
				if(!msg.isEmpty()){
					JOptionPane.showMessageDialog(ProfessorWindow.this, "O(s) CAMPO(s):\n"+msg+"PREENCHA-O(s) CORRETAMENTE.", "ERRO", JOptionPane.ERROR_MESSAGE);
					return;
				}			
				
				Professor professor = new Professor(obj[1],obj[2],obj[0],obj[3],obj[4]);
				
				if(lst.isEmpty()){
					pControl.addProfessor(professor);
					jModel.addRow(obj);
				}
				else{
					int row = jt.getSelectedRow();
					jModel.removeRow(row);
					jModel.insertRow(row,obj);
					pControl.updateProfessor(professor);
				}
			} catch (SQLException e) {				
				e.printStackTrace();
			}
			pButtons.getButtonExcluir().setEnabled(false);
			enableDisableComponents(false);
		}	
	}
}
