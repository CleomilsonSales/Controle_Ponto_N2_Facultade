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

import br.com.cleomilson.n2.dao.ContaDAO;
import br.com.cleomilson.n2.util.Conta;
import br.com.cleomilson.n2.util.Controle;
import br.com.cleomilson.n2.util.Endereco;
import br.com.cleomilson.n2.util.Main;

@SuppressWarnings("serial")
public class FContaAlter extends JFrame{
	
	private JTextField tfCod = new JTextField(20);
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
	private JTextField tfLimite = new JTextField(20);
	private JTextField tfLimiteTransferencia = new JTextField(20);
	
	//private JFrame jContaAlter = new JFrame("Alteração de Conta");
	
	public FContaAlter() throws ParseException{
		super("Alteração de Conta");
		setSize(800,450);
		setVisible(true);
		setLocationRelativeTo(null);
		repaint();
		
	}

	private void mascaras() throws ParseException{
		MaskFormatter mascaraDtNascimento = new MaskFormatter("##/##/####");
		mascaraDtNascimento.setPlaceholderCharacter('_');
		tfDataNascimento = new JFormattedTextField(mascaraDtNascimento);
		
		MaskFormatter mascaraCEP = new MaskFormatter("##.###-###");
		mascaraCEP.setPlaceholderCharacter('_');
		tfCep = new JFormattedTextField(mascaraCEP);
	}
	
