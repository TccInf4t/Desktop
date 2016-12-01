package br.com.onpecas.view;

import java.io.IOException;

import br.com.onpecas.controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CSOPControllerExpedicao extends Application {

	public static Stage primaryStage;
	public static Scene scene;
	@Override
	public void start(Stage primaryStage) throws IOException {
		CSOPControllerExpedicao.primaryStage = primaryStage;
		LoadBorder();

	}

	/*Carregamento inicial das bordas*/
	public void LoadBorder() throws IOException{
		FXMLLoader loader = new FXMLLoader();

		loader.setLocation(getClass().getResource("Login.fxml"));
		loader.setController(new LoginController());
		AnchorPane module = (AnchorPane) loader.load();

		scene = new Scene(module);
        primaryStage.setScene(scene);
        primaryStage.setTitle("CSOP Expedi��o - Login");
        primaryStage.getIcons().add(new Image(getClass().getResource("logo.png").toString()));
        primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
		System.exit(0);
	}
}
