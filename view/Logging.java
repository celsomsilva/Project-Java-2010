package br.com.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;

/**
*
* @author CMarcelo
*/
@SuppressWarnings("serial")
public class Logging extends JFrame{
	
	private JPasswordField passText;
	private JTextField loginText;
	private JLabel labelLogin,labelPass;
	private Container container;
	private JButton ok,cancel;
	private JPanel pData,panelButtons;
	private GroupLayout layout;
	
	public Logging(){
		super.setTitle("LOGIN");
		container = getContentPane();
		pData = new JPanel();
		panelButtons = new JPanel();
		panelButtons.setLayout(new FlowLayout());
		
		labelLogin = new JLabel("Usuário: ");
		loginText = new JTextField(20);
		labelPass = new JLabel("Senha:   ");
		passText = new JPasswordField(20);
		setGroupLayout();
		container.add(pData);
		ok = new JButton("Ok");
		panelButtons.add(ok);
		cancel = new JButton("Cancel");
		panelButtons.add(cancel);
		container.add(panelButtons,BorderLayout.SOUTH);
		
		setSize(350,150);
		setLocation(350, 300);
		setVisible(true);
	}
	
	public void setGroupLayout(){
		
		
		   layout = new GroupLayout(pData);
		   pData.setLayout(layout);	
		   layout.setAutoCreateGaps(true);
		   layout.setAutoCreateContainerGaps(true);
		   		   
		   GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		 		  
		   hGroup.addGroup(layout.createParallelGroup().
		            addComponent(labelLogin).
		            addComponent(labelPass));
		   hGroup.addGroup(layout.createParallelGroup().
		            addComponent(loginText).
		            addComponent(passText));
		   layout.setHorizontalGroup(hGroup);
		   
		   GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		 	
		   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
		            addComponent(labelLogin).
		            addComponent(loginText));
		   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
		            addComponent(labelPass).
		            addComponent(passText));
		   layout.setVerticalGroup(vGroup);
	}

	public JButton getCancel() {
		return cancel;
	}

	public JButton getOk() {
		return ok;
	}

	public JTextField getLoginText() {
		return loginText;
	}

	public JPasswordField getPassText() {
		return passText;
	}
}
