package br.com.easyprog.invoice.gateway.database.repository;

import br.com.easyprog.invoice.domain.Account;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class AccountRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AccountRepository(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = namedParameterJdbcTemplate;
    }

    public Account getAccount(final String accountNumber) {

        final MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("accountNumber", accountNumber);

        final StringBuilder query = new StringBuilder();

        query.append("select ");
        query.append("  acc.first_name as accountName, ");
        query.append("  acc.number as accountNumber, ");
        query.append("  GROUP_CONCAT(acc_num.number) as phoneNumber ");
        query.append("from ");
        query.append("  accounts acc ");
        query.append("  inner join dids acc_num ");
        query.append("  on acc.id = acc_num.accountid ");
        query.append("where ");
        query.append("  acc.number = : accountNumber ");
        query.append("group by acc.first_name, acc.number ");

        return this.jdbcTemplate.queryForObject(
                query.toString(), params, BeanPropertyRowMapper.newInstance(Account.class));
    }

}
