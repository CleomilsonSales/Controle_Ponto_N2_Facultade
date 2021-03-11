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
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
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

import br.com.cleomilson.n2.dao.ExtratosDAO;
import br.com.cleomilson.n2.util.Extrato;
import br.com.cleomilson.n2.util.Main;

@SuppressWarnings("serial")
public class FListaContasExtratos extends JInternalFrame{ 
	
	//private JInternalFrame jListarContasExtrato = new JInternalFrame("Extratos Geral");
	
	private JPanel painelFundo; 
	private JTable tabelaExtrato; 
	private JScrollPane barraRolagemExtrato; 
	private DefaultTableModel modeloExtrato = new DefaultTableModel(); 
	private JTextField tfCodigoExtrato = new JTextField(10);
	private JFormattedTextField tfCpfExtrato = new JFormattedTextField();
	
    private double vDebitos = 0;
    private double vCredito = 0;	
    private double vTotalExtrato = 0;
    
	private JLabel lVDesconto = new JLabel();
	private JLabel lVCredito = new JLabel();
	private JLabel lVTotal = new JLabel();
	
	public FListaContasExtratos(){
		super("Extratos Geral");
	}
    
	public void criaJanelaListaExtrato() throws ParseException, SQLException, PropertyVetoException { 

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
		
		barraRolagemExtrato = new JScrollPane(tabelaExtrato); 
		painelFundo = new JPanel(); 
		painelFundo.setLayout(new BorderLayout()); 
		painelFundo.add(BorderLayout.CENTER, barraRolagemExtrato); 
		getContentPane().add(painelFundo); 
		
		montarConsulta();
		
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
	
	private void montarConsulta() throws ParseException{
		
			MaskFormatter mascaraCpf = new MaskFormatter("###.###.###-##");
			mascaraCpf.setPlaceholderCharacter('_');
			tfCpfExtrato = new JFormattedTextField(mascaraCpf);

		tfCodigoExtrato.addFocusListener(new FocusAdapter() {  
            public void focusLost(FocusEvent evt) {  
            	jtfFocusLostNum(tfCodigoExtrato);
            }  
        });
		
		setLayout(new BorderLayout());
		
		JPanel pConsulta = new JPanel();
		pConsulta.setLayout(new GridBagLayout());
		
		JLabel lCodigo = new JLabel("Código da Conta: ");
		JLabel lCpf = new JLabel("CPF: ");
		
	    GridBagConstraints gbc = new GridBagConstraints();

	    gbc.insets = new Insets(2, 2, 2, 10);
	    
	    gbc.gridy = 0;
	    gbc.gridx = 0;
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.WEST; 
	    pConsulta.add(lCodigo, gbc);
	    
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
	    pConsulta.add(tfCodigoExtrato, gbc);
	    
	    gbc.gridy = 1; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST; 
	    pConsulta.add(tfCpfExtrato, gbc);
	   
	    
	    //segunda coluna
	    
	    JButton botaoConsultar = new JButton("Pesquisar (Enter)"); 
	    ConsultaExtrato consultaExtrato = new ConsultaExtrato();
	    botaoConsultar.addActionListener(consultaExtrato);

	    tfCodigoExtrato.addKeyListener(new KeyAdapter(){  
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {  
					try {
						if (!pesquisar(modeloExtrato)){
							JOptionPane.showMessageDialog(null, "Não foram encontrados registro para a pesquisa." ,"Ação negada",JOptionPane.WARNING_MESSAGE);	
						}; 
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Erro ao listar o extrato da conta: "+e1.getMessage(),"Ação negada",JOptionPane.WARNING_MESSAGE);
					}
				}  
			}
	    });
	    
	    tfCpfExtrato.addKeyListener(new KeyAdapter(){  
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {  
					try {
						if (!pesquisar(modeloExtrato)){
							JOptionPane.showMessageDialog(null, "Não foram encontrados registro para a pesquisa." ,"Ação negada",JOptionPane.WARNING_MESSAGE);	
						}; 
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Erro ao listar o extrato da conta: "+e1.getMessage(),"Ação negada",JOptionPane.WARNING_MESSAGE);
					}
				}  
			}
	    });
	    
	    gbc.gridy = 1; 
	    gbc.gridx = 2; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST; 
	    pConsulta.add(botaoConsultar, gbc);
	    
		JLabel lEspaco = new JLabel(" ");
		lEspaco.setFont(new Font("Verdana", Font.PLAIN, 18));
	    
	    gbc.gridy = 2; 
	    gbc.gridx = 1; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST; 
	    pConsulta.add(lEspaco, gbc);
	    
	    gbc.gridy = 3; 
	    gbc.gridx = 10; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST; 
	    pConsulta.add(lVDesconto, gbc);
	    
