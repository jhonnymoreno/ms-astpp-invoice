package br.com.easyprog.invoice.domain;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Invoice {
    private Account account;
    private Bill billOutgoingCalls;
    private Bill billReceivedCalls;
    private List<Call> outgoingCalls;
    private List<Call> receivedCalls;
}
