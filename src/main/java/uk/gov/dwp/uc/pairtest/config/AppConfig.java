package uk.gov.dwp.uc.pairtest.config;

public class AppConfig {

    public static final int MAX_TICKETS = getInt("ticket.max.allowed", 25);
    public static final int ADULT_PRICE = getInt("ticket.price.adult", 25);
    public static final int CHILD_PRICE = getInt("ticket.price.child", 15);
    public static final int INFANT_PRICE = getInt("ticket.price.infant", 0);

    public static int getInt(String key, int defaultValue) {
        return 0;
    }

}
