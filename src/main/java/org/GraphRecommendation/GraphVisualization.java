package org.GraphRecommendation;

import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class GraphVisualization extends JFrame{

    public GraphVisualization (Graph<String, DefaultWeightedEdge> graph){
        mxGraph mxGraph = new mxGraph();
        Object parent = mxGraph.getDefaultParent();

        mxGraph.getModel().beginUpdate();


        try {
            Map<String, mxICell> vertexToCellMap = new HashMap<>();
            int rows = 1;
            int strings = 1;
            for (String vertex : graph.vertexSet()) {
                mxICell cell = (mxICell) mxGraph.insertVertex(parent, null, vertex, rows*120, strings*120, 80, 30, "layer=2");
                vertexToCellMap.put(vertex, cell);
                rows += 1;
                if (rows == 100){
                    strings++;
                    rows = 1;
                }
            }
            for (DefaultWeightedEdge edge : graph.edgeSet()){
                String source = graph.getEdgeSource(edge);
                String target = graph.getEdgeTarget(edge);
                Double weight = graph.getEdgeWeight(edge);
                mxICell sourceCell = vertexToCellMap.get(source);
                mxICell targetCell = vertexToCellMap.get(target);
                mxGraph.insertEdge(parent, null, weight, sourceCell, targetCell,"layer=0,opacity=50");
                //mxGraph.setCellStyles(mxGraph.STYLE_ABSOLUTE_Z_INDEX, "1", new Object[]{user1, user2, movie1, movie2});
            }
        } catch (Exception ex){
            System.out.println("Can't make Graph" + ex.getMessage());
        } finally {
            mxGraph.getModel().endUpdate();
        }

        mxGraphComponent graphComponent = new mxGraphComponent(mxGraph);
        getContentPane().add(graphComponent);

        // Настройки окна
        setTitle("Movie Recommendation Graph");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Центрируем окно
    }

    public static void showGraph(Graph<String, DefaultWeightedEdge> graph){
        SwingUtilities.invokeLater(()->{
            GraphVisualization frame = new GraphVisualization(graph);
            frame.setVisible(true);
        });
    }
}
