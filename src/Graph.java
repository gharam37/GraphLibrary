import java.util.*; 

public class Graph {

	private String LibraryName;
	private String LibraryVersion;
    private Vector<Vertex> Vertices;
    private Vector<Edge>  Edges;
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
				else{
					Opposite = Edges.get(i).Vertex1; 

					break;
				}

			}
			
		}
		
		return Opposite;
	}
	public void insertVertex(String strUniqueID,
			String strData,
			int nX,
			int nY) throws GraphException{
		
		Vertices.add(new Vertex(strUniqueID,strData,nX,nY));
	}
	public void insertEdge(String strVertex1UniqueID,
	String strVertex2UniqueID,
	String strEdgeUniqueID,
	String strEdgeData,
	int nEdgeCost) throws GraphException{
		int countFound =0;
		Vertex Vertex1=null;
		Vertex Vertex2=null;
		for(int i=0;i<Edges.size();i++){
			if(Vertices.get(i).getUniqueID().equals(strVertex1UniqueID)){
				countFound++;
				Vertex1 = Vertices.get(i);
			}
			if(Vertices.get(i).getUniqueID().equals(strVertex2UniqueID)){
				countFound++;
				Vertex2 = Vertices.get(i);

			}
		}
		if(countFound==2){
			Edges.add(new Edge(strEdgeUniqueID,strEdgeData,nEdgeCost,Vertex1,Vertex2));
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
				for(int j=0;i<Edges.size();j++){
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
				for(int j=0;i<Edges.size();j++){
					String VertexID1=Edges.get(j).Vertex1.getUniqueID();
					String VertexID2=Edges.get(j).Vertex2.getUniqueID();

					if(VertexID1.equals(strVertexUniqueID) ||
					VertexID2.equals(strVertexUniqueID) ){
						incidentEdges.add(Edges.get(j));
						
					}
				}
				
				
			}

		}
		return incidentEdges;
	}
	public static void main(String[]args){
		 // create default vector 
        Vector v = new Vector(); 
  
        v.add(1); 
        v.add(2); 
        v.add("geeks"); 
        v.add("forGeeks"); 
        v.add(3); 
  
        System.out.println("Vector is " + v); 
		System.out.println("Printing From Graph");
	}
}
