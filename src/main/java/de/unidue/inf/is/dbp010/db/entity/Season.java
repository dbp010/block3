package de.unidue.inf.is.dbp010.db.entity;

import java.sql.Date;

public class Season {

	private long sid;
	
	private int number;
	
	private int numberofe;
	
	private Date startdate;

	public long getSid() {
		return sid;
	}

	public void setSid(long sid) {
		this.sid = sid;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getNumberofe() {
		return numberofe;
	}

	public void setNumberofe(int numberofe) {
		this.numberofe = numberofe;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
		result = prime * result + numberofe;
		result = prime * result + (int) (sid ^ (sid >>> 32));
		result = prime * result + ((startdate == null) ? 0 : startdate.hashCode());
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
		Season other = (Season) obj;
		if (number != other.number)
			return false;
		if (numberofe != other.numberofe)
			return false;
		if (sid != other.sid)
			return false;
		if (startdate == null) {
			if (other.startdate != null)
				return false;
		} else if (!startdate.equals(other.startdate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Season [sid=" + sid + ", number=" + number + ", numberofe=" + numberofe + ", startdate=" + startdate
				+ "]";
	}
}
