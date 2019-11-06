package com.cristovantamayo.ubsvoce.services.exceptions;

public class GeocodingServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public GeocodingServiceException(String message) {
        super(message);
    }

    public GeocodingServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
