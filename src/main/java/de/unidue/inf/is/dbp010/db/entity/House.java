package de.unidue.inf.is.dbp010.db.entity;

public class House {

	private long hid;
	
	private String name;
	
	private String words;
	
	private String coatofarmspath;
	
	private Castle seat;

	public long getHid() {
		return hid;
	}

	public void setHid(long hid) {
		this.hid = hid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWords() {
		return words;
	}

	public void setWords(String words) {
		this.words = words;
	}

	public String getCoatofarmspath() {
		return coatofarmspath;
	}

	public void setCoatofarmspath(String coatofarmspath) {
		this.coatofarmspath = coatofarmspath;
	}

	public Castle getSeat() {
		return seat;
	}

	public void setSeat(Castle seat) {
		this.seat = seat;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coatofarmspath == null) ? 0 : coatofarmspath.hashCode());
		result = prime * result + (int) (hid ^ (hid >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((seat == null) ? 0 : seat.hashCode());
		result = prime * result + ((words == null) ? 0 : words.hashCode());
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
		House other = (House) obj;
		if (coatofarmspath == null) {
			if (other.coatofarmspath != null)
				return false;
		} else if (!coatofarmspath.equals(other.coatofarmspath))
			return false;
		if (hid != other.hid)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (seat == null) {
			if (other.seat != null)
				return false;
		} else if (!seat.equals(other.seat))
			return false;
		if (words == null) {
			if (other.words != null)
				return false;
		} else if (!words.equals(other.words))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "House [hid=" + hid + ", name=" + name + ", words=" + words + ", coatofarmspath=" + coatofarmspath
				+ ", seat=" + seat + "]";
	}
}
