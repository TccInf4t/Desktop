package br.com.onpecas.helper;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Helper {
	// As variaveis são do tipo SimpleIntegerPropety, para que possa ser adicionado um listener no controller principal,
	//que será usado como verificação para atualizar 'automaticamente' as tables
	public static IntegerProperty AUXGROUP = new SimpleIntegerProperty();
	public static IntegerProperty AUXUSER = new SimpleIntegerProperty();
}
