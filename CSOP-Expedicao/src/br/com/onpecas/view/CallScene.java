package br.com.onpecas.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class CallScene {
	public void LoadTransportadoraInicial(){

		Stage secondStage = new Stage();

		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("TransportadoraInicial.fxml"));

		try {
			ScrollPane module= (ScrollPane) loader.load();
			CSOPControllerExpedicao.border.setCenter(module);
			/*Scene scene = new Scene(module);

			secondStage.setScene(scene);
			secondStage.show();*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
