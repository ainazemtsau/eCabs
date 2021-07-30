package constant;

public class AMPQConstant {

    public static final String MESSAGE_EXCHANGE_NAME = "messageExchange";

    public static final String BOOKING_EXCHANGE_NAME = "bookingExchange";

    public static final String BOOKING_ADD_QUEUE_NAME = "bookingAdd";
    public static final String BOOKING_UPDATE_QUEUE_NAME = "bookingUpdate";
    public static final String BOOKING_DELETE_QUEUE_NAME = "bookingDelete";
    public static final String MESSAGE_AUDIT_QUEUE_NAME = "messageAudit";

    public static final String BOOKING_ADD_ROUTING_KEY = "booking.add";
    public static final String BOOKING_UPDATE_ROUTING_KEY = "booking.update";
    public static final String BOOKING_DELETE_ROUTING_KEY = "booking.delete";
}
