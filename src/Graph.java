import java.util.*;

public class Graph {

	private String LibraryName;
	private String LibraryVersion;
	private Vector<Vertex> Vertices = new Vector<Vertex>(50, 1);
	private Vector<Edge> Edges = new Vector<Edge>(50, 1);
	private static double bestDistance = Double.POSITIVE_INFINITY;
	private static Vertex best1, best2;
	private Deque<Vertex> VerticesStackdfs = new ArrayDeque<Vertex>();
	private boolean stopDfs = false;
	static int order = 0;

	///
	final static int INF = 99999;

	public Vector<Vector<PathSegment>> findAllShortestPathsFW() throws GraphException {
		int size = Vertices.size();
		Vector<Vector<PathSegment>> shortestPAth=new Vector<Vector<PathSegment>>();;
		PathSegment pathSegment = new PathSegment();
		Vector<PathSegment> path = new Vector<PathSegment>(50, 1);
		int dist[][] = new int[size][size];
		int i, j, k;
		System.out.println("Size: " + size);

		for (i = 0; i < size; i++) {
			for (j = 0; j < size; j++) {
				if (i == j) {
					dist[i][j] = 0;
				}
				else {
					if(edgeBetVertices(Vertices.get(i)._strUniqueID, Vertices.get(j)._strUniqueID)!= null) {
						dist[i][j] = edgeBetVertices(Vertices.get(i)._strUniqueID, Vertices.get(j)._strUniqueID)._nEdgeCost;
					}
					else {
						dist[i][j] = INF;
					}
				}
			}
		}

		/*
		 * Add all vertices one by one to the set of intermediate vertices. ---> Before
		 * start of an iteration, we have shortest distances between all pairs of
		 * vertices such that the shortest distances consider only the vertices in set
		 * {0, 1, 2, .. k-1} as intermediate vertices. ----> After the end of an
		 * iteration, vertex no. k is added to the set of intermediate vertices and the
		 * set becomes {0, 1, 2, .. k}
		 */
		for (k = 0; k < size; k++) {
			// Pick all vertices as source one by one
			for (i = 0; i < size; i++) {
				// Pick all vertices as destination for the
				// above picked source
				for (j = 0; j < size; j++) {
					// If vertex k is on the shortest path from
					// i to j, then update the value of dist[i][j]
					if (dist[i][k] + dist[k][j] < dist[i][j]) {
						dist[i][j] = dist[i][k] + dist[k][j];

						/*pathSegment._vertex=Vertices.get(i);
						pathSegment._edge=edgeBetVertices(Vertices.get(i)._strUniqueID, Vertices.get(k)._strUniqueID);
						path.add(pathSegment);
						System.out.println("PathSegment1 Vertex: "+pathSegment._vertex._strUniqueID + "Edge "+ pathSegment._edge._strUniqueID);

						pathSegment._vertex=Vertices.get(k);
						pathSegment._edge=edgeBetVertices(Vertices.get(k)._strUniqueID, Vertices.get(j)._strUniqueID);
						path.add(pathSegment);
						System.out.println("PathSegment2 Vertex: "+pathSegment._vertex._strUniqueID + "Edge "+ pathSegment._edge._strUniqueID);
						shortestPAth.add(path);*/

					}

				}
			}
		}

		// Print the shortest distance matrix
		printSolution(dist);
		display(shortestPAth);

		return null;

	}
	void printSolution(int dist[][])
	{
		System.out.println("The following matrix shows the shortest "+
				"distances between every pair of vertices");
		for (int i=0; i<Vertices.size(); ++i)
		{
			for (int j=0; j<Vertices.size(); ++j)
			{
				if (dist[i][j]==INF)
					System.out.print("INF ");
				else
					System.out.print(dist[i][j]+"   ");
			}
			System.out.println();
		}
	}
	void display(Vector<Vector<PathSegment>> pathSegmant) {

		for (int i = 0; i < pathSegmant.size(); i++){
			Vector inner = (Vector)pathSegmant.elementAt(i);
			for (int j = 0; j < inner.size(); j++) {
				Edge edge = pathSegmant.get(i).get(j)._edge;
				Vertex vertex = pathSegmant.get(i).get(j)._vertex;
				System.out.println("Edge " + edge._strUniqueID + " Vertex "+vertex._strUniqueID);
			}
			System.out.println();
		}
	}
	public Edge edgeBetVertices(String VertexID1, String VertexID2) throws GraphException {
		Vector<Edge> edge1 = new Vector<Edge>();
		Vector<Edge> edge2 = new Vector<Edge>();
		edge1 = incidentEdges(VertexID1);
		edge2 = incidentEdges(VertexID2);
		for (int l = 0; l < edge2.size(); l++) {
			if (edge1.contains(edge2.get(l))) {
				return edge2.get(l);
			}
		}
		return null;
	}

