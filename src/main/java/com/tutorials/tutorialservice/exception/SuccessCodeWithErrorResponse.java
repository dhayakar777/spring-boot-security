package com.tutorials.tutorialservice.exception;

import lombok.Getter;

public class SuccessCodeWithErrorResponse  extends RuntimeException{

    @Getter
    CustomExceptionResponse response;

    @Getter
    String id;

   public SuccessCodeWithErrorResponse(String id, CustomExceptionResponse response) {
       this.response = response;
       this.id = id;
   }
   public SuccessCodeWithErrorResponse(CustomExceptionResponse response) {
       this.response = response;
   }
}
