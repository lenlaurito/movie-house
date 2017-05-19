package com.synacy.moviehouse.movie;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonObjectSerializer;

import java.io.IOException;

/**
 * Created by kenichigouang on 5/15/17.
 */
public class MovieSerializer extends JsonObjectSerializer<Movie> {
    @Override
    protected void serializeObject(Movie value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeNumberField("id", value.getId());
        jgen.writeStringField("name", value.getName());
        jgen.writeStringField("genre", String.valueOf(value.getGenre()));
        jgen.writeNumberField("duration", value.getDuration());
        jgen.writeStringField("description", value.getDescription());
    }
}
