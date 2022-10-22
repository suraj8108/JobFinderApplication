package com.exceptions;

public class NoSuchEmployerFoundException extends Exception {
  public NoSuchEmployerFoundException(int id) {
    super("No employer with id " + id + " found :(");
  }
}
