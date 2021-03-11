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

import br.com.cleomilson.n2.exceptions.ContaAtivaException;
import br.com.cleomilson.n2.exceptions.ContaNaoEncontradaException;
import br.com.cleomilson.n2.util.Conta;
import br.com.cleomilson.n2.util.Controle;
import br.com.cleomilson.n2.util.Main;


@SuppressWarnings("serial")
public class FContaExcluir extends JFrame{
	
	private JTextField tfCodigoExcluir = new JTextField(10);
	private JFormattedTextField tfCpfExcluir = new JFormattedTextField();
	
	//private JFrame jContaExcluir = new JFrame("Exclusão de Conta");
	
	public FContaExcluir() throws ParseException{
		super("Exclusão de Conta");
		setSize(450,250);
		setVisible(true);
		setLocationRelativeTo(null);
		repaint();
		
	}

	public void criarFormularioCE() throws ParseException{
		setLayout(new BorderLayout());
		
		JPanel pCorpo = new JPanel();
		pCorpo.setLayout(new GridBagLayout());
		
		//criando mascaras
		
		MaskFormatter mascaraCpf = new MaskFormatter("###.###.###-##");
		mascaraCpf.setPlaceholderCharacter('_');
		tfCpfExcluir = new JFormattedTextField(mascaraCpf);
		
		//--
		
		//ações de fields
		
		tfCodigoExcluir.addFocusListener(new FocusAdapter() {  
            public void focusLost(FocusEvent evt) {  
            	jtfFocusLostNum(tfCodigoExcluir);
            }  
        });
		
		tfCodigoExcluir.addKeyListener(new KeyAdapter(){  
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {  
					deletando();
				}  
			}
		});
		
		tfCpfExcluir.addKeyListener(new KeyAdapter(){  
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {  
					deletando();
				}  
			}
		});

		//--
		
		JLabel lCodigo = new JLabel("Código da Conta: ");
		JLabel lCpf = new JLabel("CPF: ");
		
		JButton botaoConsultar = new JButton("Excluir (Enter)"); 
		JButton botaoCancelar = new JButton("Cancelar");
		
		JLabel lTitulo = new JLabel("Exclusão de Conta");
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
	    pCorpo.add(lCodigo, gbc);
	    
	    gbc.gridy = 3; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(lCpf, gbc);
	    
	    //segunda coluna
	    
	    gbc.gridy = 2; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST; 
	    pCorpo.add(tfCodigoExcluir, gbc);
	    
	    gbc.gridy = 3; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(tfCpfExcluir, gbc);
	    
	    gbc.gridy = 10; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST; 
	    pCorpo.add(lEspaco, gbc);
	    
	    JPanel pBotoes = new JPanel();
	    pBotoes.setLayout(new FlowLayout());
	    pBotoes.add(botaoConsultar);
	    ConsultarExcluirAction consultarExcluirAction = new ConsultarExcluirAction();
	    botaoConsultar.addActionListener(consultarExcluirAction);
	    
	    pBotoes.add(botaoCancelar);
	    CancelarExcluirAction cancelarExcluirAction = new CancelarExcluirAction();
	    botaoCancelar.addActionListener(cancelarExcluirAction);
		
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

	private boolean verificarCampos(){
		boolean retorno = true;
		if ((tfCodigoExcluir.getText().equals("")) && (tfCpfExcluir.getText().equals("___.___.___-__"))){
			retorno = false;
		}
		return retorno;
	}
	
	private void limparCampos(){
		tfCodigoExcluir.setText(null);
		tfCpfExcluir.setText(null);
	}
	
	private class ConsultarExcluirAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			deletando();
		}
	}
	
	private void deletando(){
		//carregando variaveis
		int vCodigoExcluir = 0;
		if (!tfCodigoExcluir.getText().equals("")){
			vCodigoExcluir = Integer.parseInt(tfCodigoExcluir.getText());
		}

		String vCpfExcluir = null;
		if (!tfCpfExcluir.getText().equals("___.___.___-__")){
			vCpfExcluir = tfCpfExcluir.getText();
		}
		//--fim
		
		try {
			Controle ceb = new Controle();
			Conta ce = new Conta(); 
			
			ce.setCodigo(vCodigoExcluir);
			ce.setCpf(vCpfExcluir);
			
			if (verificarCampos()){
				if ((JOptionPane.showConfirmDialog(null,"Deseja realmente excluir conta?","Confirmação",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE)) == 0){
					ceb.deletarConta(ce);
					JOptionPane.showMessageDialog(null, "Conta excluida com sucesso" ,"Ação aceita",JOptionPane.INFORMATION_MESSAGE);
				}
			}else{
				JOptionPane.showMessageDialog(null, "Um campo deve ser informado." ,"Ação negada",JOptionPane.WARNING_MESSAGE);
			}
		}catch (SQLException e2) {
			JOptionPane.showMessageDialog(null, "Erro ao deletar a conta: "+e2.getMessage(),"Ação negada",JOptionPane.WARNING_MESSAGE);
		}catch(ContaAtivaException ca){
			JOptionPane.showMessageDialog(null, "Erro ao deletar a conta: "+ca.getMessage() ,"Ação negada",JOptionPane.WARNING_MESSAGE);
		}catch(ContaNaoEncontradaException cne){
			JOptionPane.showMessageDialog(null, "Erro ao deletar a conta: "+cne.getMessage() ,"Ação negada",JOptionPane.WARNING_MESSAGE);
		}
		
		limparCampos();
		tfCodigoExcluir.grabFocus();
	}
	private class CancelarExcluirAction implements ActionListener{
		public void actionPerformed(ActionEvent e){
			limparCampos();
			dispose();
		}
	}

	
}

