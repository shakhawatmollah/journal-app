package com.shakhawat.journalapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotEmpty
    @Schema(description = "Username is required")
    private String userName;

    @NotEmpty
    @Schema(description = "Email is required")
    private String email;

    private Boolean isSentimentAnalysis;

    @NotEmpty
    @Schema(description = "Password is required")
    private String password;

}
