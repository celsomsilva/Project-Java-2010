package br.com.view;


import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import br.com.dao.DBConnection;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.lang.ClassNotFoundException;

/**
*
* @author CMarcelo
*/
@SuppressWarnings("serial")
public class MainWindow extends JFrame{
	
	private Logging log;
	private JMenu[] menu;
	private final String[] menuNomes = {"Arquivo","Cadastro","Relat�rios",
			"Alunos","Bolsistas","Orientadores","Ajuda"};
	private final char[] mnMenuNomes = {'A','C','R','A','B','O','j'};
	private JMenuItem[] menuItem;
	private final String[] menuINomes = {"Conectar","Desconectar","Sair","Professor",
			"Aluno","Bolsa","Bolsista","Todos Alunos","Alunos de um ano","Todos Bolsistas",
			"Bolsistas de um ano","Bolsistas de um org�o","Todos Orientadores","Seus Orientandos",
			"T�picos da Ajuda","Sobre"};
	private final char[] mnMenuINomes = {'C','D','S','P','A','B','o','T','A',
			'T','B','o','T','S','T','S'};
	private JMenuBar jb;
	private String login;
	private char[] pass;
	private Connection con;
	private MyPanel myPanel;
	private List<String> lst;
	private WindowFrame win;			
	private String menuName;
	private int option;	
	
	public MainWindow(){
		super.setTitle("SISBOL");
		myPanel = new MyPanel();
		add(myPanel,BorderLayout.CENTER);
		jb = new JMenuBar();
		setJMenuBar(jb);
		menu = new JMenu[menuNomes.length];
		menuItem = new JMenuItem[menuINomes.length];
		for(int i=0;i<menuINomes.length;i++){
			menuItem[i] = new JMenuItem(menuINomes[i]);
			menuItem[i].setMnemonic(mnMenuINomes[i]);
			if(i<menuNomes.length){
				menu[i] = new JMenu(menuNomes[i]);
				menu[i].setMnemonic(mnMenuNomes[i]);
				jb.add(menu[i]);
			}			
		}
		lst = Arrays.asList(menuINomes);
		arquivoMenu();
		cadastroMenu();
		relatorioMenu();
		ajudaMenu();
		setSize(550,550);
		setExtendedState(MAXIMIZED_BOTH);
		setVisible(true);
		log = new Logging();
		log.getCancel().addActionListener(new eventsListener());
		log.getOk().addActionListener(new eventsListener());
		log.addWindowListener(new EnableMenu());
		log.getPassText().addActionListener(new eventsListener());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		
		MainWindow mn = new MainWindow();
		mn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}	
	
	public void arquivoMenu(){
		menu[0].add(menuItem[0]);
		menuItem[0].setEnabled(false);
		menu[0].add(menuItem[1]);
		menuItem[1].setEnabled(false);
		menu[0].addSeparator();
		menu[0].add(menuItem[2]);
		menuItem[0].addActionListener(new ArquivoMenu());
		menuItem[1].addActionListener(new ArquivoMenu());
		menuItem[2].addActionListener(new ArquivoMenu());
	}
	
	public void cadastroMenu(){
		menu[1].add(menuItem[3]);
		menu[1].add(menuItem[4]);
		menu[1].add(menuItem[5]);
		menu[1].add(menuItem[6]);
		menu[1].setEnabled(false);
		menuItem[3].addActionListener(new CadastroMenu());
		menuItem[4].addActionListener(new CadastroMenu());
		menuItem[5].addActionListener(new CadastroMenu());
		menuItem[6].addActionListener(new CadastroMenu());
				
	}
	
	public void relatorioMenu(){
		menu[2].add(menu[3]);
		menu[2].add(menu[4]);
		menu[2].add(menu[5]);
		menu[2].setEnabled(false);
		menu[3].add(menuItem[7]);
		menu[3].add(menuItem[8]);
		menu[4].add(menuItem[9]);
		menu[4].add(menuItem[10]);
		menu[4].add(menuItem[11]);
		menu[5].add(menuItem[12]);
		menu[5].add(menuItem[13]);
		menuItem[7].addActionListener(new RelatorioMenu());
		menuItem[8].addActionListener(new RelatorioMenu());
		menuItem[9].addActionListener(new RelatorioMenu());
		menuItem[10].addActionListener(new RelatorioMenu());
		menuItem[11].addActionListener(new RelatorioMenu());
		menuItem[12].addActionListener(new RelatorioMenu());
		menuItem[13].addActionListener(new RelatorioMenu());		
	}
	
