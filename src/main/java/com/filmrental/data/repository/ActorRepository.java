package com.filmrental.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.filmrental.data.entity.Actor;

public interface ActorRepository extends JpaRepository<Actor, String> {

    public List<Actor> findByFirstName(String firstName);
    public List<Actor> findByLastName(String lastName);
    
}

