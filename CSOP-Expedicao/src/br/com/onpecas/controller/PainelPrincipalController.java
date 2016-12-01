package br.com.onpecas.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.onpecas.view.CallScene;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PainelPrincipalController implements Initializable{

	@FXML MenuItem menuItemTransportadora, menuItemPedidoSemLote, menuItemLoteSemTransporte;
	@FXML ImageView imgView;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		CallScene scene = new CallScene();
		menuItemTransportadora.setOnAction(l-> scene.LoadTransportadoraInicial());
		menuItemPedidoSemLote.setOnAction(l-> scene.LoadPedidoSemLoteInicial());
		menuItemLoteSemTransporte.setOnAction(l-> scene.LoadLoteSemTransporteInicial());
		
		imgView.setImage(new Image(getClass().getResource("logo.png").toString()));
	}

}
