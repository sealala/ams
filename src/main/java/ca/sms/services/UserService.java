package ca.sms.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.jasypt.springsecurity.StrongPasswordEncoder;

import ca.sms.models.User;
import ca.sms.models.UserRepository;

@Component
public class UserService {
	@Autowired
	private UserRepository userRepos;
	/**
	 * Algorithm: SHA-256.
	 * Salt: Random.
	 * Salt size: 16 bytes.
	 * Iterations: 100000.
	 */
	private StrongPasswordEncoder encryptor = new StrongPasswordEncoder();
	static final List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();
	private final static String passwordPattern = "^.*(?=.{6,20})(?=.*\\d)(?=.*[a-zA-Z]).*$";
//	private final static String emailPattern = "[]";
	
	public boolean validate(String username, String password) {
		try {
			User user = getUser(username);
			if(isPasswordValid(user.getPassword(), password)) {
				try {
					AUTHORITIES.add(new SimpleGrantedAuthority(user.getRole()));
					Authentication auth = new UsernamePasswordAuthenticationToken(username, password, AUTHORITIES);
					SecurityContextHolder.getContext().setAuthentication(auth);
					return true;
				} catch(AuthenticationException e) {
					return false;
				}
			}
			return false;
		}catch(NullPointerException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean isPasswordValid(String encryptedPassword, String rawPassword) {
		return encryptor.isPasswordValid(encryptedPassword, rawPassword, null);
	}
	
	public void encryptPassword(User user) {
		user.setPassword(encryptor.encodePassword(user.getPassword(), null));
	}
	
	public void save(User user) {
		userRepos.save(user);
	}
	
	public User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userRole = getUserRole();
		if(userRole.equals("ROLE_ANONYMOUS")) return null;
		
		User currentUser = userRepos.findByUsername(auth.getName());
		currentUser.setPassword(null);
		return currentUser;
	}
	
	public String getUserRole() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Iterator<? extends GrantedAuthority> iterator = auth.getAuthorities().iterator();
		if(iterator.hasNext()) return iterator.next().getAuthority();
		return null;
	}
	
	public User getUser(String username) {
		return userRepos.findByUsername(username);
	}
	
	public User getUser(String firstName, String lastName) {
		return userRepos.findByFirstNameAndLastName(firstName, lastName);
	}
	
	public boolean validatePasswordPattern(String password) {
		return Pattern.matches(passwordPattern, password);
	}

	public void logout() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}

	public void updatePassword(User currentUser, String newpassword) {
		currentUser.setPassword(newpassword);
		encryptPassword(currentUser);
		save(currentUser);
	}

	public void assignUser(User user, User detailedUser) {
		user.setDetailedUser(detailedUser);
		userRepos.save(user);
	}

	public List<User> getUser(String firstName, String lastName, String role) {
		return userRepos.findByFirstNameOrLastNameAndRoleRegex(firstName, lastName, role);
	}
}
