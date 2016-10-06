package br.com.onpecas.view;

import java.io.IOException;

import br.com.onpecas.controller.PainelPrincipalController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CSOPControllerExpedicao extends Application {

	Stage primaryStage;
	static BorderPane border;

	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		LoadBorder();
	}

	/*Carregamento inicial das bordas*/
	public void LoadBorder() throws IOException{
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("PainelPrincipal.fxml"));
        loader.setController(new PainelPrincipalController());

		border = (BorderPane) loader.load();
        Scene scene = new Scene(border);

        primaryStage.setScene(scene);
        primaryStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
}
