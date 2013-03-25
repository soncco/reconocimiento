package brau.robotica.biometrico;

public class Persona {
	
	//private variables
	int _id;
	String _name;
	String _picture;
	
	// Empty constructor
	public Persona(){
		
	}
	// constructor
	public Persona(int id, String name, String picture){
		this._id = id;
		this._name = name;
		this._picture = picture;
	}
	
	// constructor
	public Persona(String name, String _picture){
		this._name = name;
		this._picture = _picture;
	}
	// getting ID
	public int getID(){
		return this._id;
	}
	
	// setting id
	public void setID(int id){
		this._id = id;
	}
	
	// getting name
	public String getName(){
		return this._name;
	}
	
	// setting name
	public void setName(String name){
		this._name = name;
	}
	
	// getting picture
	public String getPicture(){
		return this._picture;
	}
	
	// setting picture
	public void setPicture(String picture){
		this._picture = picture;
	}
}
