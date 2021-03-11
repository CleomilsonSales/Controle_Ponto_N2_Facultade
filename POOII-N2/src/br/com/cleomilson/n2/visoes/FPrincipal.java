package br.com.cleomilson.n2.visoes;

/* desenvolvido por Cleomilson Sales - email: cleomilsonsales@hotmail.com
Tecnologia java swing e awt (sem plugins);
Data: 27/05/14
*/


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


public class FPrincipal {
	
	public static JFrame jPrincial = new JFrame("Sistema de Contas - v.: 1.0.0");
	public static JDesktopPane deskPanel = new JDesktopPane();  
	
	public FPrincipal(){
	
		jPrincial.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jPrincial.setExtendedState(JFrame.MAXIMIZED_BOTH);
		jPrincial.setVisible(true);
		jPrincial.repaint();
		  
	}
	
	public void menuPrincipal() {
		JMenu mConta =  new JMenu("Contas");
		JMenuItem miCadastroConta = new JMenuItem("Inserir Conta");
		JMenuItem miExcluirConta = new JMenuItem("Excluir Conta");
		JMenuItem miDepositoConta = new JMenuItem("Depositar em Conta");
		JMenuItem miSacarConta = new JMenuItem("Sacar da Conta");
		JMenuItem miTransfConta = new JMenuItem("Transferir entre Conta");
		JMenu mConsultar = new JMenu("Consultar");
		JMenuItem miConsultaContas = new JMenuItem("Listar Contas");
		JMenuItem miConsultaContasExtrato = new JMenuItem("Extratos Contas");
		JMenuItem miSair = new JMenuItem("Sair");
		
		JMenuBar barra = new JMenuBar();
		barra.add(mConta);
		barra.add(mConsultar);
		mConta.add(miCadastroConta);
		mConta.add(miExcluirConta);
		mConta.add(miDepositoConta);
		mConta.add(miSacarConta);
		mConta.add(miTransfConta);
		mConta.add(miSair);
		mConsultar.add(miConsultaContas);
		mConsultar.add(miConsultaContasExtrato);
		
		
		AbrirCadastroConta abrirCadastroConta = new AbrirCadastroConta();
		miCadastroConta.addActionListener(abrirCadastroConta);
		
		AbrirExcluirConta abrirExcluirConta = new AbrirExcluirConta();
		miExcluirConta.addActionListener(abrirExcluirConta);
		
		AbrirDepositoConta abrirDepositoConta = new AbrirDepositoConta();
		miDepositoConta.addActionListener(abrirDepositoConta);
		
		AbrirSacarConta abrirSacarConta = new AbrirSacarConta();
		miSacarConta.addActionListener(abrirSacarConta);
		
		AbrirTransfConta abrirTransfConta = new AbrirTransfConta();
		miTransfConta.addActionListener(abrirTransfConta);
		
		AbrirConsultaConta abrirConsultaConta = new AbrirConsultaConta();
		miConsultaContas.addActionListener(abrirConsultaConta);
		
		AbrirConsultaContaExtrato abrirConsultaContaExtrato = new AbrirConsultaContaExtrato();
		miConsultaContasExtrato.addActionListener(abrirConsultaContaExtrato);
		
		SairSistema sairSistema = new SairSistema();
		miSair.addActionListener(sairSistema);
		
		jPrincial.setJMenuBar(barra);
		jPrincial.add(deskPanel);
		
		jPrincial.repaint();

	}
	
	private class AbrirCadastroConta implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try {
				FConta afcc = new FConta();
				afcc.criarFormularioCC();
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Erro ao Abrir janela: "+e1.getMessage(),"Ação negada",JOptionPane.WARNING_MESSAGE); 
			}
		}
	}
	
	private class AbrirExcluirConta implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try {
				FContaExcluir afce = new FContaExcluir();
				afce.criarFormularioCE();
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Erro ao Abrir janela: "+e1.getMessage(),"Ação negada",JOptionPane.WARNING_MESSAGE); 
			}
		}
	}
	
	private class AbrirDepositoConta implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try {
				FContaDepositar cd = new FContaDepositar();
				cd.criarFormularioCD();
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Erro ao Abrir janela: "+e1.getMessage(),"Ação negada",JOptionPane.WARNING_MESSAGE); 
			}
		}
	}
	
	private class AbrirSacarConta implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try {
				FContaSacar cd = new FContaSacar();
				cd.criarFormularioCS();
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Erro ao Abrir janela: "+e1.getMessage(),"Ação negada",JOptionPane.WARNING_MESSAGE); 
			}
		}
	}
	
	private class AbrirTransfConta implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try {
				FContaTransferencia ct = new FContaTransferencia();
				ct.criarFormularioCT();
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Erro ao Abrir janela: "+e1.getMessage(),"Ação negada",JOptionPane.WARNING_MESSAGE); 
			}
		}
	}
	
	private class AbrirConsultaConta implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try {
				FListaContas lc = new FListaContas(); 
				lc.criaJanela();
				lc.moveToFront();
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Erro ao Abrir janela: "+e1.getMessage(),"Ação negada",JOptionPane.WARNING_MESSAGE); 
			}
		}
	}
	
	private class AbrirConsultaContaExtrato implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try {
				FListaContasExtratos lce = new FListaContasExtratos(); 
				lce.criaJanelaListaExtrato();
				lce.moveToFront();

			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Erro ao Abrir janela: "+e1.getMessage(),"Ação negada",JOptionPane.WARNING_MESSAGE); 
			}
		}
	}
	
	private class SairSistema implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try {

				if ((JOptionPane.showConfirmDialog(null,"Deseja realmente sair do sistema?","Confirmação",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE)) == 0){ 
					System.exit(0);
				}
				

			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Erro ao Abrir janela: "+e1.getMessage(),"Ação negada",JOptionPane.WARNING_MESSAGE); 
			}
		}
	}

	public static void setEnabled(boolean b) {
		jPrincial.setEnabled (b);
		
	}
	public static void setVisible(boolean b) {
		jPrincial.setVisible(b);
		
	}
}
