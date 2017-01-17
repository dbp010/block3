package de.unidue.inf.is.dbp010.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import de.unidue.inf.is.dbp010.IInitializable;
import de.unidue.inf.is.dbp010.db.entity.Animal;
import de.unidue.inf.is.dbp010.db.entity.Person;
import de.unidue.inf.is.dbp010.exception.InitializeException;
import de.unidue.inf.is.dbp010.exception.PersistenceManagerException;
import de.unidue.inf.is.dbp010.exception.ShutdownException;
import de.unidue.inf.is.utils.DBUtil;

public class GOTDB2PersistenceManager 
implements IInitializable {
	
	private static GOTDB2PersistenceManager instance;
	
	public static GOTDB2PersistenceManager getInstance(){
		if(instance == null){
			instance = new GOTDB2PersistenceManager();
		}
		return instance;
	}

	private static final String DATABASE_NAME = "mygot";
	
	private static final String LOAD_PERSON_QUERY 		= 	"SELECT * FROM characters c, person p WHERE cid = ? and c.cid = p.pid";
	private static final String LOAD_ANIMAL_QUERY 		= 	"SELECT * FROM characters c, animal a WHERE cid = ? and c.cid = a.aid";
	private static final String LOAD_CASTLE_QUERY 		= 	"SELECT * FROM castle WHERE caid = ? ";
	private static final String LOAD_EPISODE_QUERY 		= 	"SELECT * FROM episodes WHERE eid = ? ";
	private static final String LOAD_HOUSE_QUERY 		= 	"SELECT * FROM houses WHERE hid = ? ";
	private static final String LOAD_LOCATION_QUERY 	= 	"SELECT * FROM episodes WHERE lid = ? ";
	private static final String LOAD_RATING_QUERY 		= 	"SELECT * FROM rating WHERE rid = ? ";
	private static final String LOAD_SEASON_QUERY 		= 	"SELECT * FROM season WHERE sid = ? ";
	private static final String LOAD_USER_QUERY 		= 	"SELECT * FROM users WHERE usid = ? ";
	
	private PreparedStatement loadPerson;
	private PreparedStatement loadAnimal;
	private PreparedStatement loadCastle;
	private PreparedStatement loadEpisode;
	private PreparedStatement loadHouse;
	private PreparedStatement loadLocation;
	private PreparedStatement loadRating;
	private PreparedStatement loadSeason;
	private PreparedStatement loadUser;
	
	private GOTDB2PersistenceManager() {}
	
	@Override
	public void init() throws InitializeException {

		try (Connection con = getConnection()){
			
			try{
				loadPerson 		= con.prepareStatement(LOAD_PERSON_QUERY);
				loadAnimal 		= con.prepareStatement(LOAD_ANIMAL_QUERY);
				loadCastle 		= con.prepareStatement(LOAD_CASTLE_QUERY);
				loadEpisode 	= con.prepareStatement(LOAD_EPISODE_QUERY);
				loadHouse 		= con.prepareStatement(LOAD_HOUSE_QUERY);
				loadLocation 	= con.prepareStatement(LOAD_LOCATION_QUERY);
				loadRating 		= con.prepareStatement(LOAD_RATING_QUERY);
				loadSeason 		= con.prepareStatement(LOAD_SEASON_QUERY);
				loadUser 		= con.prepareStatement(LOAD_USER_QUERY);
			} catch (SQLException e) {
				throw new InitializeException("Prepare statements failed", e);
			}
			
		}catch (SQLException e) {
			throw new InitializeException("Establish database connection failed", e);
		}
		
	}

	@Override
	public void shutdown() throws ShutdownException {
	}
	
	private Connection getConnection() throws SQLException{
		return DBUtil.getExternalConnection(DATABASE_NAME);
	}
	
	public Person loadPerson(long pid) throws PersistenceManagerException {
		Person person = null;
		
		try (Connection con = getConnection()){
			
		}catch (SQLException e) {
			throw new PersistenceManagerException ("Load Person with id:" + pid + " failed", e);
		}
		
		return person;
	}
	
	private Character loadCharacter(long cid, Connection con){
		Character character = null;
		
		
		
		return character;
	}
}