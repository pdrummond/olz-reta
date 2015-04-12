package iode.olz.reta.config;

import java.net.URI;
import java.net.URISyntaxException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbConfig {

	@Bean
	public DataSource dataSource() {
		URI dbUri;
		try {
			String username = "pdrummond";
			String password = "pdrummond";
			String url = "jdbc:postgresql://localhost/olz-reta";
			String dbProperty = System.getProperty("database.url");
			if(dbProperty != null) {
				dbUri = new URI(dbProperty);

				username = dbUri.getUserInfo().split(":")[0];
				password = dbUri.getUserInfo().split(":")[1];
				url = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
			} 	

			BasicDataSource basicDataSource = new BasicDataSource();
			basicDataSource.setUrl(url);
			basicDataSource.setUsername(username);
			basicDataSource.setPassword(password);
			return basicDataSource;

		} catch (URISyntaxException e) {
			//log.error("Exception getting datasource", e);
			return null;
		}
	}

}
