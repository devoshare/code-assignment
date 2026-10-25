package uk.gov.dwp.uc.pairtest.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppConfigTest {
    @Test
    @DisplayName("Should load max tickets from the configuration")
    void shouldLoadMaxTicketsFromTheConfiguration() {
        assertEquals(25, AppConfig.getInt("ticket.max.allowed", -1));
        assertEquals(25, AppConfig.MAX_TICKETS);
    }

    @Test
    @DisplayName("Should load cost of adult ticket")
    void shouldLoadCostOfAdultTicket() {
        assertEquals(25, AppConfig.getInt("ticket.price.adult", -1));
        assertEquals(25, AppConfig.ADULT_PRICE);
    }

    @Test
    @DisplayName("Should load cost of child ticket")
    void shouldLoadCostOfChildTicket() {
        assertEquals(15, AppConfig.getInt("ticket.price.child", -1));
        assertEquals(15, AppConfig.CHILD_PRICE);
    }

    @Test
    @DisplayName("Should load cost of infant ticket")
    void shouldLoadCostOfInfantTicket() {
        assertEquals(0, AppConfig.getInt("ticket.price.infant", -1));
        assertEquals(0, AppConfig.INFANT_PRICE);
    }
    @Test
    @DisplayName("Should return the default when a key is missing")
    void shouldReturnDefaultWhenKeyMissing() {
        assertEquals(42, AppConfig.getInt("no.such.key", 42));
    }
}