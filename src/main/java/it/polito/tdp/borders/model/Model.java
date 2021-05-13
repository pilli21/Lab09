package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	BordersDAO dao;
	Map<Integer,Country> idMapCountry;
	Graph<Country,DefaultEdge> grafo;
	Map<Country,Country> predecessore;
	

	public Model() {
		dao=new BordersDAO();
	}
	
	public void createGraph(int x) {
		idMapCountry=new HashMap<Integer,Country>();
		dao.loadAllCountries(idMapCountry,x);
		this.grafo = new SimpleGraph<>(DefaultEdge.class);
		
		Graphs.addAllVertices(this.grafo,idMapCountry.values());
		
		for(Border b:dao.getCountryPairs(x)) {
			this.grafo.addEdge(idMapCountry.get(b.getStato1()),idMapCountry.get(b.getStato2()));
		}
	}
	
	public int getNVertici() {
		if(grafo != null)
			return grafo.vertexSet().size();
		
		return 0;
	}
	
	public int getNArchi() {
		if(grafo != null)
			return grafo.edgeSet().size();
		
		return 0;
	}
	
	public Set<Country> getCountries(){
		return grafo.vertexSet();
	}

	public int getNumberOfConnectedComponents(){
		ConnectivityInspector<Country, DefaultEdge> ci = 
				new ConnectivityInspector<Country, DefaultEdge>(this.grafo) ;

		return ci.connectedSets().size();
	}
	
	public int getNConfini(Country c) {
		return this.grafo.degreeOf(c);
	}
	
	public List<Country> statiRaggiungibili(Country nazione) {
		BreadthFirstIterator<Country, DefaultEdge> bfv = 
				new BreadthFirstIterator<Country, DefaultEdge>(this.grafo, nazione) ;
		
		this.predecessore=new HashMap<>();
		this.predecessore.put(nazione, null);
		
		bfv.addTraversalListener(new TraversalListener<Country, DefaultEdge>() {
			
			@Override
			public void vertexTraversed(VertexTraversalEvent<Country> e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void vertexFinished(VertexTraversalEvent<Country> e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> e) {
				DefaultEdge arco = e.getEdge() ;
				Country a = grafo.getEdgeSource(arco);
				Country b = grafo.getEdgeTarget(arco);
				// ho scoperto 'a' arrivando da 'b' (se 'b' lo conoscevo) b->a
				if(predecessore.containsKey(b) && !predecessore.containsKey(a)) {
					predecessore.put(a, b) ;
//					System.out.println(a + " scoperto da "+ b) ;
				} else if(predecessore.containsKey(a) && !predecessore.containsKey(b)) {
					// di sicuro conoscevo 'a' e quindi ho scoperto 'b'
					predecessore.put(b, a) ;
//					System.out.println(b + " scoperto da "+ a) ;
				}
				
			}
			
			@Override
			public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		List<Country> result = new LinkedList<>() ;
		
		while(bfv.hasNext()) {
			Country c = bfv.next() ;
			result.add(c) ;
		}
		
		return result ;
		
	}
	
}
