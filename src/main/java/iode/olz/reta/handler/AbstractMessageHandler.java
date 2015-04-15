package iode.olz.reta.handler;

import iode.olz.reta.dao.UserTag;

import java.security.Principal;


public class AbstractMessageHandler {

	protected UserTag getUserTagFromPrincipal(Principal principal) {
		return new UserTag("@" + principal.getName());
	}
	
}
