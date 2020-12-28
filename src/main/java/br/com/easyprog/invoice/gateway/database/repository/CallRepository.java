package br.com.easyprog.invoice.gateway.database.repository;

import br.com.easyprog.invoice.domain.Call;
import br.com.easyprog.invoice.domain.CallTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Repository
public class CallRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CallRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Call> getCalls(final String accountNumber, final CallTypeEnum callType, final LocalDate startDate, final LocalDate endDate) {

        final MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("accountNumber", accountNumber)
                .addValue("callType", callType.getType())
                .addValue("startDate", startDate)
                .addValue("endDate", endDate);

        final StringBuilder query = new StringBuilder();

        query.append("select ");
        query.append("  DATE_FORMAT(calls.callstart,'%d/%m/%Y') as callDate, ");
        query.append("  DATE_FORMAT(calls.callstart, '%H:%i:%s') as callHour, ");
        query.append("  SUBSTRING_INDEX(calls.callerid, ' ', 1) as callerId, ");
        query.append("  calls.callednum as calledNum, ");
        query.append("  SEC_TO_TIME(calls.billseconds) billTime, ");
        query.append("  ROUND(calls.debit,2) as debit ");
        query.append("from ");
        query.append("  cdrs calls ");
        query.append("where ");
        query.append("  calls.number = :accountNumber ");
        query.append("and ");
        query.append("  calls.callstart between :startDate and :endDate ");
        query.append("and ");
        query.append("  calls.type = :callType ");
        query.append("and ");
        query.append("  calls.billseconds > 0 ");

        final ResultSetExtractor<List<Call>> resultSetExtractor =
                JdbcTemplateMapperFactory.newInstance()
                        .addKeys("callDate", "callHour", "callerId", "calledNum")
                        .newResultSetExtractor(Call.class);

        return this.jdbcTemplate.query(query.toString(), params, resultSetExtractor);
    }
}
