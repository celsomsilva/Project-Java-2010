package br.com.view;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
*
* @author CMarcelo
*/
@SuppressWarnings("serial")
public class PanelButtons extends JPanel{

	private JButton buttonNovo,buttonExcluir,buttonSalvar,buttonCancelar;
	
	public PanelButtons(){

		buttonNovo = new JButton("Novo");
		buttonExcluir = new JButton("Excluir");
		buttonSalvar = new JButton("Salvar");
		buttonCancelar = new JButton("Cancelar"); 
				
		add(buttonNovo); 
		add(buttonExcluir); 
		add(buttonSalvar); 
		add(buttonCancelar);
		
		buttonExcluir.setEnabled(false);
		buttonSalvar.setEnabled(false);
		
	}
	
	public JButton getButtonCancelar() {
		return buttonCancelar;
	}

	public void setButtonCancelar(JButton buttonCancelar) {
		this.buttonCancelar = buttonCancelar;
	}

	public JButton getButtonExcluir() {
		return buttonExcluir;
	}

	public void setButtonExcluir(JButton buttonExcluir) {
		this.buttonExcluir = buttonExcluir;
	}

	public JButton getButtonNovo() {
		return buttonNovo;
	}

	public void setButtonNovo(JButton buttonNovo) {
		this.buttonNovo = buttonNovo;
	}

	public JButton getButtonSalvar() {
		return buttonSalvar;
	}

	public void setButtonSalvar(JButton buttonSalvar) {
		this.buttonSalvar = buttonSalvar;
	}
}
