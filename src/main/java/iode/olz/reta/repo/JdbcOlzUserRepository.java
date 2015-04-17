package iode.olz.reta.repo;

import iode.olz.reta.dao.OlzUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcOlzUserRepository extends AbstractJdbcRepository implements OlzUserRepository {
	private static final String USER_SELECT_SQL = "SELECT userId, email, firstName, surname, createdAt, updatedAt, enabled FROM users ";
	private static final String CREATE_USER_SQL = "INSERT INTO users (userId, email, firstName, surname, createdAt, updatedAt, enabled) values(?,?,?,?,?,?,?)";
	
	@Override
	public OlzUser getUser(String userId) {
		List<OlzUser> users = jdbcTemplate.query(
				USER_SELECT_SQL + " WHERE userId ILIKE ?",
				new Object[]{ userId },
				new DefaultOlzUserRowMapper());
		if(users.size() == 1) {
			return users.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public OlzUser getUserByEmail(String email) {
		List<OlzUser> users = jdbcTemplate.query(
				USER_SELECT_SQL + " WHERE email ILIKE ?",
				new Object[]{email},
				new DefaultOlzUserRowMapper());
		if(users.size() == 1) {
			return users.get(0);
		} else {
			return null;
		}	
	}

	@Override
	public OlzUser createUser(final OlzUser user) {
		final Date now = new Date();
		jdbcTemplate.update(
				new PreparedStatementCreator() {
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(CREATE_USER_SQL);
						int idx = 0;
						ps.setString(++idx, user.getUserId());						
						ps.setString(++idx, user.getEmail());
						ps.setString(++idx, user.getFirstName());
						ps.setString(++idx, user.getSurname());
						ps.setTimestamp(++idx, toTimestamp(now));
						ps.setTimestamp(++idx, toTimestamp(now));
						ps.setBoolean(++idx, user.getEnabled());
						return ps;
					}
				});
		return user;
	}

	@Override
	public void createUserRole(String userId) {
		jdbcTemplate.update("insert into authorities values (?, 'ROLE_USER')", new Object[] {userId});
	}

	@Override
	public void setPassword(String userId, String password) {
		jdbcTemplate.update("update users set password = ? where userId = ?", new Object[] {password, userId});
	}

	public class DefaultOlzUserRowMapper implements RowMapper<OlzUser> {
		public OlzUser mapRow(ResultSet rs, int rowNum) throws SQLException {
			OlzUser user = new OlzUser(
					rs.getString("userId"), 
					rs.getString("email"),
					rs.getString("firstName"),
					rs.getString("surname"),
					toDateLong(rs.getTimestamp("createdAt")),
					toDateLong(rs.getTimestamp("updatedAt")),
					rs.getBoolean("enabled"));
			return user;
		}	
	}

}
