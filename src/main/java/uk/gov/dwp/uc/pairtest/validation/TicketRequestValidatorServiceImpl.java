package uk.gov.dwp.uc.pairtest.validation;

import uk.gov.dwp.uc.pairtest.config.AppConfig;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import java.util.Arrays;
import java.util.Objects;

public class TicketRequestValidatorServiceImpl implements TicketRequestValidatorService {

    @Override
    public void validateRequest(Long accountId, TicketTypeRequest... ticketTypeRequests)
            throws InvalidPurchaseException {

        validateAccountId(accountId);
        validateTicketTypeRequest(ticketTypeRequests);
        validateMaxTickets(ticketTypeRequests);
        validateChildInfantAdult(ticketTypeRequests);
        validateAdultInfantRatio(ticketTypeRequests);
    }

    private void validateMaxTickets(TicketTypeRequest... ticketTypeRequests)
            throws InvalidPurchaseException {

        int numOfTickets;

        try {
            numOfTickets =
                    TicketCalculatorUtil.calculateTotalNumOfTickets(ticketTypeRequests);
        } catch (ArithmeticException e) {
            throw new InvalidPurchaseException("Number of tickets exceeded");
        }

        if (numOfTickets > AppConfig.MAX_TICKETS) throw new
                InvalidPurchaseException("Number of tickets exceeded");
    }

    private static void validateAdultInfantRatio(TicketTypeRequest...
                                                         ticketTypeRequest) {
        int numOfInfantTickets =
                TicketCalculatorUtil.getTicketsCountByType(TicketTypeRequest.Type.INFANT, ticketTypeRequest);
        int numOfAdultTickets =
                TicketCalculatorUtil.getTicketsCountByType(TicketTypeRequest.Type.ADULT,
                        ticketTypeRequest);

        if (numOfInfantTickets > numOfAdultTickets) throw new
                InvalidPurchaseException("Number of Infants exceed number of " +
                "adults");
    }

    private static void validateChildInfantAdult(TicketTypeRequest... ticketTypeRequest)
            throws InvalidPurchaseException {
        int numOfInfantTickets =
                TicketCalculatorUtil.getTicketsCountByType(TicketTypeRequest.Type.INFANT, ticketTypeRequest);
        int numOfAdultTickets =
                TicketCalculatorUtil.getTicketsCountByType(TicketTypeRequest.Type.ADULT, ticketTypeRequest);

        int numOfChildTickets =
                TicketCalculatorUtil.getTicketsCountByType(TicketTypeRequest.Type.CHILD, ticketTypeRequest);

        if (numOfAdultTickets == 0) throw new InvalidPurchaseException(
                "Adult tickets is required");
    }

    //Refactored methods
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
