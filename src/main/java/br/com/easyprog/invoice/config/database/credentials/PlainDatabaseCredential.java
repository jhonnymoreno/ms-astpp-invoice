package br.com.easyprog.invoice.config.database.credentials;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "database")
public class PlainDatabaseCredential implements DatabaseCredential {
    private String user;

    private String password;

    @Override
    public Credential getCredential() {
        return Credential.builder().user(this.user).password(this.password).build();
    }
}
