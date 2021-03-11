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
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.FocusAdapter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import br.com.cleomilson.n2.util.Conta;
import br.com.cleomilson.n2.util.Controle;
import br.com.cleomilson.n2.util.Endereco;
import br.com.cleomilson.n2.util.Main;

@SuppressWarnings("serial")
public class FConta extends JFrame{
	
	private JTextField tfNomeTitular = new JTextField(20);
	private JFormattedTextField tfCpf = new JFormattedTextField();
	private JFormattedTextField tfDataNascimento = new JFormattedTextField();
	private String[] cbLista = {"M", "F"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JComboBox cbSexo = new JComboBox(cbLista);
	private JTextField tfTipoLogradouro = new JTextField(5);
	private JTextField tfLogradouro = new JTextField(20);
	private JTextField tfNumero = new JTextField(10);
	private JFormattedTextField tfCep = new JFormattedTextField();
	private JTextField tfSaldo = new JTextField(20);
	private JTextField tfLimite = new JTextField(20);
	private JTextField tfLimiteTransferencia = new JTextField(20);
	
	//private JFrame jConta = new JFrame("Cadastro de Conta");
	
	public FConta() throws ParseException{
		super("Cadastro de Conta");
		setSize(450,450);
		setVisible(true);
		setLocationRelativeTo(null);
		repaint();
		
	}

	public void criarFormularioCC() throws ParseException{
		setLayout(new BorderLayout());
		
		JPanel pCorpo = new JPanel();
		pCorpo.setLayout(new GridBagLayout());
		
		//criando mascaras
		MaskFormatter mascaraDtNascimento = new MaskFormatter("##/##/####");
		mascaraDtNascimento.setPlaceholderCharacter('_');
		tfDataNascimento = new JFormattedTextField(mascaraDtNascimento);
		
		MaskFormatter mascaraCpf = new MaskFormatter("###.###.###-##");
		mascaraCpf.setPlaceholderCharacter('_');
		tfCpf = new JFormattedTextField(mascaraCpf);
		
		MaskFormatter mascaraCEP = new MaskFormatter("##.###-###");
		mascaraCEP.setPlaceholderCharacter('_');
		tfCep = new JFormattedTextField(mascaraCEP);
		
		//--
		
		//ações de fields
		
		tfNumero.addFocusListener(new FocusAdapter() {  
            public void focusLost(FocusEvent evt) {  
            	jtfFocusLostNum(tfNumero);
            }  
        });
		
		tfSaldo.addFocusListener(new FocusAdapter() {  
            public void focusLost(FocusEvent evt) {  
            	jtfFocusLostDouble(tfSaldo);
            }  
        });	
		
		tfLimite.addFocusListener(new FocusAdapter() {  
            public void focusLost(FocusEvent evt) {  
            	jtfFocusLostDouble(tfLimite);
            }  
        });
		
		tfLimiteTransferencia.addFocusListener(new FocusAdapter() {  
            public void focusLost(FocusEvent evt) {  
            	jtfFocusLostDouble(tfLimiteTransferencia);
            }  
        });
		
	
		tfNomeTitular.addKeyListener(new KeyAdapter(){  
	        public void keyReleased(KeyEvent e){  
	        	jtUpper(tfNomeTitular);
	        }
	    });
		
		tfTipoLogradouro.addKeyListener(new KeyAdapter(){  
	        public void keyReleased(KeyEvent e){  
	        	jtUpper(tfTipoLogradouro);
	        }
	    }); 
		
		tfLogradouro.addKeyListener(new KeyAdapter(){  
	        public void keyReleased(KeyEvent e){  
	        	jtUpper(tfLogradouro);
	        }
	    }); 
		
		//--
		
		JLabel lNomeTitular = new JLabel("Nome Titular: ");
		
		JLabel lCpf = new JLabel("CPF: ");
		JLabel lDataNascimento = new JLabel("Data Nascimento: ");
		JLabel lSexo = new JLabel("Sexo: ");
		JLabel lTipoLogradouro = new JLabel("Tipo Logradouro: ");
		JLabel lLogradouro = new JLabel("Endereço: ");
		JLabel lNumero = new JLabel("Nº: ");
		JLabel lCep = new JLabel("Cep: ");
		JLabel lSaldo = new JLabel("Valor Inicial: ");
		JLabel lLimite = new JLabel("Limite: ");
		JLabel lLimiteTransferencia = new JLabel("Limite Transferência: ");
		
		JButton botaoGravar = new JButton("Gravar"); 
		JButton botaoCancelar = new JButton("Cancelar");
		
		JLabel lTitulo = new JLabel("Cadastro de Conta");
		JLabel lEspacoTitulo = new JLabel(" ");
		JLabel lEspaco = new JLabel(" ");
		// PLAIN = formato regular;
		lTitulo.setFont(new Font("Verdana", Font.PLAIN, 18));
		
	    GridBagConstraints gbc = new GridBagConstraints();

	    //espaços entre as celulas: lados de cima, esquerda, inferior e direita
	    gbc.insets = new Insets(2, 2, 2, 10);
	    
	    gbc.gridy = 0; // linha
	    gbc.gridx = 0; // coluna
	    gbc.gridwidth = 2; //quantas celulas ocupa
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
	    pCorpo.add(lNomeTitular, gbc);
	    
	    gbc.gridy = 3; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(lCpf, gbc);
	    
	    gbc.gridy = 4; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(lDataNascimento, gbc);
	   
	    gbc.gridy = 5; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(lSexo, gbc);
	    
	    gbc.gridy = 6; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(lTipoLogradouro, gbc);
	    
	    gbc.gridy = 7; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(lLogradouro, gbc);
	    
	    gbc.gridy = 8; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(lNumero, gbc);
	    
	    gbc.gridy = 9; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(lCep, gbc);
	    
	    gbc.gridy = 10; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(lSaldo, gbc);
	    
	    gbc.gridy = 11; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(lLimite, gbc);
	    
	    gbc.gridy = 12; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(lLimiteTransferencia, gbc);
	   
	    //segunda coluna
	    
	    gbc.gridy = 2; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST; 
	    pCorpo.add(tfNomeTitular, gbc);
	    
	    gbc.gridy = 3; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(tfCpf, gbc);
	    
	    gbc.gridy = 4; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(tfDataNascimento, gbc);
	   
	    gbc.gridy = 5; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(cbSexo, gbc);
	    
	    gbc.gridy = 6; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(tfTipoLogradouro, gbc);
	    
	    gbc.gridy = 7; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(tfLogradouro, gbc);
	    
	    gbc.gridy = 8; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(tfNumero, gbc);
	    
	    gbc.gridy = 9; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(tfCep, gbc);
	    
	    gbc.gridy = 10; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(tfSaldo, gbc);
	    
	    gbc.gridy = 11; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(tfLimite, gbc);
	    
	    gbc.gridy = 12; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(tfLimiteTransferencia, gbc);
	    
	    gbc.gridy = 10; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST; 
	    pCorpo.add(lEspaco, gbc);
	    
	    JPanel pBotoes = new JPanel();
	    pBotoes.setLayout(new FlowLayout());
	    pBotoes.add(botaoGravar);
	    CadastrarAction cadastrarAction = new CadastrarAction();
	    botaoGravar.addActionListener(cadastrarAction);

	    pBotoes.add(botaoCancelar);
	    CancelarAction cancelarAction = new CancelarAction();
	    botaoCancelar.addActionListener(cancelarAction);
		
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
				JOptionPane.showMessageDialog(null, "Esse Campo só aceita números" ,"Ação negada",JOptionPane.WARNING_MESSAGE);
				jt.grabFocus();
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
		if (tfNomeTitular.getText().equals("")){
			retorno = false;
		}else{
			if (tfCpf.getText().equals("___.___.___-__")){
				retorno = false;
			}else{
				if (tfDataNascimento.getText().equals("__/__/____")){
					retorno = false;
				}else{
					if (cbSexo.getSelectedItem().equals("")){
						retorno = false;
					}else{
						if (tfTipoLogradouro.getText().equals("")){
							retorno = false;
						}else{
							if (tfLogradouro.getText().equals("")){
								retorno = false;
							}else{
								if (tfNumero.getText().equals("")){
									retorno = false;
								}else{
									if (tfCep.getText().equals("__.___-___")){
										retorno = false;
									}else{
										if (tfLimite.getText().equals("")){
											retorno = false;
										}else{
											if (tfLimiteTransferencia.getText().equals("")){
												retorno = false;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return retorno;
	}
	
	private void limparCampos(){
		tfNomeTitular.setText(null);
		tfCpf.setText(null);
		tfDataNascimento.setText(null);
		tfTipoLogradouro.setText(null);
		tfLogradouro.setText(null);
		tfNumero.setText(null);
		tfCep.setText(null);
		tfSaldo.setText(null);
		tfLimite.setText(null);
		tfLimiteTransferencia.setText(null);
	}
	
	private class CadastrarAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			GravarConta();
		}
	}
	private class CancelarAction implements ActionListener{
		public void actionPerformed(ActionEvent e){
			limparCampos();
			dispose();
		}
	}
	public void GravarConta() {
		if (verificarCampos()){
			
			//carregando variaveis
			String vNomeTitular = tfNomeTitular.getText();
			String vCpf = tfCpf.getText();
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
            Date vDataNascimento = null;
			try {
				vDataNascimento = formatter.parse(tfDataNascimento.getText());
			}catch (ParseException e3) {
				JOptionPane.showMessageDialog(null, "Erro no cadastro da Conta: "+e3.getMessage(),"Ação negada",JOptionPane.WARNING_MESSAGE);
			}
			
			String vSexo = (String) cbSexo.getSelectedItem();
			String vtipoLogradouro = tfTipoLogradouro.getText();
			String vlogradouro = tfLogradouro.getText();
			int vnumero = Integer.parseInt(tfNumero.getText());
			String vcep = tfCep.getText();

			Endereco e1 = new Endereco(vtipoLogradouro, vlogradouro, vnumero, vcep);
			Endereco vEndereco = e1;
			
			double vSaldo = Double.parseDouble(tfSaldo.getText().replace(',', '.'));
			double vLimite = Double.parseDouble(tfLimite.getText().replace(',', '.'));
			double vLimiteTransferencia = Double.parseDouble(tfLimiteTransferencia.getText().replace(',', '.'));
			
			//--fim
			
			try {
			Controle ccb = new Controle();
			Conta c = new Conta(vNomeTitular,vCpf,vDataNascimento,vSexo,vEndereco,vSaldo,vLimite,vLimiteTransferencia); 
			
			ccb.cadastrarConta(c);
			JOptionPane.showMessageDialog(null, "Conta criada com sucesso" ,"Ação aceita",JOptionPane.INFORMATION_MESSAGE);
				
			}catch (SQLException e2) {
				JOptionPane.showMessageDialog(null, "Erro no cadastro da Conta: "+e2.getMessage(),"Ação negada",JOptionPane.WARNING_MESSAGE);
			}
			
			limparCampos();
			tfNomeTitular.grabFocus();
		}else{
			JOptionPane.showMessageDialog(null, "Todos os campos são obrigatórios." ,"Ação negada",JOptionPane.WARNING_MESSAGE);
		}
		
	}

	
}

