package de.unidue.inf.is.dbp010.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.unidue.inf.is.dbp010.db.entity.Animal;
import de.unidue.inf.is.dbp010.db.entity.Castle;
import de.unidue.inf.is.dbp010.db.entity.Episode;
import de.unidue.inf.is.dbp010.db.entity.House;
import de.unidue.inf.is.dbp010.db.entity.Location;
import de.unidue.inf.is.dbp010.db.entity.Person;
import de.unidue.inf.is.dbp010.db.entity.Season;
import de.unidue.inf.is.dbp010.db.util.Belonging;
import de.unidue.inf.is.dbp010.db.util.Figure;
import de.unidue.inf.is.dbp010.db.util.Member;
import de.unidue.inf.is.dbp010.db.util.Relationship;
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
														+ 	"WHERE cid = ? ";
	
	private static final String LOAD_PERSON_QUERY 		= 	"SELECT * FROM characters c LEFT JOIN person p ON c.cid = p.pid WHERE c.cid = ? ";
	private static final String LOAD_ANIMAL_QUERY 		= 	"SELECT * FROM characters c LEFT JOIN animal a ON c.cid = a.aid WHERE c.cid = ? ";
	private static final String LOAD_CASTLE_QUERY 		= 	"SELECT * FROM castle WHERE caid = ? ";
	private static final String LOAD_EPISODE_QUERY 		= 	"SELECT * FROM episodes WHERE eid = ? ";
	private static final String LOAD_HOUSE_QUERY 		= 	"SELECT * FROM houses WHERE hid = ? ";
	private static final String LOAD_LOCATION_QUERY 	= 	"SELECT * FROM location WHERE lid = ? ";
	private static final String LOAD_RATING_QUERY 		= 	"SELECT * FROM rating WHERE rid = ? ";
	private static final String LOAD_SEASON_QUERY 		= 	"SELECT * FROM season WHERE sid = ? ";
	private static final String LOAD_USER_QUERY 		= 	"SELECT * FROM users WHERE usid = ? ";
	private static final String LOAD_PLAYLIST_QUERY 	= 	"SELECT * FROM playlist p WHERE p.plid = ? ";
	
	private static final String LOAD_FIGURES_QUERY		=	"SELECT cid, name, type FROM ( "
														+		"(SELECT p.pid AS cid, c.name, 'person' as type "
														+			"FROM person AS p, characters AS c WHERE c.cid = p.pid)"
														+	"UNION "
														+		"(SELECT a.aid AS cid, c.name, 'animal' AS type "
														+			"FROM animal AS a, characters AS c WHERE c.cid = a.aid)) "
														+ 	"ORDER BY cid ASC "
														;
	
	private static final String LOAD_HOUSES_QUERY		=	"SELECT * FROM houses h "
														+	"ORDER BY h.hid DESC ";
	
	private static final String LOAD_SEASONS_QUERY		=	"SELECT * FROM season s "
														+	"ORDER BY s.sid DESC ";
	
	private static final String LOAD_PLAYLISTS_QUERY	=	"SELECT * FROM playlist p "
														+	"ORDER BY p.plid DESC ";

	private static final String LOAD_ANIMALS_BY_OWNER_QUERY	
														=	"SELECT * FROM characters c LEFT JOIN animal a ON c.cid = a.aid WHERE a.owner = ? ";

	// the table's name  is really person_re'A'lationship with the typo
	private static final String LOAD_RELATIONSHIPS_BY_SOURCEPID_QUERY	
														= 	"SELECT * FROM person_realationship r WHERE r.sourcepid = ? ";  
		
	private static final String LOAD_MEMBERS_BY_PID_QUERY
														=	"SELECT * FROM member_of m WHERE m.pid = ?";
	
	private static final String LOAD_MEMBERS_BY_HID_QUERY
														=	"SELECT * FROM member_of m WHERE m.hid = ?";
	
	private static final String LOAD_BELONGINGS_BY_HID_QUERY
														=	"SELECT * FROM belongs_to b WHERE b.hid = ?";
	
	// dynamic usable auxiliary SQL snipplets
