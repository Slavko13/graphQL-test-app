package ru.test.app.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import javax.sql.DataSource;

/**
 * Configuration class for setting up the test DataSource and Flyway migration.
 */
@Configuration
public class TestConfig {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest");

    @Bean
    public DataSource dataSource() {
        if (!postgresContainer.isRunning()) {
            postgresContainer.start();
        }

        return DataSourceBuilder
                .create()
                .url(postgresContainer.getJdbcUrl())
                .username(postgresContainer.getUsername())
                .password(postgresContainer.getPassword())
                .driverClassName(postgresContainer.getDriverClassName())
                .build();
    }

    /**
     * Configuration class for Flyway migration.
     */
    @Configuration
    public static class FlywayConfig {

        /**
         * Configures and executes Flyway migration.
         */
        @Bean
        public Flyway flyway(DataSource dataSource) {
            if (!postgresContainer.isRunning()) {
                postgresContainer.start();
            }

            Flyway flyway = Flyway.configure()
                    .dataSource(dataSource)
                    .locations("classpath:db/migration")
                    .baselineOnMigrate(true)
                    .load();
            flyway.migrate();
            return flyway;
        }
    }
}

