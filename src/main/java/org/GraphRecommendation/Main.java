package org.GraphRecommendation;


import java.util.List;
import java.util.logging.Logger;

class Main {
    public static void main(String[] args) throws Exception {
    final Logger LOGGER = Logger.getLogger(Main.class.getName());
            List<String[]> movies = DataLoader.loadData("src/main/resources/Data/movies.csv");
            List<String[]> ratings = DataLoader.loadData("src/main/resources/Data/ratings.csv");
            MovieGraph movieGraph = new MovieGraph();
            int ratingId = 1;
            String movieName;
            for(String[] rating : ratings){
                String userId = rating[0];
                try {
                    movieName = movies.get(ratingId-1)[1];
                } catch (Exception ex){
                    LOGGER.info("Попытка достать элемент, которого нет, номер элемента:" + String.valueOf(ratingId-1) + "______ Ошибка:" + ex.getMessage() );
                    continue;
                }
                double ratingValue = Double.parseDouble(rating[2]);
//                LOGGER.warning(rating[2]);
                movieGraph.addUser("User " +userId);
                movieGraph.addMovie(movieName);
                movieGraph.addRating("User " + userId, movieName, ratingValue);
                ratingId++;
            }
            GraphVisualization visualization = new GraphVisualization(movieGraph.getGraph());
            visualization.setVisible(true);

            Recommender recommender = new Recommender(movieGraph.getGraph());
            List<String> recommendations = recommender.recommendMovies("User 6");
            System.out.println("Recommendations for user 6:"  + recommendations);





    }
}