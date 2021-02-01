package br.com.easyprog.invoice.gateway.database.repository;

import br.com.easyprog.invoice.domain.Account;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AccountRepository(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = namedParameterJdbcTemplate;
    }

    public Account getAccount(final String accountNumber) {

        final MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("accountNumber", accountNumber);

        final String query = "select " +
                "  acc.first_name as accountName, " +
                "  acc.number as accountNumber, " +
                "  GROUP_CONCAT(acc_num.number) as phoneNumber " +
                "from " +
                "  accounts acc " +
                "  inner join dids acc_num " +
                "  on acc.id = acc_num.accountid " +
                "where " +
                "  acc.number = :accountNumber " +
                "group by acc.first_name, acc.number ";

        return this.jdbcTemplate.queryForObject(
                query, params, BeanPropertyRowMapper.newInstance(Account.class));
    }

}
