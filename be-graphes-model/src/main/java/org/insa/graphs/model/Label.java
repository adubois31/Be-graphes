package org.insa.graphs.model;

public class Label implements Comparable<Label> {
	
	private Node Sommet_courant;
	
	private boolean marque;
	
	private float Cost;
	
	private Arc Pere;
	/**
	 * @return the sommet_courant
	 */
	public Node getSommet_courant() {
		return Sommet_courant;
	}
	public static Label Table_Label[];
	
	public Label(Node n, boolean marked, float Cout, Arc Father) 
	{
		this.Sommet_courant=n;
		this.marque=marked;
		this.Cost=Cout;
		this.Pere=Father;
	}
	

	/**
	 * @param sommet_courant the sommet_courant to set
	 */
	public void setSommet_courant(Node sommet_courant) {
		Sommet_courant = sommet_courant;
	}

	/**
	 * @return the marque
	 */
	public boolean isMarque() {
		return marque;
	}

	/**
	 * @param marque the marque to set
	 */
	public void setMarque(boolean marque) {
		this.marque = marque;
	}

	/**
	 * @return the Cost
	 */
	public float getCost() {
		return Cost;
	}

	/**
	 * @param Cost the Cost to set
	 */
	public void setCost(float Cost) {
		this.Cost = Cost;
	}

	/**
	 * @return the pere
	 */
	public Arc getPere() {
		return Pere;
	}

	/**
	 * @param pere the pere to set
	 */
	public void setPere(Arc pere) {
		Pere = pere;
	}
	
	public float getTotalCost(){
		return Cost;
	}

	@Override
	public int compareTo (Label l) {
		
		return Float.compare(this.getTotalCost(), l.getTotalCost());
	}



}
