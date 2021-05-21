package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Label;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

import java.util.ArrayList;
import java.util.Collections;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    protected void insert(BinaryHeap<Label> BH, Node n, boolean Marked, float cost, Arc father, ShortestPathData data) {
    	Label l;
		l = new Label(n, Marked, cost, father);
		BH.insert(l);
		Label.Table_Label[n.getId()] = l;
		notifyNodeReached(l.getSommet_courant());
    }
    
    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        
        // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());
        
        Graph graph = data.getGraph();
        BinaryHeap<Label> BH = new BinaryHeap<Label>();
        final int nbNodes = graph.size();
        int Nb_Marked_Nodes = 0;
        
        // initialization
        Label.Table_Label = new Label[graph.getNodes().size()];
        Node n = data.getOrigin();
        insert(BH, n, true, (float)0.0, null,data);
        
        //iterations
        while (Nb_Marked_Nodes != nbNodes && !BH.isEmpty()){
        	Label L_Origin = BH.findMin();
        	BH.deleteMin();
        	L_Origin.setMarque(true);
        	Nb_Marked_Nodes++;
        	notifyNodeMarked(L_Origin.getSommet_courant());
        	if (L_Origin.getSommet_courant() == data.getDestination()) {
        		break;
        	}
        	for (Arc a : L_Origin.getSommet_courant().getSuccessors()) {
        		// Small test to check allowed roads...
                if (!data.isAllowed(a)) {
                    continue;
                }
                
        		Node N_Destination = a.getDestination();
				if (Label.Table_Label[N_Destination.getId()] == null) {
					insert(BH, N_Destination, false, (float) (L_Origin.getCost() + data.getCost(a)), a, data);
				}
				else {
					if (Label.Table_Label[N_Destination.getId()].isMarque() == false) {
	        			if (Label.Table_Label[N_Destination.getId()].getCost() > (L_Origin.getCost() + data.getCost(a))) {
	        				BH.remove(Label.Table_Label[N_Destination.getId()]);        					
	        				Label.Table_Label[N_Destination.getId()].setCost((float) (L_Origin.getCost() + data.getCost(a)));
	        				Label.Table_Label[N_Destination.getId()].setPere(a);
	        				BH.insert(Label.Table_Label[N_Destination.getId()]);
	        			}
	        		}
				}
        		
        	}
        }
        
        //Destination cost is infinite, the solution is infeasible...
        if (Label.Table_Label[data.getDestination().getId()] == null || !Label.Table_Label[data.getDestination().getId()].isMarque()) {
        	solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else {
        	// The destination has been found, notify the observers.
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
        	if (arcs.size() == 0) {
        		solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, data.getOrigin()));
        	}
        	else {
        		solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        	}
        }
        
        return solution;
    }

}
