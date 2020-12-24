package br.com.easyprog.invoice.config.database.credentials;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
public class Credential {
    private final String user;

    private final String password;
}
