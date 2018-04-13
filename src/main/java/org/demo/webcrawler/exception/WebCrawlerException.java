package org.demo.webcrawler.exception;

/**
 * Exception for handling errors inside the application
 *
 * @author martin
 */
public class WebCrawlerException extends Exception {
    public WebCrawlerException(String message) {
        super(message);
    }
}
