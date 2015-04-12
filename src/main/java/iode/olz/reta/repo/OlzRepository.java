package iode.olz.reta.repo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class OlzRepository {
	private final Logger log = Logger.getLogger(OlzRepository.class);

	protected JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    protected JdbcTemplate getJdbcTemplate() {
    	return jdbcTemplate;
    }
    
    protected final Date toDate(Timestamp timestamp) {
	    return timestamp != null ? new Date(timestamp.getTime()) : null;
	}
    
    protected final Long toDateLong(Timestamp timestamp) {
	    Date d = toDate(timestamp);
	    return d != null ? d.getTime() : null;
	}

	protected final Timestamp toTimestamp(Date date) {
	    return date != null ? new Timestamp(date.getTime()) : null;
	}

	protected final Timestamp toTimestamp(Long date) {
	    return date != null ? new Timestamp(date) : null;
	}
	
	protected void setLong(int idx, Long value, int type, PreparedStatement ps) throws SQLException {
		if (value == null) {
			ps.setNull(idx, type);
		} else {
			ps.setLong(idx, value);
		}
	}
	
	protected void setDouble(int idx, Double value, int type, PreparedStatement ps) throws SQLException {
		if (value == null) {
			ps.setNull(idx, type);
		} else {
			ps.setDouble(idx, value);
		}
	}
	
	protected Timestamp getFromDateTs(Date fromDate) {
		if(fromDate == null) {
			fromDate = new Date();
		} else {
			if(log.isDebugEnabled()) {
				log.debug("fromDate is " + fromDate);
				log.debug("fromDate as timestamp is " + toTimestamp(fromDate));
			}
		}
		return toTimestamp(fromDate);
	}

}
