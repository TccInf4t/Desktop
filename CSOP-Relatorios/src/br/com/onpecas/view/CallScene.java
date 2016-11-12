package br.com.onpecas.view;

import java.io.IOException;

import br.com.onpecas.controller.RelatorioController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class CallScene {

	public void LoadMain(){
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Relatorio.fxml"));
        loader.setController(new RelatorioController());

		AnchorPane border;
		try {
			border = (AnchorPane) loader.load();
			Scene scene = new Scene(border);

	        CSOPControllerRelatorio.primaryStage.setScene(scene);
	        CSOPControllerRelatorio.primaryStage.setTitle("Relatórios");
	        CSOPControllerRelatorio.primaryStage.getIcons().add(new Image(getClass().getResource("logo.png").toString()));
	        CSOPControllerRelatorio.primaryStage.show();
	        CSOPControllerRelatorio.primaryStage.centerOnScreen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
