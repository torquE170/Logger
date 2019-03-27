package com.potato.dbAccess;

import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import com.potato.resources.Entry;

public interface EntryDao {

	/**
	 * This is the method to be used to initialize
	 * database resources i.e. connection.
	 * @param ds
	 */
	public void setDataSource(DataSource ds);

	/**
	 * This is the method to be used to create
	 * a record in the series table
	 * @param name
	 * @param season
	 * @param episode
	 * @param lastViewed
	 */
	public void create(String name, Integer season, Integer episode, Date lastViewed);

	/**
	 * This is the method to be used to list down
	 * a record from the series table corresponding
	 * to a passed Entry id.
	 * @param id
	 * @return
	 */
	public Entry getEntry(Integer id);
	
	/**
	 * This is the method used to list down
	 * entries that have at the beginning, middle or ending
	 * of their name the entered String.
	 * @param oneLine
	 * @return
	 */
	public List<Entry> listSomeEntries(String oneLine);

	/**
	 * This is a method used to list down
	 * all entries from series table.
	 */
	public List<Entry> listEntries();

	/**
	 * This is the method to be used to delete
	 * a record from the series table corresponding
	 * to a passed entry id.
	 * @param id
	 */
	public void delete(Integer id);

	/**
	 * This is the method to be used to 
	 * update a record into the series table.
	 * @param id
	 * @param name
	 * @param season
	 * @param episode
	 * @param lastViewed
	 */
	public void update(Integer id, String name, Integer season, Integer episode, Date lastViewed);

	/**
	 * This method is to be used to only
	 * update season, episode and last viewed date
	 * in series table.
	 * @param id
	 * @param season
	 * @param episode
	 * @param lastViewed
	 */
	public void update(Integer id, Integer season, Integer episode, Date lastViewed);
	
	/**
	 * Method used only to edit season and episode.
	 * @param id
	 * @param season
	 * @param episode
	 */
	public void update(Integer id, Integer season, Integer episode);
	
	/**
	 * This method is to be used to only
	 * update episode and last viewed date
	 * in series table.
	 * @param id
	 * @param episode
	 * @param lastViewed
	 */
	public void update(Integer id, Integer episode, Date lastViewed);
	
	/**
	 * Method used only to edit the date.
	 * @param id
	 * @param lastViewed
	 */
	public void update(Integer id, Date lastViewed);

	/**
	 * Method that is called at the beginning of the program
	 * and updates status column in series table.
	 * @param entryJDBCTemplateObject
	 */
	public void update(Integer id, String status);
}
