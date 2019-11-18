package com.cristovantamayo.ubsvoce.services.util;

/**
 * A classes tem por objetivo dar suporte para o retorno lista menores a partir de uma lista maior.
 * Classe Auxiliar da paginacao utilizada na classe de servico {@see GeocodingService} no metodo {@see retrieveNearUbs}
 * Abordagem de Lista de Listas com {@code AbstractLis<T>} parametrizada com {@code List<T>}
 */

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public final class Paginador<T> extends AbstractList<List<T>> {

	// lista generica
    private final List<T> lista;
    // numero de elemento do
    private final int subListaSize;

    /**
     * Construtor da pagina que estabelece a porcao atual da lista
     * @param lista lista de tipo generico
     * @param subListaSize numero de item de uma paginacao
     */
    private Paginador(List<T> lista, int subListaSize) {
        this.lista = new ArrayList<>(lista);
        this.subListaSize = subListaSize;
    }
    
    /**
     * retorna instancia a pagina @type{List} da vez 
     * @param lista lista de tipo generico
     * @param subListaSize numero de item de uma paginacao
     * @param <T> tipo do item da ser paginado
     * @return paginador instancia de paginacao
     */
    public static <T> Paginador<T> ofSize(List<T> lista, int subListaSize) {
        return new Paginador<>(lista, subListaSize);
    }
    
    /**
     * Retorna a sublista segundo o indice da paginacao
     * @param indice  indice numero @type{Integer} do array de paginas
     * @return sublista retorna sublista reference ao indice do array de paginas
     */
    @Override
    public List<T> get(int indice) {
        int inicio = indice * subListaSize;
        int fim = Math.min(inicio + subListaSize, lista.size());

        if (inicio > fim) {
            throw new IndexOutOfBoundsException("O índice" + indice + " está fora do intervalo da lista <0," + (size() - 1) + ">");
        }

        return new ArrayList<>(lista.subList(inicio, fim));
    }
    /**
     * Retorna o numero total @{Integer} de sublistas do array de listas
     */
    @Override
    public int size() {
        return (int) Math.ceil((double) lista.size() / (double) subListaSize);
    }
}