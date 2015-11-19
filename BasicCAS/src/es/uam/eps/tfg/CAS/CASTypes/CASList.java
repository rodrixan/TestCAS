package es.uam.eps.tfg.CAS.CASTypes;

import java.util.ArrayList;
import java.util.List;

public class CASList extends CASElement {

	private List<CASElement> elementList;

	public CASList() {
		this.elementList = new ArrayList<>();
	}

	public CASList(List<CASElement> elementList) {
		this.elementList = elementList;
	}
	
	public CASElement get(int index) {
		return elementList.get(index);
	}

	public boolean add(CASElement elem) {
		if(elem.getType()!=CASElemType.LIST){
			return elementList.add(elem);
		}
		
		List<CASElement> addedList=addListElements(elementList, (CASList) elem);
		
		if(addedList!=null){
			elementList=addedList;
			return true;
		}
		return false;
	}
	
	public static List<CASElement> concat(CASElement... elements) {
		List<CASElement> newList = new ArrayList<CASElement>();

		for (final CASElement e : elements) {
			if (e.getType() != CASElemType.LIST) {
				newList.add(e);
			} else {
				newList=addListElements(newList, (CASList) e);
				if(newList==null){
					break;
				}
			}
		}

		return newList;
	}
	
	private static List<CASElement> addListElements(List<CASElement> oldList, CASList listElement) {
		List<CASElement> newList= new ArrayList<CASElement>(oldList);
		for (final CASElement e : listElement.elementList) {
			if (e.getType() != CASElemType.LIST) {
				newList.add(e);
			} else {
				return null;
			}
		}
		return newList;
		
	}

	public CASElement set(int index, CASElement elem) {
		CASElement previousItem=elementList.get(index);
		List<CASElement> settedList;
		
		if(elem.getType()!=CASElemType.LIST){
			elementList.set(index, elem);
		}else{
			settedList=setListElements(elementList,(CASList) elem,index);
			if(settedList!=null){
				elementList=settedList;
			}else{
				previousItem=null;
			}
		}
		
		return previousItem;
	}

	private List<CASElement> setListElements(List<CASElement> elementList, CASList listToSet, int index) {
		List<CASElement> PreviousSublist = elementList.subList(0, index);
		List<CASElement> PostSublist = elementList.subList(index, elementList.size());
		
		return concat(new CASList(PreviousSublist), listToSet, new CASList(PostSublist));
	}

	public CASElement remove(int index) {
		return elementList.remove(index);
	}

	public void moveElement(int fromIndex, int toIndex) {
		final CASElement elemToMove = elementList.remove(fromIndex);
		elementList.set(toIndex, elemToMove);
	}

	public List<CASElement> sublist(int fromIndex, int toIndex) {
		return elementList.subList(fromIndex, toIndex);
	}

	public boolean contains(CASElement element) {
		return elementList.contains(element);
	}
	
	public int indexOf(CASElement element){
		return elementList.indexOf(element);
	}

	public boolean isFormattedLike(CASElement... elements) {
		return false;
	}

	@Override
	public CASElemType getType() {
		return CASElemType.LIST;
	}

	@Override
	public String getElemRepresentation() {
		final StringBuilder builder = new StringBuilder();
		for (final CASElement e : elementList) {
			builder.append(e.getElemRepresentation()+",");
		}
		String cleanString = removeLastColon(builder.toString());
		
		return cleanString;
	}

	private String removeLastColon(final String dirtyString) {
		int lastColonIndex=dirtyString.lastIndexOf(",");
		String cleanString= dirtyString.substring(0, lastColonIndex);
		return cleanString;
	}

	@Override
	public Integer getElemValue() {
		return NAN;
	}
}
