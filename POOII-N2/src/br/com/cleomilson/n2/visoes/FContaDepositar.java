package br.com.cleomilson.n2.visoes;

/* desenvolvido por Cleomilson Sales - email: cleomilsonsales@hotmail.com
Tecnologia java swing e awt (sem plugins);
Data: 27/05/14
*/

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import br.com.cleomilson.n2.exceptions.ContaNaoEncontradaException;
import br.com.cleomilson.n2.util.Conta;
import br.com.cleomilson.n2.util.Controle;
import br.com.cleomilson.n2.util.Main;


@SuppressWarnings("serial")
public class FContaDepositar extends JFrame{
	
	private JTextField tfCodigoDeposito = new JTextField(10);
	private JFormattedTextField tfCpfDeposito = new JFormattedTextField();
	private JTextField tfValorDeposito = new JTextField(10);
	
	//private JFrame jContaDeposito = new JFrame("Deposito em Conta");
	
	public FContaDepositar() throws ParseException{
		super("Deposito em Conta");
		setSize(450,250);
		setVisible(true);
		setLocationRelativeTo(null);
		repaint();
		
	}

	public void criarFormularioCD() throws ParseException{
		setLayout(new BorderLayout());
		
		JPanel pCorpo = new JPanel();
		pCorpo.setLayout(new GridBagLayout());
		
		//criando mascaras
		
		MaskFormatter mascaraCpf = new MaskFormatter("###.###.###-##");
		mascaraCpf.setPlaceholderCharacter('_');
		tfCpfDeposito = new JFormattedTextField(mascaraCpf);
		
		//--
		
		//ações de fields
		
		tfCodigoDeposito.addFocusListener(new FocusAdapter() {  
            public void focusLost(FocusEvent evt) {  
            	jtfFocusLostNum(tfCodigoDeposito);
            }  
        });
		
		tfCodigoDeposito.addKeyListener(new KeyAdapter(){  
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {  
						depositando();						
					}  
				}
		 });
		tfCpfDeposito.addKeyListener(new KeyAdapter(){  
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {  
					depositando();						
				}  
			}
		});
		tfValorDeposito.addKeyListener(new KeyAdapter(){  
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {  
					depositando();						
				}  
			}
		});
		
		tfValorDeposito.addFocusListener(new FocusAdapter() {  
            public void focusLost(FocusEvent evt) {  
            	jtfFocusLostDouble(tfValorDeposito);
            }  
        });

		//--
		
		JLabel lCodigo = new JLabel("Código da Conta: ");
		JLabel lCpf = new JLabel("CPF: ");
		JLabel lValorDeposito = new JLabel("Valor: ");
		
		JButton botaoConsultar = new JButton("Depositar"); 
		JButton botaoCancelar = new JButton("Cancelar");
		
		JLabel lTitulo = new JLabel("Deposito em Conta");
		JLabel lEspacoTitulo = new JLabel(" ");
		JLabel lEspaco = new JLabel(" ");
		// PLAIN = formato regular;
		lTitulo.setFont(new Font("Verdana", Font.PLAIN, 18));
		
	    GridBagConstraints gbc = new GridBagConstraints();

	    gbc.insets = new Insets(2, 2, 2, 10);
	    
	    gbc.gridy = 0; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 2; 
	    gbc.anchor = GridBagConstraints.CENTER; 
	    pCorpo.add(lTitulo, gbc);
	    
	    gbc.gridy = 1; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST; 
	    pCorpo.add(lEspacoTitulo, gbc);

	    gbc.gridy = 2; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST; 
	    pCorpo.add(lCodigo, gbc);
	    
	    gbc.gridy = 3; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(lCpf, gbc);
	    
	    gbc.gridy = 4; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(lValorDeposito, gbc);
	    
	    //segunda coluna
	    
	    gbc.gridy = 2; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST; 
	    pCorpo.add(tfCodigoDeposito, gbc);
	    
	    gbc.gridy = 3; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(tfCpfDeposito, gbc);
	    
	    gbc.gridy = 4; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(tfValorDeposito, gbc);
	    
	    gbc.gridy = 10; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST; 
	    pCorpo.add(lEspaco, gbc);
	    
	    JPanel pBotoes = new JPanel();
	    pBotoes.setLayout(new FlowLayout());
	    pBotoes.add(botaoConsultar);
	    ConsultarDepositoAction consultarDepositoAction = new ConsultarDepositoAction();
	    botaoConsultar.addActionListener(consultarDepositoAction);
	    
	    pBotoes.add(botaoCancelar);
	    CancelarDepositoAction cancelarDepositoAction = new CancelarDepositoAction();
	    botaoCancelar.addActionListener(cancelarDepositoAction);
		
		JPanel pDiretos = new JPanel();
		pDiretos.setLayout(new FlowLayout());
		JLabel lDiretos = new JLabel(Main.lTextDireitos);
		pDiretos.add(lDiretos);
		add(pDiretos, BorderLayout.SOUTH);
		
		add(pCorpo, BorderLayout.NORTH);
		add(pBotoes, BorderLayout.CENTER);
		
	}

	public void jtUpper(JTextField jt){  
        String valor = jt.getText().toUpperCase();  
        jt.setText(valor);  
    }  
	
	private void jtfFocusLostNum(JTextField jt) {  
		if (jt.getText().length() > 0){ 
			try { 
				@SuppressWarnings("unused")
				long valor = Long.parseLong(jt.getText()); 
			}catch(NumberFormatException ex){ 
				jt.grabFocus();
				JOptionPane.showMessageDialog(null, "Esse Campo só aceita números" ,"Ação negada",JOptionPane.WARNING_MESSAGE); 
			} 
		} 
	} 
	
	private void jtfFocusLostDouble(JTextField jt) {  
		if (jt.getText().length() > 0){ 
			try { 
				@SuppressWarnings("unused")
				Double valorFinal = Double.parseDouble(jt.getText().replace(',', '.'));
			}catch(Exception e){ 
				JOptionPane.showMessageDialog(null, "Esse Campo só aceita números decimais. (Não necessita usar '.')" ,"Ação negada",JOptionPane.WARNING_MESSAGE);
				jt.grabFocus();
			} 
		} 
	} 

	private boolean verificarCampos(){
		boolean retorno = true;
		if ((tfCodigoDeposito.getText().equals("")) && (tfCpfDeposito.getText().equals("___.___.___-__"))){
			retorno = false;
		}
		if (tfValorDeposito.getText().equals("")){
			retorno = false;
		}
		return retorno;
	}
	
	private void limparCampos(){
		tfCodigoDeposito.setText(null);
		tfCpfDeposito.setText(null);
		tfValorDeposito.setText(null);
	}
	
	private class ConsultarDepositoAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			depositando();			
		}
	}
	
	private void depositando(){
		//carregando variaveis
		int vCodigoDeposito = 0;
		if (!tfCodigoDeposito.getText().equals("")){
			vCodigoDeposito = Integer.parseInt(tfCodigoDeposito.getText());
		}

		String vCpfDeposito = null;
		if (!tfCpfDeposito.getText().equals("___.___.___-__")){
			vCpfDeposito = tfCpfDeposito.getText();
		}
		//--fim
		
		try {
			if (verificarCampos()){
				
				Controle ceb = new Controle();
				Conta ce = new Conta(); 
				
				ce.setCodigo(vCodigoDeposito);
				ce.setCpf(vCpfDeposito);
				double vTfValorDeposito = Double.parseDouble(tfValorDeposito.getText().replace(',', '.'));
				
				ceb.depositarConta(ce,vTfValorDeposito);
			}else{
				JOptionPane.showMessageDialog(null, "Código ou CPF e valor devem ser informado." ,"Ação negada",JOptionPane.WARNING_MESSAGE);
			}
		}catch (SQLException e2) {
			JOptionPane.showMessageDialog(null, "Erro ao realizar deposito: "+e2.getMessage() ,"Ação negada",JOptionPane.WARNING_MESSAGE);
		}catch(ContaNaoEncontradaException cne){
			JOptionPane.showMessageDialog(null, "Erro ao realizar deposito: "+cne.getMessage() ,"Ação negada",JOptionPane.WARNING_MESSAGE);
		}
		limparCampos();
		tfCodigoDeposito.grabFocus();
	}
	
	private class CancelarDepositoAction implements ActionListener{
		public void actionPerformed(ActionEvent e){
			limparCampos();
			dispose();
		}
	}

	
}

