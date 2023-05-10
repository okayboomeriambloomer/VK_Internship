package com.vk.services;

import com.vk.dao.PersonDAO;
import com.vk.model.Person;
import com.vk.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PersonDetailsService implements UserDetailsService {
    private final PersonDAO personDAO;

    @Autowired
    public PersonDetailsService(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personDAO.findByUsername(username);

        if (person == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new PersonDetails(person);
    }
}
