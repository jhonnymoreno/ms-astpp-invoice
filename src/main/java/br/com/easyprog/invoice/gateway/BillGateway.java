package br.com.easyprog.invoice.gateway;

import br.com.easyprog.invoice.domain.Bill;
import br.com.easyprog.invoice.domain.CallTypeEnum;

import java.time.LocalDate;

public interface BillGateway {
    Bill getBill(final String accountNumber, final CallTypeEnum callType, final LocalDate startDate, final LocalDate endDate);
}
