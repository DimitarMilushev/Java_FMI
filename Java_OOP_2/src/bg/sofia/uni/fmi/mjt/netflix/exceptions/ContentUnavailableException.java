package bg.sofia.uni.fmi.mjt.netflix.exceptions;

public class ContentUnavailableException extends RuntimeException {
    public ContentUnavailableException(String msg)
    {
        super(msg);
    }
}
