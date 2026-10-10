public class InsufficientFundsException extends RuntimeException{
    public InsufficientFundsException(String message) {
        super(message);
    }
}
class SubscriptionExpiredException extends RuntimeException {
    public SubscriptionExpiredException(String message) {
        super(message);
    }
}

// 3. Ошибка использования замороженного абонемента
class SubscriptionFrozenException extends RuntimeException {
    public SubscriptionFrozenException(String message) {
        super(message);
    }
}
