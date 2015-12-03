package es.uam.eps.tfg.CAS.CASTypes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CASList extends CASElement implements Iterable<CASElement> {

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
		if (elem.getType() != CASElemType.LIST) {
			return elementList.add(elem);
		}

		final CASList addedList = addListElements(this, (CASList) elem);

		if (addedList != null) {
			elementList = addedList.elementList;
			return true;
		}
		return false;
	}

	public static CASList concat(CASElement... elements) {
		CASList newList = new CASList();

		for (final CASElement e : elements) {
			if (e.getType() != CASElemType.LIST) {
				newList.add(e);
			} else {
				newList = addListElements(newList, (CASList) e);
				if (newList == null) {
					break;
				}
			}
		}

		return newList;
	}

	private static CASList addListElements(CASList oldList, CASList listElement) {
		final List<CASElement> newList = new ArrayList<>(oldList.elementList);
		for (final CASElement e : listElement.elementList) {
			if (e.getType() != CASElemType.LIST) {
				newList.add(e);
			} else {
				return null;
			}
		}
		return new CASList(newList);

	}

	public CASElement set(int index, CASElement elem) {
		CASElement previousItem = elementList.get(index);
		CASList settedList;

		if (elem.getType() != CASElemType.LIST) {
			elementList.add(index, elem);
		} else {
			settedList = setListElements(this, (CASList) elem, index);
			if (settedList != null) {
				elementList = settedList.elementList;
			} else {
				previousItem = null;
			}
		}

		return previousItem;
	}

	private CASList setListElements(CASList elementList, CASList listToSet, int index) {
		final CASList previousSublist = elementList.subList(0, index);
		final CASList postSublist = elementList.subList(index, elementList.size());

		return concat(previousSublist, listToSet, postSublist);
	}

	public CASElement remove(int index) {
		return elementList.remove(index);
	}

	public CASElement moveElement(int fromIndex, int toIndex) {
		final CASElement elemToMove = elementList.remove(fromIndex);
		elementList.add(toIndex, elemToMove);
		return elemToMove;
	}

	public CASList subList(int fromIndex, int toIndex) {
		return new CASList(elementList.subList(fromIndex, toIndex));
	}

	public boolean contains(CASElement element) {
		return elementList.contains(element);
	}

	public int indexOf(CASElement element) {
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
	public String getRepresentation() {
		final StringBuilder builder = new StringBuilder();
		for (final CASElement e : elementList) {
			builder.append(e.getRepresentation() + ",");
		}
		final String cleanString = removeLastColon(builder.toString());

		return cleanString;
	}

	private String removeLastColon(final String dirtyString) {
		final int lastColonIndex = dirtyString.lastIndexOf(",");
		final String cleanString = dirtyString.substring(0, lastColonIndex);
		return cleanString;
	}

	@Override
	public int size() {
		return elementList.size();
	}

	@Override
	public int getValue() {
		return CASConstants.NAN;
	}

	@Override
	public Iterator<CASElement> iterator() {
		return elementList.iterator();
	}

	@Override
	public String toInfixNotation() {
		return "";
	}
}
