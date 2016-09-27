package br.com.onpecas.view;

import java.io.IOException;

import br.com.onpecas.controller.PainelPrincipalController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class CallScene {
	// Carrega a tela de cadastro de transportadora
	public void loadTransportadoraDetalhe(BorderPane border){
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("TransportadoraDetalhe.fxml"));

		try {
			AnchorPane anchor = (AnchorPane) loader.load();
			border.setCenter(anchor);
		} catch (IOException e) { e.printStackTrace(); }
	}

	// Carrega a tela de lista de transportadora
	public void loadTransportadoraInicial(BorderPane border){
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("TransportadoraInicial.fxml"));

		try {
			ScrollPane scroll = loader.load();
			border.setCenter(scroll);
		} catch (IOException e) { e.printStackTrace(); }
	}
}
