package br.com.onpecas.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.onpecas.model.Grupo;
import br.com.onpecas.model.Usuario;
import br.com.onpecas.view.CallScene;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PermissaoController implements Initializable {

	@FXML CheckBox CMSAcessar;
	@FXML Button btnSalvar,btnCancelar;
	@FXML Label lbGrupo;
	
	CallScene scene;
	Stage myStage;
	Grupo grupo;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		scene = new CallScene();
		
		AtribuirBotoes();
		lbGrupo.setText(grupo.getNome());
		
	}

	public PermissaoController(Stage myStage, Grupo grupo) {
		this.myStage = myStage;
		this.grupo = grupo;

	}
	
	public void AtribuirBotoes(){
		
		btnSalvar.setOnAction(l-> InsertPermissao());
		btnCancelar.setOnAction(l-> VoltarTela());
		
	}

	private void VoltarTela() {
		
		myStage.close();
		
	}

	private void InsertPermissao() {
		
		System.out.println(CMSAcessar.isSelected());
		
		VoltarTela();
		
	}

}
