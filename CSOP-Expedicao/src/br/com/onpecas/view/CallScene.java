package br.com.onpecas.view;

import java.io.IOException;

import br.com.onpecas.controller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CallScene {
	public void LoadTransportadoraInicial(){

		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("TransportadoraInicial.fxml"));
        loader.setController(new TransportadoraInicialController());

		try {
			ScrollPane module= (ScrollPane) loader.load();
			CSOPControllerExpedicao.border.setCenter(module);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void LoadEndereco(){
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Endereco.fxml"));
        loader.setController(new EnderecoController());
        Stage secondStage = new Stage();

		try {
			AnchorPane module= (AnchorPane) loader.load();

			Scene scene = new Scene(module);
			secondStage.setScene(scene);
			secondStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void LoadTransportadoraCadastroDetalhe(){
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("TransportadoraDetalhe.fxml"));
        loader.setController(new TransportadoraCadastroDetalheController());

        Stage secondStage = new Stage();

		try {
			AnchorPane module= (AnchorPane) loader.load();

			Scene scene = new Scene(module);
			secondStage.setScene(scene);
			secondStage.show();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
