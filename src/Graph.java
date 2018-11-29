import java.util.*; 

public class Graph {

	private String LibraryName;
	private String LibraryVersion;
    private Vector<Vertex> Vertices=new Vector<Vertex>(50,1);
    private Vector<Edge>  Edges= new  Vector<Edge> (50,1) ;
	private static double bestDistance = Double.POSITIVE_INFINITY;
	private static Vertex best1, best2;

    public void dfs(String strStartVertexUniqueID,
    		Visitor visitor) throws GraphException{
    	Deque<Vertex> VertciesStack = new ArrayDeque<Vertex>();
    	boolean foundInitialVertex=false;
    	Vertex FirstVertex=null;
    	for (int i=0;i<Vertices.size();i++){
    		if(Vertices.get(i).getUniqueID().equals(strStartVertexUniqueID)){
    			foundInitialVertex=true;
    			FirstVertex=Vertices.get(i);
    			System.out.println(FirstVertex+" "); 

    		    visitor.visit(FirstVertex);
    		    VertciesStack.push(FirstVertex);
    		    for(int j=0;j<FirstVertex.Adjeceny.size();j++){
    		    	if(!FirstVertex.Adjeceny.get(j).visited){
    		    		String ID=FirstVertex.Adjeceny.get(j)._strUniqueID;
    		    		dfs(ID,visitor);
    		    		
    		    	}
    		    	
    		    }
    		    
			}
    	}

    }
	public String getLibraryName( ){
		
		return LibraryName;
	}
    
	
	public Vector<Vertex> vertices()throws GraphException
	
