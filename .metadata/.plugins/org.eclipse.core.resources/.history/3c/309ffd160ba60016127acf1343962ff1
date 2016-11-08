package br.com.onpecas.view;

import java.io.IOException;

import br.com.onpecas.controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CSOPControllerRelatorio extends Application {

	public static Stage primaryStage;
	static AnchorPane border;

	@Override
	public void start(Stage primaryStage) throws IOException {
		CSOPControllerRelatorio.primaryStage = primaryStage;
		LoadBorder();
	}

	/*Carregamento inicial das bordas*/
	public void LoadBorder() throws IOException{
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Relatorio.fxml"));
        loader.setController(new RelatorioController());

		border = (AnchorPane) loader.load();
        Scene scene = new Scene(border);

        primaryStage.setScene(scene);
        primaryStage.show();
	}
	public static void main(String[] args) {
		launch(args);
		System.exit(0);
	}
}
