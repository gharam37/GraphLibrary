public class Edge{
 protected String _strUniqueID, 
 _strData;

 protected int _nEdgeCost; // cost of traversing this edge
 public Vertex Vertex1;
 public Vertex Vertex2;
  boolean visited=false;
    public Edge(){
    	
    }

	public Edge(String _strUniqueID,String _strData,int cost,Vertex Vertex1,Vertex Vertex2){
		this._strUniqueID=_strUniqueID;
		this._strData=_strData;
		this._nEdgeCost=cost;
		this.Vertex1=Vertex1;
		this.Vertex2=Vertex2;

		
		
		
	}
	public String getUniqueID( ){
return _strUniqueID;
 }

 public String getData( ){
 return _strData;
 }
 public int getCost( ){
return _nEdgeCost;
 }
}