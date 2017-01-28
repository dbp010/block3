package de.unidue.inf.is.dbp010.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.unidue.inf.is.dbp010.db.entity.Animal;
import de.unidue.inf.is.dbp010.db.entity.Castle;
import de.unidue.inf.is.dbp010.db.entity.Episode;
import de.unidue.inf.is.dbp010.db.entity.Location;
import de.unidue.inf.is.dbp010.db.entity.Person;
import de.unidue.inf.is.dbp010.db.entity.Season;
import de.unidue.inf.is.dbp010.db.util.Figure;
import de.unidue.inf.is.dbp010.exception.PersistenceManagerException;
import de.unidue.inf.is.utils.DBUtil;

public class GOTDB2PersistenceManager {
	
	private static GOTDB2PersistenceManager instance;
	
	public static GOTDB2PersistenceManager getInstance(){
		if(instance == null){
			instance = new GOTDB2PersistenceManager();
		}
		return instance;
	}

	private static final String DATABASE_NAME = "mygot";
	
	private static final String LOAD_FIGURE_QUERY		= 	"SELECT cid, name, type FROM ( "
														+		"(SELECT p.pid AS cid, c.name, 'person' as type "
														+			"FROM person AS p, characters AS c WHERE c.cid = p.pid)"
														+	"UNION "
														+		"(SELECT a.aid AS cid, c.name, 'animal' AS type "
														+			"FROM animal AS a, characters AS c WHERE c.cid = a.aid)) "
														+ 	"WHERE cid = ?";
	
	private static final String LOAD_PERSON_QUERY 		= 	"SELECT * FROM characters c, person p WHERE cid = ? and c.cid = p.pid";
	private static final String LOAD_ANIMAL_QUERY 		= 	"SELECT * FROM characters c, animal a WHERE cid = ? and c.cid = a.aid";
	private static final String LOAD_CASTLE_QUERY 		= 	"SELECT * FROM castle WHERE caid = ? ";
	private static final String LOAD_EPISODE_QUERY 		= 	"SELECT * FROM episodes WHERE eid = ? ";
	private static final String LOAD_HOUSE_QUERY 		= 	"SELECT * FROM houses WHERE hid = ? ";
	private static final String LOAD_LOCATION_QUERY 	= 	"SELECT * FROM location WHERE lid = ? ";
	private static final String LOAD_RATING_QUERY 		= 	"SELECT * FROM rating WHERE rid = ? ";
	private static final String LOAD_SEASON_QUERY 		= 	"SELECT * FROM season WHERE sid = ? ";
	private static final String LOAD_USER_QUERY 		= 	"SELECT * FROM users WHERE usid = ? ";
	
	private static final String LOAD_FIGURES_QUERY		=	"SELECT cid, name, type FROM ( "
														+		"(SELECT p.pid AS cid, c.name, 'person' as type "
														+			"FROM person AS p, characters AS c WHERE c.cid = p.pid)"
														+	"UNION "
														+		"(SELECT a.aid AS cid, c.name, 'animal' AS type "
														+			"FROM animal AS a, characters AS c WHERE c.cid = a.aid)) "
														+ 	"ORDER BY cid "
														// + 	"FETCH FIRST ? ROWS ONLY"
														;
	
	public static enum Entity {
		Figure, Person, Animal, Castle, Episode, House, Location, Rating, Season, User
	}

	private GOTDB2PersistenceManager() {}
	
	private Connection getConnection() throws SQLException{
		return DBUtil.getExternalConnection(DATABASE_NAME);
	}
	
	public Object loadEntity(long id, Entity type) throws PersistenceManagerException {
		try (Connection con = getConnection()){
			return loadEntity(id, type, con);
		}catch (SQLException e) {
			throw new PersistenceManagerException ("Create connection to load entity with id:" + id + " and type: " + type + " failed", e);
		}
	}
	
