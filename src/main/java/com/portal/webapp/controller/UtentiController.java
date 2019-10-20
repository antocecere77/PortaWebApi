package com.portal.webapp.controller;

import java.util.List;

import javax.validation.Valid;

import com.portal.webapp.service.UtentiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.portal.webapp.exception.BindingException;
import com.portal.webapp.exception.NotFoundException;
import com.portal.webapp.model.User;

@RestController
@RequestMapping(value = "/api/user")
public class UtentiController
{
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UtentiService utentiService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ResourceBundleMessageSource errMessage;

    @GetMapping(value = "/search/all")
    public List<User> getAllUser() {

        logger.info("Getting all user");
        return utentiService.getAll();
    }

    @GetMapping(value = "/search/userid/{userId}")
    public User getUser(@PathVariable("userId") String UserId) {

        logger.info("Getting user " + UserId);

        User retVal = utentiService.getUser(UserId);
        return retVal;
    }

    // ------------------- INSERT USER ------------------------------------
    @PostMapping(value = "/insert")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult bindingResult) throws BindingException
    {
        logger.info("Insert new user");
        if (bindingResult.hasErrors()) {

            String MsgErr = errMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale());
            logger.warn(MsgErr);
            throw new BindingException(MsgErr);
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        utentiService.save(user);

        HttpHeaders headers = new HttpHeaders();
        ObjectMapper mapper = new ObjectMapper();

        headers.setContentType(MediaType.APPLICATION_JSON);
        ObjectNode responseNode = mapper.createObjectNode();

        responseNode.put("code", HttpStatus.OK.toString());
        responseNode.put("message", "User " + user.getUserId() + "inserted successfully");
        return new ResponseEntity<>(responseNode, headers, HttpStatus.CREATED);
    }

    // ------------------- DELETE USER ------------------------------------
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") String userId) throws NotFoundException {
        logger.info("Deleting user " + userId);

        User user = utentiService.getUser(userId);

        if (user == null) {
            String errorMsg = String.format("User %s not found ", userId);
            logger.warn(errorMsg);
            throw new NotFoundException(errorMsg);
        }

        utentiService.delete(user);

        HttpHeaders headers = new HttpHeaders();
        ObjectMapper mapper = new ObjectMapper();

        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectNode responseNode = mapper.createObjectNode();

        responseNode.put("code", HttpStatus.OK.toString());
        responseNode.put("message", "User " + userId + " deleted successfully");
        return new ResponseEntity<>(responseNode, headers, HttpStatus.OK);
    }
}