	public Vector<Vector<PathSegment>> findShortestPathBF(
			String strStartVertexUniqueID) throws GraphException {
		Vector<Vector<PathSegment>> out = BellmanFord(this,this.getVertexByID(strStartVertexUniqueID));
		for(int i =0;i<out.size();i++) {
			for (int j = 0; j < out.elementAt(i).size(); j++) {
				System.out.println("VERTEX1 " + out.elementAt(i).get(j)._edge.Vertex1._strUniqueID + " VERTEX2 " + out.elementAt(i).get(j)._edge.Vertex2._strUniqueID);
			}
			System.out.println("----------");
		}
		return out;
	}

	public static Vector<Vector<PathSegment>> BellmanFord(Graph graph, Vertex src) {
		int V = graph.Vertices.size(), E = graph.Edges.size();
		int dist[] = new int[V];
		PathSegment predecessor[] = new PathSegment[V];

		// Step 1: Initialize distances from src to all other
		// vertices as INFINITE
		for (int i = 0; i < V; ++i) {
			dist[i] = Integer.MAX_VALUE;
			predecessor[i] = null;
		}
		dist[src.order] = 0;

		// Step 2: Relax all edges |V| - 1 times. A simple
		// shortest path from src to any other vertex can
		// have at-most |V| - 1 edges
		for (int i = 1; i < V; ++i) {
			for (int j = 0; j < E; ++j) {
				Vertex u = graph.Edges.get(j).Vertex1;
				Vertex v = graph.Edges.get(j).Vertex2;
				int weight = graph.Edges.get(j)._nEdgeCost;
				if (dist[u.order] != Integer.MAX_VALUE && dist[u.order] + weight < dist[v.order]) {
					dist[v.order] = dist[u.order] + weight;
					PathSegment ps = new PathSegment();
					ps._vertex = u;
					ps._edge = graph.Edges.get(j);
					predecessor[v.order] = ps;
					//System.out.println(" U: " + ps._vertex._strUniqueID + "V: "+v._strUniqueID + " E: " + ps._edge._strUniqueID);
				}
			}
		}

		Vector<Vector<PathSegment>> out = new Vector<>(50, 1);

		for(int i=0;i<predecessor.length;i++){
			if(i!=src.order) {
				PathSegment p = predecessor[i];
				Vector<PathSegment> parent = new Vector<>(50);
				while(p._vertex._strUniqueID!=src._strUniqueID){
					parent.addElement(p);
					p = predecessor[p._vertex.order];
				}
				parent.add(p);
				out.add(parent);

			}
		}
		// Step 3: check for negative-weight cycles.  The above
		// step guarantees shortest distances if graph doesn't
		// contain negative weight cycle. If we get a shorter
		//  path, then there is a cycle.
		for (int j = 0; j < E; ++j) {
			Vertex u = graph.Edges.get(j).Vertex1;
			Vertex v = graph.Edges.get(j).Vertex2;
			int weight = graph.Edges.get(j)._nEdgeCost;
			if (dist[u.order] != Integer.MAX_VALUE &&
					dist[u.order] + weight < dist[v.order])
				System.out.println("Graph contains negative weight cycle");
		}
//        printArr(graph,dist, V);
		return out;
	}

//    // A utility function used to print the solution
//    public static void printArr(Graph g,int dist[], int V) {
//        System.out.println("Vertex   Distance from Source");
//        for (int i = 0; i < dist.length; ++i)
//            System.out.println(g.Vertices.get(i)._strUniqueID + "\t\t" + dist[g.Vertices.get(i).order]);
//    }



	public void dfs(String strStartVertexUniqueID, Visitor visitor) throws GraphException {
		// TODO: ask to remove stack
		Deque<Vertex> VertciesStack = new ArrayDeque<Vertex>();
		boolean foundInitialVertex = false;
		Vertex FirstVertex = null;
		for (int i = 0; i < Vertices.size(); i++) {
			if (Vertices.get(i).getUniqueID().equals(strStartVertexUniqueID)) {
				foundInitialVertex = true;
				FirstVertex = Vertices.get(i);
				System.out.println("First Vertex " + FirstVertex + " ");

				visitor.visit(FirstVertex);
				VertciesStack.push(FirstVertex);

				for (int j = 0; j < FirstVertex.Adjeceny.size(); j++) {
					if (!FirstVertex.Adjeceny.get(j).visited) {
						String ID = FirstVertex.Adjeceny.get(j)._strUniqueID;
						dfs(ID, visitor);

					}

				}

			}
		}

	}

