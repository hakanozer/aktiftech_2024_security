package com.works.entities;

import com.works.customValids.StatusValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Customer {

    @NotNull
    @NotEmpty
    @Email
    @Size(min = 5, max = 50)
    private String email;

    @NotNull
    @NotEmpty
    private String password;

    @StatusValid
    private String status;

}
