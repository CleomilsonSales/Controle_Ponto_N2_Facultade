package br.com.cleomilson.n2.visoes;

/* desenvolvido por Cleomilson Sales - email: cleomilsonsales@hotmail.com
Tecnologia java swing e awt (sem plugins);
Data: 27/05/14
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import br.com.cleomilson.n2.dao.ContaDAO;
import br.com.cleomilson.n2.util.Conta;
import br.com.cleomilson.n2.util.Main;

@SuppressWarnings("serial")
public class FListaContas extends JInternalFrame { 
	
	//private JInternalFrame jListarContas = new JInternalFrame("Consultar Contas");
	
	
	private JPanel painelFundo; 
	private JTable tabela; 
	private JScrollPane barraRolagem; 
	private DefaultTableModel modelo = new DefaultTableModel(); 
	private JTextField tfNome =  new JTextField(25);
	JFormattedTextField tfCpfLista = new JFormattedTextField();
	
	
	public FListaContas(){
		super("Consultar Contas");
		/* this.addInternalFrameListener(new InternalFrameAdapter(){  
            public void internalFrameClosing(InternalFrameEvent e) {  
            	ação
            }  
         });*/  
	}

	public void criaJanela() throws SQLException, ParseException, PropertyVetoException { 
		//criando mascaras
		
		MaskFormatter mascaraCpf = new MaskFormatter("###.###.###-##");
		mascaraCpf.setPlaceholderCharacter('_');
		tfCpfLista = new JFormattedTextField(mascaraCpf);
		
		//--
		
		criaJTable();


		setSize(FPrincipal.deskPanel.getWidth(), FPrincipal.deskPanel.getHeight()); 
		setIconifiable(true); 
		setIcon(true);
		setMaximum(true);
		setSelected(true);
		setClosable(true);

		setVisible(true);  
		repaint();
		
		FPrincipal.deskPanel.add(this); 
		
		barraRolagem = new JScrollPane(tabela); 
		painelFundo = new JPanel(); 
		painelFundo.setLayout(new BorderLayout()); 
		painelFundo.add(BorderLayout.CENTER, barraRolagem); 
		getContentPane().add(painelFundo); 
		
		montarConsulta();

	}
	
	private void montarConsulta(){
		

		
		setLayout(new BorderLayout());
		
		JPanel pConsulta = new JPanel();
		pConsulta.setLayout(new GridBagLayout());
		
		JLabel lNome = new JLabel("Informe o nome:");
		JLabel lCpf = new JLabel("Informe o CPF:");
		JLabel lmsg = new JLabel("* - Dois cliques para alterar registro.");
		lmsg.setFont(new Font("Verdana", Font.PLAIN, 9));
		lmsg.setForeground(Color.red);
		
	    GridBagConstraints gbc = new GridBagConstraints();

	    gbc.insets = new Insets(2, 2, 2, 10);
	    
	    gbc.gridy = 0;
	    gbc.gridx = 0;
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST; 
	    pConsulta.add(lNome, gbc);
	    
	    gbc.gridy = 1;
	    gbc.gridx = 0;
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST; 
	    pConsulta.add(lCpf, gbc);
	    
	    //segunda coluna
	    
	    gbc.gridy = 0; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST; 
	    pConsulta.add(tfNome, gbc);
	    
	    gbc.gridy = 1; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST; 
	    pConsulta.add(tfCpfLista, gbc);
	    
	    tfNome.addKeyListener(new KeyAdapter(){  
	        public void keyReleased(KeyEvent e){  
	        	jtUpper(tfNome);
	        }
	    });
	    
	    //segunda coluna
	    
	    JButton botaoConsultar = new JButton("Pesquisar (Enter)"); 
	    ConsultaNome consultaNome = new ConsultaNome();
	    botaoConsultar.addActionListener(consultaNome);

	    tfNome.addKeyListener(new KeyAdapter(){  
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {  
					try {
						if (!pesquisar(modelo)){
							JOptionPane.showMessageDialog(null, "Não foram encontrados registro para a pesquisa." ,"Ação negada",JOptionPane.WARNING_MESSAGE);	
						}; 
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Erro ao listar a conta: "+e1.getMessage(),"Ação negada",JOptionPane.WARNING_MESSAGE);
					}
				}  
			}
	    });
	    
		tfCpfLista.addKeyListener(new KeyAdapter(){  
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {  
					try {
						if (!pesquisar(modelo)){
							JOptionPane.showMessageDialog(null, "Não foram encontrados registro para a pesquisa." ,"Ação negada",JOptionPane.WARNING_MESSAGE);	
						}; 
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Erro ao listar a conta: "+e1.getMessage(),"Ação negada",JOptionPane.WARNING_MESSAGE);
					}				
				}  
			}
		});
	    
	    gbc.gridy = 1; 
	    gbc.gridx = 3; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST; 
	    pConsulta.add(botaoConsultar, gbc);
	    
	    gbc.gridy = 3; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST; 
	    pConsulta.add(lmsg, gbc);
	    
	    JScrollPane sptabela = new JScrollPane(tabela);
	    JPanel pTable = new JPanel();

	    pTable.add(sptabela);
	    
		JPanel pDiretos = new JPanel();
		pDiretos.setLayout(new FlowLayout());
		JLabel lDiretos = new JLabel(Main.lTextDireitos);
		pDiretos.add(lDiretos);
		add(pDiretos, BorderLayout.SOUTH);

		add(pConsulta, BorderLayout.NORTH);
		add(sptabela, BorderLayout.CENTER);
		
		tfNome.requestFocus(true);
		repaint();
		
	}
	
	public void jtUpper(JTextField jt){  
        String valor = jt.getText().toUpperCase();  
        jt.setText(valor);  
    }  
	
	private class ConsultaNome implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try {
				if (!pesquisar(modelo)){
					JOptionPane.showMessageDialog(null, "Não foram encontrados registro para a pesquisa." ,"Ação negada",JOptionPane.WARNING_MESSAGE);	
				}; 
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Erro ao listar a conta: "+e1.getMessage(),"Ação negada",JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	private boolean verificarCampos(){
		boolean retorno = true;
		if ((tfNome.getText().equals("")) && (tfCpfLista.getText().equals("___.___.___-__"))){
			retorno = false;
		}
		return retorno;
	}
	
	private void criaJTable() throws SQLException { 
		tabela = new JTable(modelo); 
	
		tabela.addMouseListener(new MouseAdapter() {  
	        public void mousePressed(MouseEvent e) {  
	        if(e.getClickCount() == 2){  
	        	 String valor = tabela.getValueAt(tabela.getSelectedRow(), 0).toString();  
	        	 try {
	        		
	        		FContaAlter fca = new FContaAlter();
					fca.criarFormularioCC(Integer.parseInt(valor));
					
					dispose();
					
	        	 }catch (NumberFormatException e1) {
	        		 JOptionPane.showMessageDialog(null, "Erro ao abrir edição de conta: "+e1.getMessage(),"Ação negada",JOptionPane.WARNING_MESSAGE);
				} catch (ParseException e1) {
					 JOptionPane.showMessageDialog(null, "Erro ao abrir edição de conta: "+e1.getMessage(),"Ação negada",JOptionPane.WARNING_MESSAGE);
				}
	        }  
	        }} );  

		
		modelo.addColumn("Código"); 
		modelo.addColumn("Titular"); 
		modelo.addColumn("Cpf"); 
		modelo.addColumn("Nascimento");
		modelo.addColumn("Sexo");
		modelo.addColumn("Endereço"); 
		modelo.addColumn("Limite"); 
		modelo.addColumn("Transferência"); 
		tabela.getColumnModel().getColumn(0).setPreferredWidth(3); 
		tabela.getColumnModel().getColumn(1).setPreferredWidth(220); 
		tabela.getColumnModel().getColumn(2).setPreferredWidth(100);
		tabela.getColumnModel().getColumn(3).setPreferredWidth(60);
		tabela.getColumnModel().getColumn(4).setPreferredWidth(3); 
		tabela.getColumnModel().getColumn(5).setPreferredWidth(380); 
		tabela.getColumnModel().getColumn(6).setPreferredWidth(60); 
		tabela.getColumnModel().getColumn(7).setPreferredWidth(60);
		if (!pesquisar(modelo)){
			JOptionPane.showMessageDialog(null, "Não foram encontrados registro para a pesquisa." ,"Ação negada",JOptionPane.WARNING_MESSAGE);	
		}; 

	} 
	
	private boolean pesquisar(DefaultTableModel modelo) throws SQLException { 
		boolean retorno = false;
		String vCpfLista = null;
		String vNome = null;
		if (verificarCampos()){
			if (!tfNome.getText().equals("")){
				vNome = tfNome.getText();
			}
			
			if (!tfCpfLista.getText().equals("___.___.___-__")){
				vCpfLista = tfCpfLista.getText();
			}
		}
		
		modelo.setNumRows(0); 
		ContaDAO dao = new ContaDAO(); 
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			for (Conta c : dao.getListConta(vNome,vCpfLista)) { 
				modelo.addRow(new Object[]{
						c.getCodigo(),
						c.getNomeTitular(),
						c.getCpf(),
						df.format(c.getDataNascimento()),
						c.getSexo(),
						c.getEnderecoCompleto(),
						c.getLimite(),
						c.getLimiteTransferencia()}); 
				retorno = true;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar a conta: "+e.getMessage(),"Ação negada",JOptionPane.WARNING_MESSAGE);
		} 
		return retorno;
	} 
}