	{
		return Vertices;
	}
	public Vector<Edge> edges() throws GraphException{
		return Edges;
		
	}
	public Vertex[] endVertices(String strEdgeUniqueID)
			throws GraphException{
		Vertex Vertices[]=new Vertex[2];
		for(int i=0;i<Edges.size();i++){
			if(Edges.get(i).getUniqueID().equals(strEdgeUniqueID)){
				Vertices[0]=Edges.get(i).Vertex1;
				Vertices[1]=Edges.get(i).Vertex2;
				break;

			}
			
		}
		return Vertices;
	}
	public String getLibraryVersion( ){
		
		return LibraryVersion;
	}
	public Vertex opposite(String strVertexUniqueID,
	String strEdgeUniqueID) throws
	GraphException{
		Vertex Opposite=null;
		for(int i=0;i<Edges.size();i++){
			if(Edges.get(i).getUniqueID().equals(strEdgeUniqueID)){
				if(Edges.get(i).Vertex1._strUniqueID.equals(strVertexUniqueID))
				{
					Opposite = Edges.get(i).Vertex2; 
				break;
				}
				else if(Edges.get(i).Vertex2._strUniqueID.equals(strVertexUniqueID))
						{
					Opposite = Edges.get(i).Vertex1; 

					break;
				}

			}
			
		}
		if(Opposite==null){
			throw new GraphException("Edge or Vertix not found");

			
		}
		return Opposite;
	}
	public void insertVertex(String strUniqueID,
			String strData,
			int nX,
			int nY) throws GraphException{
		Vertex v=new Vertex(strUniqueID,strData,nX,nY);
		Vertices.add(v);
	   LinkedList<Vertex> VertexAdjeceny = new LinkedList<Vertex>();
	   VertexAdjeceny.add(v);
	       

	}
	public void insertEdge(String strVertex1UniqueID,
	String strVertex2UniqueID,
	String strEdgeUniqueID,
	String strEdgeData,
	int nEdgeCost) throws GraphException{
		int countFound =0;
		Vertex Vertex1=null;
		Vertex Vertex2=null;
		int Vertex1Index=-1;
		int Vertex2Index=-1;
		for(int i=0;i<Vertices.size();i++){
			if(Vertices.get(i).getUniqueID().equals(strVertex1UniqueID)){
				countFound++;
				System.out.println(countFound);
				Vertex1 = Vertices.get(i);
				Vertex1Index=i;
			}
			if(Vertices.get(i).getUniqueID().equals(strVertex2UniqueID)){
				countFound++;
				System.out.println(countFound);

				Vertex2 = Vertices.get(i);
				Vertex2Index=i;

			}
		}
		if(countFound==2){
			Edge e=new Edge(strEdgeUniqueID,strEdgeData,nEdgeCost,Vertex1,Vertex2);
			Edges.add(e);
			Vertices.get(Vertex1Index).Adjeceny.add(Vertices.get(Vertex2Index));
			Vertices.get(Vertex2Index).Adjeceny.add(Vertices.get(Vertex1Index));

			
		}
		else{
			throw new GraphException("Failed to Insert One vertex doesn't exist");
			/// Here a graph Exception
		}
		
		
	}
	public void removeEdge(String strEdgeUniqueID) throws
	GraphException{
		for(int i=0;i<Edges.size();i++){
			if(Edges.get(i).getUniqueID().equals(strEdgeUniqueID)){
				Vertex Vertex1=Edges.get(i).Vertex1;
				Vertex Vertex2=Edges.get(i).Vertex2;

				for(int j=0;j<Vertex1.Adjeceny.size();j++){
					if(Vertex1.Adjeceny.get(j)._strUniqueID.equals(Vertex2._strUniqueID)){
						Vertex1.Adjeceny.remove(j);
					}
				}
				for(int j=0;j<Vertex2.Adjeceny.size();j++){
					if(Vertex2.Adjeceny.get(j)._strUniqueID.equals(Vertex1._strUniqueID)){
						Vertex2.Adjeceny.remove(j);
					}
				}
				Edges.remove(Edges.get(i));
				return;
			}

		}
		
		throw new GraphException("Couldn't find Edge");
		
		
	}
	public void removeVertex(String strVertexUniqueID) throws
	GraphException{
		boolean found=false;
		for(int i=0;i<Vertices.size();i++){
			if(Vertices.get(i).getUniqueID().equals(strVertexUniqueID)){
				found =true;
				Vertices.remove(Vertices.get(i));
				for(int j=0;j<Edges.size();j++){
					String VertexID1=Edges.get(j).Vertex1.getUniqueID();
					String VertexID2=Edges.get(j).Vertex2.getUniqueID();

					if(VertexID1.equals(strVertexUniqueID) ||
					VertexID2.equals(strVertexUniqueID) ){
						Edges.remove(j);
						
					}
				}
				
			}

		}
		if(!found){
		throw new GraphException("Couldn't find Vertex");
		}
		
	}
	public Vector<Edge> incidentEdges(String strVertexUniqueID)
			throws GraphException{
		Vector<Edge> incidentEdges=new Vector<Edge>();
		boolean found=false;
		for(int i=0;i<Vertices.size();i++){
			if(Vertices.get(i).getUniqueID().equals(strVertexUniqueID)){
				found=true;
				for(int j=0;j<Edges.size();j++){
					String VertexID1=Edges.get(j).Vertex1.getUniqueID();
					String VertexID2=Edges.get(j).Vertex2.getUniqueID();

					if(VertexID1.equals(strVertexUniqueID) ||
					VertexID2.equals(strVertexUniqueID) ){
						incidentEdges.add(Edges.get(j));
						
					}
				}
				
				
			}

		}
		if(!found){
			throw new GraphException("Couldn't find Vertex");
		}
		return incidentEdges;
	}
	
	
	public void bfs(String strStartVertexUniqueID,
			Visitor visitor) throws GraphException{
        LinkedList<Vertex> queue = new LinkedList<Vertex>(); 
    	boolean foundInitialVertex=false;
    	Vertex FirstVertex=null;
    	for (int i=0;i<Vertices.size();i++){
    		if(Vertices.get(i).getUniqueID().equals(strStartVertexUniqueID)){
    			foundInitialVertex=true;
    			FirstVertex=Vertices.get(i);
    			queue.add(FirstVertex);
    		    visitor.visit(FirstVertex);

    		
    		    while(queue.size() != 0){
        		    Vertex CurrentVertex=queue.remove();

    		    	System.out.println(CurrentVertex+" "); 
    		    	for(int j=0;j<CurrentVertex.Adjeceny.size();j++){
    		    		if(!CurrentVertex.Adjeceny.get(j).visited){
    		    			CurrentVertex.Adjeceny.get(j).visited=true;
    		    			queue.add(CurrentVertex.Adjeceny.get(j));
    		    			
    		    		}
    		    	}

    		    	
    		    	
    		    }
    		    
			}
    	}
		
	}

