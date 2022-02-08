package com.playtomic.wallet.api.errorhandler;

import com.playtomic.wallet.service.WalletException;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(GlobalErrorHandler.class);

  @ExceptionHandler(WalletException.class)
  protected ResponseEntity<ErrorResponse> handleWalletException(WalletException ex, WebRequest request) {
    logger.error(ex.toString());
    HttpStatus status = HttpStatus.valueOf(ex.getErrorCode());
    ErrorResponse errorResponse = new ErrorResponse(new Date(), ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(errorResponse, status);
  }

}
