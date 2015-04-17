package iode.olz.reta.repo;

import iode.olz.reta.dao.OlzMessage;
import iode.olz.reta.dao.OlzMessageType;
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
public class JdbcMessageRepository extends AbstractJdbcRepository implements OlzMessageRepository {
	private final Logger log = Logger.getLogger(JdbcMessageRepository.class);
	private static final String MESSAGE_SELECT_SQL = "SELECT id, messageType, content, archived, createdAt, createdBy, updatedAt, updatedBy FROM messages ";
	private static final String CREATE_MESSAGE_SQL = "INSERT INTO messages (id, messageType, content, archived, createdAt, updatedAt, createdBy, updatedBy) values(UUID(?),?,?,?,?,?,?,?)";
	private static final String MESSAGE_ORDER_SQL = " ORDER BY createdAt DESC";
	private static final String MESSAGE_LIMIT = "50";
	
	@Override
	public List<OlzMessage> getPageOfMessages(Date fromDate) {
		
		Timestamp fromDateTs = getFromDateTs(fromDate);
		return jdbcTemplate.query(
				MESSAGE_SELECT_SQL + " WHERE createdAt < ?" 
				+ MESSAGE_ORDER_SQL + " LIMIT " + MESSAGE_LIMIT, 
				new Object[] {fromDateTs},			
				new DefaultOlzMessageRowMapper());		
	}

	@Override
	public OlzMessage getMessage(String id) {
		List<OlzMessage> loops = jdbcTemplate.query(
				MESSAGE_SELECT_SQL + " WHERE id = UUID(?)",
				new Object[]{id},
				new DefaultOlzMessageRowMapper());
		if(loops.size() == 1) {
			return loops.get(0);
		} else {
			return null;
		}
	}

	@Override
	public OlzMessage createMessage(final OlzMessage loopItem) {
		if(log.isDebugEnabled()) {
			log.debug("createLoop(" + loopItem + ")");
		}
		final String id[] = {loopItem.getId()};
		jdbcTemplate.update(
				new PreparedStatementCreator() {
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(CREATE_MESSAGE_SQL);
						id[0] = createOlzMessagePs(loopItem, ps);
						return ps;
					}
				});	

		return getMessage(id[0]);
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
		ps.setString(++idx, message.getContent());
		ps.setBoolean(++idx, message.isArchived());
		ps.setTimestamp(++idx, now);
		ps.setTimestamp(++idx, now);
		ps.setString(++idx, message.getCreatedBy().getTag());
		ps.setString(++idx, message.getUpdatedBy().getTag());
		return id;
	}

	public class DefaultOlzMessageRowMapper implements RowMapper<OlzMessage> {
		public OlzMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new OlzMessage(
					rs.getString("id"),
					OlzMessageType.fromTypeId(rs.getInt("messageType")),
					rs.getString("content"),
					rs.getBoolean("archived"),
					toDateLong(rs.getTimestamp("createdAt")),
					new UserTag(rs.getString("createdBy")),
					toDateLong(rs.getTimestamp("updatedAt")),
					new UserTag(rs.getString("updatedBy")));
		}
	}

}