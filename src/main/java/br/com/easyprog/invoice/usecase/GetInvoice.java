package br.com.easyprog.invoice.usecase;

import br.com.easyprog.invoice.domain.*;
import br.com.easyprog.invoice.gateway.AccountGateway;
import br.com.easyprog.invoice.gateway.BillGateway;
import br.com.easyprog.invoice.gateway.CallGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class GetInvoice {

    private final AccountGateway accountGateway;
    private final BillGateway billGateway;
    private final CallGateway callGateway;

    public GetInvoice(AccountGateway accountGateway, BillGateway billGateway, CallGateway callGateway) {
        this.accountGateway = accountGateway;
        this.billGateway = billGateway;
        this.callGateway = callGateway;
    }

    public Invoice execute(String accountNumber, LocalDateTime startDate, LocalDateTime endDate) {

        log.info("Getting Invoice. AccountNumber {}, startDate {}, endDate {}", accountNumber, startDate, endDate);
        final Account account = this.accountGateway.getAccount(accountNumber);
        final Bill billOutgoingCalls = this.billGateway.getBill(accountNumber, CallTypeEnum.OUTGOING_CALL, startDate, endDate);
        final Bill billReceivedCalls = this.billGateway.getBill(accountNumber, CallTypeEnum.RECEIVED_CALL, startDate, endDate);
        final List<Call> outgoingCalls = this.callGateway.getCalls(accountNumber, CallTypeEnum.OUTGOING_CALL, startDate, endDate);
        final List<Call> receivedCalls = this.callGateway.getCalls(accountNumber, CallTypeEnum.RECEIVED_CALL, startDate, endDate);

        return Invoice.builder()
                .account(account)
                .billOutgoingCalls(billOutgoingCalls)
                .billReceivedCalls(billReceivedCalls)
                .outgoingCalls(outgoingCalls)
                .receivedCalls(receivedCalls)
                .build();
    }
}
