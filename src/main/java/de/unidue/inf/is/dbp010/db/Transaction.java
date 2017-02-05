package de.unidue.inf.is.dbp010.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager.Entity;
import de.unidue.inf.is.dbp010.db.entity.Episode;
import de.unidue.inf.is.dbp010.db.entity.House;
import de.unidue.inf.is.dbp010.db.entity.Playlist;
import de.unidue.inf.is.dbp010.db.entity.Rating;
import de.unidue.inf.is.dbp010.db.entity.Season;
import de.unidue.inf.is.dbp010.db.util.Figure;
import de.unidue.inf.is.dbp010.db.util.RatingLink;
import de.unidue.inf.is.dbp010.exception.PersistenceManagerException;
import de.unidue.inf.is.utils.DBUtil;

public class Transaction {

	public static final String	INSERT_PLAYLIST_QUERY	=	" INSERT INTO playlist (plid, name, usid) "
														+	" VALUES (?, ?, ?)";
	
	public static final String	INSERT_RATING_QUERY		=	" INSERT INTO rating (rid, usid, rating, text) "
														+	" VALUES (?, ?, ?, ?)";
	
	public static final String	LINK_RATING_TO_CHARACTER_QUERY
														=	" INSERT INTO rat_for_char (rid, cid)"
														+	" VALUES (?, ?)";
	
	public static final String	LINK_RATING_TO_EPISODE_QUERY
														=	" INSERT INTO rat_for_epi (rid, eid)"
														+	" VALUES (?, ?)";
	
	public static final String	LINK_RATING_TO_SEASON_QUERY
														=	" INSERT INTO rat_for_sea (rid, sid)"
														+	" VALUES (?, ?)";
	
	public static final String	LINK_RATING_TO_HOUSE_QUERY
														=	" INSERT INTO rat_for_house (rid, hid)"
														+	" VALUES (?, ?)";

	@SuppressWarnings("unused")
	private static final String LOAD_USER_RATING_FOR_CHARACTER_QUERY 
														= 	" SELECT * FROM rat_for_char rc "
														+ 	" RIGHT JOIN rating r ON rc.rid = r.rid WHERE rc.cid = ? AND r.usid = ? ";

	@SuppressWarnings("unused")
	private static final String LOAD_USER_RATING_FOR_EPISODE_QUERY 
														= 	" SELECT * FROM rat_for_epi re "
														+ 	" RIGHT JOIN rating r ON re.rid = r.rid WHERE re.eid = ? AND r.usid = ? ";

	@SuppressWarnings("unused")
	private static final String LOAD_USER_RATING_FOR_HOUSE_QUERY 
														= 	" SELECT * FROM rat_for_house rh "
														+ 	" RIGHT JOIN rating r ON rh.rid = r.rid WHERE rh.hid = ? AND r.usid = ? ";

	@SuppressWarnings("unused")
	private static final String LOAD_USER_RATING_FOR_SEASON_QUERY 
														= 	" SELECT * FROM rat_for_sea rs "
														+ 	" RIGHT JOIN rating r ON rs.rid = r.rid WHERE rs.sid = ? AND r.usid = ? ";

	private static final String UPDATE_RATING_QUERY
														=	" UPDATE rating SET text = ?, rating = ? WHERE rid = ? AND usid = ?";
	
	private final Connection connection;
	
	private boolean valid = false;
	
	public Transaction(Connection connection) {
		this.connection = connection;
		this.valid = true;
	}
	
	public void commit(boolean rollback) throws PersistenceManagerException {
		try {
			getConnection().commit();
		} catch (SQLException e) {
			
			if(rollback){
				try {
					getConnection().rollback();
				} catch (SQLException e1) {
					throw new PersistenceManagerException("Commit transaction failed, rollback successful", e);
				}
				
				throw new PersistenceManagerException("Commit transaction failed, rollback FAILED!", e);
			}
			
			throw new PersistenceManagerException("Commit transaction failed, rollback DISABLED!", e);
		}
		finally {
			close();
		}
	}
	
	public void close() throws PersistenceManagerException {
		try {
			getConnection().close();
		} catch (SQLException e) {
			this.valid = false;
			throw new PersistenceManagerException("Close connection for transaction failed!", e);
		}
	}

	public void updateEntity(Object entity, Entity type) throws PersistenceManagerException {
		
		switch (type) {
		case rating:
			updateRating((Rating)entity);
			break;
		default:
			throw new PersistenceManagerException("Update entity for entity type: " + type + " not defined");
		}
		
	}

	public long insertEntity(Object entity, Entity type) throws PersistenceManagerException {
		
		long nextId		=	getNextId(type);
		
		switch (type) {
		case playlist:
			return insertPlaylist(		(Playlist)		entity,		nextId);
		case rating:
			return insertRating(		(Rating)		entity, 	nextId);
		case ratinglink:
			insertRatingLink(	(RatingLink)	entity);
			return -1;
		default:
			throw new PersistenceManagerException("Insert entity for entity type: " + type + " not defined");
		}
	}

	private long insertPlaylist(Playlist playlist, long nextId) throws PersistenceManagerException {
		try {

			PreparedStatement	insertStatement = connection.prepareStatement(INSERT_PLAYLIST_QUERY, new String[]{"plid"});
			
			insertStatement.setLong(	1, nextId);
			insertStatement.setString(	2, playlist.getName());
			insertStatement.setLong(	3, playlist.getUser().getUsid());
			
			insertStatement.execute();
			
			return getGeneratedKey(insertStatement);
			
		} catch(SQLException e){
			throw new PersistenceManagerException("Save playlist: " + playlist + " failed", e);
		}
		
	}
	
