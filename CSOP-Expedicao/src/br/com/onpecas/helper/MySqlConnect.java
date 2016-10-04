package br.com.onpecas.helper;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlConnect {


	public static Connection ConectarDb(){

		Connection con = null;

		try {
			//Direciona para o drive do banco de dados
			Class.forName("com.mysql.jdbc.Driver");

			//Define a String de conexão, que contem o server, o banco que será usario, o usuario e a senha
			con = DriverManager
					.getConnection("jdbc:mysql://10.107.144.52/csoptcc", "root", "bcd127");

		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.getMessage();
		}

		return con;

	}
}
