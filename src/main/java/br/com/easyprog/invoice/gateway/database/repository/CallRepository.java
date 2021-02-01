package br.com.easyprog.invoice.gateway.database.repository;

import br.com.easyprog.invoice.domain.Call;
import br.com.easyprog.invoice.domain.CallTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Repository
public class CallRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CallRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Call> getCalls(final String accountNumber, final CallTypeEnum callType, final LocalDateTime startDate, final LocalDateTime endDate) {

        final MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("accountNumber", accountNumber)
                .addValue("callType", callType.getType())
                .addValue("startDate", startDate)
                .addValue("endDate", endDate);

        final String query = "select " +
                "  DATE_FORMAT(calls.callstart,'%d/%m/%Y') as callDate, " +
                "  DATE_FORMAT(calls.callstart, '%H:%i:%s') as callHour, " +
                "  SUBSTRING_INDEX(calls.callerid, ' ', 1) as callerId, " +
                "  calls.callednum as calledNum, " +
                "  SEC_TO_TIME(calls.billseconds) billTime, " +
                "  ROUND(calls.debit,2) as debit " +
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
                "and " +
                "  calls.billseconds > 0 ";

        final ResultSetExtractor<List<Call>> resultSetExtractor =
                JdbcTemplateMapperFactory.newInstance()
                        .addKeys("callDate", "callHour", "callerId", "calledNum")
                        .newResultSetExtractor(Call.class);

        return this.jdbcTemplate.query(query, params, resultSetExtractor);
    }
}
