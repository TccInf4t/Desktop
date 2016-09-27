package br.com.onpecas.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.onpecas.helper.Alerta;
import br.com.onpecas.helper.Helper;
import br.com.onpecas.model.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

//classe de "controller" para usuários
public class UserController implements Initializable{

	@FXML TextField txtNomeCompleto, txtLogin, txtEmail;

	@FXML ComboBox<Grupo> cboGrupo;

	@FXML PasswordField txtSenha;

	@FXML Button btnCadastrar, btnCancelar;

	Stage myStage;
	Usuario usuario;

	static int OID_USUARIOAUXILIO;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		OID_USUARIOAUXILIO = 0;
		AtribuirBotoes();

		cboGrupo.getItems().addAll(Grupo.Select());

		//Foi criado um objeto do tipo grupo, caso ele seja nulo,
		//quer dizer que a instancia foi feita pelo primeiro modo
		if(usuario != null){
			cboGrupo.getSelectionModel().select(usuario.getGrupo().getOid_grupo());

			PrepararUpdate(usuario);
		}
	}

	public UserController(Stage myStage) {
		this.myStage = myStage;
	}

	public UserController(Stage myStage, Usuario usuario) {
		this.myStage = myStage;
		this.usuario = usuario;
	}

	/* Método que serve para atribuir os botões às suas respectivas funções*/
	public void AtribuirBotoes(){
		btnCadastrar.setOnAction(l-> InserirAtualizarGrupo());
		btnCancelar.setOnAction(l-> myStage.close());
	}

	/* Método utilizado tanto para inserir quanto para atualizar um usuario
	 * Esse método funciona da seguinte forma:
	 * Quando o UserController é chamado, mas na sua instancia é passada somente o Stage, quer dizer que esse método irá inserir um registro
	 * Se na instancia é passada, além do Stage, um objeto do tipo User, quer dizer que esse método irá atualizar um registro
	 * PS: O nome do botão definirá como o método irá agir
	 * */

	public void InserirAtualizarGrupo(){
		String nomeCompleto = txtNomeCompleto.getText();
		String login = txtLogin.getText();
		String email = txtEmail.getText();
		String senha = txtSenha.getText();
		Grupo grupo = cboGrupo.getSelectionModel().getSelectedItem();

		int oid_usuario = OID_USUARIOAUXILIO;

		switch (btnCadastrar.getText()){
			case "Cadastrar":
				if(!login.isEmpty() && !senha.isEmpty()){
					if(grupo != null){
						Usuario usuario = new Usuario();

						usuario.setNomeCompleto(nomeCompleto);
						usuario.setEmail(email);
						usuario.setLogin(login);
						usuario.setSenha(senha);
						usuario.setGrupo(grupo);

						Usuario.Insert(usuario);
						Helper.AUXUSER.setValue(1);
						myStage.close();
					}else{
						Alerta.showError("Não é possível cadastrar", "O usuario precisa de um grupo.");
					}

				}else{
					Alerta.showError("Não é possível cadastrar", "O usuario precisa de pelo menos login e senha.");
				}
			break;

			case "Atualizar":
				if(!login.isEmpty() && !senha.isEmpty()){
					if(grupo != null){
						Usuario usuario = new Usuario();

						usuario.setNomeCompleto(nomeCompleto);
						usuario.setEmail(email);
						usuario.setLogin(login);
						usuario.setSenha(senha);
						usuario.setGrupo(grupo);
						usuario.setOid_usuario(oid_usuario);

						Usuario.Update(usuario);
						Helper.AUXUSER.setValue(1);
						myStage.close();
					}else{
						Alerta.showError("Não é possível atualizar", "O usuario precisa de um grupo.");
					}

				}else{
					Alerta.showError("Não é possível atualizar", "O usuario precisa de pelo menos login e senha.");
				}
			break;
		}
	}

	/* Esse método funciona como um passo antes da atualização
	 * Ele pega o objeto que será atualizado, preenche as caixas de texto com os seus dados e muda o nome do botão para Atualizar*/
	public void PrepararUpdate(Usuario usuario){
		txtNomeCompleto.setText(usuario.getNomeCompleto());
		txtLogin.setText(usuario.getLogin());
		txtEmail.setText(usuario.getEmail());
		txtSenha.setText(usuario.getSenha());

		OID_USUARIOAUXILIO = usuario.getOid_usuario();

		this.btnCadastrar.setText("Atualizar");
	}
}
