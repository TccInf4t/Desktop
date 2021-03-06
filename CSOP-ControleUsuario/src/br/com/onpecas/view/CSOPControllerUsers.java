package br.com.onpecas.view;

import java.io.IOException;

import br.com.onpecas.controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.*;

public class CSOPControllerUsers extends Application {

	CallScene scene;
	BorderPane border;
	public static Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws IOException {
		CSOPControllerUsers.primaryStage = primaryStage;

		LoadMain();
	}

	//Esse m�todo serve para carregar a tela inicial do m�dulo Controle de Usuario
	public void LoadMain() throws IOException{

		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Login.fxml"));
        loader.setController(new LoginController());

		AnchorPane module= (AnchorPane) loader.load();
		Scene scene = new Scene(module);
		primaryStage.setTitle("CSOP Controle de Usuario");
		primaryStage.getIcons().add(new Image(getClass().getResource("logo.png").toString()));
        primaryStage.setScene(scene);
        primaryStage.show();

	}
	public static void main(String[] args) {
		launch(args);
		System.exit(0);
	}
}
