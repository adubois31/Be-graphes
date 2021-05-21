package org.insa.graphs.algorithm.shortestpath;

public class TestAStarAlgorithm extends TestDijkstraAlgorithm{
	
	
	public static ShortestPathSolution Solution_generation (ShortestPathData Data){
		AStarAlgorithm AStar = new AStarAlgorithm(Data);
		ShortestPathSolution Solution = AStar.doRun();
		return Solution;
		
	}
}