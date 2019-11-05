package com.cristovantamayo.ubsvoce.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import com.cristovantamayo.ubsvoce.entities.enums.ScoreType;
import com.cristovantamayo.ubsvoce.repositories.UnidadeRepository;

public class UnidadeTest {
	@Autowired
	UnidadeRepository repo;
	
	
	public UnidadeTest() {
		// TODO Auto-generated constructor stub
	}
     
    @Tag("DEV")
    @Test
    public void deveRetornarValorDaInstanciaCompleta() 
    {
	     Geocode geocode = new Geocode(-23.9,  46.8);
	     Score score = new Score(ScoreType.MEDIO_OU_ABAIXO, ScoreType.ACIMA, ScoreType.MEDIO_OU_ABAIXO, ScoreType.MEDIO_OU_ABAIXO);
	     Unidade unidade = new Unidade("Unidade Principal", "Rua da Gruta, 1020", "Maimoré", "554167768000", geocode, score);
	     geocode.setUnidade(unidade);
	     score.setUnidade(unidade);
	        
	     assertEquals(46.8, unidade.getGeocode().getGeocode_long());
     
    }
    
    @Tag("DEV")
    @Test
    public void deveRetornarValorCorretoDeScoreTypeEnum() {
    	assertEquals(1, ScoreType.MEDIO_OU_ABAIXO);
    	assertEquals(2, ScoreType.ACIMA);
    	assertEquals(3, ScoreType.MUITO_ACIMA);
    }
    
    
     
    @Tag("PROD")
    @Disabled
    @Test
    public void testCalcTwo() 
    {
        //System.out.println("======TEST TWO EXECUTED=======");
    	assertEquals(1, 1);
    }     
}
