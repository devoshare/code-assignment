package uk.gov.dwp.uc.pairtest.validation;

import uk.gov.dwp.uc.pairtest.config.AppConfig;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

import java.util.Arrays;

public class TicketCalculatorUtil {

    //No Initialisation
    private TicketCalculatorUtil() {
    }

    public static int calculateTotalNumOfTickets(TicketTypeRequest... ticketTypeRequests) {
        //Not checking the nullness of the TicketTypeRequests as this has
        // already been checked in validator.

        int adults =
                TicketCalculatorUtil.getTicketsCountByType(TicketTypeRequest.Type.ADULT,
                        ticketTypeRequests);


        int children =
                TicketCalculatorUtil.getTicketsCountByType(TicketTypeRequest.Type.CHILD,
                        ticketTypeRequests);

        int infants =
                TicketCalculatorUtil.getTicketsCountByType(TicketTypeRequest.Type.INFANT,
                        ticketTypeRequests);

        return Math.addExact(Math.addExact(adults, children), infants);
    }

    public static int calculateTotalAmount(TicketTypeRequest... ticketTypeRequest) {
        //Not checking the nullness of the TicketTypeRequests as this has
        // already been checked
        int adults =
                TicketCalculatorUtil.getTicketsCountByType(TicketTypeRequest.Type.ADULT,
                        ticketTypeRequest);

        int children =
                TicketCalculatorUtil.getTicketsCountByType(TicketTypeRequest.Type.CHILD,
                        ticketTypeRequest);

        int infants =
                TicketCalculatorUtil.getTicketsCountByType(TicketTypeRequest.Type.INFANT,
                        ticketTypeRequest);

        return Math.addExact(Math.addExact((adults * AppConfig.ADULT_PRICE),
                (children * AppConfig.CHILD_PRICE)), (infants * AppConfig.INFANT_PRICE));

    }

    public static int calculateTotalSeats(TicketTypeRequest... ticketTypeRequest) {
        int adults =
                TicketCalculatorUtil.getTicketsCountByType(TicketTypeRequest.Type.ADULT,
                        ticketTypeRequest);
        int children =
                TicketCalculatorUtil.getTicketsCountByType(TicketTypeRequest.Type.CHILD,
                        ticketTypeRequest);


        return Math.addExact(adults, children);
    }

    public static int getTicketsCountByType(TicketTypeRequest.Type type,
                                            TicketTypeRequest... requests) {
        return Arrays.stream(requests)
                .filter(r -> r.getTicketType() == type)
                .mapToInt(TicketTypeRequest::getNoOfTickets)
                .reduce(0, Math::addExact);
    }

}
