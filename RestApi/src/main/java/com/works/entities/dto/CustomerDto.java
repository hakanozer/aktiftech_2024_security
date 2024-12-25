package com.works.entities.dto;

import com.works.entities.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class CustomerDto implements Serializable {

    Long cid;

    @NotNull
    @Size(min = 2, max = 50)
    @NotEmpty
    String nameSurname;

    @NotNull
    @Size(min = 2, max = 30)
    @NotEmpty
    String username;

    @NotNull
    @Size(min = 2, max = 10)
    @NotEmpty
    String password;

    @NotNull
    Boolean enable;

    @NotNull
    List<Role> roles;


}