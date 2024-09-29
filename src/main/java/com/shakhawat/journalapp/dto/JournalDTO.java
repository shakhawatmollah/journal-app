package com.shakhawat.journalapp.dto;

import com.shakhawat.journalapp.enums.Sentiment;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JournalDTO {

    @NotEmpty
    @Schema(description = "Journal Title is required")
    private String title;

    @NotEmpty
    @Schema(description = "Content is required")
    private String content;

    @NotEmpty
    @Schema(description = "Sentiment is required")
    private Sentiment sentiment;
}
