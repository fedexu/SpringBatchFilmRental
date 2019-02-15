package com.filmrental.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.filmrental.data.entity.Actor;

public class ActorItemProcessor implements ItemProcessor<Actor, Actor> {

    private static final Logger log = LoggerFactory.getLogger(ActorItemProcessor.class);

    @Override
    public Actor process(final Actor actor) throws Exception {
       
        Actor transformedActor = new Actor(actor.getActorId(), actor.getFirstName().toUpperCase(), actor.getLastName().toUpperCase(), actor.getLastUpdate());

        log.info("Converting {} into {}" , actor, transformedActor);

        return transformedActor;
    }

}