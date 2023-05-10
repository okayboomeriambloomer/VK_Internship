package com.vk.dao;

import com.vk.config.PasswordEncoderImpl;
import com.vk.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.Map;

@Component
public class PersonDAO {
    private final Map<String, Person> map;

    private final PasswordEncoderImpl passwordEncoder;

    @Autowired
    public PersonDAO(PasswordEncoderImpl passwordEncoder) {
        this.passwordEncoder = passwordEncoder;

        map = new HashMap<>();

        map.put("Anton", new Person("Anton", passwordEncoder.encode("123"), "ROLE_USER"));
        map.put("Chugunov", new Person("Chugunov", passwordEncoder.encode("00000"), "ROLE_USER"));
        map.put("Martov", new Person("Martov", passwordEncoder.encode("qwerty"), "ROLE_ADMIN"));
    }

    public Person findByUsername(String name) {
        return map.getOrDefault(name, null);
    }
}
