package com.synacy.moviehouse.schedule;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonObjectSerializer;

import java.io.IOException;

/**
 * Created by kenichigouang on 5/15/17.
 */
public class ScheduleSerializer extends JsonObjectSerializer<Schedule> {
    @Override
    protected void serializeObject(Schedule value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeNumberField("id", value.getId());
        jgen.writeNumberField("movie", value.getMovie().getId());
        jgen.writeNumberField("cinema", value.getCinema().getId());
        jgen.writeStringField("startDateTime", String.valueOf(value.getStartDateTime()));
        jgen.writeStringField("endDateTime", String.valueOf(value.getEndDateTime()));
    }
}
