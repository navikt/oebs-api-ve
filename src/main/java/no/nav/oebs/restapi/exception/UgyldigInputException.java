package no.nav.oebs.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UgyldigInputException extends PlsqlException {

	private static final long serialVersionUID = 1L;

	public UgyldigInputException(String message) {
		super(message);
	}
}
