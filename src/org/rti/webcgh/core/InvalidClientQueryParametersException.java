package org.rti.webcgh.core;

public class InvalidClientQueryParametersException extends WebcghApplicationException {

    /**
     * Constructor
     */
    public InvalidClientQueryParametersException() {
        super();
    }
    
    /**
     * Constructor
     * @param msg Message
     */
    public InvalidClientQueryParametersException(String msg) {
        super(msg);
    }
    
    /**
     * Constructor
     * @param origThrowable Original throwable
     */
    public InvalidClientQueryParametersException(Throwable origThrowable) {
        super();
        nestedThrowable = origThrowable;
    }
    
    /**
     * Constructor
     * @param msg Message
     * @param origThrowable Original throwable
     */
    public InvalidClientQueryParametersException(String msg, Throwable origThrowable) {
        super(msg);
        nestedThrowable = origThrowable;
    }
}
