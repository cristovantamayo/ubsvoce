package com.cristovantamayo.ubsvoce.entities.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumeração de contém as frases de avaliação padrão dos estabelecimentos de saúde.
 * @author Cristovan
 *
 */

public enum ScoreType {
	
	MEDIO_OU_ABAIXO("Desempenho mediano ou  um pouco abaixo da média", 1),
	ACIMA("Desempenho acima da média", 2),
	MUITO_ACIMA("Desempenho muito acima da média", 3);
	
	private String descricao;
	private int value;
	
	private ScoreType(String descricao, int value) {
		this.descricao = descricao;
		this.value=value;
	}
	
	public String getString() {
		return descricao;
	}
	public int getValue() {
		return value;
	}
	 
	// Tática de busca reversa em Enums
    // Tabela Lookup, armazena um mapa <descricao, ítem> do Enum
    private static final Map<String, ScoreType> lookup = new HashMap<>();
    // Tabela LookupI armazena um mapa <valor, ítem> do Enum 
    private static final Map<Integer, ScoreType> lookupI = new HashMap<>();
  
    //Popula a tabela lookup em Tempo de Execução
    static
    {
        for(ScoreType type : ScoreType.values())
        {
            lookup.put(type.getString(), type);
            lookupI.put(type.getValue(), type);
        }
    }
    
    /**
     * Retorna o Item do Tipo Enumerado pelo atributo descricao @type{String}
     * @param texto descricao forcecida pelo arquivo CSV nos campos de classificacao
     * @return scoreType @type{ScoreType} Tipo Enumerado
     */
    public static ScoreType retrieve(String texto) {
    	return lookup.get(texto);
    }
    
    /**
     * Retorna o Item do Tipo Enumerado pelo atributo valor @type{Integer}
     * @param value indice numerico referente a posicao do Enum
     * @return scoreType @type{ScoreType} Tipo Enumerado
     */
    public static ScoreType retrieveByValue(Integer value) {
    	return lookupI.get(value);
    }
}