	public String getLibraryName() {

		return LibraryName;
	}

	public Vector<Vertex> vertices() throws GraphException

	{
		return Vertices;
	}

	public Vector<Edge> edges() throws GraphException {
		return Edges;

	}

	public Vertex[] endVertices(String strEdgeUniqueID) throws GraphException {
		Vertex Vertices[] = new Vertex[2];
		for (int i = 0; i < Edges.size(); i++) {
			if (Edges.get(i).getUniqueID().equals(strEdgeUniqueID)) {
				Vertices[0] = Edges.get(i).Vertex1;
				Vertices[1] = Edges.get(i).Vertex2;
				break;

			}

		}
		return Vertices;
	}

	public String getLibraryVersion() {

		return LibraryVersion;
	}

	public Vertex opposite(String strVertexUniqueID, String strEdgeUniqueID) throws GraphException {
		Vertex Opposite = null;
		for (int i = 0; i < Edges.size(); i++) {
			if (Edges.get(i).getUniqueID().equals(strEdgeUniqueID)) {
				if (Edges.get(i).Vertex1._strUniqueID.equals(strVertexUniqueID)) {
					Opposite = Edges.get(i).Vertex2;
					break;
				} else if (Edges.get(i).Vertex2._strUniqueID.equals(strVertexUniqueID)) {
					Opposite = Edges.get(i).Vertex1;

					break;
				}

			}

		}
		if (Opposite == null) {
			throw new GraphException("Edge or Vertix not found");

		}
		return Opposite;
	}

	public void insertVertex(String strUniqueID, String strData, int nX, int nY) throws GraphException {
		Vertex v = new Vertex(strUniqueID, strData, nX, nY, order++);
		Vertices.add(v);
		LinkedList<Vertex> VertexAdjeceny = new LinkedList<Vertex>();
		VertexAdjeceny.add(v);

	}

	public void insertEdge(String strVertex1UniqueID, String strVertex2UniqueID, String strEdgeUniqueID,
			String strEdgeData, int nEdgeCost) throws GraphException {
		int countFound = 0;
		Vertex Vertex1 = null;
		Vertex Vertex2 = null;
		int Vertex1Index = -1;
		int Vertex2Index = -1;
		for (int i = 0; i < Vertices.size(); i++) {
			if (Vertices.get(i).getUniqueID().equals(strVertex1UniqueID)) {
				countFound++;
				//System.out.println(countFound);
				Vertex1 = Vertices.get(i);
				Vertex1Index = i;
			}
			if (Vertices.get(i).getUniqueID().equals(strVertex2UniqueID)) {
				countFound++;
				//System.out.println(countFound);

				Vertex2 = Vertices.get(i);
				Vertex2Index = i;

			}
		}
		if (countFound >=2) {
			Edge e = new Edge(strEdgeUniqueID, strEdgeData, nEdgeCost, Vertex1, Vertex2);
			Edges.add(e);
			Vertices.get(Vertex1Index).Adjeceny.add(Vertices.get(Vertex2Index));
			Vertices.get(Vertex2Index).Adjeceny.add(Vertices.get(Vertex1Index));

		} else {
			System.out.println(countFound);
			throw new GraphException("Failed to Insert One vertex doesn't exist");
			/// Here a graph Exception
		}

	}

	public void removeEdge(String strEdgeUniqueID) throws GraphException {
		for (int i = 0; i < Edges.size(); i++) {
			if (Edges.get(i).getUniqueID().equals(strEdgeUniqueID)) {
				Vertex Vertex1 = Edges.get(i).Vertex1;
				Vertex Vertex2 = Edges.get(i).Vertex2;

				for (int j = 0; j < Vertex1.Adjeceny.size(); j++) {
					if (Vertex1.Adjeceny.get(j)._strUniqueID.equals(Vertex2._strUniqueID)) {
						Vertex1.Adjeceny.remove(j);
					}
				}
				for (int j = 0; j < Vertex2.Adjeceny.size(); j++) {
					if (Vertex2.Adjeceny.get(j)._strUniqueID.equals(Vertex1._strUniqueID)) {
						Vertex2.Adjeceny.remove(j);
					}
				}
				Edges.remove(Edges.get(i));
				return;
			}

		}

		throw new GraphException("Couldn't find Edge");

	}

