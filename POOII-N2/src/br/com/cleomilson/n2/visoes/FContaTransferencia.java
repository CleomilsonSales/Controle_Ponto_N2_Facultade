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

import br.com.cleomilson.n2.exceptions.ContaIguaisException;
import br.com.cleomilson.n2.exceptions.ContaLimiteTransfNaoAceitaException;
import br.com.cleomilson.n2.exceptions.ContaNaoEncontradaException;
import br.com.cleomilson.n2.exceptions.ContaNaoPossoSaldoException;
import br.com.cleomilson.n2.util.Conta;
import br.com.cleomilson.n2.util.Controle;
import br.com.cleomilson.n2.util.Main;


@SuppressWarnings("serial")
public class FContaTransferencia extends JFrame{
	
	private JTextField tfCodigoTransfS = new JTextField(10);
	private JFormattedTextField tfCpfTransfS = new JFormattedTextField();
	private JTextField tfCodigoTransfE = new JTextField(10);
	private JFormattedTextField tfCpfTransfE = new JFormattedTextField();
	private JTextField tfValorTransf = new JTextField(10);
	
	//private JFrame jContaTransf = new JFrame("Transfência entre Conta");
	
	public FContaTransferencia() throws ParseException{
		super("Transfência entre Conta");
		setSize(450,350);
		setVisible(true);
		setLocationRelativeTo(null);
		repaint();
		
	}

	public void criarFormularioCT() throws ParseException{
		setLayout(new BorderLayout());
		
		JPanel pCorpo = new JPanel();
		pCorpo.setLayout(new GridBagLayout());
		
		//criando mascaras
		
		MaskFormatter mascaraCpf = new MaskFormatter("###.###.###-##");
		mascaraCpf.setPlaceholderCharacter('_');
		tfCpfTransfS = new JFormattedTextField(mascaraCpf);
		
		mascaraCpf.setPlaceholderCharacter('_');
		tfCpfTransfE = new JFormattedTextField(mascaraCpf);
		
		//--
		
		//ações de fields
		
		tfCodigoTransfS.addFocusListener(new FocusAdapter() {  
            public void focusLost(FocusEvent evt) {  
            	jtfFocusLostNum(tfCodigoTransfS);
            }  
        });
		
		tfCodigoTransfE.addFocusListener(new FocusAdapter() {  
            public void focusLost(FocusEvent evt) {  
            	jtfFocusLostNum(tfCodigoTransfE);
            }  
        });
		
		tfCodigoTransfS.addKeyListener(new KeyAdapter(){  
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {  
						transferindo();						
					}  
				}
		 });
		
