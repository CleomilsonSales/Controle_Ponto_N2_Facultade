package br.com.cleomilson.n2.util;

import br.com.cleomilson.n2.visoes.FLogin;
import br.com.cleomilson.n2.visoes.FPrincipal;

public class Principal {
	public static void main(String[] args) {
		//abrindo primeira tela
		final FPrincipal fPrincipal = new FPrincipal();
		fPrincipal.menuPrincipal();
		final FLogin fLogin = new FLogin();
		fLogin.criarFormularioLogin();

		FPrincipal.setEnabled (false);
		

	}
}
