package com.portal.webapp.security;

import com.portal.webapp.model.User;
import com.portal.webapp.service.UtentiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService
{
	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Autowired
	UtentiService utentiService;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		String errorMsg = "";
		
		if (userId == null || userId.length() < 2) {
			errorMsg = "User id not found";
			logger.warn(errorMsg);
	    	throw new UsernameNotFoundException(errorMsg);
		} 
		
		//UtentiSecurity utente = this.GetHttpValue(UserId);
		UtentiSecurity user = getUserSecurity(userId);
		
		if (user == null) {
			errorMsg = String.format("User %s not found", userId);
			logger.warn(errorMsg);
			
			throw new UsernameNotFoundException(errorMsg);
		}
		
		UserBuilder builder = null;
		builder = org.springframework.security.core.userdetails.User.withUsername(user.getUserId());
		builder.disabled((user.getAttivo().equals("Si") ? false : true));
		builder.password(user.getPassword());
		
		String[] profile = user.getRuoli()
				 .stream().map(a -> "ROLE_" + a).toArray(String[]::new);
		builder.authorities(profile);
		
		return builder.build();
	}

	private UtentiSecurity getUserSecurity(String userId) {
		User user = utentiService.getUser(userId);
		UtentiSecurity utentiSecurity = new UtentiSecurity();

		utentiSecurity.setId(user.getId());
		utentiSecurity.setUserId(user.getUserId());
		utentiSecurity.setPassword(user.getPassword());
		utentiSecurity.setAttivo(user.getAttivo());
		utentiSecurity.setRuoli(user.getRuoli());
		return utentiSecurity;
	}
}
	