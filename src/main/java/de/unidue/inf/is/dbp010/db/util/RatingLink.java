package de.unidue.inf.is.dbp010.db.util;

import de.unidue.inf.is.dbp010.db.entity.Rating;

public class RatingLink {

	public enum RatingType {
		character, episode, house, season
	}
	
	private Rating		rating;
	
	private RatingType	type;
	
	private Object 		ratingEntity;

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public RatingType getType() {
		return type;
	}

	public void setType(RatingType type) {
		this.type = type;
	}

	public Object getRatingEntity() {
		return ratingEntity;
	}

	public void setRatingEntity(Object ratingEntity) {
		this.ratingEntity = ratingEntity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rating == null) ? 0 : rating.hashCode());
		result = prime * result + ((ratingEntity == null) ? 0 : ratingEntity.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		RatingLink other = (RatingLink) obj;
		if (rating == null) {
			if (other.rating != null)
				return false;
		} else if (!rating.equals(other.rating))
			return false;
		if (ratingEntity == null) {
			if (other.ratingEntity != null)
				return false;
		} else if (!ratingEntity.equals(other.ratingEntity))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RatingLink [rating=" + rating + ", type=" + type + ", ratingEntity=" + ratingEntity + "]";
	}
	
	
}
