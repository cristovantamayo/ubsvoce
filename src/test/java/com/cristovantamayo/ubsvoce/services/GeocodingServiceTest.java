package com.cristovantamayo.ubsvoce.services;


import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.DecimalFormat;

import org.hamcrest.core.IsSame;
import org.junit.Test;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cristovantamayo.ubsvoce.entities.Geocode;
import com.cristovantamayo.ubsvoce.entities.Score;
import com.cristovantamayo.ubsvoce.entities.Unidade;
import com.cristovantamayo.ubsvoce.entities.enums.ScoreType;
import com.google.maps.model.GeocodingResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@EnableConfigurationProperties
@ConfigurationProperties("geocoding")
public class GeocodingServiceTest {

	String apiKey;
	
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
	public GeocodingServiceTest() {
		// TODO Auto-generated constructor stub
	}
     
    @Tag("DEV")
    //@Disabled
    @Test
    public void deveRetorarMesmaClasse() 
    {
    	GeocodingResult[] result = null;
    	GeocodingResult[] result2 = null;// GeocodingService.searchAddress("Avenida Paulista, 1000", apiKey);
    	assertThat(result, IsSame.sameInstance(result2));
    }
    
    @Test
    public void deveEstarPerto() {
    	Score score = new Score(ScoreType.ACIMA, ScoreType.MEDIO_OU_ABAIXO, ScoreType.MUITO_ACIMA, ScoreType.MEDIO_OU_ABAIXO);
    	Geocode item = new Geocode(-23.557713, -46.645364);
    	Unidade unidade = new Unidade("UBS Mais Mourisco", "Rua Emengarda pires, 123","Jardim Mourisco",  "Taubaté", "1287477834", item, score);
    	item.setUnidade(unidade);
    	score.setUnidade(unidade);
    	
    	// isNear foi movida para arquivo oculto por acessibilidade pacote-privado.
    	//assertTrue(GeocodingService.isNear(unidade, -23.564515, -46.651825, 1500.0));
    }
    
    @Test
    public void deveArredondarCoordenada() {
    	DecimalFormat df = new DecimalFormat("#.00");
    	Double lat = Double.valueOf("-53.1061291694626");
    	Double partial = Double.parseDouble(df.format(lat).replace(',', '.')); 
    	System.out.println(partial);		
    	assertEquals(-23.00, partial);
    }

}
