package org.GraphRecommendation;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class MovieGraph {
    private Graph<String, DefaultWeightedEdge> graph;
    //При инициализации объекта создаётся SimpleWeightedGraph - взвешенный неориентированный граф
    public MovieGraph() {
        graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
    }
    //Добавляем вершину - пользователя
    public void addUser(String userId) {
        graph.addVertex(userId);
    }
    //Добавляем вершину - фильм
    public void addMovie(String movieId) {
        graph.addVertex(movieId);
    }
    //Добавляем ребро - между фильмом и пользователем
    public void addRating(String userId, String movieId, double rating) {
        DefaultWeightedEdge edge = graph.addEdge(userId, movieId);
        graph.setEdgeWeight(edge, rating);
    }
    //геттер для графа
    public Graph<String, DefaultWeightedEdge> getGraph() {
        return graph;
    }
}
