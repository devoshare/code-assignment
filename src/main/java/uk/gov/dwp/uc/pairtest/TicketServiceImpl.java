package uk.gov.dwp.uc.pairtest;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import java.util.Arrays;
import java.util.Objects;

public class TicketServiceImpl implements TicketService {
    /**
     * Should only have private methods other than the one below.
     */

    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {
        validateAccountId(accountId);
        validateTicketTypeRequest(ticketTypeRequests);
    }

    private void validateAccountId(Long accountId) throws InvalidPurchaseException {
        if ((accountId == null) || (accountId <= 0))
            throw new InvalidPurchaseException("AccountID is required and " +
                    "cannot be negative or zero");
    }

    private void validateTicketTypeRequest(TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {
        if (ticketTypeRequests == null || ticketTypeRequests.length == 0)
            throw new InvalidPurchaseException("TicketTypeRequest is required");

        if (Arrays.stream(ticketTypeRequests).anyMatch(Objects::isNull))
            throw new InvalidPurchaseException("TicketTypeRequest can not " +
                    "contain null or empty values");

        if (Arrays.stream(ticketTypeRequests).anyMatch(tr -> tr.getTicketType() == null))
            throw new InvalidPurchaseException("TicketTypeRequest can not contain null or empty values");

        if (Arrays.stream(ticketTypeRequests).anyMatch(tr -> tr.getNoOfTickets() < 0))
            throw new InvalidPurchaseException("TicketTypeRequest can not " +
                    "less than 0 number of tickets");
    }

}
