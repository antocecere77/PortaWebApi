package com.portal.webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portal.webapp.model.User;
import com.portal.webapp.repository.UtentiRepository;

@Service
@Transactional(readOnly = true)
public class UtentiServiceImpl implements UtentiService
{

    @Autowired
    UtentiRepository utentiRepository;

    @Override
    public List<User> getAll()
    {
        return utentiRepository.findAll();
    }

    @Override
    public User getUser(String UserId)
    {
        return utentiRepository.findByUserId(UserId);
    }


    @Override
    public void save(User user)
    {
        utentiRepository.save(user);
    }

    @Override
    public void delete(User user)
    {
        utentiRepository.delete(user);
    }

}
