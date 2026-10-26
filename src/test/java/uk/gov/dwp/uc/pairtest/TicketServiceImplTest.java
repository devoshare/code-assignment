package uk.gov.dwp.uc.pairtest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.validation.TicketRequestValidatorService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class TicketServiceImplTest {
    private TicketService ticketService;

    @Mock
    private TicketPaymentService ticketPaymentService;

    @Mock
    private SeatReservationService seatReservationService;

    @Mock
    private TicketRequestValidatorService ticketRequestValidatorService;

    private static final Long VALID_ACC_ID = 54L;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        ticketService = new TicketServiceImpl(ticketPaymentService,
                seatReservationService, ticketRequestValidatorService);
    }

    @Test
    @DisplayName("Should make payment and reserve for seats after validation " +
            "and calculations")
    void shouldPayAndReserveForValidRequest() {
        ticketService.purchaseTickets(VALID_ACC_ID,
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2),
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1),
                new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1));

        verify(ticketPaymentService).makePayment(VALID_ACC_ID, 65);   // 2x25 + 1x15 + 0
        verify(seatReservationService).reserveSeat(VALID_ACC_ID, 3); // infant has no seat
        verifyNoMoreInteractions(ticketPaymentService, seatReservationService);
    }

    @Test
    @DisplayName("Should validate first, then pay and then reserve the seats")
    void shouldCallMethodsInOrder() {
        ticketService.purchaseTickets(VALID_ACC_ID, new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1));
        InOrder inOrder = inOrder(ticketRequestValidatorService, ticketPaymentService,
                seatReservationService);
        inOrder.verify(ticketRequestValidatorService).validateRequest(eq(VALID_ACC_ID),
                any(TicketTypeRequest[].class));
        inOrder.verify(ticketPaymentService).makePayment(VALID_ACC_ID, 25);
        inOrder.verify(seatReservationService).reserveSeat(VALID_ACC_ID, 1);
    }

    @Test
    @DisplayName("Should not pay or reserve seats if validation of request " +
            "fails")
    void shouldNotPayAndReserveForInvalidRequest() {
        TicketTypeRequest infantOnly = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1);
        doThrow(new InvalidPurchaseException("rejected"))
                .when(ticketRequestValidatorService).validateRequest(VALID_ACC_ID, infantOnly);

        InvalidPurchaseException thrown = assertThrows(InvalidPurchaseException.class,
                () -> ticketService.purchaseTickets(VALID_ACC_ID, infantOnly));

        verifyNoInteractions(ticketPaymentService, seatReservationService);
    }

    @Test
    @DisplayName("Should use same TicketType across the third party requests")
    void shouldUseSameTicketTypeAcrossTheThirdPartyRequests() {
        ticketService.purchaseTickets(VALID_ACC_ID,
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 3),
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 4));

        verify(ticketPaymentService).makePayment(VALID_ACC_ID, 175);
        verify(seatReservationService).reserveSeat(VALID_ACC_ID, 7);
    }

    //------------------Refactor--------------------------
    //Tests for validating the AccountId and TicketTypeRequest have been
    // moved to TicketRequestValidatorServiceImpl
    //----------------------------------------------------

}