	public List<Object> loadEntities(Entity type, int count) throws PersistenceManagerException {
		
		String query;
		
		switch (type) {
		case Figure:
			query = LOAD_FIGURES_QUERY;
			break;
		default:
			throw new PersistenceManagerException("Load entities not defined for entity type: " + type);
		}
		
		try (Connection connection = getConnection()){
			
			PreparedStatement pstmt = connection.prepareStatement(query);
//			pstmt.setInt(1, count);
			pstmt.setMaxRows(count);
			ResultSet resultSet = pstmt.executeQuery();
			
			if(resultSet == null || resultSet.getFetchSize() == 0)
				return Collections.emptyList();
			
			List<Object> objects = new ArrayList<>();
			
			while(resultSet.next()){
				Object object = createObject(type, resultSet, connection);
				objects.add(object);
			}
			
			return objects;
		} catch (SQLException e) {
			throw new PersistenceManagerException("Failed to load " + count + " entities of type" + type + " from database", e);
		}
	}

	private Object loadEntity(long id, Entity type, Connection connection) throws PersistenceManagerException{
		String 		loadQuery;
		
			switch(type) {
			case Figure:
				loadQuery = LOAD_FIGURE_QUERY;
				break;
			case Person:
				loadQuery = LOAD_PERSON_QUERY;
				break;
			case Animal:
				loadQuery = LOAD_ANIMAL_QUERY;
				break;
			case Castle:
				loadQuery = LOAD_CASTLE_QUERY;
				break;
			case Episode:
				loadQuery = LOAD_EPISODE_QUERY;
				break;
			case House:
				loadQuery = LOAD_HOUSE_QUERY;
				break;
			case Location:
				loadQuery = LOAD_LOCATION_QUERY;
				break;
			case Rating:
				loadQuery = LOAD_RATING_QUERY;
				break;
			case Season:
				loadQuery = LOAD_SEASON_QUERY;
				break;
			case User:
				loadQuery = LOAD_USER_QUERY;
				break;
			default:
				throw new PersistenceManagerException("Unknown entity type: " + type);
			}
			
			try {
				
				PreparedStatement loadStatement = connection.prepareStatement(loadQuery);
				loadStatement.setLong(1, id);
				
				ResultSet resultSet = loadStatement.executeQuery();
				
				if (resultSet == null || !resultSet.next())
					return null;
				
				return createObject(type, resultSet, connection);
				
			}catch (SQLException e) {
				throw new PersistenceManagerException ("Execute load entity statement: " + loadQuery + " with id:" + id + " failed", e);
			}
	}

	private Object createObject(Entity type, ResultSet resultSet, Connection connection) throws PersistenceManagerException {
			switch(type) {
			
			case Figure:
				return createCharacter(resultSet, connection);
			case Person:
				return createPerson(resultSet, connection);
			case Animal:
				return createAnimal(resultSet, connection);
			case Castle:
				return createCastle(resultSet, connection);
			case Episode:
				return createEpisode(resultSet, connection);
			case House:
				return createHouse(resultSet, connection);
			case Location:
				return createLocation(resultSet, connection);
			case Rating:
				return createRating(resultSet, connection);
			case Season:
				return createSeason(resultSet, connection);
			case User:
				return createUser(resultSet, connection);
			default:
				throw new PersistenceManagerException("Unknown entity type: " + type);
			}
	}
	
