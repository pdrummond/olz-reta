package iode.olz.reta.repo;

import iode.olz.reta.dao.Channel;
import iode.olz.reta.dao.UserTag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcChannelRepository extends AbstractJdbcRepository implements ChannelRepository {
	private final Logger log = Logger.getLogger(JdbcChannelRepository.class);
	private static final String CHANNEL_SELECT_SQL = "SELECT c.id, c.messageId, c.title, c.content, c.createdAt, c.createdBy, c.updatedAt, c.updatedBy FROM channels c";
	private static final String CREATE_CHANNEL_SQL = "INSERT INTO channels (id, messageId, title, content, createdAt, updatedAt, createdBy, updatedBy) values(UUID(?),UUID(?),?,?,?,?,?,?)";
	private static final String CHANNEL_ORDER_SQL = " ORDER BY createdAt DESC";
	private static final String CHANNEL_LIMIT = "50";
	
	@Override
	public List<Channel> getPageOfChannels(Date fromDate) {
		
		Timestamp fromDateTs = getFromDateTs(fromDate);
		return jdbcTemplate.query(
				CHANNEL_SELECT_SQL + " WHERE createdAt < ?" 
				+ CHANNEL_ORDER_SQL + " LIMIT " + CHANNEL_LIMIT, 
				new Object[] {fromDateTs},			
				new DefaultChannelRowMapper());		
	}
	
	@Override
	public Channel getChannel(String id) {
		List<Channel> channels = jdbcTemplate.query(
				CHANNEL_SELECT_SQL + " WHERE id = UUID(?)",
				new Object[]{id},
				new DefaultChannelRowMapper());
		if(channels.size() == 1) {
			return channels.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Channel createChannel(final Channel channel) {
		if(log.isDebugEnabled()) {
			log.debug("> createChannel(" + channel + ")");
		}
		final String id[] = {channel.getId()};
		jdbcTemplate.update(
				new PreparedStatementCreator() {
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(CREATE_CHANNEL_SQL);
						id[0] = createChannelPs(channel, ps);
						return ps;
					}
				});	
		if(log.isDebugEnabled()) {
			log.debug("< createChannel()");
		}
		return getChannel(id[0]);
	}
	
	private String createChannelPs(Channel channel, PreparedStatement ps) throws SQLException {		
		String id = channel.getId();
		if(id == null) {
			id = UUID.randomUUID().toString();
		}
		Timestamp now = toTimestamp(new Date());
		int idx = 0;
		ps.setString(++idx, id);
		ps.setString(++idx, channel.getMessageId());
		ps.setString(++idx, channel.getTitle());
		ps.setString(++idx, channel.getContent());
		ps.setTimestamp(++idx, now);
		ps.setTimestamp(++idx, now);
		ps.setString(++idx, channel.getCreatedBy().getTag());
		ps.setString(++idx, channel.getUpdatedBy().getTag());
		return id;
	}

	public class DefaultChannelRowMapper implements RowMapper<Channel> {
		public Channel mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Channel(
					rs.getString("id"),					
					rs.getString("messageId"),
					rs.getString("title"),
					rs.getString("content"),
					toDateLong(rs.getTimestamp("createdAt")),
					new UserTag(rs.getString("createdBy")),
					toDateLong(rs.getTimestamp("updatedAt")),
					new UserTag(rs.getString("updatedBy")));
		}
	}
}