	public void ajudaMenu(){
		menu[6].add(menuItem[14]);
		menu[6].addSeparator();
		menu[6].add(menuItem[15]);
		
		//T�PICOS DE AJUDA
		menuItem[14].addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent ev){
						JOptionPane.showMessageDialog(MainWindow.this, "T�pico em constru��o", "EM CONSTRU��O", JOptionPane.INFORMATION_MESSAGE);
					}
				}
		);//SOBRE
		menuItem[15].addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent ev){
						JOptionPane.showMessageDialog(MainWindow.this, "SISBOL - SISTEMA DE CADASTRO DE BOLSAS\nVers�o 1.0\nCr�ditos: Celso Marcelo da Silva\nProfessor Orlando Bernardo Filho", "ABOUT", JOptionPane.INFORMATION_MESSAGE);
					}
				}
		);
	}	
	
	public void reports(String param,String file){
		try {  
			 
	          HashMap<String,String> parameters = new HashMap<String,String>();
	          //zero � tipo um flag que enviarei sinalizando que n�o haver� par�metros
	          //porque, se houver, ele n�o pode ser ""(vazio)
	          param=(param.equals("0")?"":param);
	          parameters.put("PARAMETER_1",param); 
	          JasperPrint impressao =  JasperFillManager.fillReport("relatorios/"+file+".jasper",parameters,con);  
	          JasperViewer jrviewer = new JasperViewer(impressao,false); 
	          jrviewer.addWindowListener(new EnableMenu());
	          jrviewer.setVisible(true); 
	       }catch (Exception e) {
	    	   
	          e.printStackTrace(); 
	       }
	}
	

	//PARA EVITAR V�RIAS MUDAN�AS NO BANCO AO MESMO TEMPO, N�O PERMITINDO
	//QUE ABRA, POR EXEMPLO, JANELAS PROFESSOR E ALUNO AO MESMO TEMPO (E
	//TAMB�M SA�DA DIRETA PELO MENU PRINCIPAL)
	public void disableMenu(){

		menu[0].setEnabled(false);
		menu[1].setEnabled(false);
		menu[2].setEnabled(false);
	}
	
	private class EnableMenu extends WindowAdapter{
		//quando uma janela fecha			
		public void windowClosed(WindowEvent ev){
			if(ev.getWindow()!=log){
				menu[0].setEnabled(true);
				menu[1].setEnabled(true);
				menu[2].setEnabled(true);
			}
		}		
		
		//quando uma janela fecha ao clicar em fechar, na borda da janela
		public void windowClosing(WindowEvent ev) {
			
			if(ev.getWindow()!=log){

				menu[0].setEnabled(true);
				menu[1].setEnabled(true);
				menu[2].setEnabled(true);
			}else{
				menu[0].setEnabled(true);
				menuItem[0].setEnabled(true);
			}
			
		}		
	}
		
	private class eventsListener extends DBConnection implements ActionListener {


		public void actionPerformed(ActionEvent ev) {

			if(ev.getSource()==log.getOk() || ev.getSource()==log.getPassText()){
				login = log.getLoginText().getText();
				pass = log.getPassText().getPassword();
				log.dispose();
				try {
					con = this.getMyDBConnection(login, pass);
					menu[1].setEnabled(true);
					menu[2].setEnabled(true);
					menuItem[0].setEnabled(false);
					menuItem[1].setEnabled(true);
				} catch (SQLException e) {
					menuItem[0].setEnabled(true);
					JOptionPane.showMessageDialog(MainWindow.this, "ERRO AO CONECTAR-SE AO BANCO DE DADOS.\nVERIFIQUE SEU LOGIN E SUA SENHA", "ERRO", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				} catch (ClassNotFoundException e) {

					e.printStackTrace();
				}
			}
			else{
				log.dispose();
				menuItem[0].setEnabled(true);
			}

		}			
	}
	
	private class ArquivoMenu implements ActionListener{
				
		public void actionPerformed(ActionEvent ev) {
			menuName = ((JMenuItem)ev.getSource()).getActionCommand();
			//LISTA FEITA PARA DESCOBRI O INDEX DO SUBMENU E USAR ASSIM O SWITCH
			option = lst.indexOf(menuName);
			switch(option){
			
			//CONECTAR	
			case 0: log = new Logging();
			log.getCancel().addActionListener(new eventsListener());
			log.getOk().addActionListener(new eventsListener());
			log.getPassText().addActionListener(new eventsListener());
			log.addWindowListener(new EnableMenu());
			break;
			
			//DESCONECTAR	
			case 1: try {
				con.close();
				menuItem[0].setEnabled(true);
				menuItem[1].setEnabled(false);
				menu[1].setEnabled(false);
				menu[2].setEnabled(false);
			} catch (SQLException e) {				
				e.printStackTrace();
			}
			break;
			
			//SAIR		
			case 2: try {
				con.close();
			} catch (SQLException e) {				
				e.printStackTrace();
			}
			System.exit(0);	
			break;		
			}			
		}
	}
		
	private class CadastroMenu implements ActionListener{

		public void actionPerformed(ActionEvent ev) {
			menuName = ((JMenuItem)ev.getSource()).getActionCommand();
			option = lst.indexOf(menuName);
			
			switch(option){
			//PROFESSOR 	
			case 3: win = new ProfessorWindow(con);
			break;
			//ALUNO 
			case 4: win = new AlunoWindow(con);
			break;
			//BOLSA	
			case 5: win = new BolsaWindow(con);	
			break;
			//BOLSISTA
			case 6: win = new BolsistaWindow(con);	
			break;					
			}

			win.setVisible(true);
			disableMenu();
			win.addWindowListener(new EnableMenu());

		}

	}
	
	private class RelatorioMenu implements ActionListener{

		String str;
		String fileName;
		public void actionPerformed(ActionEvent ev) {
			menuName = ((JMenuItem)ev.getSource()).getActionCommand();
			option = lst.indexOf(menuName);
			
			switch(option){
			
			//RELAT�RIO//ALUNOS//TODOS 	
			case 7: str = "0";
			fileName = "Alunos";
			break;
			
			//RELAT�RIO//ALUNOS//PORANO
			case 8: str=JOptionPane.showInputDialog(null,
					"Informe o ano: ", "Ano", JOptionPane.QUESTION_MESSAGE);
			fileName = "AlunosDeUmAno";
			break;
			
			//RELAT�RIO//BOLSISTAS//TODOS
			case 9: str = "0";
			fileName = "Bolsistas";	
			break;
			
			//RELAT�RIO//BOLSISTAS//POR ANO
			case 10: str=JOptionPane.showInputDialog(null,
					"Informe o ano: ", "Ano", JOptionPane.QUESTION_MESSAGE);
			fileName = "BolsistasDeUmAno";
			break;	
			
			//RELAT�RIO//BOLSISTAS//POR ORG�O
			case 11: str=JOptionPane.showInputDialog(null,
					"Informe o org�o: ", "Org�o", JOptionPane.QUESTION_MESSAGE);
			fileName = "BolsistasPorOrgao";
			break;	
			
			//RELAT�RIO//ORIENTADORES//TODOS
			case 12: str = "0";	
			fileName = "Orientadores";
			break;	
			
			//RELAT�RIO//ORIENTADORES//SEUS ORIENTADOS
			case 13: str=JOptionPane.showInputDialog(null,
					"Informe a matr�cula do professor: ", "Professor", JOptionPane.QUESTION_MESSAGE);
			fileName = "OrientandosPorProfessor";
			break;						
			}

			if(str==null){
				str="";						
			}
			if(!str.isEmpty()){
				reports(str,fileName);
				disableMenu();	
			}
		}

	}
	
}
