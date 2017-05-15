package com.synacy.moviehouse.schedule;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by steven on 5/13/17.
 */
@Component
public class ScheduleSerializer extends JsonObjectSerializer<Schedule> {
    @Override
    protected void serializeObject(Schedule schedule, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeNumberField("id", schedule.getId());
        jgen.writeObjectField("movie", schedule.getMovie());
        jgen.writeObjectField("cinema", schedule.getCinema());
        //jgen.writeStringField("date", schedule.getDate().toString());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm Z");
        jgen.writeStringField("startDateTime", dateFormat.format(schedule.getStartDateTime()));
        jgen.writeStringField("endDateTime", dateFormat.format(schedule.getEndDateTime()));
    }
}
