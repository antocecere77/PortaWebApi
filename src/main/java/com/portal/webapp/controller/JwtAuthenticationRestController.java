package com.portal.webapp.controller;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import com.portal.webapp.security.JwtTokenUtil;
import com.portal.webapp.utils.jwt.JwtTokenResponse;
import com.portal.webapp.exception.AuthenticationException;
import com.portal.webapp.utils.jwt.JwtTokenRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
//@CrossOrigin(origins = "http://localhost:4200")
public class JwtAuthenticationRestController {

    @Value("${security.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsService userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationRestController.class);

    @PostMapping(value = "${security.uri}")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtTokenRequest authenticationRequest) throws AuthenticationException {

        logger.info("Authenticate user and generate token");
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        logger.warn(String.format("Token %s craeted for user %s", token, userDetails.getUsername()));
        return ResponseEntity.ok(new JwtTokenResponse(token));
    }

    @RequestMapping(value = "${security.uri}", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {

        String authToken = request.getHeader(tokenHeader);
        final String token = authToken.substring(7);

        if (jwtTokenUtil.canTokenBeRefreshed(token)) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new JwtTokenResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @ExceptionHandler({ AuthenticationException.class })
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    private void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            logger.warn("User %s disabled", username);
            throw new AuthenticationException("User %s disabled", e);
        }
        catch (BadCredentialsException e) {
            logger.warn("User/password invalid for user %s", username);
            throw new AuthenticationException("User/password invalid for user " + username, e);
        }
        catch (Throwable th) {
            logger.warn("Generic authentication error for user %s", username);
            logger.warn("Generic authentication error for user %s", username);
            throw new AuthenticationException("Generic authentication error for user " + username, th);
        }
    }
}