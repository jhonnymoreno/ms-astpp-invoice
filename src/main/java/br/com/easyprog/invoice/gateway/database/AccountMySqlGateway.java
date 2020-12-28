package br.com.easyprog.invoice.gateway.database;

import br.com.easyprog.invoice.domain.Account;
import br.com.easyprog.invoice.gateway.AccountGateway;
import br.com.easyprog.invoice.gateway.database.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccountMySqlGateway implements AccountGateway {

    private final AccountRepository accountRepository;

    public AccountMySqlGateway(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account getAccount(String accountNumber) {
        log.info("Getting Account information {}", accountNumber);
        return this.accountRepository.getAccount(accountNumber);
    }
}
