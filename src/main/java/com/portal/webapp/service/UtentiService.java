package com.portal.webapp.service;

import java.util.List;

import com.portal.webapp.model.User;

public interface UtentiService
{
    public List<User> getAll();

    public User getUser(String UserId);

    public void save(User utente);

    public void delete(User utente);

}
