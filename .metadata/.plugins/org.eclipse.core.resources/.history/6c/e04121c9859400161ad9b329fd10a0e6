package br.com.onpecas.view;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class CSOP extends Application {

	Stage primaryStage;
	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;

		LoadModule();
	}

	public void LoadModule() throws IOException{
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModuloInicial.fxml"));

		AnchorPane module= (AnchorPane) loader.load();
		//border.setCenter(module);
		Scene scene = new Scene(module);

        primaryStage.setScene(scene);
        primaryStage.show();

        ("FILE://C:\CSOP\expedicao.jar")
	}
	public static void main(String[] args) {
		launch(args);
	}
}
