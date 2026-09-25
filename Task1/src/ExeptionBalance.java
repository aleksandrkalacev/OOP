public class ExeptionBalance extends RuntimeException{
    public ExeptionBalance(String message){
        super(message);
    }

    class ExeptionSubscriotion extends RuntimeException {
        public ExeptionSubscriotion(String message){
            super(message);
        }
    }

    class ExeptionFrozen extends RuntimeException {
        public ExeptionFrozen(String message){
            super(message);
        }
    }
}
