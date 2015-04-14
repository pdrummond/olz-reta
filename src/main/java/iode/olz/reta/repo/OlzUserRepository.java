package iode.olz.reta.repo;

import iode.olz.reta.dao.OlzUser;

public interface OlzUserRepository {

	public OlzUser getUser(String userId);
	public OlzUser createUser(OlzUser user);
	public void createUserRole(String userId);
	public void setPassword(String userId, String password);
	public OlzUser getUserByEmail(String email);
}