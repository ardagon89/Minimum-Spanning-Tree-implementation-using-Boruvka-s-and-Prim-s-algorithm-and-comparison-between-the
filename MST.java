// Starter code for SP9

package sxa190016;

import sxa190016.Graph.Vertex;
import sxa190016.Graph.Edge;
import sxa190016.Graph.GraphAlgorithm;
import sxa190016.Graph.Factory;
import sxa190016.Graph.Timer;

import sxa190016.BinaryHeap.Index;
import sxa190016.BinaryHeap.IndexedHeap;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;
import java.io.FileNotFoundException;
import java.io.File;

/**
 * @author sxa190016
 * @author bsv180000
 * @version 1.0 MST: Short project 9
 * 				Implement Boruvka's Algorithm.
 */
public class MST extends GraphAlgorithm<MST.MSTVertex> {
	/**
	 * Algorithm name
	 */
	String algorithm;
	/**
	 * Total MST weight
	 */
	public long wmst;
	
	/**
	 * List of edges which constitute the mst
	 */
	List<Edge> mst;

	/**
	 * Constructor to initialize member variables
	 * 
	 * @param g		The input Graph
	 */
	MST(Graph g) {
		super(g, new MSTVertex((Vertex) null));
		this.mst = new LinkedList<Edge>();
	}

	/**
	 * @author sxa190016
	 * @author bsv180000
	 * @version 1.0 MST: Short project 9
	 * 				MST vertex contains extra fields like component number, parent, etc.
	 */
	public static class MSTVertex implements Index, Comparable<MSTVertex>, Factory {
		/**
		 * Component number of this vertex
		 */
		int component;
		/**
		 * Whether it is visited or not
		 */
		boolean visited;
		/**
		 * The index of this vertex
		 */
		int index;
		/**
		 * The distance of this vertex from its parent
		 */
		int d;
		/**
		 * The underlying vertex object
		 */
		Vertex vertex;
		/**
		 * Flag to see if this vertex has been seen
		 */
		boolean seen;
		/**
		 * Store the parent of the current node
		 */
		Vertex parent;

		/**
		 * Constructor to initialize member variables
		 * 
		 * @param u		The base vertex u
		 */
		MSTVertex(Vertex u) {
			this.component = 0;
			this.visited = false;
			this.seen = false;
			this.d = Integer.MAX_VALUE;
			this.index = 0;
			this.vertex = u;
			this.parent = null;
		}

		/**
		 * Constructor to initialize member variables
		 * 
		 * @param u		The input MSTVertex
		 */
		MSTVertex(MSTVertex u) {  // for prim2
			this.component = u.component;
			this.d = u.d;
			this.index = u.index;
			this.parent = u.parent;
			this.seen = u.seen;
			this.vertex = u.vertex;
			this.visited = false;
		}

		/**
		 * Method to get an MSTVertex from Graph.Vertex
		 * 
		 * @param u		The input vertex u
		 * @return		The MSTVertex
		 */
		public MSTVertex make(Vertex u) { return new MSTVertex(u); }

		/**
		 * Set the index of the MSTVertex
		 * 
		 * @param index		The index to be set
		 */
		public void putIndex(int index) { 
			this.index = index;
		}

		/**
		 * Get the index of the MSTVertex
		 * 
		 * @return		The index of the MSTVertex
		 */
		public int getIndex() { 
			return this.index; 
		}

		/**
		 * Compare one MSTVertex with another
		 * 
		 * @param other	The other MSTVertex to be compared with
		 * @return		The integer result of comparison
		 */
		public int compareTo(MSTVertex other) {
			if(other==null)
			{
				return 1;
			}
			else
			{
				if(this.d<other.d)
				{
					return -1;
				}
				else if(this.d>other.d)
				{
					return 1;
				}
				else
				{
					return 0;
				}
			}
		}
	}

	/**
	 * Make MST using Kruskal Algorithm
	 * 	
	 * @return		The total weight of the MST
	 */
	public long kruskal() {
		algorithm = "Kruskal";
		Edge[] edgeArray = g.getEdgeArray();
		mst = new LinkedList<>();
		wmst = 0;
		return wmst;
	}

	/**
	 * Find the MST using Boruvka's algorithm
	 * 
	 * @return		The weight of the MST
	 */
	public long boruvka() {
		algorithm = "Boruvka";

		wmst = 0;

		Graph F = new Graph(this.g.size());
		Edge[] E = this.g.getEdgeArray();
		
		//Get the number of components
		int count = this.countAndLabel(F);
		
		//If there are more than 1 components
		while(count>1)
		{
			this.addSafeEdges(E, F, count);
			count = this.countAndLabel(F);
		}

		//Get the weight of the final MST
		wmst = this.getMSTWeight(F);

		return wmst;
	}

	/**
	 * Count the number of components and label them
	 * 
	 * @param F		The MST graph
	 * @return		The number of components
	 */
	public int countAndLabel(Graph F)
	{
		int component = 0;
		for(Vertex v : F.getVertexArray())
		{
			get(v).visited = false;
		}

		//For each vertex
		for(Vertex x : F.getVertexArray())
		{
			//If not visited yet make a new component
			if(!get(x).visited)
			{
				Queue<Vertex> q = new LinkedList<Vertex>();
				get(x).visited = true;
				q.add(x);
				while(!q.isEmpty())
				{
					Vertex u = q.remove();
					get(u).component = component;
					for(Edge e : F.outEdges(u))
					{
						Vertex v = e.otherEnd(u);
						if(!get(v).visited)
						{
							get(v).visited = true;
							q.add(v);
						}
					}
					for(Edge e : F.inEdges(u))
					{
						Vertex v = e.otherEnd(u);
						if(!get(v).visited)
						{
							get(v).visited = true;
							q.add(v);
						}
					}
				}
				component++;
			}
		}    	
		return component;
	}

