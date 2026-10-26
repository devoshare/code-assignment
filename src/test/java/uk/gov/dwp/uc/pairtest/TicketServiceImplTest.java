package uk.gov.dwp.uc.pairtest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.MockitoAnnotations.openMocks;

class TicketServiceImplTest {
    @InjectMocks
    TicketServiceImpl ticketServiceImpl;

    @Mock
    SeatReservationService seatReservationService;

    @Mock
    TicketPaymentService ticketPaymentService;


    //valid ticket request for Adult - 10 tickets
    private static final TicketTypeRequest validTicketTypeRequest =
            new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 10);

    //valid AccountId - needs to be used in multiple test methods below
    private static final Long VALID_ACCOUNT_ID = 53L;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }




}