	private void updateRating(Rating rating) throws PersistenceManagerException {
		try{
			
			PreparedStatement insertStatement = connection.prepareStatement(UPDATE_RATING_QUERY);
			
			insertStatement.setString(	1, rating.getText());
			insertStatement.setInt(		2, rating.getRating());
			insertStatement.setLong(	3, rating.getRid());
			insertStatement.setLong(	4, rating.getUser().getUsid());
			
			insertStatement.execute();
			
		}catch (SQLException e) {
			throw new PersistenceManagerException("Failed to update rating: " + rating, e);
		}
	}
	
	private long insertRating(Rating rating, long nextId) throws PersistenceManagerException {
		try {
			PreparedStatement insertStatement = connection.prepareStatement(INSERT_RATING_QUERY, new String[]{"rid"});
			
			insertStatement.setLong(	1,	nextId);
			insertStatement.setLong(	2, 	rating.getUser().getUsid());
			insertStatement.setInt(		3, 	rating.getRating());
			insertStatement.setString(	4, 	rating.getText());
			
			insertStatement.execute();
			
			return getGeneratedKey(insertStatement);
			
		} catch(SQLException e){
			throw new PersistenceManagerException("Save rating: " + rating + " failed", e);
		}
		
	}
	
	private long getGeneratedKey(Statement stmt) throws PersistenceManagerException {
		
		ResultSet	resultSet	=	null;
				
		try {
			
			resultSet = stmt.getGeneratedKeys();
			
			if(resultSet == null || !resultSet.next())
				throw new PersistenceManagerException("No generated key returned");
			
			return resultSet.getLong(1);
			
		} catch (SQLException e) {
			throw new PersistenceManagerException("Failed to fetch generated key", e);
		}
		finally {
			DBUtil.close(resultSet);
		}
	}

	private long getMaxId(String selectMaxIdQuery) throws PersistenceManagerException {
		
		ResultSet 	resultSet 	=	null;
		
		try {
			
			Statement	statement	=	connection.createStatement();
			resultSet 				= 	statement.executeQuery(selectMaxIdQuery);
			
			return 		(resultSet == null || !resultSet.next()) 
					?	-1
					:	resultSet.getLong(1);
			
		} catch (SQLException e) {
			throw new PersistenceManagerException("Failed to fetch max id via statement: " + selectMaxIdQuery, e);
		}
		finally {
			DBUtil.close(resultSet);
		}
	}

	private void insertRatingLink(RatingLink ratingLink) throws PersistenceManagerException {

		String 	linkRatingToEntityQuery	=	null;
		long	ratingEntityId			=	-1;
		
		switch(ratingLink.getType()){
		case	character:
			linkRatingToEntityQuery		=	LINK_RATING_TO_CHARACTER_QUERY;
			ratingEntityId				=	((Figure)ratingLink.getRatingEntity()).getCharacter().getCid();
			break;
		case	episode:
			linkRatingToEntityQuery		=	LINK_RATING_TO_EPISODE_QUERY;
			ratingEntityId				=	((Episode)ratingLink.getRatingEntity()).getEid();
			break;
		case	house:
			linkRatingToEntityQuery		=	LINK_RATING_TO_HOUSE_QUERY;
			ratingEntityId				=	((House)ratingLink.getRatingEntity()).getHid();
			break;
		case	season:
			linkRatingToEntityQuery		=	LINK_RATING_TO_SEASON_QUERY;
			ratingEntityId				=	((Season)ratingLink.getRatingEntity()).getSid();
			break;
		default:
			throw new PersistenceManagerException("Insert rating link for rating type: " + ratingLink.getType() + " not defined");
		}
		
		try{
		
			PreparedStatement insertRatingTypeStatement = connection.prepareStatement(linkRatingToEntityQuery);
			
			insertRatingTypeStatement.setLong(	1, 	ratingLink.getRating().getRid()	);
			insertRatingTypeStatement.setLong(	2, 	ratingEntityId);
			
			insertRatingTypeStatement.execute();
			
		}catch (SQLException e) {
			throw new PersistenceManagerException("Failed to save rating link: " + ratingLink, e);
		}
	}

	private Connection getConnection() throws PersistenceManagerException {
		if(this.valid)
			return connection; 
		else 
			throw new PersistenceManagerException("Transaction is not valid anymore");
	}
	
	private long getNextId(Entity type) throws PersistenceManagerException {
		
		String idColumnName		=	null;
		String tableName		=	null;
		
		switch(type){
		case rating:
			idColumnName		=	"rid";
			tableName			=	"rating";
			break;
		case playlist:
			idColumnName		=	"plid";
			tableName			=	"playlist";
			break;
		case user:
			idColumnName		=	"usid";
			tableName			=	"user";
			break;
		case ratinglink:
			return -1;
		default:
			throw new PersistenceManagerException("Get next id undefined for entity type: " + type);
		}
		
		StringBuilder sb = new StringBuilder("SELECT");
		sb.append(" max(");
		sb.append(idColumnName);
		sb.append(")");
		sb.append(" FROM ");
		sb.append(tableName);
		
		long maxId 		=	getMaxId(sb.toString()) ;
		
		return	maxId	<	1	
						?	1	
						:	maxId + 1;
	}
	
}