	/**
	 * Find and add safe edges to F
	 * 
	 * @param E		The array of edges in the original graph g
	 * @param F		The MST graph
	 * @param count	The number of components
	 */
	public void addSafeEdges(Edge[] E, Graph F, int count)
	{
		Edge[] safe= new Edge[count];
		
		//Set the safe edges to null
		for(int i=0; i<count; i++)
		{
			safe[i] = null;
		}
		
		//For each edge find if it is the minimum safe edge
		for(Edge e : E)
		{
			if(e!=null)
			{
				MSTVertex u = get(e.fromVertex());
				MSTVertex v = get(e.toVertex());
				if(u.component!=v.component)
				{
					if(safe[u.component]==null || e.compareTo(safe[u.component])<0)
					{
						safe[u.component] = e;
					}
					if(safe[v.component]==null || e.compareTo(safe[v.component])<0)
					{
						safe[v.component] = e;
					}
				}
			}			
		}
		HashSet<Edge> hs = new HashSet<Edge>();
		
		//Add safe edges to the MST graph F
		for(Edge e : safe)
		{
			if(!hs.contains(e))
			{
				hs.add(e);
				F.addEdge(e.fromVertex(), e.toVertex(), e.getWeight(), F.edgeSize()+1);
			}
		}
	}

	/**
	 * The total weight of the MST graph F and add MST edges to this.mst
	 * 
	 * @param F		The MST graph F
	 * @return		The total weight of the MST
	 */
	public long getMSTWeight(Graph F)
	{
		long totalWeight = 0L;
		for(Edge e : F.getEdgeArray())
		{
			this.mst.add(e);
			totalWeight += e.getWeight();
		}
		return totalWeight;
	}

	/**
	 * Make the MST using Prim's Algorithm
	 * 
	 * @param s		The start vertex
	 * @return		The total weight of the MST
	 */
	public long prim2(Vertex s) {
		algorithm = "indexed heaps";
		mst = new LinkedList<>();
		wmst = 0;
		IndexedHeap<MSTVertex> q = new IndexedHeap<>(g.size());
		
		//Set all vertices to default values
		for(Vertex u : this.g.getVertexArray())
		{
			get(u).seen = false;
			get(u).parent = null;
			get(u).d = Integer.MAX_VALUE;
		}
		
		//Set source values
		get(s).seen = true;
		get(s).d = 0;
		
		//Add all vertices to the Indexed Heap
		for(Vertex u : this.g.getVertexArray())
		{
			q.add(get(u));
		}
		
		//While Heap is not empty
		while(!q.isEmpty())
		{
			//Add minimum distance vertex to the MST 
			MSTVertex u = q.remove();
			u.seen = true;
			this.wmst += u.d;
			
			//For all neighbors
			for(Edge e : this.g.incident(u.vertex))
			{
				Vertex v = e.otherEnd(u.vertex);
				
				//If neighbor is not seen till now and it is at a lesser distance
				if(!get(v).seen && e.getWeight()<get(v).d)
				{
					get(v).d = e.getWeight();
					get(v).parent = u.vertex;
					q.decreaseKey(get(v));
				}
			}
		}
		
		return wmst;
	}

	/**
	 * Make MST using prim's algo
	 * 
	 * @param s		The start vertex s
	 * @return		The total weight of the MST
	 */
	public long prim1(Vertex s) {
		algorithm = "PriorityQueue<Edge>";
		mst = new LinkedList<>();
		wmst = 0;
		PriorityQueue<Edge> q = new PriorityQueue<>();
		return wmst;
	}

	/**
	 * Find the MST using one of the algorithms based on choice
	 * 
	 * @param g			The graph
	 * @param s			The start vertex
	 * @param choice	The choice of algorithm
	 * @return			The class instance
	 */
	public static MST mst(Graph g, Vertex s, int choice) {
		MST m = new MST(g);
		switch(choice) {
		case 0:
			m.boruvka();
			break;
		case 1:
			m.prim1(s);
			break;
		case 2:
			m.prim2(s);
			break;
		case 3:
			m.kruskal();
			break;
		default:

			break;
		}
		return m;
	}

	/**
	 * The main method to test the code
	 * 
	 * @param args		Command line string arguments
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner in;
		
		//Choose the algorithm to test
		int choice = 2;
		
		//Initialize scanner
		if (args.length == 0 || args[0].equals("-")) {
			in = new Scanner(System.in);
		} else {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		}
		
		//Select algorithm based on cmd input
		if (args.length > 1) { choice = Integer.parseInt(args[1]); }

		//Construct the graph
		Graph g = Graph.readGraph(in);
		
		//Set start vertex as the first vertex
		Vertex s = g.getVertex(1);

		//Initialize the timer
		Timer timer = new Timer();
		
		//Initialize MST algorithm
		MST m = mst(g, s, choice);
		
		//Print the total weight of the MST
		System.out.println(m.algorithm + "\n" + m.wmst);
		
		//Print the time taken
		System.out.println(timer.end());
		
	}
}
