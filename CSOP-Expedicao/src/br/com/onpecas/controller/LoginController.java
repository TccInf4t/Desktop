package br.com.onpecas.controller;

import java.net.URL;
import java.util.*;
import br.com.onpecas.helper.Alerta;
import br.com.onpecas.model.*;
import br.com.onpecas.view.CallScene;
import javafx.fxml.*;
import javafx.scene.control.*;

public class LoginController implements Initializable{

	@FXML Button btnAcessar, btnCancelar, btnOutro;
	@FXML TextField txtServer, txtLogin, txtSenha;

	public boolean aux;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ConfigButoes();
		VerificarServer();

		aux = false;

	}

	private void ConfigButoes() {
		txtServer.setDisable(true);
		btnAcessar.setOnAction(l-> Acessar());
		btnCancelar.setOnAction(l-> Cancelar());
		btnOutro.setOnAction(l-> MudarIp());
	}

	private void VerificarServer() {
		txtServer.setText(Login.VerificarServer());
	}

	public void Acessar(){
		if(aux){
			String ip = txtServer.getText();
			if(ip.isEmpty() || ip == null){
				Alerta.showError("N�o foi poss�vel se logar", "Digite um ip");
			}else{
				Login.AtribuiIP(ip);
			}
			CallScene callScene = new CallScene();
			callScene.LoadPainelPrincipal();
		}else{
			CallScene callScene = new CallScene();
			callScene.LoadPainelPrincipal();
		}
	}

	public void MudarIp(){
		txtServer.setDisable(false);
		aux = true;
	}

	public void Cancelar(){
		System.exit(0);
	}
}
