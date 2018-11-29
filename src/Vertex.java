import java.util.LinkedList;
import java.util.Vector;


public class Vertex {

	protected String _strUniqueID;
	protected String _strData; 
	protected int _nX,_nY;
	boolean visited=false;
	
    @Override
	public String toString() {
		return "Vertex [_strUniqueID=" + _strUniqueID  ;
	}
	LinkedList<Vertex> Adjeceny = new LinkedList<Vertex> (); //Sisters

	public Vertex(String _strUniqueID,String _strData,int nx,int ny){
		this._strUniqueID=_strUniqueID;
		this._strData=_strData;
		this._nX=nx;
		this._nY=ny;
		
		
	}
	public Vertex(String _strUniqueID,String _strData){
		this._strUniqueID=_strUniqueID;
		this._strData=_strData;
		
		
	}
	public String getUniqueID( ){
		return _strUniqueID;
	}

	public String getData( ){
		 return _strData;
	}
	public int getX( ){
		 return _nX;
	}
	public int getY( ){
		 return _nY;
    } 
	public static void main(String[] args){
		System.out.println("HelloWorld");
	}
}
