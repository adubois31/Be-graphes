package org.insa.graphs.algorithm.shortestpath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.junit.BeforeClass;
import org.junit.Test;
import org.insa.graphs.algorithm.AbstractSolution;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;


public class TestDijkstraAlgorithm {
	
	protected static Graph graph, graph_insa, graph_France;
	
	protected static Path oneNodePath;
	
	protected static ShortestPathSolution oneNodeSolution, simplePathSolution, mediumPathSolution, BFmediumSolution, InfeasiblePathSolution, BigPathSolution;
	
	protected static double cost;
	
	public static ShortestPathSolution Solution_generation (ShortestPathData Data){
		DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(Data);
		ShortestPathSolution Solution = Dijkstra.doRun();
		
		
		return Solution;
		
	}
	
	@BeforeClass
    public static void initAll() throws Exception{
		
		//get graph of map carré
		String mapName = "/home/a_dubois/Bureau/3_MIC/BE-graphes/Be-graphes/Maps/carre.mapgr";
		GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
		graph = reader.read();
		//get graph of map insa
		mapName = "/home/a_dubois/Bureau/3_MIC/BE-graphes/Be-graphes/Maps/insa.mapgr";
		reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
		graph_insa = reader.read();

		
		//init OneNodePath
		Node node = graph.getNodes().get(0);
		oneNodePath = new Path(graph, node);
		
		ShortestPathData data = new ShortestPathData(graph, node, node, ArcInspectorFactory.getAllFilters().get(0));
		oneNodeSolution = Solution_generation (data);
		
		//Init simple path
		cost = 0;
		for (int i = 5; i < 9; i++) {
			for (Arc a : graph.getNodes().get(i).getSuccessors()) {
				if (a.getDestination() == graph.getNodes().get(i+1)) {
					cost += a.getLength();
					break;
				}
			}
		}
		
		data = new ShortestPathData(graph, graph.getNodes().get(5), graph.getNodes().get(9), ArcInspectorFactory.getAllFilters().get(0));
		simplePathSolution = Solution_generation (data);
		
		//Init medium path
		data = new ShortestPathData(graph_insa, graph_insa.getNodes().get(805), graph_insa.getNodes().get(70), ArcInspectorFactory.getAllFilters().get(0));
		mediumPathSolution = Solution_generation (data);
		
		BellmanFordAlgorithm BF = new BellmanFordAlgorithm(data);
		BFmediumSolution = BF.doRun();
		
		//Init Infeasible path
		data = new ShortestPathData(graph_insa, graph_insa.getNodes().get(75), graph_insa.getNodes().get(1255), ArcInspectorFactory.getAllFilters().get(0));
		InfeasiblePathSolution = Solution_generation (data);
	}
	
	@Test
	public void TestOneNodePath() {
		assertEquals(0, oneNodePath.getOrigin().compareTo(oneNodeSolution.getPath().getOrigin()));
		assertTrue(Math.abs(oneNodePath.getLength() - oneNodeSolution.getPath().getLength()) < 0.01);
		assertTrue(oneNodeSolution.getPath().isValid());
	}
	
	@Test
	public void TestSimplePath() {
		assertTrue(Math.abs(cost - simplePathSolution.getPath().getLength()) < 0.01);
		assertTrue(simplePathSolution.getPath().isValid());
	}
	
	@Test
	public void TestMediumPath() {
		assertTrue(Math.abs(mediumPathSolution.getPath().getLength() - BFmediumSolution.getPath().getLength()) < 0.01);
		assertTrue(mediumPathSolution.getPath().isValid());
	}
	
	@Test
	public void TestInfeasiblePath() {
		assertEquals(InfeasiblePathSolution.getStatus(), AbstractSolution.Status.INFEASIBLE);
	}
	
}