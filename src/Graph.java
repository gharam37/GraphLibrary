import java.util.*; 

public class Graph {

	private String LibraryName;
	private String LibraryVersion;
    private Vector<Vertex> Vertices=new Vector<Vertex>(50,1);
    private Vector<Edge>  Edges= new  Vector<Edge> (50,1) ;

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
	public static void runTestCase1( ) throws GraphException{ 
		
	Graph g = new Graph( );
	GradingVisitor gVisitor = new GradingVisitor( );
	g.insertVertex("1", "1" ,0,0);
	g.insertVertex("2", "2",0,0 );
	g.insertVertex("3", "3" ,0,0);
	g.insertVertex("4", "4" ,0,0);
	g.insertVertex("5", "5" ,0,0); 
	g.insertEdge("1","4","88","88",5);
	g.insertEdge("1","2","2","2", 2);
	g.insertEdge("2", "3","14","14",14);
	g.insertEdge("2", "4","99","99",5);
	g.insertEdge("2", "5","4","4",4);
	//g.insertEdge("4", "5 ", "58", "58", 58);
	//g.insertEdge("3", "5 ", "34", "34", 34);
	//g.dfs("1",gVisitor );
	g.bfs("1", gVisitor);
	
	}

	public static void runTestCase5() throws GraphException{ 
		
	Graph g = new Graph( );
	GradingVisitor gVisitor = new GradingVisitor( );
	g.insertVertex("1", "1" ,0,0);
	g.insertVertex("2", "2",0,0 );
	g.insertVertex("3", "3" ,0,0);
	g.insertVertex("4", "4" ,0,0);
	g.insertVertex("0", "0" ,0,0); 
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
	
	}








	public static void main(String[]args) throws GraphException{
		Graph Graph=new Graph();

		Graph.insertVertex("1","1",0,0);
		Graph.insertVertex("2","2",0,0);
       
        Graph.insertEdge("1", "2", "1", "5", 1);
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
