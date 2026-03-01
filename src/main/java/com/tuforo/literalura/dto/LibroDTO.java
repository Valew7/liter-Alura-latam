package com.tuforo.literalura.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibroDTO(
        Integer id,
        String title,
        List<AutorDTO> authors,
        List<String> languages,
        @JsonAlias("download_count") Integer downloadCount) {
}
