package com.serotonin.bacnet4j.npdu;


/**
 * 这个异常在两个类中被使用 <br/>
 * IncomingRequestParser 类的 parseApdu() <br/>
 * IncomingMessageExecutor 类的 parseFrame()
 * */
public class MessageValidationAssertionException extends Exception {
    private static final long serialVersionUID = -1;

    public MessageValidationAssertionException(String message) {
        super(message);
    }
}
