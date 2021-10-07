package com.tutorials.tutorialservice.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomExceptionResponse {

    private UUID uuid;
    private List<CustomException> exceptionList;
}
