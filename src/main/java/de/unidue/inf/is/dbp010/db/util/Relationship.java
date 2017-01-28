package de.unidue.inf.is.dbp010.db.util;

import de.unidue.inf.is.dbp010.db.entity.Person;

public class Relationship {
	
	Person sourcep;
	Person targetp;
	String rel_type;
	
	public Person getSourcep() {
		return sourcep;
	}
	
	public void setSourcep(Person sourcep) {
		this.sourcep = sourcep;
	}
	
	public Person getTargetp() {
		return targetp;
	}
	
	public void setTargetp(Person targetp) {
		this.targetp = targetp;
	}
	
	public String getRel_type() {
		return rel_type;
	}
	
	public void setRel_type(String rel_type) {
		this.rel_type = rel_type;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rel_type == null) ? 0 : rel_type.hashCode());
		result = prime * result + ((sourcep == null) ? 0 : sourcep.hashCode());
		result = prime * result + ((targetp == null) ? 0 : targetp.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Relationship other = (Relationship) obj;
		if (rel_type == null) {
			if (other.rel_type != null)
				return false;
		} else if (!rel_type.equals(other.rel_type))
			return false;
		if (sourcep == null) {
			if (other.sourcep != null)
				return false;
		} else if (!sourcep.equals(other.sourcep))
			return false;
		if (targetp == null) {
			if (other.targetp != null)
				return false;
		} else if (!targetp.equals(other.targetp))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Relationship [sourcep=" + sourcep + ", targetp=" + targetp + ", rel_type=" + rel_type + "]";
	}

}
