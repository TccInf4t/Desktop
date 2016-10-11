package br.com.onpecas.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import br.com.onpecas.helper.MySqlConnect;

public class Relatorio {

	public static List<HashMap<Integer,String>> ListarFaturamento(String periodo){
		Connection con = MySqlConnect.ConectarDb();
		List<HashMap<Integer,String>> lstMap = new ArrayList<>();

		if(periodo.equals("Anual")){
			String sql ="select *, extract(year from dtrealizado) as datareferencia, sum(valortotal)+sum(frete) as ValorFaturado from visualizacaorelatorios group by extract(year from dtrealizado) ;";

			try {
				ResultSet rs = con.createStatement().executeQuery(sql);

				while(rs.next()){
					HashMap<Integer,String> itemHash = new HashMap<>();

					itemHash.put(1, rs.getString("datareferencia"));
					itemHash.put(2, rs.getString("ValorFaturado"));

					lstMap.add(itemHash);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else if(periodo.equals("Mensal")){
			String sql ="select *,concat(extract(month from dtrealizado),' - ', extract(year from dtrealizado)) as datareferencia, sum(valortotal)+sum(frete) as ValorFaturado from visualizacaorelatorios group by extract(year from dtrealizado), extract(month from dtrealizado) ;";

			try {
				ResultSet rs = con.createStatement().executeQuery(sql);

				while(rs.next()){
					HashMap<Integer,String> itemHash = new HashMap<>();

					itemHash.put(1, rs.getString("datareferencia"));
					itemHash.put(2, rs.getString("ValorFaturado"));

					lstMap.add(itemHash);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}else if(periodo.equals("Semanal")){
			String sql = "select *,"+
					"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Sunday'), '%X%V %W'), '%d/%m/%y') as semanainicial, "+
					"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Saturday'), '%X%V %W'), '%d/%m/%y') as semanafinal, "+
					"sum(valortotal)+sum(frete) as ValorFaturado "+
					"from visualizacaorelatorios group by yearweek(dtrealizado) ;";
			try {
				ResultSet rs = con.createStatement().executeQuery(sql);

				while(rs.next()){
					HashMap<Integer,String> itemHash = new HashMap<>();
					String datareferencia = String.format("De %s at� %s", rs.getString("semanainicial"), rs.getString("semanafinal"));

					itemHash.put(1, datareferencia);
					itemHash.put(2, rs.getString("ValorFaturado"));

					lstMap.add(itemHash);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return lstMap;
	}
}