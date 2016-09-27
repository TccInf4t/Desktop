package br.com.onpecas.view;

import java.io.IOException;

import br.com.onpecas.controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CSOPControllerExpedicao extends Application {
	BorderPane border;
	Stage stage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.stage = primaryStage;

		CallScene callScene = new CallScene();
		loadBorder();
		/*callScene.loadTransportadoraInicial(this.border);*/
	}

	public void loadBorder(){
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("PainelPrincipal.fxml"));
		loader.setController(new PainelPrincipal());

		try {
			border = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Scene scene = new Scene(border);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args){
		launch();
	}
}