	private Object createCharacter(ResultSet resultSet, Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	private Object createHouse(ResultSet resultSet, Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	private Object createUser(ResultSet resultSet, Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	private Object createSeason(ResultSet resultSet, Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	private Object createRating(ResultSet resultSet, Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Figure createFigure(ResultSet resultSet, Connection connection) throws PersistenceManagerException{
		Figure figure = new Figure();

		try {
			long	cid 	= 	resultSet.getLong(		"CID");
			String	name	=	resultSet.getString(	"NAME");
			String	type	=	resultSet.getString(	"TYPE");
			
			figure.setCid(cid);
			figure.setName(name);
			figure.setType(type);
			
		}catch (SQLException e) {
			throw new PersistenceManagerException("Create figure object from result set failed", e);
		}
		
		return figure;
	}
	
	private Location createLocation(ResultSet resultSet, Connection connection) throws PersistenceManagerException{
		Location location = new Location();

		try {
			long	lid 	= 	resultSet.getLong(		"LID");
			String	name	=	resultSet.getString(	"NAME");
			
			location.setLid(lid);
			location.setName(name);
			
		}catch (SQLException e) {
			throw new PersistenceManagerException("Create location object from result set failed", e);
		}
		
		return location;
	}

	private Episode createEpisode(ResultSet resultSet, Connection connection) throws PersistenceManagerException{
		Episode episode = new Episode();
		
		try{
			long 	eid 			= 	resultSet.getLong(		"EID");
			long	sid				= 	resultSet.getLong(		"SID");
			String	title 			=	resultSet.getString(	"TITLE");
			int 	number			=	resultSet.getInt(		"NUMBER");
			String	summary 		=	resultSet.getString(	"SUMMARY");
			Date 	releasedate		= 	resultSet.getDate(		"RELEASEDATE");
			
			Season 	season 			= 	(Season)	loadEntity(sid, Entity.Season, connection);

			episode.setEid(eid);
			episode.setTitle(title);
			episode.setNumber(number);
			episode.setSummary(summary);
			episode.setReleasedate(releasedate);
			
			episode.setSeason(season);
			
		}catch(SQLException e){
			throw new PersistenceManagerException("Create episode object from result set failed");
		}
		
		return episode;
	}
	
	private Castle createCastle(ResultSet resultSet, Connection connection) throws PersistenceManagerException{
		Castle castle = new Castle();
		
		try{
			long 	caid 			= resultSet.getLong(	"CAID");
			String	name 			= resultSet.getString(	"NAME");
			long 	locationId 		= resultSet.getLong(	"LOCATION");
			
			Location 	location 	= 	(Location)	loadEntity(locationId, Entity.Location, connection);

			castle.setCaid(caid);
			castle.setName(name);
			castle.setLocation(location);
			
		}catch(SQLException e){
			throw new PersistenceManagerException("Create castle object from result set failed");
		}
		
		return castle;
	}

	private Animal createAnimal(ResultSet resultSet, Connection connection) throws PersistenceManagerException {
		Animal animal = new Animal();
		
		try{
			int 	cid 			= resultSet.getInt(		"CID");
			String	name 			= resultSet.getString(	"NAME");
			long 	birthplaceId 	= resultSet.getLong(	"BIRTHPLACE");
			long	ownerId			= resultSet.getLong(	"OWNER");
			
			Location 	birthplace 	= 	(Location)	loadEntity(birthplaceId, Entity.Location, connection);
			Person		owner		=	(Person)	loadEntity(ownerId, Entity.Person, connection);

			animal.setCid(cid);
			animal.setName(name);
			
			animal.setBirthplace(birthplace);
			animal.setOwner(owner);
			
		}catch(SQLException e){
			throw new PersistenceManagerException("Create animal object from result set failed");
		}
		
		return animal;
	}

	private Person createPerson(ResultSet resultSet, Connection connection) throws PersistenceManagerException {
		
		Person person = new Person();
		
		try{
			String	name 			= resultSet.getString(	"NAME");
			int 	cid 			= resultSet.getInt(		"CID");
			int 	birthplaceId 	= resultSet.getInt(		"BIRTHPLACE");
			String 	biografie 		= resultSet.getString(	"BIOGRAFIE");
			String	title			= resultSet.getString(	"TITLE");
			
			Location birthplace 	= (Location)	loadEntity(birthplaceId, Entity.Location, connection);

			person.setCid(cid);
			person.setName(name);
			person.setTitle(title);
			person.setBirthplace(birthplace);
			person.setBiografie(biografie);
			
		}catch(SQLException e){
			throw new PersistenceManagerException("Create person object from result set failed");
		}
		
		return person;
	}
}