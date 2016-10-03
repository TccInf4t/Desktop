package br.com.onpecas.view;

import java.io.IOException;

import br.com.onpecas.controller.*;
import br.com.onpecas.model.Endereco;
import br.com.onpecas.model.Transportadora;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CallScene {
	public static Stage secondStage;
	public static Stage thirdStage;

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

	public void LoadEndereco(Endereco endereco){
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Endereco.fxml"));
        loader.setController(new EnderecoController(endereco));
        thirdStage = new Stage();
		try {
			AnchorPane module= (AnchorPane) loader.load();

			Scene scene = new Scene(module);
			thirdStage.setScene(scene);
			thirdStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void LoadTransportadoraCadastroDetalhe(Transportadora transportadora){
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("TransportadoraDetalhe.fxml"));
        loader.setController(new TransportadoraCadastroDetalheController(transportadora));
        secondStage = new Stage();
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
