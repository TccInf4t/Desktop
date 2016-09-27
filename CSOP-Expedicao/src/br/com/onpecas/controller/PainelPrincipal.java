package br.com.onpecas.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.onpecas.view.CallScene;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

public class PainelPrincipal implements Initializable {
	@FXML MenuItem menuItemTransp;

	CallScene call;
	BorderPane border;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		call = new CallScene();

		menuItemTransp.setOnAction(l->call.loadTransportadoraInicial(border));
	}

}