//	private static final String FETCH_ROW_COUNT_SNIPPLET 		= "FETCH FIRST ? ROWS ONLY";
	
	public static enum Entity {
		Figure, Person, Animal, Castle, Episode, House, Location, Rating, Season, User, Playlist, Relationship, Member, Belonging
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
	
	/**
	 * Loads all entities of given type
	 * @param type The entity type
	 * @return List of entity objects
	 * @throws PersistenceManagerException 
	 */
	public List<Object> loadEntities(Entity type) throws PersistenceManagerException {
		return loadEntities(type, -1);
	}
	
	/**
	 * Loads <code>count</code> entities of given type. If count is <= -1, all
	 * entities will be loaded.
	 * @param type The entity type
	 * @param count Number of entities to load
	 * @return List of entity objects
	 * @throws PersistenceManagerException 
	 */
	public List<Object> loadEntities(Entity type, int count) throws PersistenceManagerException {
		
		if(count == 0) return Collections.emptyList();
		
		String query;
		
		switch (type) {
		case Figure:
			query = LOAD_FIGURES_QUERY;
			break;
		case House:
			query = LOAD_HOUSES_QUERY;
			break;
		case Season:
			query = LOAD_SEASONS_QUERY;
			break;
		case Playlist:
			query = LOAD_PLAYLISTS_QUERY;
			break;
		default:
			throw new PersistenceManagerException("Load entities not defined for entity type: " + type);
		}
		
		try (Connection connection = getConnection()){
//			PreparedStatement pstmt;			
			
			if(count >= 1){
				// This is strangely NOT working ... :(
				//{
//				query += FETCH_ROW_COUNT_SNIPPLET;
//				pstmt = connection.prepareStatement(query);
//				pstmt.setString(1, String.valueOf(count));
				//}
				query += "FETCH FIRST " + count + " ROWS ONLY";
			}
//			else{
//				pstmt = connection.prepareStatement(query);
//			}
			
//			ResultSet resultSet = executeLoadQuery(query, count, connection);
			
			ResultSet resultSet = executeQuery(query, connection);
			return loadEntities(resultSet, type, connection);
			
		} catch (SQLException e) {
			throw new PersistenceManagerException("Failed to load " + ((count < 0) ? "all" : count ) + " entities of type " + type + " from database", e);
		}
	}

	private ResultSet executeQuery(String query, Connection connection) throws SQLException {
		Statement stmt = connection.createStatement();
		ResultSet resultSet = stmt.executeQuery(query);
		return (resultSet == null || !resultSet.next()) ? null : resultSet;
	}

	private List<Object> loadEntities(ResultSet resultSet, Entity type, Connection connection) throws PersistenceManagerException {
		try {

			if (resultSet == null) return Collections.emptyList();
		
			List<Object> objects = new ArrayList<>();
			
			do {
				Object object = createObject(type, resultSet, connection);
				objects.add(object);
			} while (resultSet.next());

			return objects;
			
		} catch (SQLException e) {
			throw new PersistenceManagerException(
					"Failed to load entities of type " + type + " from database", e);
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
			case Playlist:
				loadQuery = LOAD_PLAYLIST_QUERY;
				break;
			default:
				throw new PersistenceManagerException("Load entity not defined for entity type: " + type);
			}

			ResultSet resultSet = executeLoadQuery(loadQuery, id, connection);
			return createObject(type, resultSet, connection);
	}

	private ResultSet executeLoadQuery(String loadQuery, long id, Connection connection) throws PersistenceManagerException {
		try {

			PreparedStatement loadStatement = connection.prepareStatement(loadQuery);
			loadStatement.setLong(1, id);
			
			ResultSet resultSet = loadStatement.executeQuery();
			
			return (resultSet == null || !resultSet.next()) ? null : resultSet;	
			
		}catch (SQLException e) {
			throw new PersistenceManagerException ("Execute load entity statement: " + loadQuery + " with id:" + id + " failed", e);
		}
	}
	
	private Object createObject(Entity type, ResultSet resultSet, Connection connection) throws PersistenceManagerException {
		
		if(resultSet == null)	return null;
		
		
		switch(type) {
			
			case Figure:
				return createFigure(resultSet, connection);
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
			case Relationship:
				return createRelationship(resultSet, connection);
			case Member:
				return createMember(resultSet, connection);
			case Belonging:
				return createBelonging(resultSet, connection);
			default:
				throw new PersistenceManagerException("Unknown entity type: " + type);
		}
	}

	private Belonging createBelonging(ResultSet resultSet, Connection connection) throws PersistenceManagerException {
		Belonging	belonging	=	new Belonging();
		
		try {
			
			long	lid				= 	resultSet.getLong(		"LID");
			long	hid				= 	resultSet.getLong(		"HID");
			long	episode_from_id	=	resultSet.getLong(		"EPISODE_FROM");
			long	episode_to_id	=	resultSet.getLong(		"EPISODE_TO");
			
			Location	location		= 	(Location)	loadEntity(lid,				Entity.Location, 	connection);
			House		house			= 	(House) 	loadEntity(hid,				Entity.House, 		connection);
			Episode		episode_from	=	(Episode)	loadEntity(episode_from_id,	Entity.Episode, 	connection);
			Episode		episode_to		=	(Episode)	loadEntity(episode_to_id,	Entity.Episode, 	connection);
			
			belonging.setLocation(location);
			belonging.setHouse(house);
			belonging.setEpisode_from(episode_from);
			belonging.setEpisode_to(episode_to);
			
		}catch (SQLException e) {
			throw new PersistenceManagerException("Create member object from result set failed", e);
		}
		
		return belonging;
	}

	private Member createMember(ResultSet resultSet, Connection connection) throws PersistenceManagerException {
		Member 		member = new Member();

		try {
			
			long	pid				= 	resultSet.getLong(		"PID");
			long	hid				= 	resultSet.getLong(		"HID");
			long	episode_from_id	=	resultSet.getLong(		"EPISODE_FROM");
			long	episode_to_id	=	resultSet.getLong(		"EPISODE_TO");
			
			Person	person			= 	(Person)	loadEntity(pid,				Entity.Person,	connection);
			House	house			= 	(House) 	loadEntity(hid,				Entity.House,	connection);
			Episode	episode_from	=	(Episode)	loadEntity(episode_from_id,	Entity.Episode,	connection);
			Episode	episode_to		=	(Episode)	loadEntity(episode_to_id,	Entity.Episode,	connection);
			
			member.setPerson(person);
			member.setHouse(house);
			member.setEpisode_from(episode_from);
			member.setEpisode_to(episode_to);
			
		}catch (SQLException e) {
			throw new PersistenceManagerException("Create member object from result set failed", e);
		}
		
		return member;
	}

	private Relationship createRelationship(ResultSet resultSet, Connection connection) throws PersistenceManagerException{
		Relationship relationship = new Relationship();

		try {
			
			long	sourcepid		= 	resultSet.getLong(		"SOURCEPID");
			long	targetpid		= 	resultSet.getLong(		"TARGETPID");
			String	rel_type		=	resultSet.getString(	"REL_TYPE");
						
			Person sourcep			= 	(Person) loadEntity(sourcepid, Entity.Person,	connection);
			Person targetp			= 	(Person) loadEntity(targetpid, Entity.Person,	connection);
			
			relationship.setSourcep(sourcep);
			relationship.setTargetp(targetp);
			relationship.setRel_type(rel_type);
			
		}catch (SQLException e) {
			throw new PersistenceManagerException("Create house object from result set failed", e);
		}
		
		return relationship;
	}

	private House createHouse(ResultSet resultSet, Connection connection) throws PersistenceManagerException {
		House house = new House();

		try {
			long	hid 			= 	resultSet.getLong(		"HID");
			String	name			=	resultSet.getString(	"NAME");
			String	words			=	resultSet.getString(	"WORDS");
			String	coatofarmspath	=	resultSet.getString(	"COATOFARMSPATH");
			long	seatId			=	resultSet.getLong(		"SEAT");
			
			Castle seat 			=	(Castle) loadEntity(seatId, Entity.Castle ,	connection);
			
			house.setHid(hid);
			house.setName(name);
			house.setWords(words);
			house.setCoatofarmspath(coatofarmspath);
			house.setSeat(seat);
			
		}catch (SQLException e) {
			throw new PersistenceManagerException("Create house object from result set failed", e);
		}
		
		return house;
	}

	private Object createUser(ResultSet resultSet, Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	private Season createSeason(ResultSet resultSet, Connection connection) throws PersistenceManagerException {
		Season season = new Season();

		try {
			long	sid 			= 	resultSet.getLong(		"SID");
			int		number			=	resultSet.getInt(		"NUMBER");
			int		numberofe		=	resultSet.getInt(		"NUMBEROFE");
			Date	startdate		=	resultSet.getDate(		"STARTDATE");
			
			season.setSid(sid);
			season.setNumber(number);
			season.setNumberofe(numberofe);
			season.setStartdate(startdate);
			
		}catch (SQLException e) {
			throw new PersistenceManagerException("Create season object from result set failed", e);
		}
		
		return season;
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
			throw new PersistenceManagerException("Create episode object from result set failed", e);
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
			throw new PersistenceManagerException("Create castle object from result set failed", e);
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
			
			Location 	birthplace 	= 	(Location)	loadEntity(birthplaceId, Entity.Location, 	connection);
			Person		owner		=	(Person)	loadEntity(ownerId, Entity.Person, 			connection);

			animal.setCid(cid);
			animal.setName(name);
			
			animal.setBirthplace(birthplace);
			animal.setOwner(owner);
			
		}catch(SQLException e){
			throw new PersistenceManagerException("Create animal object from result set failed", e);
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
			throw new PersistenceManagerException("Create person object from result set failed", e);
		}
		
		return person;
	}

	public List<Object> loadRelationshipsBySourcepid(long sourcepid) throws PersistenceManagerException {
		try (Connection connection = getConnection()){
			
			ResultSet resultSet = executeLoadQuery(LOAD_RELATIONSHIPS_BY_SOURCEPID_QUERY, sourcepid, connection);
			return loadEntities(resultSet, Entity.Relationship, connection);
			
		}catch (SQLException e) {
			throw new PersistenceManagerException ("Create connection to load entities of type: " 
													+ Entity.Relationship + " for sourcepid: " + sourcepid + " failed", e);
		}
	}

	public List<Object> loadAnimalsByOwner(long owner) throws PersistenceManagerException {
		try (Connection connection = getConnection()){
			
			ResultSet resultSet = executeLoadQuery(LOAD_ANIMALS_BY_OWNER_QUERY, owner, connection);
			return loadEntities(resultSet, Entity.Animal, connection);
			
		}catch (SQLException e) {
			throw new PersistenceManagerException ("Create connection to load entities of type: " 
													+ Entity.Animal + " for owner: " + owner + " failed", e);
		}
	}
	
	public List<Object> loadMembersByPid(long pid) throws PersistenceManagerException {
		try (Connection connection = getConnection()){
			
			ResultSet resultSet = executeLoadQuery(LOAD_MEMBERS_BY_PID_QUERY, pid, connection);
			return loadEntities(resultSet, Entity.Member, connection);
			
		}catch (SQLException e) {
			throw new PersistenceManagerException ("Create connection to load entities of type: " 
													+ Entity.Member + " for pid: " + pid + " failed", e);
		}
	}
	
	public List<Object> loadMembersByHid(long hid) throws PersistenceManagerException {
		try (Connection connection = getConnection()){
			
			ResultSet resultSet = executeLoadQuery(LOAD_MEMBERS_BY_HID_QUERY, hid, connection);
			return loadEntities(resultSet, Entity.Member, connection);
			
		}catch (SQLException e) {
			throw new PersistenceManagerException ("Create connection to load entities of type: " 
													+ Entity.Member + " for hid: " + hid + " failed", e);
		}
	}

	public List<Object> loadBelongingsByHid(long hid) throws PersistenceManagerException{
		try (Connection connection = getConnection()){
			
			ResultSet resultSet = executeLoadQuery(LOAD_BELONGINGS_BY_HID_QUERY, hid, connection);
			return loadEntities(resultSet, Entity.Belonging, connection);
			
		}catch (SQLException e) {
			throw new PersistenceManagerException ("Create connection to load entities of type: " 
													+ Entity.Belonging + " for hid: " + hid + " failed", e);
		}
		
	}
}