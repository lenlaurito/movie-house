package com.synacy.moviehouse.schedule;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.synacy.moviehouse.utilities.DateUtils;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ScheduleSerializer extends JsonObjectSerializer<Schedule> {

	@Override
	protected void serializeObject(Schedule schedule, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeNumberField("id", schedule.getId());
		jgen.writeObjectField("movieId", schedule.getMovie().getId());
		jgen.writeObjectField("cinemaId", schedule.getCinema().getId());
		jgen.writeObjectField("startDateTime", DateUtils.formatDateAsString(schedule.getStartDateTime()));
		jgen.writeObjectField("endDateTime", DateUtils.formatDateAsString(schedule.getEndDateTime()));
	}

}
