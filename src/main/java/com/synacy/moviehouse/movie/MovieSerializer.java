package com.synacy.moviehouse.movie;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by steven on 5/13/17.
 */
@Component
public class MovieSerializer extends JsonObjectSerializer<Movie>{
    @Override
    protected void serializeObject(Movie movie, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeNumberField("id", movie.getId());
        jgen.writeStringField("name", movie.getName());
        jgen.writeStringField("genre", movie.getGenre().toString());
        jgen.writeNumberField("duration", movie.getDuration());
        jgen.writeStringField("description", movie.getDescription());
    }
}