	public void removeVertex(String strVertexUniqueID) throws GraphException {
		boolean found = false;
		for (int i = 0; i < Vertices.size(); i++) {
			if (Vertices.get(i).getUniqueID().equals(strVertexUniqueID)) {
				found = true;
				Vertices.remove(Vertices.get(i));
				for (int j = 0; j < Edges.size(); j++) {
					String VertexID1 = Edges.get(j).Vertex1.getUniqueID();
					String VertexID2 = Edges.get(j).Vertex2.getUniqueID();

					if (VertexID1.equals(strVertexUniqueID) || VertexID2.equals(strVertexUniqueID)) {
						Edges.remove(j);

					}
				}

			}

		}
		if (!found) {
			throw new GraphException("Couldn't find Vertex");
		}

	}

	public Vector<Edge> incidentEdges(String strVertexUniqueID) throws GraphException {
		Vector<Edge> incidentEdges = new Vector<Edge>();
		boolean found = false;
		for (int i = 0; i < Vertices.size(); i++) {
			if (Vertices.get(i).getUniqueID().equals(strVertexUniqueID)) {
				found = true;
				for (int j = 0; j < Edges.size(); j++) {
					String VertexID1 = Edges.get(j).Vertex1.getUniqueID();
					String VertexID2 = Edges.get(j).Vertex2.getUniqueID();

					if (VertexID1.equals(strVertexUniqueID) || VertexID2.equals(strVertexUniqueID)) {
						incidentEdges.add(Edges.get(j));

					}
				}

			}

		}
		if (!found) {
			throw new GraphException("Couldn't find Vertex");
		}
		return incidentEdges;
	}

	public void bfs(String strStartVertexUniqueID, Visitor visitor) throws GraphException {
		LinkedList<Vertex> queue = new LinkedList<Vertex>();
		boolean foundInitialVertex = false;
		Vertex FirstVertex = null;
		for (int i = 0; i < Vertices.size(); i++) {
			if (Vertices.get(i).getUniqueID().equals(strStartVertexUniqueID)) {
				foundInitialVertex = true;
				FirstVertex = Vertices.get(i);
				queue.add(FirstVertex);
				visitor.visit(FirstVertex);

				while (queue.size() != 0) {
					Vertex CurrentVertex = queue.remove();

					//System.out.println(CurrentVertex + " ");
					for (int j = 0; j < CurrentVertex.Adjeceny.size(); j++) {
						if (!CurrentVertex.Adjeceny.get(j).visited) {
							CurrentVertex.Adjeceny.get(j).visited = true;
							queue.add(CurrentVertex.Adjeceny.get(j));

						}
					}

				}

			}
		}

	}

	// a helper dfs method for pathdfs
	public void dfs(String strStartVertexUniqueID, String strEndVertexUniqueID, Visitor visitor) throws GraphException {
		if (stopDfs) {
			return;
		}
		boolean foundInitialVertex = false;
		Vertex FirstVertex = null;
		for (int i = 0; i < Vertices.size(); i++) {
			if (Vertices.get(i).getUniqueID().equals(strStartVertexUniqueID)) {
				if (Vertices.get(i).getUniqueID().equals(strEndVertexUniqueID) ) {
					stopDfs = true;
				}
				foundInitialVertex = true;
				FirstVertex = Vertices.get(i);
				System.out.println("First Vertex " + FirstVertex + " ");
				visitor.visit(FirstVertex);
				VerticesStackdfs.push(FirstVertex);
				//System.out.println("size0 " + VerticesStackdfs.size());
				for (int j = 0; j < FirstVertex.Adjeceny.size(); j++) {
					if (!FirstVertex.Adjeceny.get(j).visited) {
						String ID = FirstVertex.Adjeceny.get(j)._strUniqueID;
						dfs(ID, strEndVertexUniqueID, visitor);

					}

				}

			}

		}
	}

