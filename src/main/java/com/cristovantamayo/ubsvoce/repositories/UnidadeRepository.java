package com.cristovantamayo.ubsvoce.repositories;
/**
 *  Repositorio para Unidade implementando JpaRepository<@type{Unidade}>.
 */
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cristovantamayo.ubsvoce.entities.Unidade;

@Repository
public interface UnidadeRepository extends JpaRepository<Unidade, Long> {
	/**
	 *  Consulta que faz uso apenas da porcao inteira das Geocoordenadas.
	 *  Lembrando de para grau terretre corresponde a aproximadamente 111 quilometros e nossas pesquisas sao regionais.
	 *  Economia de 85% no tempo de processamente de uma consulta padrao.
	 *  
	 * @param lat coordenada latitude
	 * @param lng corrdenada longitude
	 * @return unidades lista de Unidade (UBS) retornada
	 */
	@Query("SELECT u FROM Unidade u WHERE ROUND(u.geocode.geocode_lat) = ?1 AND ROUND(u.geocode.geocode_long) = ?2")
	List<Unidade> findPartialByLatAndLng(Double lat, Double lng);
}