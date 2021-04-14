package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;

import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Label;
import org.insa.graphs.algorithm.utils.BinaryHeap;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        Graph graph = data.getGraph();
        final int nbNodes = graph.size();
        //Initialisation tableau de label
        BinaryHeap<Label>BH=new BinaryHeap<Label>;
        for (int i=0; i<nbNodes;i++) 
        {
        	
        }
        ShortestPathSolution solution = null;
        // TODO:
        return solution;
    }
}