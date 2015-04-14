package iode.olz.reta.repo;

import iode.olz.reta.dao.LoopItem;
import iode.olz.reta.dao.OlzItemType;

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
public class JdbcLoopItemRepository extends OlzRepository implements LoopItemRepository {
	private final Logger log = Logger.getLogger(JdbcLoopItemRepository.class);
	private static final String LOOP_ITEM_SELECT_SQL = "SELECT l.id, l.itemType, l.content, l.archived, l.createdAt, l.createdBy, l.updatedAt, l.updatedBy FROM loopItems l ";
	private static final String CREATE_LOOP_ITEM_SQL = "INSERT INTO loopItems (id, itemType, content, archived, createdAt, updatedAt, createdBy, updatedBy) values(UUID(?),?,?,?,?,?,?,?)";

	@Override
	public LoopItem getLoopItem(String id) {
		List<LoopItem> loops = jdbcTemplate.query(
				LOOP_ITEM_SELECT_SQL + " WHERE id = UUID(?)",
				new Object[]{id},
				new DefaultLoopItemRowMapper());
		if(loops.size() == 1) {
			return loops.get(0);
		} else {
			return null;
		}
	}

	@Override
	public LoopItem createLoopItem(final LoopItem loopItem) {
		if(log.isDebugEnabled()) {
			log.debug("createLoop(" + loopItem + ")");
		}
		final String id[] = {loopItem.getId()};
		jdbcTemplate.update(
				new PreparedStatementCreator() {
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(CREATE_LOOP_ITEM_SQL);
						id[0] = createLoopItemPs(loopItem, ps);
						return ps;
					}
				});	

		return getLoopItem(id[0]);
	}

	private String createLoopItemPs(LoopItem loopItem, PreparedStatement ps) throws SQLException {		
		String id = loopItem.getId();
		if(id == null) {
			id = UUID.randomUUID().toString();
		}
		Timestamp now = toTimestamp(new Date());
		int idx = 0;
		ps.setString(++idx, id);
		ps.setInt(++idx, loopItem.getItemType().getTypeId());
		ps.setString(++idx, loopItem.getContent());
		ps.setBoolean(++idx, loopItem.isArchived());
		ps.setTimestamp(++idx, now);
		ps.setTimestamp(++idx, now);
		ps.setString(++idx, loopItem.getCreatedBy());
		ps.setString(++idx, loopItem.getUpdatedBy());
		return id;
	}

	public class DefaultLoopItemRowMapper implements RowMapper<LoopItem> {
		public LoopItem mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new LoopItem(
					rs.getString("id"),
					OlzItemType.fromTypeId(rs.getInt("itemType")),
					rs.getString("content"),
					rs.getBoolean("archived"),
					toDateLong(rs.getTimestamp("createdAt")),
					rs.getString("createdBy"),
					toDateLong(rs.getTimestamp("updatedAt")),
					rs.getString("updatedBy"));
		}
	}

}