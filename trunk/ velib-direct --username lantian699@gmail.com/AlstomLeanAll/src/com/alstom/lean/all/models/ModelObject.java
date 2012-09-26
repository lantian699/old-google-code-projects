package com.alstom.lean.all.models;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;


public abstract class ModelObject implements Serializable {

	private static final long serialVersionUID = -8030699721740683823L;

/*	public abstract void loadIdFromNode(Node node);

	*//**
	 * Set all properties of an object from a node
	 * 
	 * @param node
	 *            node to parse
	 * @return Subclass of ModelObject to populate with subnodes
	 *//*
	public abstract Map<Class<? extends ModelObject>, List<Node>> loadFromNode(Node node);

	public abstract Object getId();

	@Override
	public boolean equals(Object aThat) {
		if (this == aThat)
			return true;
		if (!(aThat instanceof ModelObject))
			return false;
		ModelObject that = (ModelObject) aThat;
		return that.getId().equals(this.getId());
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 89 * hash + (this.getId() == null ? 0 : this.getId().hashCode());
		return hash;
	}*/
}
