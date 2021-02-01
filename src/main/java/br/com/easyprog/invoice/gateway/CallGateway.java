package br.com.easyprog.invoice.gateway;

import br.com.easyprog.invoice.domain.Call;
import br.com.easyprog.invoice.domain.CallTypeEnum;

import java.time.LocalDateTime;
import java.util.List;

public interface CallGateway {
    List<Call> getCalls(final String accountNumber, final CallTypeEnum callType, final LocalDateTime startDate, final LocalDateTime endDate);
}
