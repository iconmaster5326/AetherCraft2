package com.iconmaster.eqlib.recipe;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author iconmaster
 */
public class RecipeLink {

	public List<ItemDataStack> inputs;
	public List<ItemDataStack> outputs;

	public RecipeLink() {
		this.inputs = new ArrayList<ItemDataStack>();
		this.outputs = new ArrayList<ItemDataStack>();
	}

	public RecipeLink(List<ItemDataStack> inputs, List<ItemDataStack> outputs) {
		this.inputs = inputs;
		this.outputs = outputs;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 97 * hash + (this.inputs != null ? this.inputs.hashCode() : 0);
		hash = 97 * hash + (this.outputs != null ? this.outputs.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final RecipeLink other = (RecipeLink) obj;
		if (this.inputs != other.inputs && (this.inputs == null || !this.inputs.equals(other.inputs))) {
			return false;
		}
		return !(this.outputs != other.outputs && (this.outputs == null || !this.outputs.equals(other.outputs)));
	}

	@Override
	public String toString() {
		return "RecipeLink{" + "inputs=" + inputs + ", outputs=" + outputs + '}';
	}
}