	public Vertex[] closestPair() throws GraphException{
    	Vector<Vertex> vertices = this.Vertices;
		int n = vertices.size();
		if (n <= 1) return null;

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

		// compute closest pair with both endpoints in left subarray or both in right subarray
		double delta1 = closest(verticesByX, verticesByY, aux, lo, mid);
		double delta2 = closest(verticesByX, verticesByY, aux, mid + 1, hi);
		double delta = Math.min(delta1, delta2);

		// merge back so that pointsByY[lo..hi] are sorted by y-coordinate
		merge(verticesByY, aux, lo, mid, hi);

		// aux[0..m-1] = sequence of points closer than delta, sorted by y-coordinate
		int m = 0;
		for (int i = lo; i <= hi; i++) {
			if (Math.abs(verticesByY[i]._nX - median._nX) < delta) {
				aux[m++] = verticesByY[i];
			}
		}

		// compare each point to its neighbors with y-coordinate closer than delta
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
						System.out.println("better distance = " + delta + " from " + best1._strUniqueID + " to " + best2._strUniqueID);
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
			if (i > mid) a[k] = aux[j++];
			else if (j > hi) a[k] = aux[i++];
			else if (less(aux[j], aux[i])) a[k] = aux[j++];
			else a[k] = aux[i++];
		}
	}

	private static boolean less(Vertex v, Vertex w) {
		return compareTo(v, w) < 0;
	}

	public static int compareTo(Vertex a, Vertex that) {
		if (that != null && a != null) {
			if (a._nY < that._nY) return -1;
			if (a._nY > that._nY) return +1;
			if (a._nX < that._nX) return -1;
			if (a._nX > that._nX) return +1;
		}
		return 0;
	}

	public static boolean equalVertices(Vertex a, Vertex b) {
		return ((a._nX == b._nX) && (a._nY == b._nY));
	}


	public static void runTestCase1( ) throws GraphException{ 
		
	Graph g = new Graph( );
	GradingVisitor gVisitor = new GradingVisitor( );
	g.insertVertex("1", "1", 30, 7);
	g.insertVertex("2", "2", 15, 30);
	g.insertVertex("3", "3", 90, 50);
	g.insertVertex("4", "4", 12, 9);
	g.insertVertex("5", "5", 90, 4);
	g.insertEdge("1","4","88","88",5);
	g.insertEdge("1","2","2","2", 2);
	g.insertEdge("2", "3","14","14",14);
	g.insertEdge("2", "4","99","99",5);
	g.insertEdge("2", "5","4","4",4);
	//g.insertEdge("4", "5 ", "58", "58", 58);
	//g.insertEdge("3", "5 ", "34", "34", 34);
	//g.dfs("1",gVisitor );
	g.bfs("1", gVisitor);
	Vertex[] out = g.closestPair();
	System.out.println("Point 1: " + out[0]._strUniqueID);
	System.out.println("Point 2: " + out[1]._strUniqueID);


	}

	public static void runTestCase5() throws GraphException{ 
		
	Graph g = new Graph( );
	GradingVisitor gVisitor = new GradingVisitor( );
	g.insertVertex("1", "1", 0, 0);
	g.insertVertex("2", "2", 1, 30);
	g.insertVertex("3", "3", 9, 0);
	g.insertVertex("4", "4", 0, 5);
	g.insertVertex("0", "0", 0, 9);
	g.insertEdge("0","1","1","88",6);
	g.insertEdge("0","2","2","2", 1);
	g.insertEdge("1", "2","3","14",14);
	g.insertEdge("1", "3","4","99",5);
	g.insertEdge("1", "4","5","4",4);
	g.insertEdge("4", "3","6","99",5);
	g.insertEdge("3", "2","7","4",4);
	//g.insertEdge("4", "5 ", "58", "58", 58);
	//g.insertEdge("3", "5 ", "34", "34", 34);
	//g.dfs("1",gVisitor );
	g.dfs("1", gVisitor);

	Vertex[] out = g.closestPair();
	System.out.println("Point 1: " + out[0]._strUniqueID);
	System.out.println("Point 2: " + out[1]._strUniqueID);

	}








	public static void main(String[]args) throws GraphException{
		Graph Graph=new Graph();

//		Graph.insertVertex("1","1",0,0);
//		Graph.insertVertex("2","2",0,0);
//
//        Graph.insertEdge("1", "2", "1", "5", 1);
        //Graph.removeEdge("1");
		//Graph.removeVertex("2");
		//Graph.removeVertex("2");
        
		//System.out.println(Graph.opposite("1","1"));
        //Vertex [] Vertices= Graph.endVertices("1");
        
        
        /*for(int i=0;i<Graph.Vertices.size();i++){
    		System.out.println(Graph.Vertices.get(i).Adjeceny.get(0)); //Printing head 
    	}*/
        //Graph.removeEdge("1");
        for(int i=0;i<Graph.Vertices.size();i++){
    		System.out.println(Graph.Vertices.get(i).Adjeceny.get(0)); //Printing head 
    	}
        //runTestCase1();
        runTestCase5();
    
        /*for(int i=0;i<Graph.incidentEdges("1").size();i++){
		System.out.println(Graph.incidentEdges("1").get(i));
	   }*/
        
		/*for(int i=0;i<Graph.edges().size();i++){
			System.out.println(Graph.edges().get(i));
		}*/
		/*for(int i=0;i<Graph.vertices().size();i++){
			System.out.println(Graph.vertices().get(i));
		}*/
        
		/*for(int i=0;i<Graph.Edges.size();i++){
			System.out.println(Graph.Edges.get(i));
		}*/

	}
}
