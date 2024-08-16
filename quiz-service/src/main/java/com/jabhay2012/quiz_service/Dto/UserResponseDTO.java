package com.jabhay2012.quiz_service.Dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserResponseDTO {
    private Integer id;
    private String response;

}
