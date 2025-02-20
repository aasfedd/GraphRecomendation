package org.GraphRecommendation;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Recommender {
    private Graph<String, DefaultWeightedEdge> graph;

    public Recommender(Graph<String, DefaultWeightedEdge> graph) {
        this.graph = graph;
    }

    public List<String> recommendMovies(String userID) {
        if (!graph.containsVertex(userID)) {
            System.out.println("Пользователь не найден в графе");
            return List.of();
        }

        // Фильмы, которые пользователь уже оценил
        Set<String> watchedMovies = graph.edgesOf(userID).stream()
                .map(edge -> {
                    String source = graph.getEdgeSource(edge);
                    String target = graph.getEdgeTarget(edge);
                    return source.equals(userID) ? target : source;
                })
                .filter(vertex -> !vertex.startsWith("User")) // Убираем других пользователей
                .collect(Collectors.toSet());

        // Пользователи, которые смотрели те же фильмы
        Set<String> similarUsers = new HashSet<>();
        for (String movie : watchedMovies) {
            for (DefaultWeightedEdge edge : graph.edgesOf(movie)) {
                String source = graph.getEdgeSource(edge);
                String target = graph.getEdgeTarget(edge);
                String otherUser = source.equals(movie) ? target : source;
                if (otherUser.startsWith("User") && !otherUser.equals(userID)) {
                    similarUsers.add(otherUser);
                }
            }
        }

        // Фильмы, которые смотрели похожие пользователи, но не смотрел наш пользователь
        Set<String> recommendedMovies = new HashSet<>();
        for (String similarUser : similarUsers) {
            for (DefaultWeightedEdge edge : graph.edgesOf(similarUser)) {
                String source = graph.getEdgeSource(edge);
                String target = graph.getEdgeTarget(edge);
                String movie = source.equals(similarUser) ? target : source;

                if (!watchedMovies.contains(movie) && !movie.startsWith("user_")) {
                    recommendedMovies.add(movie);
                }
            }
        }

        return recommendedMovies.stream().toList();
    }
}