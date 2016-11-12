package br.com.onpecas.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import br.com.onpecas.helper.*;
import br.com.onpecas.model.*;
import br.com.onpecas.view.CallScene;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public class LoginController implements Initializable{

	@FXML Button btnAcessar, btnCancelar, btnOutro;
	@FXML TextField txtServer, txtLogin, txtSenha;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ConfigButoes();
		VerificarServer();
		txtSenha.setOnKeyPressed(k->{
			KeyCombination  enter = new KeyCodeCombination(KeyCode.ENTER);
			if(enter.match(k)){
				Acessar();
			}
		});
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

		String ip = txtServer.getText();
		if(ip.isEmpty() || ip == null){
			Alerta.showError("Não foi possível se logar", "Digite um ip");
		}else{
			Login.AtribuiIP(ip);
			Helper.ip = ip;
			Connection con = MySqlConnect.ConectarDb();

			if(con != null){
				try {
					con.close();

					if(txtLogin.getText().isEmpty() || txtSenha.getText().isEmpty()){
						Alerta.showError("Não foi possível se logar", "Preencha usuario e senha");
					}else{
						String login = txtLogin.getText();
						String senha = txtSenha.getText();
						int verUser = Login.VerificaUsuario(login, senha);
						if(verUser == 1){
							CallScene callScene = new CallScene();
							callScene.LoadPainelPrincipal();
						}else if (verUser == 2){
							Alerta.showError("Não foi possível se logar", "Você não tem acesso à esse módulo\nContate o Administrador");
						}else if(verUser == 0){
							Alerta.showError("Não foi possível se logar", "Usuario ou senha incorretos");
						}
					}

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				Alerta.showError("Não foi possível se logar", "Servidor Inacessivel");
			}
		}
	}

	public void MudarIp(){
		txtServer.setDisable(false);
	}

	public void Cancelar(){
		System.exit(0);
	}
}
