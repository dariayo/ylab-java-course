package ru.dariayo.validation;

public class OrderValidator {

    public static boolean isValidOrderId(String orderId) {
        if (orderId == null || orderId.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(orderId);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
