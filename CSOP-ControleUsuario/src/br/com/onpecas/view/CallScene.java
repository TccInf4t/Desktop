package br.com.onpecas.view;

import java.io.IOException;

import br.com.onpecas.controller.*;
import br.com.onpecas.model.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.*;

//Classe que serve para carregar e controlar as telas

public class CallScene {

	public static Stage secondStage;

	/*Esse m�todo serve para carregar a tela de controle de permiss�es*/
	public void LoadPermission(Grupo grupo){

		secondStage = new Stage();

		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Permissoes.fxml"));
	    loader.setController(new PermissaoController(secondStage, grupo));

		try {
			AnchorPane module= (AnchorPane) loader.load();

			Scene scene = new Scene(module);
			secondStage.initModality(Modality.APPLICATION_MODAL);
			secondStage.setScene(scene);
			secondStage.setTitle("Permis�es");
			secondStage.getIcons().add(new Image(getClass().getResource("logo.png").toString()));
			
			secondStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*Esse m�todo serve para carregar a tela que insere e atualiza grupo
	 * Ela recebe um objeto do tipo grupo,
	 * caso o objeto seja nulo, o controller ir� inserir, caso contr�rio, ele ir� atualizar
	 * PS: � criada uma nova tela (Stage)
	 * */
	public  void LoadGroup(Grupo grupo){

		secondStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("GrupoCRUD.fxml"));
        loader.setController(new GrupoController(grupo));

		AnchorPane module;
		try {
			module = (AnchorPane) loader.load();

			Scene scene = new Scene(module);
			secondStage.initModality(Modality.APPLICATION_MODAL);
			secondStage.setTitle("Grupo");
			secondStage.getIcons().add(new Image(getClass().getResource("logo.png").toString()));
			secondStage.setScene(scene);
			secondStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*Esse m�todo serve para carregar a tela que insere e atualiza usuario
	 * Ela recebe um objeto do tipo Usuario,
	 * caso o objeto seja nulo, o controller ir� inserir, caso contr�rio, ele ir� atualizar
	 * PS: � criada uma nova tela (Stage)
	 * */
	public void LoadUser(Usuario usuario){

		secondStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("UsuarioCRUD.fxml"));
        loader.setController(new UserController(usuario));

		AnchorPane module;
		try {
			module = (AnchorPane) loader.load();

			Scene scene = new Scene(module);
			secondStage.initModality(Modality.APPLICATION_MODAL);

			secondStage.setTitle("Usuarios");
			secondStage.getIcons().add(new Image(getClass().getResource("logo.png").toString()));
			secondStage.setScene(scene);
			secondStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void LoadMain(){
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("UsuariosGrupos.fxml"));
        loader.setController(new UsuariosGruposController());

		ScrollPane module;
		try {
			module = (ScrollPane) loader.load();
			Scene scene = new Scene(module);
			CSOPControllerUsers.primaryStage.setTitle("CSOP - Controle de Usuario");
			CSOPControllerUsers.primaryStage.getIcons().add(new Image(getClass().getResource("logo.png").toString()));
			CSOPControllerUsers.primaryStage.setScene(scene);
			CSOPControllerUsers.primaryStage.show();
			CSOPControllerUsers.primaryStage.centerOnScreen();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
