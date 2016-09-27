package br.com.onpecas.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.onpecas.helper.Alerta;
import br.com.onpecas.helper.Helper;
import br.com.onpecas.model.Grupo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

//Classe de "controller" para grupos de usuários
public class GrupoController implements Initializable {

	@FXML Button btnCadastrarGrupo, btnCancelar;
	@FXML TextField txtNome;
	@FXML TextArea txtDescricao;

	Stage myStage;
	Grupo grupo;

	static int OID_GRUPOAUXILIO;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		OID_GRUPOAUXILIO = 0;
		AtribuirBotoes();

		//Foi criado um objeto do tipo grupo, caso ele seja nulo,
		//quer dizer que a instancia oi feita pelo primeiro modo
		if(grupo != null){
			PrepararUpdate(grupo);
		}

	}

	//Terá a opção de instanciar dois modelos da classe
	//O primeiro modelo só será passado o Stage, para que possa ser fechado depois
	public GrupoController(Stage myStage) {
		this.myStage = myStage;
	}

	//O segundo é passado, alem do stage, um objeto do tipo Grupo, para ser usado na edição
	public GrupoController(Stage myStage, Grupo grupo) {
		this.myStage = myStage;
		this.grupo = grupo;
		//PrepararUpdate(grupo);
	}

	//Esse método serve para atribuir os botões à suas funcionalidade
	private void AtribuirBotoes() {
		btnCadastrarGrupo.setOnAction(l-> InserirAtualizarGrupo());
		btnCancelar.setOnAction(l-> myStage.close());
	}

	/* Método utilizado tanto para inserir quanto para atualizar um grupo
	 * Esse método funciona da seguinte forma:
	 * Quando o GrupoController é chamado, mas na sua instancia é passada somente o Stage, quer dizer que esse método irá inserir um registro
	 * Se na instancia é passada, além do Stage, um objeto do tipo Grupo, quer dizer que esse método irá atualizar um registro
	 * PS: O nome do botão definirá como o método irá agir
	 * */

	public void InserirAtualizarGrupo(){
		String nome = txtNome.getText();
		String descricao = txtDescricao.getText();
		int oid_grupo = OID_GRUPOAUXILIO;

		switch (btnCadastrarGrupo.getText()){
			case "Cadastrar":
				if(!nome.isEmpty()){
					Grupo grupo = new Grupo();

					grupo.setNome(nome);
					grupo.setDescricao(descricao);

					Grupo.Insert(grupo);
					Helper.AUXGROUP.setValue(1);
					myStage.close();

				}else{
					Alerta.showError("Não é possível criar o grupo", "O grupo precisa de um nome para ser criado.");
				}

				break;
			case "Atualizar":
				if(!nome.isEmpty()){
					Grupo grupo = new Grupo();

					grupo.setNome(nome);
					grupo.setDescricao(descricao);
					grupo.setOid_grupo(oid_grupo);

					Grupo.Update(grupo);
					Helper.AUXGROUP.setValue(1);
					myStage.close();

				}else{
					Alerta.showError("Não é possível criar o grupo", "O grupo precisa de um nome para ser atualizado.");
				}
				break;
		}

	}

	//Metodo que sera usado para orientar na atualização dos dados
	//Esse metodo irá receber um objeto do tipo Grupo e separara seus componentes para os TextField
	//Tambem mudará o nome do botão para Atualizar
	public void PrepararUpdate(Grupo grupo){

		txtNome.setText(grupo.getNome());
		txtDescricao.setText(grupo.getDescricao());
		OID_GRUPOAUXILIO = grupo.getOid_grupo();

		this.btnCadastrarGrupo.setText("Atualizar");
	}
}
