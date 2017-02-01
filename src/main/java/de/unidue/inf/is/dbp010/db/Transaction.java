package de.unidue.inf.is.dbp010.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager.Entity;
import de.unidue.inf.is.dbp010.db.entity.Playlist;
import de.unidue.inf.is.dbp010.exception.PersistenceManagerException;

public class Transaction {

	public static final String	INSERT_PLAYLIST_QUERY	=	"INSERT INTO playlist (plid, name, usid) VALUES ((select max(plid) + 1 FROM playlist ), ?, ?)";
			
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
			try {
				getConnection().close();
			} catch (SQLException e) {
				this.valid = false;
				throw new PersistenceManagerException("Close connection for transaction failed!", e);
			}
		}
	}
	
	public void saveEntity(Object entity, Entity type) throws PersistenceManagerException {
		
		switch (type) {
		case playlist:
			savePlaylist((Playlist)entity);
			break;

		default:
			break;
		}
		
	}

	private void savePlaylist(Playlist playlist) throws PersistenceManagerException {
		
//		String	nextIdStmt	=	getNextIdStatement(Entity.playlist);
		
		try {
			PreparedStatement insertStatement = connection.prepareStatement(INSERT_PLAYLIST_QUERY);
			insertStatement.setString(	1, playlist.getName());
			insertStatement.setLong(	2, playlist.getUser().getUsid());
			
			insertStatement.execute();
		} catch(SQLException e){
			throw new PersistenceManagerException("Save playlist: " + playlist + " failed");
		}
		
	}
	
//	private long getMaxId(Entity type) throws PersistenceManagerException {
//		
//		String	idColumnName 	=	null;
//		String	tableName		=	null;
//		
//		switch(type) {
//		case	playlist:
//			idColumnName		=	"plid";
//			tableName			=	"playlist";
//			break;
//		case	rating:
//			idColumnName		=	"rid";
//			tableName			=	"rating";
//			break;
//		default:
//			throw new PersistenceManagerException("Get max id for entity type: " + type + " not defined");
//		}
//		
//		StringBuilder	sb	=	new	StringBuilder("SLECET MAX(")
//		.append(idColumnName)
//		.append(") FROM ")
//		.append(tableName);
//		
//		try {
//			
//			ResultSet resultSet = executeQuery(sb.toString());
//			
//			if(resultSet == null)
//				return 0;
//			
//			return resultSet.getLong(idColumnName);
//			
//		} catch (SQLException e) {
//			throw new PersistenceManagerException("Get max id for entity type: " + type + " failed", e);
//		}
//	}
	
//	private ResultSet executeQuery(String query) throws SQLException, PersistenceManagerException {
//		Statement stmt = getConnection().createStatement();
//		ResultSet resultSet = stmt.executeQuery(query);
//		return (!resultSet.next()) ? null : resultSet;
//	}
	
	private Connection getConnection() throws PersistenceManagerException {
		if(this.valid)
			return connection; 
		else 
			throw new PersistenceManagerException("Transaction is not valid anymore");
	}
	
}
