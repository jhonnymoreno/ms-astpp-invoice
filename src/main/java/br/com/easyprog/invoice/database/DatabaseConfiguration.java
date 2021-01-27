package br.com.easyprog.invoice.database;

import br.com.easyprog.invoice.database.credentials.DatabaseCredential;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.TimeZone;

@Configuration
@Slf4j
@EnableTransactionManagement
public class DatabaseConfiguration {

    private final DatabaseProperties databaseProperties;

    private final DatabaseCredential databaseCredential;

    @Autowired
    public DatabaseConfiguration(final DatabaseProperties databaseProperties,
                                 final DatabaseCredential databaseCredential) {
        this.databaseProperties = databaseProperties;
        this.databaseCredential = databaseCredential;
    }

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        return new HikariDataSource(this.hikariConfig());
    }

    private HikariConfig hikariConfig() {

        System.out.println("###### HikariConfig");

        System.out.println("###### databaseCredential \n" + databaseCredential.toString());

        final HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername(this.databaseCredential.getCredential().getUser());
        hikariConfig.setPassword(this.databaseCredential.getCredential().getPassword());
        hikariConfig.setJdbcUrl(this.databaseProperties.getUrl());
        hikariConfig.setDriverClassName(this.databaseProperties.getClassName());
        hikariConfig.setConnectionTimeout(this.databaseProperties.getConnectionTimeout());
        hikariConfig.setMaxLifetime(this.databaseProperties.getMaxLifetime());
        hikariConfig.setConnectionTestQuery(this.databaseProperties.getConnectionTestQuery());
        hikariConfig.setMaximumPoolSize(this.databaseProperties.getMaxPoolSize());

        System.out.println(hikariConfig.toString());

        return hikariConfig;
    }
}
