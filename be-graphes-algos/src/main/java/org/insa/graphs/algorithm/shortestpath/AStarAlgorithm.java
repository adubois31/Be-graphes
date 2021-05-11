package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Label;
import org.insa.graphs.model.LabelStar;
import org.insa.graphs.model.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {

	@Override
	protected void insert (BinaryHeap<Label> BH, Node n, boolean marked, float Cout, Arc Father, float estimated_cost) {
		LabelStar lab = new LabelStar(n,marked,Cout,null,estimated_cost);
		BH.insert(lab);
		Label.Table_Label[n.getId()]=lab;
	}
	
    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }

}