	public Vector<PathSegment> pathDFS(String strStartVertexUniqueID, String strEndVertexUniqueID)
			throws GraphException {
		Vector<PathSegment> output = new Vector<PathSegment>(50, 1);
		PathSegment pathSegment = new PathSegment();
		GradingVisitor gVisitor = new GradingVisitor();
		Deque<Vertex> VerticesStackdfs2 = new ArrayDeque<Vertex>();

		// dfs on the points required from the start vertex to the end vertex
		stopDfs = false;
		dfs(strStartVertexUniqueID, strEndVertexUniqueID, gVisitor);

		int size = VerticesStackdfs.size();
		//check if a path exists if it only contains one element
		if (size == 1) {
			pathSegment._edge = getCommonEdge(strStartVertexUniqueID, strEndVertexUniqueID);
			pathSegment._vertex = VerticesStackdfs.pop();
			if (pathSegment._edge == null) {
				throw new GraphException("No path between these vertices");
			}
			System.out.println("PathSegment Edge: " + pathSegment._edge._strUniqueID + "PathSegment Vertex: "
					+ pathSegment._vertex);
			output.add(pathSegment);
			return output;
		}
		// check that all vertices resulted from dfs have path between each other
		// VertciesStackdfs2 will have the wanted vertices
		VerticesStackdfs2.push(getVertexByID(strEndVertexUniqueID));
		Vertex last = getVertexByID(strEndVertexUniqueID);
		if (last._strUniqueID.equals(strEndVertexUniqueID)) {
			VerticesStackdfs.pop();
		}
		for (int j = 0; j < size - 1; j++) {
			if (getCommonEdge(last._strUniqueID, VerticesStackdfs.peek()._strUniqueID) == null) {
				System.out.println("IN: " + VerticesStackdfs.pop());
			} else {
				last = VerticesStackdfs.pop();
				VerticesStackdfs2.push(last);
			}
		}

		System.out.println("size: " + VerticesStackdfs2.size());
		// get the edges and the vertices of the dfs between start and end vertex
		size = VerticesStackdfs2.size();
		//if the stack contains only one element it means there is no path exist
		if (size == 1) {
			throw new GraphException("No path between these vertices");
		}
		for (int i = 0; i < size - 1; i++) {
			Vertex vertex = VerticesStackdfs2.pop();
			pathSegment._edge = getCommonEdge(vertex._strUniqueID, VerticesStackdfs2.peek()._strUniqueID);
			pathSegment._vertex = vertex;
			output.add(pathSegment);
			System.out.println("PathSegment Edge: " + pathSegment._edge._strUniqueID + "PathSegment Vertex: "
					+ pathSegment._vertex._strUniqueID);

		}

		return output;
	}

	// a helper method that get a vertex by giving its uniqueID
	public Vertex getVertexByID(String uniqueId) {
		for (int i = 0; i < Vertices.size(); i++) {
			if (Vertices.get(i)._strUniqueID.equals(uniqueId))
				return Vertices.get(i);
		}
		return null;
	}

	// a helper method that takes as parameter ID of 2 vertices and return the edge
	// between them or return null if no edge between them
	public Edge getCommonEdge(String uniqueId1, String uniqueId2) throws GraphException {
		Vector<Edge> edge2 = incidentEdges(uniqueId2);
		for (int j = 0; j < edge2.size(); j++) {
			System.out.println("test1 " + edge2.get(j)._strUniqueID);
			if (opposite(uniqueId2, edge2.get(j)._strUniqueID)._strUniqueID == uniqueId1) {
				return edge2.get(j);
			}
		}
		return null;
	}

	public Vertex[] closestPair() throws GraphException {
		Vector<Vertex> vertices = this.Vertices;
		int n = vertices.size();
		if (n <= 1)
			return null;

		Vertex[] out = new Vertex[2];
		// sort by x-coordinate (breaking ties by y-coordinate)
		Vertex[] verticesByX = new Vertex[n];
		for (int i = 0; i < n; i++)
			verticesByX[i] = vertices.get(i);

		Arrays.sort(verticesByX, Comparator.comparingInt((Vertex a) -> a._nX));

		// check for coincident points
		for (int i = 0; i < n - 1; i++) {
			if (equalVertices(verticesByX[i], verticesByX[i + 1])) {
				bestDistance = 0.0;
				best1 = verticesByX[i];
				best2 = verticesByX[i + 1];
				out[0] = best1;
				out[1] = best2;
				return out;
			}
		}

		// sort by y-coordinate (but not yet sorted)
		Vertex[] verticesByY = new Vertex[n];
		for (int i = 0; i < n; i++)
			verticesByY[i] = verticesByX[i];

		// auxiliary array
		Vertex[] aux = new Vertex[n];

		closest(verticesByX, verticesByY, aux, 0, n - 1);
		out[0] = best1;
		out[1] = best2;
		return out;
	}

