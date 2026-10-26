package uk.gov.dwp.uc.pairtest;

import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.validation.TicketCalculatorUtil;
import uk.gov.dwp.uc.pairtest.validation.TicketRequestValidatorService;

import java.util.Arrays;
import java.util.Objects;

public class TicketServiceImpl implements TicketService {


    private final TicketPaymentService ticketPaymentService;
    private final SeatReservationService seatReservationService;
    private final TicketRequestValidatorService ticketRequestValidatorService;

    //Constructor as needed and injecting other services into it
    public TicketServiceImpl(TicketPaymentService ticketPaymentServiceImpl,
                             SeatReservationService seatReservationServiceImpl, TicketRequestValidatorService ticketRequestValidatorServiceImpl) {
        ticketPaymentService = ticketPaymentServiceImpl;
        seatReservationService = seatReservationServiceImpl;
        ticketRequestValidatorService = ticketRequestValidatorServiceImpl;
    }
    /**
     * Should only have private methods other than the one below.
     */
    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {
        ticketRequestValidatorService.validateRequest(accountId, ticketTypeRequests);
        ticketPaymentService.makePayment(accountId,
                TicketCalculatorUtil.calculateTotalAmount(ticketTypeRequests));
        seatReservationService.reserveSeat(accountId,
                TicketCalculatorUtil.calculateTotalSeats(ticketTypeRequests));

    }

}
