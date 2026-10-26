package uk.gov.dwp.uc.pairtest.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verifyNoInteractions;

class TicketRequestValidatorServiceImplTest {

    @InjectMocks
    TicketRequestValidatorServiceImpl ticketRequestValidatorService;

    //private static final int MAX_TICKETS = 25;

    private static final Long VALID_ACCOUNT_ID = 56L;

    //valid ticket request for Adult - 10 tickets
    private static final TicketTypeRequest validTicketTypeRequest =
            new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 10);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //Business Rule Max 25 tickets
    @Test
    @DisplayName("Should throw exception for max tickets")
    void shouldThrowExceptionForMaxTickets() {
        TicketTypeRequest ticketTypeRequestAdult =
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 26);
        TicketTypeRequest ticketTypeRequestChild =
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 10);
        TicketTypeRequest ticketTypeRequestInfant = new
                TicketTypeRequest(TicketTypeRequest.Type.INFANT, 10);
        assertThrows(InvalidPurchaseException.class,
                () -> ticketRequestValidatorService.validateRequest(VALID_ACCOUNT_ID,
                        ticketTypeRequestAdult, ticketTypeRequestChild, ticketTypeRequestInfant));
    }

    //Business rule Child ticket without Adult ticket
    @Test
    @DisplayName("Should throw exception for Child without Adult Ticket")
    void shouldThrowExceptionForChildWithoutAdult() {
        TicketTypeRequest ticketTypeRequestChild =
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 10);
        assertThrows(InvalidPurchaseException.class,
                () -> ticketRequestValidatorService.validateRequest(VALID_ACCOUNT_ID,
                        ticketTypeRequestChild));
    }

    //Business rule Infant ticket without Adult ticket
    @Test
    @DisplayName("Should throw exception for Infant without Adult Ticket")
    void shouldThrowExceptionForInfantWithoutAdult() {
        TicketTypeRequest ticketTypeRequestInfant =
                new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 10);
        assertThrows(InvalidPurchaseException.class,
                () -> ticketRequestValidatorService.validateRequest(VALID_ACCOUNT_ID,
                        ticketTypeRequestInfant));
    }

    //Assuming one Infant sits on One Adults lap 1:1 ratio
    @Test
    @DisplayName("Should throw exception for more infants than adults")
    void shouldThrowExceptionForMoreInfantsThanAdults() {
        TicketTypeRequest ticketTypeRequestAdult =
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 10);
        TicketTypeRequest ticketTypeRequestInfant =
                new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 11);
        assertThrows(InvalidPurchaseException.class,
                () -> ticketRequestValidatorService.validateRequest(VALID_ACCOUNT_ID,
                        ticketTypeRequestAdult, ticketTypeRequestInfant));
    }

    //Somee edge cases
    @Test
    @DisplayName("Should accept exactly the maximum number of tickets")
    void shouldAcceptExactlyMaxTickets() {
        TicketTypeRequest adults = new TicketTypeRequest(TicketTypeRequest.Type.ADULT,
                25);
        assertDoesNotThrow(() ->
                ticketRequestValidatorService.validateRequest(VALID_ACCOUNT_ID, adults));
    }

    @Test
    @DisplayName("Should reject one more than the maximum number of tickets")
    void shouldRejectOneOverMaxTickets() {
        TicketTypeRequest adults = new TicketTypeRequest(TicketTypeRequest.Type.ADULT,
                26);
        assertThrows(InvalidPurchaseException.class, () ->
                ticketRequestValidatorService.validateRequest(VALID_ACCOUNT_ID, adults));
    }

    @Test
    @DisplayName("Should accept infants equal to adults")
    void shouldAcceptInfantsEqualToAdults() {
        TicketTypeRequest adults = new TicketTypeRequest(TicketTypeRequest.Type.ADULT,
                5);
        TicketTypeRequest infants = new TicketTypeRequest(TicketTypeRequest.Type.INFANT,
                5);
        assertDoesNotThrow(() ->
                ticketRequestValidatorService.validateRequest(VALID_ACCOUNT_ID, adults, infants));
    }

    @Test
    @DisplayName("Should accept an adult-only request")
    void shouldAcceptAdultOnlyRequest() {
        TicketTypeRequest adults = new TicketTypeRequest(TicketTypeRequest.Type.ADULT,
                3);
        assertDoesNotThrow(() ->
                ticketRequestValidatorService.validateRequest(VALID_ACCOUNT_ID, adults));
    }

    @Test
    @DisplayName("Should accept children with an adult")
    void shouldAcceptChildrenWithAdult() {
        TicketTypeRequest adult = new TicketTypeRequest(TicketTypeRequest.Type.ADULT,
                1);
        TicketTypeRequest children = new TicketTypeRequest(TicketTypeRequest.Type.CHILD,
                3);
        assertDoesNotThrow(() ->
                ticketRequestValidatorService.validateRequest(VALID_ACCOUNT_ID, adult, children));
    }

    @Test
    @DisplayName("Should count the same ticket type across multiple requests")
    void shouldCombineRequestsOfSameType() {
        TicketTypeRequest a1 = new TicketTypeRequest(TicketTypeRequest.Type.ADULT,
                13);
        TicketTypeRequest a2 = new TicketTypeRequest(TicketTypeRequest.Type.ADULT,
                13);
        assertThrows(InvalidPurchaseException.class, () ->
                ticketRequestValidatorService.validateRequest(VALID_ACCOUNT_ID, a1, a2)); // 26 total
    }

    //very edge case
    @Test
    @DisplayName ("Should throw exception is the number of ticksts go above " +
            "MAX value of integer")
    void shouldThrowExceptionForMaxNumberOfTicksts() {
        TicketTypeRequest a1 =
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, Integer.MAX_VALUE);
        TicketTypeRequest a2 =
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, Integer.MAX_VALUE);
        assertThrows(InvalidPurchaseException.class, () ->
                ticketRequestValidatorService.validateRequest(VALID_ACCOUNT_ID, a1, a2)); // 26 total

    }

    @Test
    @DisplayName("Should throw exception if accountId is null")
    void shouldThrowExceptionIfAccountIdIsNull() {
        assertThrows(InvalidPurchaseException.class,
                () -> ticketRequestValidatorService.validateRequest(null,
                        validTicketTypeRequest));
    }

    @Test
    @DisplayName("Should throw exception if accountId is zero")
    void shouldThrowExceptionIfAccountIdIsZero() {
        assertThrows(InvalidPurchaseException.class,
                () -> ticketRequestValidatorService.validateRequest(0L,
                        validTicketTypeRequest));
    }

    @Test
    @DisplayName("Should throw exception if accountId is Negative")
    void shouldThrowExceptionIfAccountIdIsNegative() {
        assertThrows(InvalidPurchaseException.class, () ->
                ticketRequestValidatorService.validateRequest(-1L,
                        validTicketTypeRequest));
    }

    @Test
    @DisplayName("Should throw exception if TicketTypeRequest is null")
    void shouldThrowExceptionIfTicketTypeRequestIsNull() {
        assertThrows(InvalidPurchaseException.class,
                () -> ticketRequestValidatorService.validateRequest(VALID_ACCOUNT_ID,
                        (TicketTypeRequest[]) null));
    }

    @Test
    @DisplayName("Should throw exception if TicketTypeRequest is empty ")
    void shouldThrowExceptionIfTicketTypeRequestIsEmpty() {
        TicketTypeRequest[] emptyTicketTypeRequest =
                new TicketTypeRequest[0];
        assertThrows(InvalidPurchaseException.class,
                () -> ticketRequestValidatorService.validateRequest(VALID_ACCOUNT_ID,
                        emptyTicketTypeRequest));
    }

    @Test
    @DisplayName("Should throw exception if negative tickets requested")
    void shouldThrowExceptionIfNegativeTicketsRequested() {
        TicketTypeRequest ticketTypeRequest =
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, -3);
        assertThrows(InvalidPurchaseException.class,
                () -> ticketRequestValidatorService.validateRequest(VALID_ACCOUNT_ID,
                        ticketTypeRequest));
    }

}