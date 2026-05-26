package br.com.ifba.prg04deskflow.infrastructure.exception;

import java.io.Serial;

//Classe que recebe o chamado da exceção
public class BusinessException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public BusinessException() {
        super();
    }

    public BusinessException(final String message) {
        super(message);
    }


    public BusinessException(final Throwable cause) {
        super(cause);
    }

    public BusinessException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
