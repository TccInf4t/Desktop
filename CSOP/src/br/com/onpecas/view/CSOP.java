package br.com.onpecas.view;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class CSOP extends Application {

	BorderPane border;
	Stage primaryStage;
	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;

		LoadBorder();
		LoadModule();
	}

	public void LoadBorder() throws IOException{
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("PainelPrincipal.fxml"));

		border = (BorderPane) loader.load();
        Scene scene = new Scene(border);

        primaryStage.setScene(scene);
        primaryStage.show();

	}
	public void LoadModule() throws IOException{
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModuloInicial.fxml"));

		AnchorPane module= (AnchorPane) loader.load();
		border.setCenter(module);

	}
	public static void main(String[] args) {
		launch(args);
	}
}