		tfCodigoTransfE.addKeyListener(new KeyAdapter(){  
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {  
					transferindo();						
				}  
			}
		});
		
		tfCpfTransfS.addKeyListener(new KeyAdapter(){  
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {  
					transferindo();			
				}  
			}
		});
		
		tfCpfTransfE.addKeyListener(new KeyAdapter(){  
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {  
					transferindo();			
				}  
			}
		});
		
		tfValorTransf.addKeyListener(new KeyAdapter(){  
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {  
					transferindo();	
				}  
			}
		});
		
		tfValorTransf.addFocusListener(new FocusAdapter() {  
            public void focusLost(FocusEvent evt) {  
            	jtfFocusLostDouble(tfValorTransf);
            }  
        });

		//--
		
		JLabel lCodigoS = new JLabel("Código da Conta (Débito): ");
		JLabel lCpfS = new JLabel("CPF (Débito): ");
		JLabel lCodigoE = new JLabel("Código da Conta (Crédito): ");
		JLabel lCpfE = new JLabel("CPF (Crédito): ");
		JLabel lValor = new JLabel("Valor: ");
		
		JButton botaoConsultar = new JButton("Transferir (Enter)"); 
		JButton botaoCancelar = new JButton("Cancelar");
		
		JLabel lTitulo = new JLabel("Transferência entre Conta");
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
	    pCorpo.add(lCodigoS, gbc);
	    
	    gbc.gridy = 3; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(lCpfS, gbc);
	    
	    gbc.gridy = 4; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST; 
	    pCorpo.add(lCodigoE, gbc);
	    
	    gbc.gridy = 5; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(lCpfE, gbc);
	    
	    gbc.gridy = 6; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(lEspaco, gbc);
	    
	    gbc.gridy = 7; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(lValor, gbc);
	    
	    //segunda coluna
	    
	    gbc.gridy = 2; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST; 
	    pCorpo.add(tfCodigoTransfS, gbc);
	    
	    gbc.gridy = 3; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(tfCpfTransfS, gbc);
	    
	    gbc.gridy = 4; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST; 
	    pCorpo.add(tfCodigoTransfE, gbc);
	    
	    gbc.gridy = 5; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(tfCpfTransfE, gbc);
	    
	    gbc.gridy = 7; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(tfValorTransf, gbc);
	    
	    gbc.gridy = 10; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST; 
	    pCorpo.add(lEspaco, gbc);
	    
	    JPanel pBotoes = new JPanel();
	    pBotoes.setLayout(new FlowLayout());
	    pBotoes.add(botaoConsultar);
	    ConsultarTransfAction consultarTransfAction = new ConsultarTransfAction();
	    botaoConsultar.addActionListener(consultarTransfAction);
	    
	    pBotoes.add(botaoCancelar);
	    CancelarTransfAction cancelarTransfAction = new CancelarTransfAction();
	    botaoCancelar.addActionListener(cancelarTransfAction);
		
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
		if ((tfCodigoTransfS.getText().equals("")) && (tfCpfTransfS.getText().equals("___.___.___-__"))){
			retorno = false;
		}
		if ((tfCodigoTransfE.getText().equals("")) && (tfCpfTransfE.getText().equals("___.___.___-__"))){
			retorno = false;
		}
		if (tfValorTransf.getText().equals("")){
			retorno = false;
		}
		return retorno;
	}
	
	private void limparCampos(){
		tfCodigoTransfS.setText(null);
		tfCpfTransfS.setText(null);
		tfCodigoTransfE.setText(null);
		tfCpfTransfE.setText(null);
		tfValorTransf.setText(null);
	}
	
	private class ConsultarTransfAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			transferindo();			
		}
	}
	
	private void transferindo(){
		//carregando variaveis
		int vCodigoTransfS = 0;
		if (!tfCodigoTransfS.getText().equals("")){
			vCodigoTransfS = Integer.parseInt(tfCodigoTransfS.getText());
		}

		String vCpfTransfS = null;
		if (!tfCpfTransfS.getText().equals("___.___.___-__")){
			vCpfTransfS = tfCpfTransfS.getText();
		}
		
		int vCodigoTransfE = 0;
		if (!tfCodigoTransfE.getText().equals("")){
			vCodigoTransfE = Integer.parseInt(tfCodigoTransfE.getText());
		}

		String vCpfTransfE = null;
		if (!tfCpfTransfE.getText().equals("___.___.___-__")){
			vCpfTransfE = tfCpfTransfE.getText();
		}
		//--fim
		
		try {
			if (verificarCampos()){
				Controle ceb = new Controle();
				Conta ceS = new Conta(); 
				Conta ceE = new Conta();
				
				ceS.setCodigo(vCodigoTransfS);
				ceS.setCpf(vCpfTransfS);
				
				ceE.setCodigo(vCodigoTransfE);
				ceE.setCpf(vCpfTransfE);
				
				double vTfValorTransf = Double.parseDouble(tfValorTransf.getText().replace(',', '.'));

				ceb.transferirConta(ceS,ceE,vTfValorTransf);
				
				JOptionPane.showMessageDialog(null, "Transferência realizada com sucesso." ,"Ação aceita",JOptionPane.INFORMATION_MESSAGE);
			}else{
				JOptionPane.showMessageDialog(null, "Código ou CPF das contas e o valor devem ser informado." ,"Ação negada",JOptionPane.WARNING_MESSAGE);
			}
		}catch (SQLException e2) {
			JOptionPane.showMessageDialog(null, "Erro ao realizar transferência: "+e2.getMessage() ,"Ação negada",JOptionPane.WARNING_MESSAGE);
		}catch(ContaNaoEncontradaException cne){
			JOptionPane.showMessageDialog(null, "Erro ao realizar transferência: "+cne.getMessage() ,"Ação negada",JOptionPane.WARNING_MESSAGE);
		}catch(ContaIguaisException ci){
			JOptionPane.showMessageDialog(null, "Erro ao realizar transferência: "+ci.getMessage() ,"Ação negada",JOptionPane.WARNING_MESSAGE);
		}catch(ContaNaoPossoSaldoException cnps){
			JOptionPane.showMessageDialog(null, "Erro ao realizar transferência: "+cnps.getMessage() ,"Ação negada",JOptionPane.WARNING_MESSAGE);
		} catch (IllegalArgumentException e3) {
			JOptionPane.showMessageDialog(null, "Erro ao realizar transferência: "+e3.getMessage() ,"Ação negada",JOptionPane.WARNING_MESSAGE);
		} catch (ContaLimiteTransfNaoAceitaException e) {
			JOptionPane.showMessageDialog(null, "Erro ao realizar transferência: "+e.getMessage() ,"Ação negada",JOptionPane.WARNING_MESSAGE);
		}
		
		
		limparCampos();
		tfCodigoTransfS.grabFocus();
	}
	
	private class CancelarTransfAction implements ActionListener{
		public void actionPerformed(ActionEvent e){
			limparCampos();
			dispose();
		}
	}

	
}

