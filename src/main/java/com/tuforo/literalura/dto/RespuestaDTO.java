package com.tuforo.literalura.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RespuestaDTO(
        Integer count,
        String next,
        String previous,
        List<LibroDTO> results) {
}
