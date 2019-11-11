package com.cristovantamayo.ubsvoce.services;
/**
 * Classe de Serviço reponsavel por tratar a importação e transferência dos dados do arquivo UBS.csv
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cristovantamayo.ubsvoce.entities.Geocode;
import com.cristovantamayo.ubsvoce.entities.Score;
import com.cristovantamayo.ubsvoce.entities.Unidade;
import com.cristovantamayo.ubsvoce.entities.enums.ScoreType;
import com.cristovantamayo.ubsvoce.repositories.UnidadeRepository;

// Setando a Camanda no Spring
@Service
public class UnidadeService {

	// Injetando o Repositorio
	@Autowired
	private UnidadeRepository repository;

	/**
	 * Retorna a Unidade específica
	 * @param id
	 * @return Unidade
	 */
	public Unidade find(Integer id) {
		Long idl = new Long(id);
		// Esta versão do Spring retorna Optional, get() necessário.
		Unidade obj = repository.findById(idl).get();
		return obj;
	}

	/**
	 * Persiste uma Unidade
	 * @param unidade
	 */
	public void add(Unidade unidade) {
		repository.save(unidade);
	}

	/**
	 * Persiste uma lista de Unidade atomicamente
	 * @param lista
	 */
	public void addAll(List<Unidade> lista) {
		repository.saveAll(lista);
	}
	
	
	/**
	 * Ponto de entrada do arquivo CSV após upload
	 * @param file
	 * @return Long <n> linhas
	 */
	public Long addCSVFile(File file) {
		if(file.isFile()) {
			Long n = 0L;
			try {
				
				BufferedReader csvReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

				if(checkCSVHeader(csvReader.readLine())){
				
					List<Unidade> lista = new ArrayList<Unidade>();
					
					// row conterá da linha atual do arquivo em format String
					String row;
					// para cada linha lida
					while ((row = csvReader.readLine()) != null) {
						
						// decompoem a linha em um array de 13 elementos
					    String[] reg = row.split(",");
					    
					    // Instanciando uma unidade
					    Unidade unidade = instatiateUnidade(reg);
					    
					    // Armazena unidade para somente ao final, havendo sucesso, inserir no banco
					    lista.add(unidade);
					    
					    //if(n > 0) break;
					    
					    n++;
					}
					csvReader.close();
					
					// Persistir todo atomicamente
					addAll(lista);
				}
				
				return n;
			}
			catch(IOException e) {
				return -1L;
			}
		}
		return null;
	}
	
	/**
	 * Simple verificação da primeira linha no padrão do arquivo
	 * @param header
	 * @return boolean
	 */
	public boolean checkCSVHeader(String header) {
		return true; //header.equals("vlr_latitude,vlr_longitude,cod_munic,cod_cnes,nom_estab,dsc_endereco,dsc_bairro,dsc_cidade,dsc_telefone,dsc_estrut_fisic_ambiencia,dsc_adap_defic_fisic_idosos,dsc_equipamentos,dsc_medicamentos");
	}

	
	/**
	 * Instancia a Unidade completa com base nos registro de entrada
	 * @param <String[]> registro
	 * @return <Unidade> unidade
	 * 
	 * sequencial conhecida dos registros
	 * 
	 * 0	=> vlr_latitude
	 * 1	=> vlr_longitude
	 * 2	=> cod_munic
	 * 3	=> cod_cnes
	 * 4	=> nom_estab
	 * 5	=> dsc_endereco
	 * 6	=> dsc_bairro
	 * 7	=> dsc_cidade
	 * 8	=> dsc_telefone
	 * 9	=> dsc_estrut_fisic_ambiencia
	 * 10	=> dsc_adap_defic_fisic_idosos
	 * 11	=> dsc_equipamentos
	 * 12	=> dsc_medicamentos
	 * 
	 */
	private Unidade instatiateUnidade(String[] reg) {
		
		
		// Instanciaando Geocode separadamente
		Geocode geocode = new Geocode(
			Double.parseDouble(reg[0]), 
			Double.parseDouble(reg[1])
		);
		
		// Instanciando Score separadamente
		// ScoreType.get() é um metodo personalizado de busca reversa dentro do Enum
		Score 	score = new Score(
			ScoreType.retrieve(reg[9]),
			ScoreType.retrieve(reg[10]),
			ScoreType.retrieve(reg[11]),
			ScoreType.retrieve(reg[12])
		);
		
		Unidade unidade = new Unidade(
				reg[4], 
				reg[5], 
				reg[6],
				reg[7], 
				reg[8],
				geocode,
				score
		);
		
		geocode.setUnidade(unidade);
		score.setUnidade(unidade);
		
		return unidade;
	}

}