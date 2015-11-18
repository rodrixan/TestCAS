package es.uam.eps.tfg.CASTest.CASTypes;

import java.util.ArrayList;
import java.util.List;

public class CASList extends CASElement {

	private final List<CASElement> elementList;

	public CASList() {
		this.elementList = new ArrayList<>();
	}

	public CASList(List<CASElement> elementList) {
		this.elementList = elementList;
	}

	public boolean add(CASElement elem) {
		return elementList.add(elem);
	}

	public CASElement get(int index) {
		return elementList.get(index);
	}

	public CASElement set(int index, CASElement elem) {
		return elementList.set(index, elem);
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

	public List<CASElement> concat(CASElement... elements) {
		final List<CASElement> newList = new ArrayList<CASElement>();

		for (final CASElement e : elements) {
			if (e.getType() != CASElemType.LIST) {
				newList.add(e);
			} else {
				addListElements(newList, (CASList) e);
			}
		}

		return newList;
	}

	private void addListElements(List<CASElement> newList, CASList listElement) {
		for (final CASElement e : listElement.elementList) {
			if (e.getType() != CASElemType.LIST) {
				newList.add(e);
			} else {
				addListElements(newList, (CASList) e);
			}
		}
	}

	public boolean detect(CASElement element) {
		return elementList.contains(element);
	}

	public boolean isFormattedLike(CASElement... elements) {
		return false;
	}

	@Override
	public CASElemType getType() {
		return CASElemType.LIST;
	}

	@Override
	String getElemRepresentation() {
		final StringBuilder builder = new StringBuilder();
		for (final CASElement e : elementList) {
			builder.append(e.getElemRepresentation());
		}
		return builder.toString();
	}

	@Override
	Integer getExpressionValue() {
		return NAN;
	}
}
