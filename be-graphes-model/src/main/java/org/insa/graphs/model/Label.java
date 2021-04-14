package org.insa.graphs.model;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@SuppressWarnings("rawtypes")
final public class Label implements Comparable {
	
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
	public static ArrayList<Label>Table_Label=new ArrayList<Label>();
	
	public Label(Node n, boolean father) 
	{
		if (father) 
		{
			this.Cost=0;
		}
		else 
		{
			this.Cost=Float.POSITIVE_INFINITY;
		}
		this.marque=father;
		this.Pere=null;
		this.Sommet_courant=n;
	}
	
	public static Label getLabel(Node n) throws NoSuchElementException
	{
		Label l = null;
		for (int i = 0; i<Label.Table_Label.size(); i++) 
		{
			if (Label.Table_Label.get(i).Sommet_courant == n) 
			{
				l = Label.Table_Label.get(i);
				break;
			}
		}
		if (l==null) 
		{
			throw new NoSuchElementException();
		}
		return l;
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

	@Override
	public int compareTo (Label l) {
		
		return Float.compare(this.getCost(), l.getCost());
	}



}
