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
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import br.com.cleomilson.n2.dao.LoginDAO;
import br.com.cleomilson.n2.util.Main;

@SuppressWarnings("serial")
public class FLogin extends JFrame{

	private JTextField tfUsuario = new JTextField("ADMINISTRADOR",20);
	private JPasswordField tfSenha = new JPasswordField(20);
	
	//private JFrame jLogin = new JFrame("Entrar Sistema de Contas");
	
	public FLogin(){
		super("Entrar Sistema de Contas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350,200);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);

	}
	
	public void requestFocus(){
		tfSenha.requestFocus(true);
		repaint();

	}
	
	public void criarFormularioLogin(){
		setLayout(new BorderLayout());
		
		JPanel pCorpo = new JPanel();
		pCorpo.setLayout(new GridBagLayout());
		
		JLabel lUsuario = new JLabel("Usuário: ");
		JLabel lSenha = new JLabel("Senha: ");
		JButton botao = new JButton("Entrar (Enter)"); 
		JLabel lTitulo = new JLabel("Login no Sistema");
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
	    pCorpo.add(lEspaco, gbc);

	    gbc.gridy = 2; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST; 
	    pCorpo.add(lUsuario, gbc);
	    
	    gbc.gridy = 2; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(tfUsuario, gbc);
		
	    gbc.gridy = 3; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(lSenha, gbc);
	   
	    gbc.gridy = 3; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST;
	    pCorpo.add(tfSenha, gbc);
	    
	    gbc.gridy = 4; 
	    gbc.gridx = 0; 
	    gbc.gridwidth = 2;
	    gbc.anchor = GridBagConstraints.CENTER;
	    pCorpo.add(botao, gbc);
	    
	    EntrarAction entrarAction = new EntrarAction();
		botao.addActionListener(entrarAction);

		JPanel pDiretos = new JPanel();
		pDiretos.setLayout(new FlowLayout());
		JLabel lDiretos = new JLabel(Main.lTextDireitos);
		pDiretos.add(lDiretos);
		add(pDiretos, BorderLayout.SOUTH);
		
		add(pCorpo, BorderLayout.NORTH);


		tfUsuario.addKeyListener(new KeyAdapter(){  
	        public void keyReleased(KeyEvent e){  
	        	jtUpper(tfUsuario);
	        }
	    });
		
		tfSenha.addKeyListener(new KeyAdapter(){  
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {  
					try {
						logando();
					}catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Erro ao fazer login: "+ex.getMessage() ,"Ação negada",JOptionPane.WARNING_MESSAGE);
					}
				}  
			}
	    });
		repaint();
	}

	
	public void jtUpper(JTextField jt){  
        String valor = jt.getText().toUpperCase();  
        jt.setText(valor);  
    }  
	
	@SuppressWarnings("deprecation")
	private boolean verificarCampos(){
		boolean retorno = false;
		if (tfUsuario.getText().equals("")){
			retorno = true;
		}else{
			if (tfSenha.getText().equals("")){
				retorno =true;
			}
		}
		return retorno;
	}
	
	@SuppressWarnings("deprecation")
	private void logando(){
		if (verificarCampos()){
			JOptionPane.showMessageDialog(null, "Usuário e/ou senha são obrigatórios." ,"Ação negada",JOptionPane.WARNING_MESSAGE); 
		 }else{ 

			try {
				 LoginDAO lb = new LoginDAO();
				if (lb.logar(tfUsuario.getText(),tfSenha.getText())){
					dispose();
					FPrincipal.setEnabled(true);
					FPrincipal.setVisible(true);

				}else{
					JOptionPane.showMessageDialog(null, "Usuário e/ou senha inválida." ,"Ação negada",JOptionPane.WARNING_MESSAGE);
				}
			}catch(SQLException e){
				JOptionPane.showMessageDialog(null, "Erro ao fazer login: "+e.getMessage() ,"Ação negada",JOptionPane.WARNING_MESSAGE);
			}catch (HeadlessException hs) {
				JOptionPane.showMessageDialog(null, "Erro ao fazer login: "+hs.getMessage() ,"Ação negada",JOptionPane.WARNING_MESSAGE);
			}
			
		}
	}

	private class EntrarAction implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try {
				logando();
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Erro ao fazer login: "+e1.getMessage() ,"Ação negada",JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
}