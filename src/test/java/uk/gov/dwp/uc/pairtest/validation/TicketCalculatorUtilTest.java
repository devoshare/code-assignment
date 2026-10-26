package uk.gov.dwp.uc.pairtest.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

import static org.junit.jupiter.api.Assertions.*;

class TicketCalculatorUtilTest {

    TicketTypeRequest[] ticketTypeRequests;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ticketTypeRequests =
                new TicketTypeRequest[]{new TicketTypeRequest(TicketTypeRequest.Type.ADULT,
                        10), new TicketTypeRequest(TicketTypeRequest.Type.CHILD,
                        5), new TicketTypeRequest(TicketTypeRequest.Type.INFANT,
                        8)};
    }

    @Test
    @DisplayName("Calculate total tickets count across multiple requests")
    void shouldCalculateTotalTicketsCountAcrossMultipleRequests() {
        int numOfTickets = TicketCalculatorUtil.calculateTotalNumOfTickets(ticketTypeRequests);
        assertEquals(23, numOfTickets);
    }

    //INFANT = 0; Child = 15, Adult = 25
    //Total = 10 x 25 + 5 x 15 = 325
    @Test
    @DisplayName("Calculate total amount for the tickets")
    void shouldCalculateTotalAmountForTheTickets() {
        int numOfTickets = TicketCalculatorUtil.calculateTotalAmount(ticketTypeRequests);
        assertEquals(325, numOfTickets);
    }

    @Test
    @DisplayName("Calculate num of seats for the tickets")
    void shouldCalculateNumOfSeatsForTheTickets() {
        int numOSeats = TicketCalculatorUtil.calculateTotalSeats(ticketTypeRequests);
        assertEquals(15, numOSeats);
    }

    @Test
    @DisplayName("Calculate total number of Adult tickets")
    void shouldCountTotalNumOfAdultTickets() {
        int numOfAdults =
                TicketCalculatorUtil.getTicketsCountByType(TicketTypeRequest.Type.ADULT,
                        ticketTypeRequests);
        assertEquals(10, numOfAdults);
    }

    @Test
    @DisplayName("Calculate total number of Child tickets")
    void shouldCountTotalNumOfChildTickets() {
        int numOfChildren =
                TicketCalculatorUtil.getTicketsCountByType(TicketTypeRequest.Type.CHILD,
                        ticketTypeRequests);
        assertEquals(5, numOfChildren);
    }

    @Test
    @DisplayName("Calculate total number of Infant tickets")
    void shouldCountTotalNumOfInfantTickets() {
        int numOfInfants =
                TicketCalculatorUtil.getTicketsCountByType(TicketTypeRequest.Type.INFANT,
                        ticketTypeRequests);
        assertEquals(8, numOfInfants);
    }


}