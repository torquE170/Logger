package com.potato.dbAccess;

import java.util.*;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import com.potato.resources.Entry;

public class EntryJDBCTemplate implements EntryDao {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	@Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	@Override
	public void create(String name, Integer season, Integer episode, Date lastViewed) {
		String SQL = "INSERT INTO series (name, season, episode, lastViewed) VALUES(?, ?, ?, ?)";
		jdbcTemplateObject.update(SQL, name, season, episode, lastViewed);
		System.out.println("We have new Record with:\nName: " + name + "\nSeason: " + season + "\nEpisode: " + episode + "\nLast Viewed Date: " + lastViewed);

	}
	@Override
	public Entry getEntry(Integer id) {
		String SQL = "SELECT * FROM series WHERE id = ?";
		Entry myEntry = (Entry) jdbcTemplateObject.queryForObject(SQL, new Object[] {id}, new EntryMapper());
		return myEntry;
	}
	@Override
	public List<Entry> listSomeEntries(String oneLine) {
		String SQL = "SELECT * FROM series WHERE name LIKE '%" + oneLine + "%'";
		List<Entry> myEntry = jdbcTemplateObject.query(SQL, new EntryMapper());
		return myEntry;
	}
	@Override
	public List<Entry> listEntries() {
		String SQL = "SELECT * FROM series";
		List<Entry> myEntries = jdbcTemplateObject.query(SQL, new EntryMapper());
		return myEntries;
	}
	@Override
	public void delete(Integer id) {
		String SQL = "DELETE FROM series WHERE id = ?";
		jdbcTemplateObject.update(SQL, id);
		System.out.println("Deleted Record with id: " + id);
	}
	@Override
	public void update(Integer id, String name, Integer season, Integer episode, Date lastViewed) {
		String SQL = "UPDATE series SET name = ?, season = ?, episode = ?, lastViewed = ? WHERE id = ?";
		jdbcTemplateObject.update(SQL, name, season, episode, lastViewed, id);
		System.out.println("Updated Record with id: " + id);
	}
	@Override
	public void update(Integer id, Integer season, Integer episode) {
		String SQL = "UPDATE series SET season = ?, episode = ? WHERE id = ?";
		jdbcTemplateObject.update(SQL, season, episode, id);
		System.out.println("Updated Record with id: " + id);
	}
	@Override
	public void update(Integer id, Integer season, Integer episode, Date lastViewed) {
		String SQL = "UPDATE series SET season = ?, episode = ?, lastViewed = ? WHERE id = ?";
		jdbcTemplateObject.update(SQL, season, episode, lastViewed, id);
		System.out.println("Updated Record with id: " + id);
	}
	@Override
	public void update(Integer id, Integer episode, Date lastViewed) {
		String SQL = "UPDATE series SET episode = ?, lastViewed = ? WHERE id = ?";
		jdbcTemplateObject.update(SQL, episode, lastViewed, id);
		System.out.println("Updated Record with id: " + id);
	}
	@Override
	public void update(Integer id, Date lastViewed) {
		String SQL = "UPDATE series SET lastViewed = ? WHERE id = ?";
		jdbcTemplateObject.update(SQL, lastViewed, id);
		System.out.println("Updated Record with id: " + id);
	}
	@Override
	public void update(Integer id, String status) {
		String SQL = " UPDATE series SET status = ? WHERE id = ?";
		jdbcTemplateObject.update(SQL, status, id);
	}
}
