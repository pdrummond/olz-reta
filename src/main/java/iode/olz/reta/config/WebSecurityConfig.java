package iode.olz.reta.config;


//@Configuration
class WebSecurityConfig { //extends GlobalAuthenticationConfigurerAdapter {

	/*@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService());
	}

	@Bean
	UserDetailsService userDetailsService() {
		
		
		return new UserDetailsService() {			
			@Override
			public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
				List<GrantedAuthority> authList = AuthorityUtils.createAuthorityList("USER", "write");
				return new User("pdrummond", "pdrummond", true, true, true, true, authList);				
			}
		};
	}*/
}