package com.filmrental.data.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.filmrental.data.entity.Actor;

public interface ActorRepository extends MongoRepository<Actor, String> {

    public List<Actor> findByFirstName(String firstName);
    public List<Actor> findByLastName(String lastName);
    
    
}

