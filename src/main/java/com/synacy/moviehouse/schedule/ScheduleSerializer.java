package com.synacy.moviehouse.schedule;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by banjoe on 5/15/17.
 */

@Component
public class ScheduleSerializer extends JsonObjectSerializer<Schedule> {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void serializeObject(Schedule schedule, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        String startDateTime = dateFormat.format(schedule.getStartDateTime());
        String endDateTime = dateFormat.format(schedule.getEndDateTime());

        jgen.writeNumberField("id", schedule.getId());
        jgen.writeNumberField("movieId", schedule.getMovie().getId());
        jgen.writeNumberField("cinemaId", schedule.getCinema().getId());
        jgen.writeStringField("startDateTime", startDateTime);
        jgen.writeStringField("endDateTime", endDateTime);
    }
}
