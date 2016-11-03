package br.com.onpecas.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import br.com.onpecas.helper.MySqlConnect;

public class Relatorio {

	private String tituloData, valorQuantidade;

	public String getTituloData() {return tituloData;}
	public void setTituloData(String tituloData) {this.tituloData = tituloData;}
	public String getValorQuantidade() {return valorQuantidade;}
	public void setValorQuantidade(String valorQuantidade) {this.valorQuantidade = valorQuantidade;}

	//Classe criada para realizar as buscas no banco de dados, filtrando de acordo com o que foi selecionado no controller
	public static List<Relatorio> FiltrarPedido(){
		Connection con = MySqlConnect.ConectarDb();
		List<Relatorio> lstRelatorio = new ArrayList<Relatorio>();

		String sql = "select p.*, s.*, count(*) as qtdpedido from pedido as p inner join status as s "
				+ "on (p.oid_status = s.oid_status) group by s.oid_status;";
		try {
			ResultSet rs = con.createStatement().executeQuery(sql);

			while(rs.next()){
				Relatorio relatorio = new Relatorio();

				relatorio.setTituloData(rs.getString("nome"));
				relatorio.setValorQuantidade(rs.getString("qtdpedido"));

				lstRelatorio.add(relatorio);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lstRelatorio;
	}

	public static List<Relatorio> FiltarFaturamento(String periodo, boolean temData, String dataInicial, String dataFinal){
		Connection con = MySqlConnect.ConectarDb();
		List<Relatorio> lstRelatorio = new ArrayList<>();
		String sql = "";

		if(temData){
			if(periodo.equals("Semanal")){
				sql = "select *,"
						+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Sunday'), '%X%V %W'), '%d/%m/%y') as semanainicial, "
						+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Saturday'), '%X%V %W'), '%d/%m/%y') as semanafinal, "
						+"concat('De: ', date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Sunday'), '%X%V %W'), '%d/%m/%y'), ' - At�: ',"
						+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Saturday'), '%X%V %W'), '%d/%m/%y')) as datareferencia,"
						+"sum(valortotal)+sum(frete) as valorfaturado "
						+"from visualizacaorelatorios "
						+ "where dtrealizado between '"+dataInicial+"' and '"+dataFinal+"' "
						+ "group by yearweek(dtrealizado) ;";

			}else if(periodo.equals("Mensal")){
				sql ="select *,concat(extract(month from dtrealizado),' - ', extract(year from dtrealizado)) as datareferencia, "
						+ "sum(valortotal)+sum(frete) as valorfaturado from visualizacaorelatorios "
						+ "where dtrealizado between '"+dataInicial+"' and '"+dataFinal+"' "
						+ "group by extract(year from dtrealizado), "
						+ "extract(month from dtrealizado) ;";

			}else if(periodo.equals("Anual")){
				sql ="select *, extract(year from dtrealizado) as datareferencia, sum(valortotal)+sum(frete) as valorfaturado "
						+ "from visualizacaorelatorios "
						+ "where dtrealizado between '"+dataInicial+"' and '"+dataFinal+"' "
						+ "group by extract(year from dtrealizado) ;";

			}else if(periodo.equals("Diario")){
				sql = "select *, dtrealizado as datareferencia, "
						+"sum(valortotal)+sum(frete) as valorfaturado "
						+"from visualizacaorelatorios "
						+ "where dtrealizado between '"+dataInicial+"' and '"+dataFinal+"' "
						+ "group by dtrealizado ;";

			}
		}else{
			if(periodo.equals("Semanal")){
				sql = "select *,"
						+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Sunday'), '%X%V %W'), '%d/%m/%y') as semanainicial, "
						+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Saturday'), '%X%V %W'), '%d/%m/%y') as semanafinal, "
						+"concat('De: ', date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Sunday'), '%X%V %W'), '%d/%m/%y'), ' - At�: ',"
						+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Saturday'), '%X%V %W'), '%d/%m/%y')) as datareferencia,"
						+"sum(valortotal)+sum(frete) as valorfaturado "+
						"from visualizacaorelatorios group by yearweek(dtrealizado) ;";

			}else if(periodo.equals("Mensal")){
				sql ="select *,concat(extract(month from dtrealizado),' - ', extract(year from dtrealizado)) as datareferencia, "
						+ "sum(valortotal)+sum(frete) as valorfaturado from visualizacaorelatorios group by extract(year from dtrealizado), "
						+ "extract(month from dtrealizado) ;";

			}else if(periodo.equals("Anual")){
				sql ="select *, extract(year from dtrealizado) as datareferencia, sum(valortotal)+sum(frete) as valorfaturado "
						+ "from visualizacaorelatorios group by extract(year from dtrealizado) ;";

			}else if(periodo.equals("Diario")){
				sql = "select *, dtrealizado as datareferencia, "
						+"sum(valortotal)+sum(frete) as valorfaturado "
						+"from visualizacaorelatorios group by dtrealizado ;";

			}
		}

		try {
			ResultSet rs = con.createStatement().executeQuery(sql);

			while(rs.next()){
				Relatorio relatorio = new Relatorio();

				relatorio.setTituloData(rs.getString("datareferencia"));
				relatorio.setValorQuantidade(rs.getString("ValorFaturado"));

				System.out.println(relatorio.getTituloData()+"--"+relatorio.getValorQuantidade());
				lstRelatorio.add(relatorio);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lstRelatorio;
	}
}
