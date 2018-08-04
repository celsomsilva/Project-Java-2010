package br.com.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
*
* @author CMarcelo
*/
@SuppressWarnings("serial")
public abstract class WindowFrame extends JFrame {
	

	protected JTable jt;
	protected Container cont;
	protected JScrollPane scroll;
	protected Toolkit tk;
	protected Dimension tela;
	protected DefaultTableModel jModel;
	protected PanelButtons pButtons;
	protected GroupLayout layout;
	
	public WindowFrame(){
		
		cont = getContentPane();
		
		jt = new JTable();
		scroll = new JScrollPane(jt);
		cont.add(scroll,BorderLayout.NORTH);
		jModel = (DefaultTableModel) jt.getModel();
		
		pButtons = new PanelButtons();
		cont.add(pButtons,BorderLayout.SOUTH);	
		
		tk = Toolkit.getDefaultToolkit();
		tela = tk.getScreenSize();
		int widthSize = 2*tela.width/3;
		int heigtSize = 4*tela.height/5;
		setSize(widthSize,heigtSize);
		setLocation(tela.width/6, tela.height/10);

		tela.setSize(widthSize, heigtSize/2);
		scroll.setPreferredSize(tela);
		
		setVisible(true);		
	}

	public abstract void triggerTable();
	
	public abstract void setGroupLayout();
	
	public abstract void listenersImplementation();
}
