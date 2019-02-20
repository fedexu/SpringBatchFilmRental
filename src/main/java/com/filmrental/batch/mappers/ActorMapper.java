package com.filmrental.batch.mappers;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.filmrental.data.entity.Actor;

public class ActorMapper implements FieldSetMapper<Actor>  {

	@Override
	public Actor mapFieldSet(FieldSet fieldSet) throws BindException {
		Actor actor = new Actor();
		actor.setFirstName(fieldSet.readString("firstName"));
		actor.setLastName(fieldSet.readString("lastName"));
		actor.setLastUpdate(fieldSet.readDate("lastUpdate", "dd/MM/yyyy"));
		try {
			actor.setId(fieldSet.readLong("id"));
		} catch (NumberFormatException nfe) {
			actor.setId(null);
		}
		return actor;
	}

}
