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
    	Label lab;
		lab = new Label(n, Marked, cost, father);
		BH.insert(lab);
		Label.Table_Label[n.getId()] = lab;
		notifyNodeReached(lab.getSommet_courant());
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
        
        // initialisation
        Label.Table_Label = new Label[graph.getNodes().size()];
        Node node = data.getOrigin();
        insert(BH, node, true, (float)0.0, null,data);
        
        //iterations
        while (Nb_Marked_Nodes != nbNodes && !BH.isEmpty())
        {
        	Label Lab_origine = BH.findMin(); // recuperation point le plus proche
        	BH.deleteMin();
        	Lab_origine.setMarque(true);
        	Nb_Marked_Nodes++;
        	notifyNodeMarked(Lab_origine.getSommet_courant());
        	if (Lab_origine.getSommet_courant() == data.getDestination()) //sortie boucle si on est arrive à destination
        	{
        		break;
        	}
        	for (Arc successeur : Lab_origine.getSommet_courant().getSuccessors()) 
        	{
        		// Test verification si la route est authorisée.
                if (!data.isAllowed(successeur)) 
                {
                    continue;
                }
                
        		Node N_Destination = successeur.getDestination();
				if (Label.Table_Label[N_Destination.getId()] == null) // si le sommet était à l'infini on lui cree un label
				{
					insert(BH, N_Destination, false, (float) (Lab_origine.getCost() + data.getCost(successeur)), successeur, data);
				}
				else 
				{
					if ((Label.Table_Label[N_Destination.getId()].isMarque() == false)&&(Label.Table_Label[N_Destination.getId()].getCost() > (Lab_origine.getCost() + data.getCost(successeur)))) 
					{//verification que le sommet n'est pas marque et que le cout en passant plutot par ce sommet est plus petit
	        			BH.remove(Label.Table_Label[N_Destination.getId()]); //suppression du label dans le tas binaire       					
	        			Label.Table_Label[N_Destination.getId()].setCost((float) (Lab_origine.getCost() + data.getCost(successeur)));
	        			Label.Table_Label[N_Destination.getId()].setPere(successeur);
	        			BH.insert(Label.Table_Label[N_Destination.getId()]); // re insertion du nouveau cout associe au node de destination
	        			
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
        	if (arcs.size() == 0) {//cas où la destination et l'origine sont le même sommet
        		solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, data.getOrigin()));
        	}
        	else {
        		solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        	}
        }
        
        return solution;
    }

}
