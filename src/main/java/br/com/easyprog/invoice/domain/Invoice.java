package br.com.easyprog.invoice.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Invoice {
    private Account account;
    private Bill billOutgoingCalls;
    private Bill billReceivedCalls;
    private List<Call> outgoingCalls;
    private List<Call> receivedCalls;
}
