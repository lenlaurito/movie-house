package com.synacy.moviehouse.schedule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

/**
 * Created by michael on 5/15/17.
 */
@Configuration
public class ScheduleConfig {

    @Bean
    SimpleDateFormat simpleDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }
}
