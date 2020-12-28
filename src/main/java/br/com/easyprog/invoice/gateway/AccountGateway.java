package br.com.easyprog.invoice.gateway;

import br.com.easyprog.invoice.domain.Account;

public interface AccountGateway {
    Account getAccount(final String accountNumber);
}