	private void pesquisar(int valor) throws SQLException, ParseException { 
		ContaDAO dao = new ContaDAO(); 
		 DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		 
		 mascaras();
		 
		try {
			for (Conta c : dao.getListConta(valor)) { 
				
				tfCod.setText(Integer.toString(c.getCodigo()));
				tfCod.setEditable(false);
				tfNomeTitular.setText(c.getNomeTitular());
				tfCpf.setText(c.getCpf());
				tfCpf.setEditable(false);
				tfDataNascimento.setText(df.format(c.getDataNascimento()));
				if (c.getSexo().equals("M")){
					cbSexo.setSelectedIndex(0);	
				}else{
					cbSexo.setSelectedIndex(1);
				}
				
				String vLogradouroComp = c.getEnderecoCompleto();
				
				String eTipoLogradouro = vLogradouroComp.substring(0,vLogradouroComp.indexOf(" "));
				String eLogradouro = vLogradouroComp.substring(vLogradouroComp.indexOf(" ")+1,vLogradouroComp.indexOf(", "));
				String eNLogradouro = vLogradouroComp.substring(vLogradouroComp.indexOf(", ")+2,vLogradouroComp.indexOf(" -"));
				String eCEP = vLogradouroComp.substring(vLogradouroComp.indexOf(": ")+2);
				
				tfTipoLogradouro.setText(eTipoLogradouro);
				tfLogradouro.setText(eLogradouro);
				tfNumero.setText(eNLogradouro);
				tfCep.setText(eCEP);
				tfLimite.setText(Double.toString(c.getLimite()));
				tfLimiteTransferencia.setText(Double.toString(c.getLimiteTransferencia()));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar a conta: "+e.getMessage(),"Ação negada",JOptionPane.WARNING_MESSAGE);
		} 

	} 
	
	public void criarFormularioCC(int valorCod) throws ParseException{
		
		try {
			pesquisar(valorCod);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao carregar lista da alteração" ,"Ação negada",JOptionPane.WARNING_MESSAGE);
		}
		
		setLayout(new BorderLayout());
		
		JPanel pCorpo = new JPanel();
		pCorpo.setLayout(new GridBagLayout());
		
		//ações de fields
		
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
		
		tfNumero.addFocusListener(new FocusAdapter() {  
            public void focusLost(FocusEvent evt) {  
            	jtfFocusLostNum(tfNumero);
            }  
        });
		
		//--
		JLabel lCod = new JLabel("Código: ");
		JLabel lNomeTitular = new JLabel("Nome Titular: ");
		
		JLabel lCpf = new JLabel("CPF: ");
		JLabel lDataNascimento = new JLabel("Data Nascimento: ");
		JLabel lSexo = new JLabel("Sexo: ");
		JLabel lTipoLogradouro = new JLabel("Tipo Logradouro: ");
		JLabel lLogradouro = new JLabel("Endereço: ");
		JLabel lNumero = new JLabel("Nº: ");
		JLabel lCep = new JLabel("Cep: ");
		JLabel lLimite = new JLabel("Limite: ");
		JLabel lLimiteTransferencia = new JLabel("Limite Transferência: ");
		
		JButton botaoGravar = new JButton("Gravar"); 
		JButton botaoCancelar = new JButton("Cancelar");
		
		JLabel lTitulo = new JLabel("Alteração de cadastro da Conta");
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
	    pCorpo.add(lCod, gbc);

	    gbc.gridy = 3; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST; 
	    pCorpo.add(lNomeTitular, gbc);
	    
	    gbc.gridy = 4; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(lCpf, gbc);
	    
	    gbc.gridy = 5; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(lDataNascimento, gbc);
	   
	    gbc.gridy = 6; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(lSexo, gbc);
	    
	    gbc.gridy = 7; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(lTipoLogradouro, gbc);
	    
	    gbc.gridy = 8; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(lLogradouro, gbc);
	    
	    gbc.gridy = 9; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(lNumero, gbc);
	    
	    gbc.gridy = 10; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(lCep, gbc);
	    
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
	    pCorpo.add(tfCod, gbc);	    
	    
	    gbc.gridy = 3; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST; 
	    pCorpo.add(tfNomeTitular, gbc);
	    
	    gbc.gridy = 4; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(tfCpf, gbc);
	    
	    gbc.gridy = 5; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(tfDataNascimento, gbc);
	   
	    gbc.gridy = 6; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(cbSexo, gbc);
	    
	    gbc.gridy = 7; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(tfTipoLogradouro, gbc);
	    
	    gbc.gridy = 8; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(tfLogradouro, gbc);
	    
	    gbc.gridy = 9; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(tfNumero, gbc);
	    
	    gbc.gridy = 10; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(tfCep, gbc);
	    
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
	    
	    gbc.gridy = 13; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST; 
	    pCorpo.add(lEspaco, gbc);
	    
	    JPanel pBotoes = new JPanel();
	    pBotoes.setLayout(new FlowLayout());
	    pBotoes.add(botaoGravar);
	    UpAction upAction = new UpAction();
	    botaoGravar.addActionListener(upAction);

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
		
		tfNomeTitular.requestFocus();
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
			if (tfCpf.getText().equals("")){
				retorno = false;
			}else{
				if (tfDataNascimento.getText().equals("__/__/____")){
					retorno = false;
				}else{
					if (cbSexo.getSelectedItem().equals("")){
						retorno = false;
					}else{
						if (tfLogradouro.getText().equals("")){
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
		return retorno;
	}
	
	private void limparCampos(){
		tfNomeTitular.setText(null);
		tfCpf.setText(null);
		tfDataNascimento.setText(null);
		tfLogradouro.setText(null);
		tfLimite.setText(null);
		tfLimiteTransferencia.setText(null);
	}
	
	private class UpAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			AtualizarConta();
		}
	}
	private class CancelarAction implements ActionListener{
		public void actionPerformed(ActionEvent e){
			limparCampos();
			dispose();
		}
	}
	public void AtualizarConta() {
		//carregando variaveis
		if (verificarCampos()){
			
			int vCodigo = Integer.parseInt(tfCod.getText());
			String vNomeTitular = tfNomeTitular.getText();
			String vCpf = tfCpf.getText();
	        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
	        Date vDataNascimento = null;
			try {
				vDataNascimento = formatter.parse(tfDataNascimento.getText());
			}catch (ParseException e3) {
				JOptionPane.showMessageDialog(null, "Erro na atualização cadastro da Conta: "+e3.getMessage(),"Ação negada",JOptionPane.WARNING_MESSAGE);
			}
			
			String vTipoLogradouro2 = tfTipoLogradouro.getText();
			String vLogradouro2 = tfLogradouro.getText();
			int vNumero2 = Integer.parseInt(tfNumero.getText());
			String vCep2 = tfCep.getText();
			
			String vSexo = (String) cbSexo.getSelectedItem();
			double vLimite = Double.parseDouble(tfLimite.getText().replace(',', '.'));
			double vLimiteTransferencia = Double.parseDouble(tfLimiteTransferencia.getText().replace(',', '.'));
			
			Endereco e2 = new Endereco(vTipoLogradouro2, vLogradouro2, vNumero2, vCep2);
			Endereco vEndereco2 = e2;
			
			//--fim
			
			try {
				Controle ccb = new Controle();
				Conta c = new Conta(vCodigo,vNomeTitular,vCpf,vDataNascimento,vSexo,vEndereco2,vLimite,vLimiteTransferencia); 

				ccb.atualizarConta(c);
				JOptionPane.showMessageDialog(null, "Conta alterada com sucesso" ,"Ação aceita",JOptionPane.INFORMATION_MESSAGE);
				dispose();
				
			}catch (SQLException e3) {
				JOptionPane.showMessageDialog(null, "Erro na atualização cadastro da Conta: "+e3.getMessage(),"Ação negada",JOptionPane.WARNING_MESSAGE);
			} 
			
			limparCampos();
			tfNomeTitular.grabFocus();

		}else{
			JOptionPane.showMessageDialog(null, "Todos os campos são obrigatórios." ,"Ação negada",JOptionPane.WARNING_MESSAGE);
		}

	}
}

