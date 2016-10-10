package br.com.onpecas.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import br.com.onpecas.helper.MySqlConnect;

public class Relatorio {

	public static List<HashMap<Integer,String>> ListarFaturamento(){
		Connection con = MySqlConnect.ConectarDb();
		List<HashMap<Integer,String>> lstMap = new ArrayList<>();

		String sql ="select *, sum(valortotal)+sum(frete) as ValorFaturado from visualizacaorelatorios group by dtrealizado ;";

		try {
			ResultSet rs = con.createStatement().executeQuery(sql);

			while(rs.next()){
				HashMap<Integer,String> itemHash = new HashMap<>();

				itemHash.put(1, rs.getString("dtrealizado"));
				itemHash.put(2, rs.getString("ValorFaturado"));

				lstMap.add(itemHash);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lstMap;
	}
}
