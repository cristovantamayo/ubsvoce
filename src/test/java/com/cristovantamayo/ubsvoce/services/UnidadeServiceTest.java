package com.cristovantamayo.ubsvoce.services;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


//@ContextConfiguration(interfaces = UnidadeRepository.class)
@EnableJpaRepositories("com.cristovantamayo.ubsvoce.repositories.UnidadeRepository")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UnidadeServiceTest {
	
	
	@Autowired
	private UnidadeService unidadeService;
	
	@Autowired
	private WebApplicationContext webApplicationContext;

    @Test
    public void deve() throws Exception {
    	
    	String content = "vlr_latitude,vlr_longitude,cod_munic,cod_cnes,nom_estab,dsc_endereco,dsc_bairro,dsc_cidade,dsc_telefone,dsc_estrut_fisic_ambiencia,dsc_adap_defic_fisic_idosos,dsc_equipamentos,dsc_medicamentos\r\n" + 
    			"-10.9112370014188,-37.0620775222768,280030,3492,US OSWALDO DE SOUZA,TV ADALTO BOTELHO,GETULIO VARGAS,Aracaju,7931791326,Desempenho acima da média,Desempenho muito acima da média,Desempenho mediano ou  um pouco abaixo da média,Desempenho acima da média\r\n" + 
    			"-9.48594331741306,-35.8575725555409,270770,6685315,USF ENFERMEIRO PEDRO JACINTO AREA 09,R 15 DE AGOSTO,CENTRO,Rio Largo,Não se aplica,Desempenho mediano ou  um pouco abaixo da média,Desempenho mediano ou  um pouco abaixo da média,Desempenho mediano ou  um pouco abaixo da média,Desempenho mediano ou  um pouco abaixo da média\r\n" + 
    			"-23.896,-53.41,411885,6811299,UNIDADE DE ATENCAO PRIMARIA SAUDE DA FAMILIA,RUA GUILHERME BRUXEL,CENTRO,Perobal,4436251462,Desempenho muito acima da média,Desempenho muito acima da média,Desempenho mediano ou  um pouco abaixo da média,Desempenho muito acima da média\r\n" + 
    			"-16.447874307632,-41.0098600387561,313580,6335616,POSTO DE SAUDE DE BOM JESUS DA ALDEIA,RUA TEOFILO OTONI,ALDEIA,Jequitinhonha,3337411423,Desempenho mediano ou  um pouco abaixo da média,Desempenho acima da média,Desempenho mediano ou  um pouco abaixo da média,Desempenho mediano ou  um pouco abaixo da média";

        MockMultipartFile file = new MockMultipartFile("abs.csv", "abs.csv", "text/plain", content.getBytes());

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("http://localhost:8080/import/uploader")
                        .file(file))
                    .andExpect(status().is(200));
                    //.andExpect(content().string("success"));
    }
    
	@Test
	public void deveChecarPrimeiraLinhaDoArquivoCSV() {

		String header = "vlr_latitude,vlr_longitude,cod_munic,cod_cnes,nom_estab,dsc_endereco,dsc_bairro,dsc_cidade,dsc_telefone,dsc_estrut_fisic_ambiencia,dsc_adap_defic_fisic_idosos,dsc_equipamentos,dsc_medicamentos";
		assertTrue(unidadeService.checkCSVHeader(header));
	}
	
}