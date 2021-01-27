package br.com.easyprog.invoice.database;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "database")
public class DatabaseProperties {

    private String url;

    private String className;

    private String packageName;

    private String connectionTestQuery = "";

    private Long connectionTimeout = 120000L;

    private Long maxLifetime = 600000L;

    private Integer maxPoolSize = 10;
}
