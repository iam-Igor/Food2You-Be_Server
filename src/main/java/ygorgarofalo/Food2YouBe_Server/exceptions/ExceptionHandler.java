package ygorgarofalo.Food2YouBe_Server.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ygorgarofalo.Food2YouBe_Server.payloads.ErrPayloadList;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandler {

    //400
    @org.springframework.web.bind.annotation.ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrPayloadList handleBadRequest(BadRequestException e) {
        List<String> errorsMessages = new ArrayList<>();
        if (e.getErrorList() != null)
            errorsMessages = e.getErrorList().stream().map(err -> err.getDefaultMessage()).toList();
        return new ErrPayloadList(e.getMessage(), errorsMessages);
    }


    //401
    @org.springframework.web.bind.annotation.ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorsBody handleUnauthorized(UnauthorizedException ex) {
        return new ErrorsBody(ex.getMessage());
    }


    // 403
    @org.springframework.web.bind.annotation.ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorsBody handleAccessDenied(AccessDeniedException ex) {
        return new ErrorsBody("Il tuo ruolo non permette di accedere a questa funzionalità!");
    }

    //404
    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorsBody handleNotFoundExc(NotFoundException ex) {
        return new ErrorsBody(ex.getMessage());
    }


    //500 family
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorsBody handleGenericError(Exception ex) {
        ex.printStackTrace();
        return new ErrorsBody("Problema lato server.");

    }
}
