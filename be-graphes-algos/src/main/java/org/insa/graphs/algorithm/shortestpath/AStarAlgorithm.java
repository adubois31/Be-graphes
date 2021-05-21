package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractInputData.Mode;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Label;
import org.insa.graphs.model.LabelStar;
import org.insa.graphs.model.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {

	@Override
	protected void insert (BinaryHeap<Label> BH, Node n, boolean marked, float Cout, Arc Father, ShortestPathData data) {
		Label lab;
		float cout_estim;
		if (data.getMode()==Mode.LENGTH) 
		{
			cout_estim=(float) n.getPoint().distanceTo(data.getDestination().getPoint());
		}
		else 
		{
			float max_speed = (float) Math.max(data.getMaximumSpeed(), data.getGraph().getGraphInformation().getMaximumSpeed())  ;
			cout_estim=(float)n.getPoint().distanceTo(data.getDestination().getPoint())/max_speed;
		}
		lab = new LabelStar(n,marked,Cout,Father,cout_estim);
		BH.insert(lab);
		Label.Table_Label[n.getId()]=lab;
		notifyNodeReached(lab.getSommet_courant());
	}
	
    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }

}
