package models;

import lombok.Data;

@Data
public class CreateAndUpdateRegistrationResponse {
    private Integer id;
    private String token;
    private String error;
}
