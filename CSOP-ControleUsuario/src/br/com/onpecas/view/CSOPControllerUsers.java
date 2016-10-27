package br.com.onpecas.view;

import java.io.IOException;

import br.com.onpecas.controller.UsuariosGruposController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.*;

public class CSOPControllerUsers extends Application {

	CallScene scene;
	BorderPane border;
	Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;

		LoadMain();
	}

	//Esse método serve para carregar a tela inicial do módulo Controle de Usuario
	public void LoadMain() throws IOException{

		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("UsuariosGrupos.fxml"));
        loader.setController(new UsuariosGruposController());

		ScrollPane module= (ScrollPane) loader.load();
		Scene scene = new Scene(module);

        primaryStage.setScene(scene);
        primaryStage.show();

	}
	public static void main(String[] args) {
		launch(args);
	}
}
