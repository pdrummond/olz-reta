package iode.olz.reta.repo;

import iode.olz.reta.dao.HashTag;
import iode.olz.reta.dao.HashTagType;
import iode.olz.reta.dao.HashTagValueType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcHashTagRepository extends OlzRepository implements HashTagRepository {
	private static final String HASHTAG_SELECT_SQL = "SELECT id, loopItemId, tag, tagName, longValue, doubleValue, textValue, color, hashTagType, valueType, createdAt, createdBy, updatedAt, updatedBy FROM hashTags ";
	//private final Logger log = Logger.getLogger(JdbcHashTagRepository.class);

	@Override
	public List<HashTag> getHashTags(String loopItemId) {

		List<HashTag> hashTags = jdbcTemplate.query(
				HASHTAG_SELECT_SQL + " WHERE loopItemId = UUID(?)",
				new Object[] {loopItemId},
				new DefaultHashTagRowMapper());
		return hashTags;
	}

	@Override
	public HashTag getHashTag(String hashTagId) {
		List<HashTag> hashTags = jdbcTemplate.query(
				HASHTAG_SELECT_SQL + " WHERE id = UUID(?)",
				new Object[]{hashTagId},
				new DefaultHashTagRowMapper());
		if(hashTags.size() == 1) {
			return hashTags.get(0);
		} else {
			return null;
		}
	}

	@Override
	public HashTag createHashTag(final HashTag hashTag) {
		final Timestamp now = toTimestamp(new Date());
		final String id[] = {hashTag.getId()};
		jdbcTemplate.update(
				new PreparedStatementCreator() {
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(
								"INSERT INTO hashTags (id, loopItemId, tag, tagName, longValue, doubleValue, textValue, color, hashTagType, valueType, createdAt, updatedAt, createdBy, updatedBy) "
										+ "VALUES (UUID(?),UUID(?),?,?,?,?,?,?,?,?,?,?,?,?)");
						if(id[0] == null) {
							id[0] = UUID.randomUUID().toString();
						}
						int idx = 0;
						ps.setString(++idx, id[0]);
						ps.setString(++idx, hashTag.getLoopItemId());
						ps.setString(++idx, hashTag.getTag());
						ps.setString(++idx, hashTag.getTagName());
						setLong(++idx, hashTag.getLongValue(), Types.BIGINT, ps);
						setDouble(++idx, hashTag.getDoubleValue(), Types.REAL, ps);
						ps.setString(++idx, hashTag.getTextValue());
						ps.setString(++idx, hashTag.getColor());
						ps.setInt(++idx, hashTag.getHashTagType().getTypeId());
						ps.setInt(++idx, hashTag.getValueType().getTypeId());
						ps.setTimestamp(++idx, now);
						ps.setTimestamp(++idx, now);
						ps.setString(++idx, hashTag.getCreatedBy());
						ps.setString(++idx, hashTag.getUpdatedBy());
						return ps;
					}
				});
		return getHashTag(id[0]);		
	}



	@Override
	public HashTag updateHashTag(final HashTag hashTag) {
		this.jdbcTemplate.update(
				"UPDATE hashTag SET loopItemId = UUID(?), tag = ?, tagName = ?, longValue = ?, doubleValue = ?, textValue = ?, color = ?, hashTagType = ?, valueType = ?, updatedBy = ?, updatedAt = ? WHERE id = UUID(?)", new PreparedStatementSetter() {			
					public void setValues(PreparedStatement ps) throws SQLException {
						int idx = 0;
						Timestamp now = toTimestamp(new Date());
						ps.setString(++idx, hashTag.getLoopItemId());
						ps.setString(++idx, hashTag.getTag());
						ps.setString(++idx, hashTag.getTagName());
						setLong(++idx, hashTag.getLongValue(), Types.BIGINT, ps);
						setDouble(++idx, hashTag.getDoubleValue(), Types.REAL, ps);
						ps.setString(++idx, hashTag.getTextValue());
						ps.setString(++idx, hashTag.getColor());
						ps.setInt(++idx, hashTag.getHashTagType().getTypeId());
						ps.setInt(++idx, hashTag.getValueType().getTypeId());
						ps.setString(++idx, hashTag.getUpdatedBy());
						ps.setTimestamp(++idx, now);		
						ps.setString(++idx, hashTag.getId());
					}
				});
		return hashTag;
	}

	@Override
	public void deleteHashTag(String hashTagId) {
		jdbcTemplate.update("delete from hashTag where id = UUID(?)", new Object[] {hashTagId});
	}

	@Override
	public void deleteLoopHashTags(String loopItemId) {
		jdbcTemplate.update("delete from hashTag where loopItemId = UUID(?)", new Object[] {loopItemId});		
	}

	public class DefaultHashTagRowMapper implements RowMapper<HashTag> {
		public HashTag mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new HashTag(
					rs.getString("id"),
					rs.getString("loopItemId"),
					rs.getString("tag"),
					rs.getString("tagName"),
					rs.getLong("longValue"),
					rs.getDouble("doubleValue"),
					rs.getString("textValue"),					
					rs.getString("color"),
					HashTagType.fromTypeId(rs.getInt("hashTagType")),
					HashTagValueType.fromTypeId(rs.getInt("valueType")),
					toDateLong(rs.getTimestamp("createdAt")),
					rs.getString("createdBy"),
					toDateLong(rs.getTimestamp("updatedAt")),
					rs.getString("updatedBy"));
		}
	}

	@Override
	public boolean tagExists(HashTag hashTag) {
		return jdbcTemplate.queryForObject("select count(*) from hashTag WHERE loopItemId = UUID(?) AND tag ILIKE ?", 
				new Object[] {hashTag.getLoopItemId(), hashTag.getTag()}, Integer.class) > 0;
	}

	@Override
	public void deleteAllUserEntries(String userId) {
		jdbcTemplate.update("delete from hashTag where createdBy = ?", new Object[] {userId});
	}
}