	private static double closest(Vertex[] verticesByX, Vertex[] verticesByY, Vertex[] aux, int lo, int hi) {
		if (hi <= lo) {
			return Double.POSITIVE_INFINITY;
		}

		int mid = lo + (hi - lo) / 2;
		Vertex median = verticesByX[mid];

		// compute closest pair with both endpoints in left subarray or both in right
		// subarray
		double delta1 = closest(verticesByX, verticesByY, aux, lo, mid);
		double delta2 = closest(verticesByX, verticesByY, aux, mid + 1, hi);
		double delta = Math.min(delta1, delta2);

		// merge back so that verticesByY[lo..hi] are sorted by y-coordinate
		merge(verticesByY, aux, lo, mid, hi);

		// aux[0..m-1] = sequence of points closer than delta, sorted by y-coordinate
		int m = 0;
		for (int i = lo; i <= hi; i++) {
			if (Math.abs(verticesByY[i]._nX - median._nX) < delta) {
				aux[m++] = verticesByY[i];
			}
		}

		// compare each vertex to its neighbors with y-coordinate closer than delta
		for (int i = 0; i < m; i++) {
			// a geometric packing argument shows that this loop iterates at most 7 times
			for (int j = i + 1; (j < m) && (aux[j]._nY - aux[i]._nY < delta); j++) {
				double dx = aux[i]._nX - aux[j]._nX;
				double dy = aux[i]._nY - aux[j]._nY;
				double distance = Math.sqrt(dx * dx + dy * dy);
				if (distance < delta) {
					delta = distance;
					if (distance < bestDistance) {
						bestDistance = delta;
						best1 = aux[i];
						best2 = aux[j];
						System.out.println("better distance = " + delta + " from " + best1._strUniqueID + " to "
								+ best2._strUniqueID);
					}
				}
			}
		}
		return delta;
	}

	private static void merge(Vertex[] a, Vertex[] aux, int lo, int mid, int hi) {
		// copy to aux[]
		for (int k = lo; k <= hi; k++) {
			aux[k] = a[k];
		}

		// merge back to a[]
		int i = lo, j = mid + 1;
		for (int k = lo; k <= hi; k++) {
			if (i > mid)
				a[k] = aux[j++];
			else if (j > hi)
				a[k] = aux[i++];
			else if (less(aux[j], aux[i]))
				a[k] = aux[j++];
			else
				a[k] = aux[i++];
		}
	}

	private static boolean less(Vertex v, Vertex w) {
		return compareTo(v, w) < 0;
	}

	public static int compareTo(Vertex a, Vertex that) {
		if (that != null && a != null) {
			if (a._nY < that._nY)
				return -1;
			if (a._nY > that._nY)
				return +1;
			if (a._nX < that._nX)
				return -1;
			if (a._nX > that._nX)
				return +1;
		}
		return 0;
	}

	public static boolean equalVertices(Vertex a, Vertex b) {
		return ((a._nX == b._nX) && (a._nY == b._nY));
	}

	public static void runTestCase1() throws GraphException {

		Graph g = new Graph();
		GradingVisitor gVisitor = new GradingVisitor();
		g.insertVertex("1", "1", 30, 7);
		g.insertVertex("2", "2", 15, 30);
		g.insertVertex("3", "3", 90, 50);
		g.insertVertex("4", "4", 12, 9);
		g.insertVertex("5", "5", 90, 4);
		g.insertEdge("1", "4", "88", "88", 5);
		g.insertEdge("1", "2", "2", "2", 2);
		g.insertEdge("2", "3", "14", "14", 14);
		g.insertEdge("2", "4", "99", "99", 5);
		g.insertEdge("2", "5", "4", "4", 4);
		// g.insertEdge("4", "5 ", "58", "58", 58);
		// g.insertEdge("3", "5 ", "34", "34", 34);
		// g.dfs("1",gVisitor );

		//g.bfs("1", gVisitor);

		Vertex[] out = g.closestPair();
		System.out.println("Point 1: " + out[0]._strUniqueID);
		System.out.println("Point 2: " + out[1]._strUniqueID);

		g.pathDFS("1", "5");
	}

