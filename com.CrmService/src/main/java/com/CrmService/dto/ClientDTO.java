package com.CrmService.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class ClientDTO {
    private int id;
    @NotBlank(message = "{notblank.clientname}")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "{alphaAndSpaces.userName}")
    private String name;
    @NotBlank(message = "{notblank.clientType}")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "{alphaAndSpaces.userName}")
    private String type;
    @NotBlank(message = "{notblank.category}")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "{alphaAndSpaces.userName}")
    private String category;
}