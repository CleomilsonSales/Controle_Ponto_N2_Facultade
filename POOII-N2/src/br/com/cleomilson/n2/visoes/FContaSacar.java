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
import br.com.cleomilson.n2.exceptions.ContaNaoPossoSaldoException;
import br.com.cleomilson.n2.util.Conta;
import br.com.cleomilson.n2.util.Controle;
import br.com.cleomilson.n2.util.Main;


@SuppressWarnings("serial")
public class FContaSacar extends JFrame{
	
	private JTextField tfCodigoSacar = new JTextField(10);
	private JFormattedTextField tfCpfSacar = new JFormattedTextField();
	private JTextField tfValorSacar = new JTextField(10);
	
	//private JFrame jContaSacar = new JFrame("Sacar da Conta");
	
	public FContaSacar() throws ParseException{
		super("Sacar da Conta");
		setSize(450,250);
		setVisible(true);
		setLocationRelativeTo(null);
		repaint();
		
	}

	public void criarFormularioCS() throws ParseException{
		setLayout(new BorderLayout());
		
		JPanel pCorpo = new JPanel();
		pCorpo.setLayout(new GridBagLayout());
		
		//criando mascaras
		
		MaskFormatter mascaraCpf = new MaskFormatter("###.###.###-##");
		mascaraCpf.setPlaceholderCharacter('_');
		tfCpfSacar = new JFormattedTextField(mascaraCpf);
		
		//--
		
		//ações de fields
		
		tfCodigoSacar.addFocusListener(new FocusAdapter() {  
            public void focusLost(FocusEvent evt) {  
            	jtfFocusLostNum(tfCodigoSacar);
            }  
        });
		
		tfCodigoSacar.addKeyListener(new KeyAdapter(){  
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {  
						sacando();						
					}  
				}
		 });
		tfCpfSacar.addKeyListener(new KeyAdapter(){  
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {  
					sacando();						
				}  
			}
		});
		tfValorSacar.addKeyListener(new KeyAdapter(){  
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {  
					sacando();						
				}  
			}
		});
		
		tfValorSacar.addFocusListener(new FocusAdapter() {  
            public void focusLost(FocusEvent evt) {  
            	jtfFocusLostDouble(tfValorSacar);
            }  
        });

		//--
		
		JLabel lCodigo = new JLabel("Código da Conta: ");
		JLabel lCpf = new JLabel("CPF: ");
		JLabel lValor = new JLabel("Valor: ");
		
		JButton botaoConsultar = new JButton("Sacar (Enter)"); 
		JButton botaoCancelar = new JButton("Cancelar");
		
		JLabel lTitulo = new JLabel("Sacar da Conta");
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
	    pCorpo.add(lValor, gbc);
	    
	    //segunda coluna
	    
	    gbc.gridy = 2; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST; 
	    pCorpo.add(tfCodigoSacar, gbc);
	    
	    gbc.gridy = 3; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(tfCpfSacar, gbc);
	    
	    gbc.gridy = 4; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(tfValorSacar, gbc);
	    
	    gbc.gridy = 10; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST; 
	    pCorpo.add(lEspaco, gbc);
	    
	    JPanel pBotoes = new JPanel();
	    pBotoes.setLayout(new FlowLayout());
	    pBotoes.add(botaoConsultar);
	    ConsultarSacarAction consultarSacarAction = new ConsultarSacarAction();
	    botaoConsultar.addActionListener(consultarSacarAction);
	    
	    pBotoes.add(botaoCancelar);
	    CancelarSacarAction cancelarSacarAction = new CancelarSacarAction();
	    botaoCancelar.addActionListener(cancelarSacarAction);
		
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
		if ((tfCodigoSacar.getText().equals("")) && (tfCpfSacar.getText().equals("___.___.___-__"))){
			retorno = false;
		}
		if (tfValorSacar.getText().equals("")){
			retorno = false;
		}
		return retorno;
	}
	
	private void limparCampos(){
		tfCodigoSacar.setText(null);
		tfCpfSacar.setText(null);
		tfValorSacar.setText(null);
	}
	
	private class ConsultarSacarAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			sacando();			
		}
	}
	
	private void sacando(){
		//carregando variaveis
		int vCodigoSacar = 0;
		if (!tfCodigoSacar.getText().equals("")){
			vCodigoSacar = Integer.parseInt(tfCodigoSacar.getText());
		}

		String vCpfSacar = null;
		if (!tfCpfSacar.getText().equals("___.___.___-__")){
			vCpfSacar = tfCpfSacar.getText();
		}
		//--fim
		
		try {
			if (verificarCampos()){
				Controle ceb = new Controle();
				Conta ce = new Conta(); 
				
				ce.setCodigo(vCodigoSacar);
				ce.setCpf(vCpfSacar);
				double vTfValor = Double.parseDouble(tfValorSacar.getText().replace(',', '.'));
			
				ceb.sacarConta(ce,vTfValor);
			}else{
				JOptionPane.showMessageDialog(null, "Código ou CPF e valor devem ser informado." ,"Ação negada",JOptionPane.WARNING_MESSAGE);
			}
		}catch (SQLException e2) {
			JOptionPane.showMessageDialog(null, "Erro ao realizar saque: "+e2.getMessage() ,"Ação negada",JOptionPane.WARNING_MESSAGE);
		}catch(ContaNaoEncontradaException cne){
			JOptionPane.showMessageDialog(null, "Erro ao realizar saque: "+cne.getMessage() ,"Ação negada",JOptionPane.WARNING_MESSAGE);
		}catch(ContaNaoPossoSaldoException cnps){
			JOptionPane.showMessageDialog(null, "Erro ao realizar saque: "+cnps.getMessage() ,"Ação negada",JOptionPane.WARNING_MESSAGE);
		}
		limparCampos();
		tfCodigoSacar.grabFocus();
	}
	
	private class CancelarSacarAction implements ActionListener{
		public void actionPerformed(ActionEvent e){
			limparCampos();
			dispose();
		}
	}

	
}

