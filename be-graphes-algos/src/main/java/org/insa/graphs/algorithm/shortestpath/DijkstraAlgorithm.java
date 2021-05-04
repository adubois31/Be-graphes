package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Label;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.algorithm.AbstractSolution.Status;
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
     // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());
        
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
        	if (Label_Origine.getSommet_courant()==data.getDestination()) //si on atteint la destination on arrête les itérations
        	{
        		break;
        	}
        	for (Arc arc: Label_Origine.getSommet_courant().getSuccessors()) 
        	{
        		// Small test to check allowed roads...
                if (!data.isAllowed(arc)) {
                    continue;
                }
                
                notifyNodeReached(arc.getDestination());
                
        		Node node_dest = arc.getDestination();
        		
        		if (Label.Table_Label[node_dest.getId()]==null) //création du label (node jamais rencontré auparavant
        		{
        			Label lab = new Label(node_dest,false,(float)(Label_Origine.getCost()+ data.getCost(arc)),arc);
        			Label.Table_Label[node_dest.getId()]=lab;
        			BH.insert(lab);	
        		}
        		else 
        		{
        			if((Label.Table_Label[node_dest.getId()].isMarque()==false)&&(Label.Table_Label[node_dest.getId()].getCost()>((float) (Label_Origine.getCost()+data.getCost(arc))))) 
        			{
        				BH.remove(Label.Table_Label[node_dest.getId()]);
        				Label.Table_Label[node_dest.getId()].setCost((float) (Label_Origine.getCost()+data.getCost(arc)));
        				Label.Table_Label[node_dest.getId()].setPere(arc);
        				BH.insert(Label.Table_Label[node_dest.getId()]);
        			}
        		}
        	}
        }
        // result interpretation
        // Dest cost is infinite : no possible solution
        if ((Label.Table_Label[data.getDestination().getId()]==null) ||(!Label.Table_Label[data.getDestination().getId()].isMarque())) 
        {
        	solution = new ShortestPathSolution(data,Status.INFEASIBLE);
        }
        else 
        {// Destination found --> notifying the user
        	notifyDestinationReached(data.getDestination());
        	// Create the path from the array of predecessors...
            ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc = Label.Table_Label[data.getDestination().getId()].getPere();
            while (arc != null) {
                arcs.add(arc);
                arc = Label.Table_Label[arc.getOrigin().getId()].getPere();
            }

            // Reverse the path...
            Collections.reverse(arcs);

            // Create the final solution.
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        }

        
        
        	
 
        return solution;
    }
}