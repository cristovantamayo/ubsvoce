package com.cristovantamayo.ubsvoce.services.util;

/**
 * A classes tem por objetivo dar suporte para o retorno lista menores a partir de uma lista maior.
 * Classe Auxiliar da paginação utilizada na classe de serviço {@see GeocodingService} no método {@see retrieveNearUbs}
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
     * Construtor da pagina que estabelece a porção atual da lista
     * @param <List<T>> lista
     * @param <int> pedacoSize
     */
    public Paginador(List<T> lista, int subListaSize) {
        this.lista = new ArrayList<>(lista);
        this.subListaSize = subListaSize;
    }
    
    /**
     * Chamada estática instanciando a pagina da vez 
     * @param lista 
     * @param pedacoSize
     * @return
     */
    public static <T> Paginador<T> ofSize(List<T> lista, int subListaSize) {
        return new Paginador<>(lista, subListaSize);
    }
    
    /**
     * Retorna a sublista segundo o índice das sublistas
     * @param <int> index
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
     * Retorna o numero total de sublistas do array de listas
     */
    @Override
    public int size() {
        return (int) Math.ceil((double) lista.size() / (double) subListaSize);
    }
}