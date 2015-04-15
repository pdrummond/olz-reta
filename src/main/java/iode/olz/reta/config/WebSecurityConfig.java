package iode.olz.reta.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableWebMvcSecurity //"Mvc" gives us Thymeleaf csrf support
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.jdbcAuthentication()
		.passwordEncoder(passwordEncoder())
		.dataSource(dataSource)
		.rolePrefix("")
		.usersByUsernameQuery("SELECT userId AS username, password, enabled FROM users WHERE userId=?")               
		.authoritiesByUsernameQuery("SELECT userId AS username, authority FROM authorities WHERE userId = ?");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http		
		.addFilterAfter(new CsrfTokenGeneratorFilter(), CsrfFilter.class)	
		.authorizeRequests()		
		.antMatchers(
				"/", "/oric/**", "/img/**", "/js/**", "/css/**", "/images/**", "/videos/**", "/fonts/**", 				
				"/login", "/register", "/register-success", "/register-failure", "/register-failure-no-invite",
				"/confirm", "/confirm/**", "/confirm-success", "/confirm-failure", "/confirm-failure-expired",
				"/register-interest", "/register-interest-success", "/register-interest-failure", "/user/interest",
				"/forgot-password", "/reset-password/**",
				"/about", "/welcome", "/terms", "/privacy").permitAll()
				.anyRequest().authenticated()	
				.and()
				.formLogin()
				.loginPage("/login")							
				.permitAll()
				.and()
				.csrf();
	}
}