package br.com.easyprog.invoice.gateway.database;

import br.com.easyprog.invoice.domain.Bill;
import br.com.easyprog.invoice.domain.CallTypeEnum;
import br.com.easyprog.invoice.gateway.BillGateway;
import br.com.easyprog.invoice.gateway.database.repository.BillRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class BillMySqlGateway implements BillGateway {

    private final BillRepository billRepository;

    public BillMySqlGateway(final BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @Override
    public Bill getBill(String accountNumber, CallTypeEnum callType, LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Getting Bill. AccountNumber {}, callType {}, startDate {}, endDate {}", accountNumber, callType, startDate, endDate);
        return this.billRepository.getBill(accountNumber, callType, startDate, endDate);
    }
}
