package org.insa.graphs.model;

public class LabelStar extends Label{
	
	private final float estimated_cost;

	public LabelStar(Node n, boolean marked, float Cout, Arc Father,float estimated_cost) {
		super(n, marked, Cout, Father);
		this.estimated_cost = estimated_cost;
	}
	
	public float getTotalCost(){
		return this.getCost()+this.estimated_cost;
	}
	

	
}