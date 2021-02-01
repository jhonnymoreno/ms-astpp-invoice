package br.com.easyprog.invoice.gateway.database;

import br.com.easyprog.invoice.domain.Call;
import br.com.easyprog.invoice.domain.CallTypeEnum;
import br.com.easyprog.invoice.gateway.CallGateway;
import br.com.easyprog.invoice.gateway.database.repository.CallRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class CallMySqlGateway implements CallGateway {

    private final CallRepository callRepository;

    public CallMySqlGateway(final CallRepository callRepository) {
        this.callRepository = callRepository;
    }

    @Override
    public List<Call> getCalls(String accountNumber, CallTypeEnum callType, LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Getting Calls. AccountNumber {}, callType {}, startDate {}, endDate {}", accountNumber, callType, startDate, endDate);
        return this.callRepository.getCalls(accountNumber, callType, startDate, endDate);
    }
}
