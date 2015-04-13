package iode.olz.reta.repo;

import iode.olz.reta.dao.LoopItem;
import iode.olz.reta.dao.OlzItem;
import iode.olz.reta.dao.OlzItemType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcOlzItemRepository extends OlzRepository implements OlzItemRepository {
	//private final Logger log = Logger.getLogger(JdbcLoopItemRepository.class);
	private static final String OLZ_ITEM_SELECT_SQL = "SELECT l.id, l.itemType, l.content, l.archived, l.createdAt, l.createdBy, l.updatedAt, l.updatedBy FROM loopItems l ";
	private static final String OLZ_ITEM_ORDER_SQL = " ORDER BY createdAt DESC";
	private static final String OLZ_ITEM_LIMIT = "50";
	
	@Override
	public List<OlzItem> getPageOfItems(Date fromDate) {
		
		//TODO: This will need to join with the activityItem table when it's implemented
		
		Timestamp fromDateTs = getFromDateTs(fromDate);
		return jdbcTemplate.query(
				OLZ_ITEM_SELECT_SQL + " WHERE createdAt < ?" 
				+ OLZ_ITEM_ORDER_SQL + " LIMIT " + OLZ_ITEM_LIMIT, 
				new Object[] {fromDateTs},			
				new DefaultOlzItemRowMapper());		
	}	
	
	public class DefaultOlzItemRowMapper implements RowMapper<OlzItem> {
		public OlzItem mapRow(ResultSet rs, int rowNum) throws SQLException {
			OlzItemType itemType = OlzItemType.fromTypeId(rs.getInt("itemType"));
			switch(itemType) {
			case LOOP_ITEM: {
				return new LoopItem(
						rs.getString("id"),
						itemType,
						rs.getString("content"),
						rs.getBoolean("archived"),
						toDateLong(rs.getTimestamp("createdAt")),
						rs.getString("createdBy"),
						toDateLong(rs.getTimestamp("updatedAt")),
						rs.getString("updatedBy"));
			}
			default: throw new RuntimeException("Unsupported itemType: " + itemType);
			}
		}
	}
	
}