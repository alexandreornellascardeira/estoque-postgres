package br.com.senac.db;


public class PersistenceException extends RuntimeException {

  private static final long serialVersionUID = 1L;

public PersistenceException() {}

  public PersistenceException(String message) {
    super(message);
  }

  public PersistenceException(String message, Throwable cause) {
    super(message, cause);
  }

  public PersistenceException(Throwable cause) {
    super(cause);
  }

  public PersistenceException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
