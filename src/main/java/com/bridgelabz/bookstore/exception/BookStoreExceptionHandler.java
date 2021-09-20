package com.bridgelabz.bookstore.exception;

import com.bridgelabz.bookstore.builder.MessageProperties;
import com.bridgelabz.bookstore.dto.ResponseDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class BookStoreExceptionHandler {

    /**
     * Purpose : Ability to throw exception when duplicate user gets inserted.
     *
     * @param exception is the Object of the DataIntegrityViolationException class.
     *
     * @return Custom Exception Message.
     */

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseDTO> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        ResponseDTO responseDTO = new ResponseDTO(MessageProperties.USER_ALREADY_EXIST.getMessage(), exception.getMessage());
        return new ResponseEntity<>(responseDTO, HttpStatus.LOCKED);
    }


    /**
     * Purpose: Ability to throw exception when field validation failed.
     *
     * @param exception is the Object of the MethodArgumentNotValidException class.
     *
     * @return object of the response dto and message of exception handler.
     */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {
        List<ObjectError> errorList = exception.getBindingResult().getAllErrors();
        List<String> errMsg = errorList.stream()
//                .map(objErr -> objErr.getDefaultMessage())
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        ResponseDTO responseDTO = new ResponseDTO(MessageProperties.REST_REQUEST_EXCEPTION.getMessage(), errMsg);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    /**
     *  Purpose :   Ability to throw runtime exception with Proper custom message.
     *
     * @param exception is the Object of the BookStoreException Class.
     *
     * @return response of the handleUserServiceException method as in the form of
     *         Proper Message.
     */

    @ExceptionHandler(BookStoreException.class)
    public ResponseEntity<ResponseDTO> handleUserServiceException(
            BookStoreException exception) {
        ResponseDTO responseDTO = new ResponseDTO(MessageProperties.REST_REQUEST_EXCEPTION.getMessage(),
                exception.getMessage());
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

}
