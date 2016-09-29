package br.com.onpecas.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.onpecas.view.CallScene;
import javafx.fxml.*;
import javafx.scene.control.*;

public class TransportadoraCadastroDetalheController implements Initializable{

	@FXML Button btnAbrirEndereco;

	CallScene callscene;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		callscene = new CallScene();
		AtribuirBotoes();
	}

	public void AtribuirBotoes(){
		btnAbrirEndereco.setOnAction(l-> AdicionarEndereco());
	}

	public void AdicionarEndereco(){
		callscene.LoadEndereco();
	}
}
