package br.com.cleomilson.n2.visoes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class FContaLista{
	
	private JTextField tfNomeTitularPesquisa = new JTextField(20);
	
	private JFrame jContaLista = new JFrame("Pesquisando Conta");
	
	public FContaLista() throws ParseException, SQLException{
		jContaLista.setSize(450,450);
		jContaLista.setVisible(true);
		jContaLista.setLocationRelativeTo(null);
	}

	public void CriarFormularioCL() throws ParseException, SQLException{
		jContaLista.setLayout(new BorderLayout());
		
		JPanel pCorpo = new JPanel();
		pCorpo.setLayout(new GridBagLayout());

		tfNomeTitularPesquisa.addKeyListener(new KeyAdapter(){  
	        public void keyReleased(KeyEvent e){  
	        	jtUpper(tfNomeTitularPesquisa);
	        }
	    });
		
		JLabel lNomeTitularPesquisa = new JLabel("Nome Titular: ");
		
		JButton botaoGravar = new JButton("Consultar"); 
		
	    GridBagConstraints gbc = new GridBagConstraints();

	    //espaços entre as celulas: lados de cima, esquerda, inferior e direita
	    gbc.insets = new Insets(2, 2, 2, 10);
	    
	    gbc.gridy = 0; // linha
	    gbc.gridx = 0; // coluna
	    gbc.gridwidth = 2; //quantas celulas ocupa
	    gbc.anchor = GridBagConstraints.WEST; 
	    pCorpo.add(lNomeTitularPesquisa, gbc);
	    
		//ContaDAO contaSelect = new ContaDAO();
		DefaultTableModel modelo = new DefaultTableModel(); 
		JTable tabela = new JTable(modelo); 
		
		//ArrayList<Conta> lista = contaSelect.selecionar();
		
		
		//modelo.insertRow(lista);
	    
	    
		
	    
	    gbc.gridy = 0; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST; 
	    pCorpo.add(tabela, gbc);

	    
	    JPanel pBotoesConsultar = new JPanel();
	    pBotoesConsultar.setLayout(new FlowLayout());
	    pBotoesConsultar.add(botaoGravar);
	    ConsultarAction consultarAction = new ConsultarAction();
	    botaoGravar.addActionListener(consultarAction);

		jContaLista.add(pCorpo, BorderLayout.NORTH);
		jContaLista.add(pBotoesConsultar, BorderLayout.SOUTH);
	}

	public void jtUpper(JTextField jt){  
        String valor = jt.getText().toUpperCase();  
        jt.setText(valor);  
    }  
	
	private boolean verificarCampos(){
		boolean retorno = true;
		if (tfNomeTitularPesquisa.getText().equals("")){
			retorno = false;
		}
		return retorno;
	}
	
	private void LimparCampos(){
		tfNomeTitularPesquisa.setText(null);
	}
	
	private class ConsultarAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			//consulta
		}
	}
}

