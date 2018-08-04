package br.com.view;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
*
* @author CMarcelo
*/
@SuppressWarnings("serial")
public abstract class PessoaWindow extends WindowFrame{
	
	protected JPanel pData;
	protected JTextField nome,cpf,matricula;
	protected JLabel lNome,lCpf,lMatricula;
	
	public PessoaWindow(){		
		
		pData = new JPanel();
		panelData();				
		cont.add(pData,BorderLayout.CENTER);
		
	}

	public void panelData(){
		nome = new JTextField();
	    nome.setEnabled(false);
		cpf = new JTextField();
	    cpf.setEnabled(false);
		matricula = new JTextField();
	    matricula.setEnabled(false);
		lNome = new JLabel("Nome: ");
	    lNome.setEnabled(false);
		lCpf = new JLabel("CPF: ");
	    lCpf.setEnabled(false);
		lMatricula = new JLabel("Matrícula: ");
	    lMatricula.setEnabled(false);
	}	
	
}
