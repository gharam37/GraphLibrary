public class Edge{
 protected String _strUniqueID, //a unique id identifying edge
 _strData; //data associated with this edge.
 //Data could be name of edge or
 // any meaningful property for
 // an edge.
 protected int _nEdgeCost; // cost of traversing this edge
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