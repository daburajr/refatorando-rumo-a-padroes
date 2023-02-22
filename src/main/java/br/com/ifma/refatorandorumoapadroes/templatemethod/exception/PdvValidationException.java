package br.com.ifma.refatorandorumoapadroes.templatemethod.exception;

public class PdvValidationException extends RuntimeException {

    public PdvValidationException(String msg) {
        super(msg);
    }

}
