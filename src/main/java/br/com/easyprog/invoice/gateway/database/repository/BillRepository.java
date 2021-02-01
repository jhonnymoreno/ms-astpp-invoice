package br.com.easyprog.invoice.gateway.database.repository;

import br.com.easyprog.invoice.domain.Bill;
import br.com.easyprog.invoice.domain.CallTypeEnum;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class BillRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BillRepository(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Bill getBill(final String accountNumber, final CallTypeEnum callType, final LocalDateTime startDate, final LocalDateTime endDate) {
        final MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("accountNumber", accountNumber)
                .addValue("callType", callType.getType())
                .addValue("startDate", startDate)
                .addValue("endDate", endDate);

        final String query = "select " +
                "  calls.type as callType, " +
                "  ROUND(SUM(calls.debit),2) as debit, " +
                "  SEC_TO_TIME(SUM(calls.billseconds)) as totalBillTime " +
                "from " +
                "  accounts acc " +
                "  inner join cdrs calls " +
                "  on calls.accountid = acc.id " +
                "where " +
                "  acc.number = :accountNumber " +
                "and " +
                "  calls.callstart between :startDate and :endDate " +
                "and " +
                "  calls.type = :callType " +
                "group by calls.type ";

        return this.jdbcTemplate.queryForObject(
                query, params, BeanPropertyRowMapper.newInstance(Bill.class));
    }
}
