package br.com.onpecas.helper;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlConnect {


	public static Connection ConectarDb(){

		Connection con = null;

		String ip = Helper.ip;
		try {
			//Direciona para o drive do banco de dados
			Class.forName("com.mysql.jdbc.Driver");
			
			//Define a String de conex�o, que contem o server, o banco que ser� usario, o usuario e a senha
			con = DriverManager
					.getConnection("jdbc:mysql://"+ip+"/dbcsop", "csop", "csoptcc@2016");

		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.getMessage();
		}

		return con;

	}
}
