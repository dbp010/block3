package de.unidue.inf.is.dbp010.db.entity;

public class Character {

	private long cid;
	
	private String name;
	
	private Location birthplace;
	
	public long getCid() {
		return cid;
	}

	public void setCid(long cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(Location birthplace) {
		this.birthplace = birthplace;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((birthplace == null) ? 0 : birthplace.hashCode());
		result = prime * result + (int) (cid ^ (cid >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Character other = (Character) obj;
		if (birthplace == null) {
			if (other.birthplace != null)
				return false;
		} else if (!birthplace.equals(other.birthplace))
			return false;
		if (cid != other.cid)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Character [cid=" + cid + ", name=" + name + ", birthplace=" + birthplace + "]";
	}
	
}