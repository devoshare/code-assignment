package uk.gov.dwp.uc.pairtest.validation;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

public interface TicketRequestValidatorService {
    public void validateRequest(Long accountId,
                                TicketTypeRequest... ticketTypeRequests);
}
