package com.potato.dbAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.potato.resources.Entry;

public class EntryMapper implements RowMapper<Entry> {
	
	@Override
	public Entry mapRow(ResultSet rs, int rowNum) throws SQLException {
		Entry myEntry = new Entry();
		myEntry.setId(rs.getInt("id"));
		myEntry.setName(rs.getString("name"));
		myEntry.setSeason(rs.getInt("season"));
		myEntry.setEpisode(rs.getInt("episode"));
		myEntry.setLastViewed(rs.getDate("lastViewed"));
		myEntry.setStatus(rs.getString("status"));
		
		return myEntry;
	}
}
