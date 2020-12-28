package br.com.easyprog.invoice.gateway.database.repository;

import br.com.easyprog.invoice.domain.Bill;
import br.com.easyprog.invoice.domain.CallTypeEnum;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.LocalDate;

public class BillRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BillRepository(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Bill getBill(final String accountNumber, final CallTypeEnum callType, final LocalDate startDate, final LocalDate endDate) {
        final MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("accountNumber", accountNumber)
                .addValue("callType", callType.getType())
                .addValue("startDate", startDate)
                .addValue("endDate", endDate);

        final StringBuilder query = new StringBuilder();

        query.append("select ");
        query.append("  calls.type as callType, ");
        query.append("  ROUND(SUM(calls.debit),2) as debit, ");
        query.append("  SEC_TO_TIME(SUM(calls.billseconds)) as totalBillTime ");
        query.append("from ");
        query.append("  accounts acc ");
        query.append("  inner join cdrs calls ");
        query.append("  on calls.accountid = acc.id ");
        query.append("where ");
        query.append("  acc.number = :accountNumber ");
        query.append("and ");
        query.append("  calls.callstart between :startDate and :endDate ");
        query.append("and ");
        query.append("  calls.type = :callType ");
        query.append("group by calls.type ");

        return this.jdbcTemplate.queryForObject(
                query.toString(), params, BeanPropertyRowMapper.newInstance(Bill.class));
    }
}
