package com.synacy.moviehouse.cinema;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonObjectSerializer;

import java.io.IOException;
import java.rmi.ConnectIOException;

/**
 * Created by kenichigouang on 5/15/17.
 */
public class CinemaSerializer extends JsonObjectSerializer<Cinema>{
    @Override
    protected void serializeObject(Cinema value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeNumberField("id", value.getId());
        jgen.writeStringField("name", value.getName());
        jgen.writeStringField("type", String.valueOf(value.getType()));
    }
}
