package br.com.easyprog.invoice.gateway;

import br.com.easyprog.invoice.domain.Bill;
import br.com.easyprog.invoice.domain.CallTypeEnum;

import java.time.LocalDateTime;

public interface BillGateway {
    Bill getBill(final String accountNumber, final CallTypeEnum callType, final LocalDateTime startDate, final LocalDateTime endDate);
}
