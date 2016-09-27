package br.com.onpecas.view;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class CSOPControllerUsers extends Application {

	CallScene scene;
	BorderPane border;
	Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;

		scene = new CallScene();

		LoadBorder();
		scene.LoadMain(this.border);

	}

	//Esse m�todo serve para carregar a estrutura de borda do sistema
		public void LoadBorder() throws IOException{

			FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(getClass().getResource("PainelPrincipal.fxml"));

			border = (BorderPane) loader.load();
	        Scene scene = new Scene(border);

	        primaryStage.setScene(scene);
	        primaryStage.show();

		}

	public static void main(String[] args) {
		launch(args);
	}
}
