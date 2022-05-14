package ru.laboratory.blps.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
@Configuration
public class DatabaseConfiguration {

    private final DataSource dataSource;


    @EventListener(ApplicationReadyEvent.class)
    public void initTs()
    {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("search_engine.sql"));
        resourceDatabasePopulator.execute(dataSource);
        resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("search_index.sql"));
        resourceDatabasePopulator.execute(dataSource);
    }
}
