package iode.olz.reta.repo;

import iode.olz.reta.dao.LoopItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcLoopItemRepository extends OlzRepository implements LoopItemRepository {
	private final Logger log = Logger.getLogger(JdbcLoopItemRepository.class);
	private static final String CREATE_LOOP_ITEM_SQL = "INSERT INTO loopItems (id, itemType, content, archived, createdAt, updatedAt, createdBy, updatedBy) values(UUID(?),?,?,?,?,?,?,?)";

	@Override
	public void createLoopItem(final LoopItem loopItem) {
		if(log.isDebugEnabled()) {
			log.debug("createLoop(" + loopItem + ")");
		}

		jdbcTemplate.update(
				new PreparedStatementCreator() {
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(CREATE_LOOP_ITEM_SQL);
						createLoopItemPs(loopItem, ps);
						return ps;
					}
				});		
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
}