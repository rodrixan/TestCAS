package operations;

import java.util.List;

import types.Element;

public class SUM {
	private List<Element> params;

	
	public SUM(List<Element> params){
		this.params=params;
	}
	
	public List<Element> getParams() {
		return params;
	}

	@Override
	public String toString() {
		StringBuilder builder= new StringBuilder();
		
		for(Element e : params)
		{
			if(params.indexOf(e)==params.size()-1){
				builder.append(e.toString());
			}
			else
			{
				builder.append(e.toString()+" + ");
			}
		}
		
		return builder.toString();
	}

	public void conmutativeSUM(int i, int j) {
		
		Element extracted=params.remove(i);
		params.add(j, extracted);
	}
	
	
	
	
}
