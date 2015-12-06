package es.uam.eps.tfg.CAS.CASTypes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implements a list of CASElements
 *
 * @author Rodrigo de Blas
 *
 */
public class CASList extends CASElement implements Iterable<CASElement> {

	private List<CASElement> elementList;

	public CASList() {
		this.elementList = new ArrayList<>();
	}

	public CASList(List<CASElement> elementList) {
		this.elementList = elementList;
	}

	/**
	 * @param index
	 *            position of the element to get
	 * @return the element at given position
	 */
	public CASElement get(int index) {
		return elementList.get(index);
	}

	/**
	 * Adds an element to the end of the list
	 *
	 * @param elem
	 *            element to add.
	 * @return true if success, false if error
	 */
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

	/**
	 * Creates a new list from given elements. It's the same result of creating
	 * a new list and add the elements.
	 *
	 * @param elements
	 *            list of elements to concat together
	 * @return a new list with the given elements.
	 */
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

	/**
	 * Adds the elements of a list to another one.
	 *
	 * @param oldList
	 *            list where add the new one
	 * @param listElement
	 *            list to add
	 * @return new list with the new elements to add
	 */
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

	/**
	 * Sets an element into a position of the list. It shifts the other elements
	 * one position more.
	 *
	 * @param index
	 *            position to place the element
	 * @param elem
	 *            element to set
	 * @return element in previous position
	 */
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

	/**
	 * Sets the elements from a list to another one.
	 *
	 * @param elementList
	 *            current list
	 * @param listToSet
	 *            list to be set
	 * @param index
	 *            index to place the elements from the list.
	 * @return new list with the elements of another in the given position
	 */
	private CASList setListElements(CASList elementList, CASList listToSet, int index) {
		final CASList previousSublist = elementList.subList(0, index);
		final CASList postSublist = elementList.subList(index, elementList.size());

		return concat(previousSublist, listToSet, postSublist);
	}

	/**
	 * Removes an element from the list
	 *
	 * @param index
	 *            position of the element to remove
	 * @return previous element in the position
	 */
	public CASElement remove(int index) {
		return elementList.remove(index);
	}

	/**
	 * Move one element to another one's position. It shifts one position more
	 * the rest of the elements.
	 *
	 * @param fromIndex
	 *            initial position
	 * @param toIndex
	 *            final position
	 * @return the element in 'fromIndex' position
	 */
	public CASElement moveElement(int fromIndex, int toIndex) {
		final CASElement elemToMove = elementList.remove(fromIndex);
		elementList.add(toIndex, elemToMove);
		return elemToMove;
	}

	/**
	 * Returns a sublist from the list.
	 * 
	 * @param fromIndex
	 *            initial position
	 * @param toIndex
	 *            final position
	 * @return sublist from initial to final position
	 */
	public CASList subList(int fromIndex, int toIndex) {
		return new CASList(elementList.subList(fromIndex, toIndex));
	}

	/**
	 * @param element
	 *            element to check if it's in the list or not
	 * @return true if the list contains the element, false if not.
	 */
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
