
public class OurVisitor {
	
	public void visit( Vertex v ){
		   v.visited=true;
		}

	public void visit( Edge e ){
		  e.visited=true;
	}

}
