package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Label;
import org.insa.graphs.model.Node;
import org.insa.graphs.algorithm.utils.BinaryHeap;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        
     // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin()); 
        Graph graph = data.getGraph();
        final int nbNodes = graph.size();
        BinaryHeap<Label> BH = new BinaryHeap<Label>();
        int NbNodeMarque = 0;
        
      //Initialisation tableau de label
        Label.Table_Label = new Label[graph.getNodes().size()];
        for (Node n : graph.getNodes()) 
        {
        	Label l;
        	if (n.equals(data.getOrigin()))
        	{
        		l = new Label(n,true,0,null);
        		BH.insert(l);
        		Label.Table_Label[n.getId()]=l;
        	}
        }
        //Iterations
        
        while (NbNodeMarque!=nbNodes && !BH.isEmpty()) 
        {
        	Label Label_Origine = BH.findMin();
        	BH.deleteMin();
        	Label_Origine.setMarque(true);
        	NbNodeMarque++;
        	for (Arc arc: Label_Origine.getSommet_courant().getSuccessors()) 
        	{
        		Node node_dest = arc.getDestination();
        		if (Label.Table_Label[node_dest.getId()]==null) //création du label (node jamais rencontré auparavant
        		{
        			Label lab = new Label(node_dest,false,(Label_Origine.getCost()+arc.getLength() ),arc);
        			Label.Table_Label[node_dest.getId()]=l;
        			BH.insert(lab);	
        		}
        		else 
        		{
        			if((Label.Table_Label[node_dest.getId()].isMarque()==false)&&(Label.Table_Label[node_dest.getId()].getCost()>(Label_Origine.getCost()+arc.getLength()))) 
        			{
        				
        			}
        		}
        	}
        }
        	
        	
 
        return solution;
    }
}