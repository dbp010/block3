package de.unidue.inf.is.dbp010.db.entity;

public class Person extends Character {

	private String title;
	
	private String biografie;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBiografie() {
		return biografie;
	}

	public void setBiografie(String biografie) {
		this.biografie = biografie;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((biografie == null) ? 0 : biografie.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (biografie == null) {
			if (other.biografie != null)
				return false;
		} else if (!biografie.equals(other.biografie))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Person [title=" + title + ", biografie=" + biografie + ", getCid()=" + getCid() + ", getName()="
				+ getName() + ", getBirthplace()=" + getBirthplace() + "]";
	}
	
}