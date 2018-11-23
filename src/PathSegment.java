// the following class could be used as the building block of a path where a
// path consists of path segments and each path segment consist of a
// vertex and associated edge with it.
public class PathSegment {
 protected Vertex _vertex; // the vertex in this path segment
 protected Edge _edge; // the edge associated with this vertex
 public Vertex getVertex( ){
 return _vertex;
 }
 public Edge getEdge( ){
 return _edge;
 }
} 