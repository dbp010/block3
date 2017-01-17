package de.unidue.inf.is.dbp010.db.entity;

public class User {

	private long usid;
	
	private String longin;
	
	private String Name;
	
	private String password;

	public long getUsid() {
		return usid;
	}

	public void setUsid(long usid) {
		this.usid = usid;
	}

	public String getLongin() {
		return longin;
	}

	public void setLongin(String longin) {
		this.longin = longin;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Name == null) ? 0 : Name.hashCode());
		result = prime * result + ((longin == null) ? 0 : longin.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + (int) (usid ^ (usid >>> 32));
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
		User other = (User) obj;
		if (Name == null) {
			if (other.Name != null)
				return false;
		} else if (!Name.equals(other.Name))
			return false;
		if (longin == null) {
			if (other.longin != null)
				return false;
		} else if (!longin.equals(other.longin))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (usid != other.usid)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [usid=" + usid + ", longin=" + longin + ", Name=" + Name + ", password=" + password + "]";
	}
}