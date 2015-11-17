package types;

public class Element {
	private String value="";
	
	public Element(String value){
		this.value=value;
	}
	
	@Override
	public String toString(){
		return value;
	}
}