	public static void runTestCase5() throws GraphException {

		Graph g = new Graph();
		GradingVisitor gVisitor = new GradingVisitor();
		g.insertVertex("1", "1", 0, 0);
		g.insertVertex("2", "2", 1, 30);
		g.insertVertex("3", "3", 9, 0);
		g.insertVertex("4", "4", 0, 5);
		g.insertVertex("0", "0", 0, 9);
		g.insertEdge("0", "1", "1", "88", 6);
		g.insertEdge("0", "2", "2", "2", 1);
		g.insertEdge("1", "2", "3", "14", 14);
		g.insertEdge("1", "3", "4", "99", 5);
		g.insertEdge("1", "4", "5", "4", 4);
		g.insertEdge("4", "3", "6", "99", 5);
		g.insertEdge("3", "2", "7", "4", 4);

		g.insertEdge("1", "0", "1", "88", 6);
		g.insertEdge("2", "0", "2", "2", 1);
		g.insertEdge("2", "1", "3", "14", 14);
		g.insertEdge("3", "1", "4", "99", 5);
		g.insertEdge("4", "1", "5", "4", 4);
		g.insertEdge("3", "4", "6", "99", 5);
		g.insertEdge("2", "3", "7", "4", 4);
		// g.insertEdge("4", "5 ", "58", "58", 58);
		// g.insertEdge("3", "5 ", "34", "34", 34);
		// g.dfs("1",gVisitor );
		//g.dfs("1", gVisitor);

		/*Vertex[] out = g.closestPair();
		System.out.println("Point 1: " + out[0]._strUniqueID);
		System.out.println("Point 2: " + out[1]._strUniqueID);
		g.pathDFS("1", "4");
		g.findShortestPathBF("0");
		System.out.println("Point 2: " + out[1]._strUniqueID);*/
		//g.pathDFS("1", "4");

	}
	public static Vector<Edge> SortEdges(Vector<Edge> Edges){
		Vector<Edge> Output = new Vector<Edge>(50,1);
		for(int i=0;i<Edges.size();i++){
			Output.add(Edges.get(i));
			
		}
		for( int i=0;i<Output.size()-1;i++){
			int MinimumIndex=i;
			for (int j = i+1; j < Output.size(); j++) {
				if(Output.get(j)._nEdgeCost<Output.get(MinimumIndex)._nEdgeCost){
					MinimumIndex=j;
					
				}
			}

			Edge temp= Output.get(MinimumIndex);
			Output.set(MinimumIndex,Output.get(i));
			Output.set(i, temp);
			
			
		}
		
		return Output;
		
	}
	public  Vector<PathSegment> minSpanningTree()
			throws GraphException{
		Vector<PathSegment> Output = new Vector<PathSegment>(50,1);
		Vector<Edge> SortedEdges = SortEdges(Edges);
		Vector<Vertex> OutputVertices = new Vector<Vertex>(50,1);
        Graph Accumlator = new Graph();
		int StopAt = Vertices.size();
		int i=0;
		boolean found1=false;
		boolean found2=false;
	    int countFound=0;

        for(int k=0;k<Vertices.size();k++){
        	Accumlator.insertVertex(Vertices.get(k)._strUniqueID,"",0,0);
        }
        for(int k=0;k<Accumlator.Vertices.size();k++){
        	System.out.println(Accumlator.Vertices.get(k));
        }
 		while( i<SortedEdges.size() &&Accumlator.Edges.size()<StopAt){


            found1=false;
            found2=false;
            
            Accumlator.insertEdge(SortedEdges.get(i).Vertex1._strUniqueID, SortedEdges.get(i).Vertex2._strUniqueID,
            		SortedEdges.get(i)._strUniqueID, SortedEdges.get(i)._strData, SortedEdges.get(i).getCost());
            

            System.out.println();

			if(isCyclic(Accumlator)){

				Accumlator.removeEdge(SortedEdges.get(i)._strUniqueID);

		}
			i++;


	}
 		for(int f=0;f<Accumlator.Edges.size();f++){
 			System.out.println(Accumlator.Edges.get(f)._strUniqueID);
 			PathSegment Segmant= new PathSegment();
 			Segmant._edge=Accumlator.Edges.get(f);
 			Output.add(Segmant);
 		}

		return Output;

	}
	public static  void sort(int arr[]) 
    { 
        int n = arr.length; 
  
        // One by one move boundary of unsorted subarray 
        for (int i = 0; i < n-1; i++) 
        { 
            // Find the minimum element in unsorted array 
            int min_idx = i; 
            for (int j = i+1; j < n; j++) 
                if (arr[j] < arr[min_idx]) 
                    min_idx = j; 
  
            // Swap the found minimum element with the first 
            // element 
            int temp = arr[min_idx]; 
            arr[min_idx] = arr[i]; 
            arr[i] = temp; 
        } 
    }
	public static boolean isCyclicUtil(Vertex vertex, GradingVisitor Visitor, Vertex Parent) 
    { 
        Visitor.visit(vertex); 
  
        // Recur for all the vertices adjacent to this vertex 
       for(int j=0;j<vertex.Adjeceny.size()-1;j++)
        { 
  
    	   //System.out.println(vertex.Adjeceny.get(0));
    	   //System.out.println(vertex._strUniqueID);

    	   
    	   //System.out.println(vertex.Adjeceny.get(1));

            // If an adjacent is not visited, then recur for 
            // that adjacent 
            if (!vertex.Adjeceny.get(j+1).visited) 
            { 
                if (isCyclicUtil(vertex.Adjeceny.get(j+1), Visitor, vertex)) 
                    return true; 
            } 
  
            // If an adjacent is visited and not parent of  
            // current vertex, then there is a cycle. 
            else if (vertex  != Parent ) 
               return true; 
        } 
        return false; 
    } 
	public static boolean isCyclic(Graph g){
		
		return isCyclicUtil(g.Vertices.get(0), new GradingVisitor (), null);
	}
	public static Graph subgraph(Graph g , Vector<Vertex>Verticies){
		Graph Output=new Graph();
		for(int i=0;i<Verticies.size();i++){
			Output.Vertices.addElement(Verticies.get(i));
			Vertex CurrentVertex=null;
			for(int j=0;j<g.Vertices.size();j++){
				CurrentVertex=g.Vertices.get(j);
				if(CurrentVertex._strUniqueID.equals(Verticies.get(i)._strUniqueID)){
					//System.out.println(CurrentVertex);
					break;
				}
				
				
			}

			LinkedList<Vertex> AdjecentInList = new LinkedList<Vertex> ();
			for( int k=0;k<CurrentVertex.Adjeceny.size();k++){
				for(int j=0;j<Verticies.size();j++){
					if(Verticies.get(j)._strUniqueID.equals(CurrentVertex.Adjeceny.get(k)._strUniqueID)){
				
					//System.out.println("Here");
					AdjecentInList.add(CurrentVertex.Adjeceny.get(k));
					}
				}
				
			}
			Output.Vertices.get(Output.Vertices.size()-1).Adjeceny=AdjecentInList;

		}
		
		return Output;
	}
	public static void main(String[] args) throws GraphException {
		int[] ArrayToSort=new int[]{5,2,14,5,4};

		
//
//		Graph g = new Graph();
//		g.insertVertex("1", "1", 30, 7);
//		g.insertVertex("2", "2", 15, 30);
//		g.insertVertex("3", "3", 90, 50);
//		g.insertVertex("4", "4", 12, 9);
//		g.insertVertex("5", "5", 90, 4);
//		g.insertEdge("1", "4", "88", "88", 5);
//		g.insertEdge("1", "2", "2", "2", 2);
//		g.insertEdge("2", "3", "14", "14", 14);
//		g.insertEdge("2", "4", "99", "99", 5);
//		g.insertEdge("2", "5", "4", "4", 4);
		
        //Vertex C= new Vertex ("4", "4", 12, 9);


        //newVertices.add(C);


		GradingVisitor Visitor=  new GradingVisitor();
		
		Graph g2=new Graph();
		g2.insertVertex("A", "1", 30, 7);
		g2.insertVertex("B", "2", 15, 30);
		g2.insertVertex("C", "3", 90, 50);
		g2.insertVertex("D", "4", 12, 9);
		g2.insertVertex("E", "5", 90, 4);
		g2.insertVertex("F", "5", 90, 4);

		g2.insertEdge("A", "C", "88", "88", 5);
		g2.insertEdge("C", "B", "2", "2", 2);
		g2.insertEdge("C", "E", "100", "100", 4);
		g2.insertEdge("C", "D", "99", "99", 5);
		g2.insertEdge("D", "F", "4", "4", 100);
		
        //g.minSpanningTree();

		System.out.println(isCyclic(g2));
		//g.getVertexByID("A");
		System.out.println( g2.getVertexByID("A").order);
		g2.findShortestPathBF("A");
		g2.findAllShortestPathsFW();
		g2.findAllShortestPathsFW();

		//runTestCase1();
		//runTestCase5();

	

	}
}
