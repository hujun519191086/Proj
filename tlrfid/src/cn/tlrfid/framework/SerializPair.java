package cn.tlrfid.framework;

import java.io.Serializable;

import android.util.Pair;

public class SerializPair<F, S> implements Serializable {
	public F first;
	public S second;
	
	public SerializPair() {
	}
	
	/**
	 * Constructor for a Pair.
	 * 
	 * @param first the first object in the Pair
	 * @param second the second object in the pair
	 */
	public SerializPair(F first, S second) {
		this.first = first;
		this.second = second;
	}
	
	@Override
	public int hashCode() {
		return (first == null ? 0 : first.hashCode()) ^ (second == null ? 0 : second.hashCode());
	}
	
	public static <A, B> Pair<A, B> create(A a, B b) {
		return new Pair<A, B>(a, b);
	}
	
	private static final long serialVersionUID = 1L;
	
}
