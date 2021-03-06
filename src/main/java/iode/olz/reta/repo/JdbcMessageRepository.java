package iode.olz.reta.repo;

import iode.olz.reta.dao.Channel;
import iode.olz.reta.dao.OlzMessage;
import iode.olz.reta.dao.OlzMessageType;
import iode.olz.reta.dao.UserTag;
import iode.olz.reta.handler.FilterMessageHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcMessageRepository extends AbstractJdbcRepository implements OlzMessageRepository {
	private final Logger log = Logger.getLogger(JdbcMessageRepository.class);
	private static final String MESSAGE_SELECT_SQL = "SELECT m.id, m.messageType, m.title, m.content, m.channelId, m.archived, m.status, m.createdAt, m.createdBy, m.updatedAt, m.updatedBy FROM messages m";
	private static final String CREATE_MESSAGE_SQL = "INSERT INTO messages (id, messageType, title, content, channelId, archived, status, createdAt, updatedAt, createdBy, updatedBy) values(UUID(?),?,?,?,UUID(?),?,?,?,?,?,?)";
	private static final String UPDATE_MESSAGE_SQL = "UPDATE messages SET messageType = ?, title = ?, content = ?, channelId = UUID(?), archived = ?, status = ?, updatedAt = ?, updatedBy = ? where id = UUID(?)"; 
	private static final String MESSAGE_ORDER_SQL = " ORDER BY createdAt DESC";
	private static final String MESSAGE_LIMIT = "50";

	@Autowired 
	ChannelRepository channelRepo;

	@Override
	public List<OlzMessage> getPageOfMessages(Date fromDate) {
		boolean archived = isArchivedFilterFlag();
		Timestamp fromDateTs = getFromDateTs(fromDate);
		return jdbcTemplate.query(
				MESSAGE_SELECT_SQL 
				+ " WHERE m.createdAt < ?"
				+ " AND m.archived = ? "
				+ MESSAGE_ORDER_SQL + " LIMIT " + MESSAGE_LIMIT, 
				new Object[] {fromDateTs, archived},			
				new DefaultOlzMessageRowMapper());		
	}

	private boolean isArchivedFilterFlag() {
		return FilterMessageHandler.filterQuery != null && FilterMessageHandler.filterQuery.contains("is:archived");
	}

	@Override
	public List<OlzMessage> getPageOfMessagesWithFilter(Date fromDate, String query) {

		boolean archived = isArchivedFilterFlag();
		String filterQueryWithoutFlags = getFilterQueryWithoutFlags();

		if(StringUtils.isEmpty(filterQueryWithoutFlags)) {
			return getPageOfMessages(fromDate);
		} else {
			Timestamp fromDateTs = getFromDateTs(fromDate);
			return jdbcTemplate.query(
					MESSAGE_SELECT_SQL + ", hashtags h "
							+ " WHERE h.messageId = m.id"
							+ " AND m.archived = ? "
							+ " AND h.tag ILIKE ? "
							+ " AND m.createdAt < ?" 
							+ MESSAGE_ORDER_SQL + " LIMIT " + MESSAGE_LIMIT, 
							new Object[] {archived, filterQueryWithoutFlags, fromDateTs},			
							new DefaultOlzMessageRowMapper());	
		}
	}

	@Override
	public boolean filterMessage(OlzMessage message) {
		
		boolean archived = isArchivedFilterFlag();
		String filterQueryWithoutFlags = getFilterQueryWithoutFlags();
		
		List<OlzMessage> results = jdbcTemplate.query(
				MESSAGE_SELECT_SQL + ", hashtags h "
						+ " WHERE h.messageId = m.id"						
						+ " AND m.id = UUID(?)"
						+ " AND archived = ? "
						+ " AND h.tag ILIKE ? ", 
						new Object[] {message.getId(), archived, filterQueryWithoutFlags},			
						new DefaultOlzMessageRowMapper());	
		return results.size() > 0;
	}

	private String getFilterQueryWithoutFlags() {
		String query = "";
		if(!StringUtils.isEmpty(FilterMessageHandler.filterQuery)) {
			query = FilterMessageHandler.filterQuery.replace("is:archived", "").trim();
		}
		return query;
	}

	@Override
	public OlzMessage getMessage(String id) {
		List<OlzMessage> messages = jdbcTemplate.query(
				MESSAGE_SELECT_SQL + " WHERE id = UUID(?)",
				new Object[]{id},
				new DefaultOlzMessageRowMapper());
		if(messages.size() == 1) {
			return messages.get(0);
		} else {
			return null;
		}
	}

	@Override
	public OlzMessage createMessage(final OlzMessage message) {
		if(log.isDebugEnabled()) {
			log.debug("> createMessage(" + message + ")");
		}
		final String id[] = {message.getId()};
		jdbcTemplate.update(
				new PreparedStatementCreator() {
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(CREATE_MESSAGE_SQL);
						id[0] = createOlzMessagePs(message, ps);
						return ps;
					}
				});	
		if(log.isDebugEnabled()) {
			log.debug("< createMessage()");
		}
		return getMessage(id[0]);
	}

	@Override
	public OlzMessage updateMessage(final OlzMessage message) {
		if(log.isDebugEnabled()) {
			log.debug("> updateMessage(" + message + ")");
		}
		this.jdbcTemplate.update(UPDATE_MESSAGE_SQL, new PreparedStatementSetter() {			
			public void setValues(PreparedStatement ps) throws SQLException {
				int idx = 0;
				ps.setInt(++idx, message.getMessageType().getTypeId());
				ps.setString(++idx, message.getTitle());
				ps.setString(++idx, message.getContent());
				ps.setString(++idx, message.getChannel() == null ? null: message.getChannel().getId());
				ps.setBoolean(++idx, message.isArchived());
				ps.setLong(++idx, message.getStatus());
				ps.setTimestamp(++idx, toTimestamp(message.getUpdatedAt()));
				ps.setString(++idx, message.getUpdatedBy().getTag());
				ps.setString(++idx, message.getId());
			}
		});
		if(log.isDebugEnabled()) {
			log.debug("< updateMessage()");
		}
		return message;
	}


	private String createOlzMessagePs(OlzMessage message, PreparedStatement ps) throws SQLException {		
		String id = message.getId();
		if(id == null) {
			id = UUID.randomUUID().toString();
		}
		Timestamp now = toTimestamp(new Date());
		int idx = 0;
		ps.setString(++idx, id);
		ps.setInt(++idx, message.getMessageType().getTypeId());
		ps.setString(++idx, message.getTitle());
		ps.setString(++idx, message.getContent());
		ps.setString(++idx, message.getChannel() == null ? null : message.getChannel().getId());
		ps.setBoolean(++idx, message.isArchived());
		ps.setLong(++idx, message.getStatus());
		ps.setTimestamp(++idx, now);
		ps.setTimestamp(++idx, now);
		ps.setString(++idx, message.getCreatedBy().getTag());
		ps.setString(++idx, message.getUpdatedBy().getTag());
		return id;
	}

	public class DefaultOlzMessageRowMapper implements RowMapper<OlzMessage> {
		public OlzMessage mapRow(ResultSet rs, int rowNum) throws SQLException {

			String channelId = rs.getString("channelId");
			Channel channel = channelId == null? null : channelRepo.getChannel(channelId);

			return new OlzMessage(
					rs.getString("id"),
					OlzMessageType.fromTypeId(rs.getInt("messageType")),
					rs.getString("title"),
					rs.getString("content"),
					channel,
					rs.getBoolean("archived"),
					rs.getLong("status"),
					null,
					toDateLong(rs.getTimestamp("createdAt")),
					new UserTag(rs.getString("createdBy")),
					toDateLong(rs.getTimestamp("updatedAt")),
					new UserTag(rs.getString("updatedBy")));
		}
	}
}