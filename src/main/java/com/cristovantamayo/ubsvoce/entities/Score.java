package com.cristovantamayo.ubsvoce.entities;
/**
 * Entidade JPA Score
 * Represensa uma UBS
 * 		<Long> 		id
 * 		<Integer> 	name
 * 		<Integer> 	city
 * 		<Integer> 	neighborhod
 * 		<Integer> 	telefone
 * 		<Unidade> 	unidade {@code @OneToOne} {@see Unidade}
*/
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.cristovantamayo.ubsvoce.entities.enums.ScoreType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity // Especifica que a classe é uma entidade
@Table(name = "unidade_score") // Espeficica do nome da tabela da entidade
public class Score implements Serializable {
	
	private static final long serialVersionUID = 1694198991698647019L;
	
	// Espeficica a Chave Primaria
	@Id
	private Long id;
	@JsonProperty(value="size") // Alias no JSON
	private Integer score_size;
	@JsonProperty(value="adaptation_for_senior")
	private Integer adaptation_for_senior;
	@JsonProperty(value="medical_equipment")
	private Integer medical_equipment;
	@JsonProperty(value="medicine")
	private Integer medicine;
	
	
	@JsonBackReference // Evita que haja uma conversão recursiva das informações da Unidade na saida da API
	@MapsId // Vincula o Id da entidade ao ID da unidade
	@OneToOne // Estabelece o lado do relacionamento 'um para um' da entidade com a unidade
	@JoinColumn(name = "unidade_id") // Espeficica o nome da chave estrageira
	private Unidade unidade;
	
	public Score() {}
	
	public Score(ScoreType score_size, ScoreType adaptation_for_senior, ScoreType medical_equipment, ScoreType medicine) {
		this.score_size = score_size.getValue();
		this.adaptation_for_senior = adaptation_for_senior.getValue();
		this.medical_equipment = medical_equipment.getValue();
		this.medicine = medicine.getValue();
	}
	
	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}
	
	public int getScore_size() {
		return score_size;
	}

	public void setScore_size(ScoreType score_size) {
		this.score_size = score_size.getValue();
	}

	public int getAdaptation_for_senior() {
		return adaptation_for_senior;
	}

	public void setAdaptation_for_senior(ScoreType adaptation_for_senior) {
		this.adaptation_for_senior = adaptation_for_senior.getValue();
	}

	public int getMedical_equipment() {
		return medical_equipment;
	}

	public void setMedical_equipment(ScoreType medical_equipment) {
		this.medical_equipment = medical_equipment.getValue();
	}

	public int getMedicine() {
		return medicine;
	}

	public void setMedicine(ScoreType medicine) {
		this.medicine = medicine.getValue();
	}
	
}
