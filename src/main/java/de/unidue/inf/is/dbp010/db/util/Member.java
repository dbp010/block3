package de.unidue.inf.is.dbp010.db.util;

import de.unidue.inf.is.dbp010.db.entity.Episode;
import de.unidue.inf.is.dbp010.db.entity.House;
import de.unidue.inf.is.dbp010.db.entity.Person;

public class Member {

	Person 	person;
	House	house;
	Episode episode_from;
	Episode episode_to;
	
	public Person getPerson() {
		return person;
	}
	
	public void setPerson(Person person) {
		this.person = person;
	}
	
	public House getHouse() {
		return house;
	}
	
	public void setHouse(House house) {
		this.house = house;
	}
	
	public Episode getEpisode_from() {
		return episode_from;
	}
	
	public void setEpisode_from(Episode episode_from) {
		this.episode_from = episode_from;
	}
	
	public Episode getEpisode_to() {
		return episode_to;
	}
	
	public void setEpisode_to(Episode episode_to) {
		this.episode_to = episode_to;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((episode_from == null) ? 0 : episode_from.hashCode());
		result = prime * result + ((episode_to == null) ? 0 : episode_to.hashCode());
		result = prime * result + ((house == null) ? 0 : house.hashCode());
		result = prime * result + ((person == null) ? 0 : person.hashCode());
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
		Member other = (Member) obj;
		if (episode_from == null) {
			if (other.episode_from != null)
				return false;
		} else if (!episode_from.equals(other.episode_from))
			return false;
		if (episode_to == null) {
			if (other.episode_to != null)
				return false;
		} else if (!episode_to.equals(other.episode_to))
			return false;
		if (house == null) {
			if (other.house != null)
				return false;
		} else if (!house.equals(other.house))
			return false;
		if (person == null) {
			if (other.person != null)
				return false;
		} else if (!person.equals(other.person))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Member [person=" + person + ", house=" + house + ", episode_from=" + episode_from + ", episode_to="
				+ episode_to + "]";
	}
	
}
