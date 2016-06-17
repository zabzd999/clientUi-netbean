package com.hnblc.message.vo;

public class ValuePair<T, V>  implements Cloneable{

	private T defalt;
	private V strict;
	
	
	public ValuePair(T defalt, V strict) {
		this.defalt = defalt;
		this.strict = strict;
	}
	public T getDefalt() {
		return defalt;
	}
	public void setDefalt(T defalt) {
		this.defalt = defalt;
	}
	public V getStrict() {
		return strict;
	}
	public void setStrict(V strict) {
		this.strict = strict;
	}
	
	public ValuePair<T, V> getCopy() {
		try {
			ValuePair<T, V> clone = (ValuePair<T, V>) super.clone();
			return clone;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