	    gbc.gridy = 3; 
	    gbc.gridx = 11; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST; 
	    pConsulta.add(lVCredito, gbc);
	    
	    gbc.gridy = 3; 
	    gbc.gridx = 12; 
	    gbc.gridwidth = 1; 
	    gbc.anchor = GridBagConstraints.WEST; 
	    pConsulta.add(lVTotal, gbc);
		
	    JScrollPane sptabela = new JScrollPane(tabelaExtrato);
	    JPanel pTable = new JPanel();

	    pTable.add(sptabela);

	    JPanel pDiretos = new JPanel();
		pDiretos.setLayout(new FlowLayout());
		JLabel lDiretos = new JLabel(Main.lTextDireitos);
		pDiretos.add(lDiretos);
		add(pDiretos, BorderLayout.SOUTH);

		add(pConsulta, BorderLayout.NORTH);
		add(sptabela, BorderLayout.CENTER);
		
		tfCodigoExtrato.requestFocus(true);
		repaint();
		
	}
	
	public void jtUpper(JTextField jt){  
        String valor = jt.getText().toUpperCase();  
        jt.setText(valor);  
    }  
	
	private class ConsultaExtrato implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try {
				if (!pesquisar(modeloExtrato)){
					JOptionPane.showMessageDialog(null, "Não foram encontrados registro para a pesquisa." ,"Ação negada",JOptionPane.WARNING_MESSAGE);	
				};  
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Erro ao listar o extrato da conta: "+e1.getMessage(),"Ação negada",JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	private void criaJTable() throws SQLException { 
		tabelaExtrato = new JTable(modeloExtrato); 
		modeloExtrato.addColumn("Código"); 
		modeloExtrato.addColumn("Titular"); 
		modeloExtrato.addColumn("Cpf"); 
		modeloExtrato.addColumn("Movimentação");
		modeloExtrato.addColumn("Tipo Trans");
		modeloExtrato.addColumn("Transação");
		modeloExtrato.addColumn("Valor"); 
		tabelaExtrato.getColumnModel().getColumn(0).setPreferredWidth(40); 
		tabelaExtrato.getColumnModel().getColumn(1).setPreferredWidth(400); 
		tabelaExtrato.getColumnModel().getColumn(2).setPreferredWidth(80);
		tabelaExtrato.getColumnModel().getColumn(3).setPreferredWidth(80);
		tabelaExtrato.getColumnModel().getColumn(4).setPreferredWidth(60);
		tabelaExtrato.getColumnModel().getColumn(5).setPreferredWidth(200); 
		tabelaExtrato.getColumnModel().getColumn(6).setPreferredWidth(80);
		//pesquisar(modeloExtrato); 

	} 
	
	private boolean pesquisar(DefaultTableModel modeloExtrato) throws SQLException { 
		vTotalExtrato = 0;
		vCredito = 0;
		vDebitos = 0;
		boolean retorno = false;
		DecimalFormat formatarDouble = new DecimalFormat("#,##0.00");
		
		modeloExtrato.setNumRows(0); 
		
		int vCod = 0;
		if (!tfCodigoExtrato.getText().equals("")){
			vCod = Integer.parseInt(tfCodigoExtrato.getText());
		}
		
		String vCpf = tfCpfExtrato.getText();
		
		if (tfCpfExtrato.equals("___.___.___-__")){
			vCpf = "";
		}
		
	    ExtratosDAO dao = new ExtratosDAO(); 
	    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			for (Extrato ex : dao.getListContaExtrato(vCod, vCpf)) { 
				if(!ex.getTransacao().equals("CRIACAO CONTA")){
					modeloExtrato.addRow(new Object[]{
							ex.getCodigo(),
							ex.getNomeTitular(),
							ex.getCpf(),
							df.format(ex.getDataTransacao()),
							ex.getTipoExtrato(),
							ex.getTransacao(),
							ex.getValor()}); 
					retorno = true;
					
					if (ex.getTipoExtrato().equals("D")){
						vDebitos = vDebitos + ex.getValor();
					}else{
						if (ex.getTipoExtrato().equals("C")){
							vCredito = vCredito + ex.getValor();
						}	
					}
				}
				
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar o extrato da conta: "+e.getMessage(),"Ação negada",JOptionPane.WARNING_MESSAGE);
		}
		
		vTotalExtrato = vCredito - vDebitos;
		lVDesconto.setText("Total Débito: "+ formatarDouble.format(vDebitos));
		lVCredito.setText("Total Crédito: "+ formatarDouble.format(vCredito));
		lVTotal.setText("Saldo: "+ formatarDouble.format(vTotalExtrato));
		return retorno;
	} 
}

