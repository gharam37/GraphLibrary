public class GradingVisitor implements Visitor{

protected String _strResult = new String();

public void visit( Vertex v ){
   _strResult += "v=" + v.getUniqueID( ) + " ";
   v.visited=true;
}

public void visit( Edge e ){
  _strResult += "e=" + e.getUniqueID( ) + " " ;
  e.visited=true;
}

public String getResult( ){
return _strResult;
}
}