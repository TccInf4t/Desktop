package br.com.onpecas.view;

import java.io.IOException;

import br.com.onpecas.controller.CSOPController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.*;

public class CSOP extends Application {

	public static Stage primaryStage;
	@Override
	public void start(Stage primaryStage) throws IOException {
		CSOP.primaryStage = primaryStage;
		CSOP.primaryStage.getIcons().add(new Image(getClass().getResource("logo.png").toString()));
		LoadModule();
	}

	public void LoadModule() throws IOException{
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModuloInicial.fxml"));

        loader.setController(new CSOPController());
		AnchorPane module= (AnchorPane) loader.load();
		//border.setCenter(module);
		Scene scene = new Scene(module);

        primaryStage.setScene(scene);
        primaryStage.show();

	}
	public static void main(String[] args) {
		launch(args);
	}
}
