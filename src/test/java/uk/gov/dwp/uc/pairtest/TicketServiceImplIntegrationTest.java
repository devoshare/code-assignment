package uk.gov.dwp.uc.pairtest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.validation.TicketRequestValidatorService;
import uk.gov.dwp.uc.pairtest.validation.TicketRequestValidatorServiceImpl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TicketServiceImplIntegrationTest {

    @Mock
    private TicketPaymentService ticketPaymentService;

    @Mock
    private SeatReservationService seatReservationService;

    @Mock
    private TicketRequestValidatorService ticketRequestValidatorService;

    TicketService  ticketService;

    private static final Long VALID_ACC_ID = 54L;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        ticketService = new TicketServiceImpl(ticketPaymentService,
                seatReservationService, ticketRequestValidatorService);
    }

    //Calculates the correct amount for the requested tickets and makes a
    // payment request to the `TicketPaymentService`
    @Test
    @DisplayName("Integration Test: calculates correct amount and makes " +
            "payment")
    void shouldMakeCorrectPaymentIT () {
        ticketService.purchaseTickets(1L,
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1),
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1),
                new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1));

        verify(ticketPaymentService).makePayment(1L, 40);   // 25 adult + 15 child
        verifyNoMoreInteractions(ticketPaymentService);
    }

    //Calculates the correct no of seats to reserve and makes a seat
    // reservation request to the `SeatReservationService`.
    @Test
    @DisplayName("Integration Test: calculate correct num of seats and make " +
            "the seat reservations")
    void shouldMakeCorrectSeatReservationsIT () {
        ticketService.purchaseTickets(1L,
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 4),
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 5),
                new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 2));

        verify(seatReservationService).reserveSeat(1L, 9);   // 25 adult +
        verifyNoMoreInteractions(seatReservationService);

    }

}
