package com.synacy.moviehouse.cinema;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by steven on 5/13/17.
 */
@Component
public class CinemaSerializer extends JsonObjectSerializer<Cinema>{
    @Override
    protected void serializeObject(Cinema cinema, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeNumberField("id", cinema.getId());
        jgen.writeStringField("name", cinema.getName());
        jgen.writeStringField("type", cinema.getType().toString());
    }
}
