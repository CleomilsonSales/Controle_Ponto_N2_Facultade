package br.com.cleomilson.n2.util;

/* desenvolvido por Cleomilson Sales - email: cleomilsonsales@hotmail.com
Tecnologia java swing e awt (sem plugins);
Data: 27/05/14
*/

import br.com.cleomilson.n2.visoes.FLogin;
import br.com.cleomilson.n2.visoes.FPrincipal;

public class Main {
	public static final String lTextDireitos = "Cleomilson Sales - ADS30 - POO2";

	public static void main(String[] args) {
	
		final FPrincipal fPrincipal = new FPrincipal();
		fPrincipal.menuPrincipal();
		final FLogin fLogin = new FLogin();
		fLogin.criarFormularioLogin();

		FPrincipal.setEnabled (false);
		fLogin.requestFocus();

	}
}
