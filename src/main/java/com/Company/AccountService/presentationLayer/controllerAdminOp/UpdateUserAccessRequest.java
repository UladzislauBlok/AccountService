package com.Company.AccountService.presentationLayer.controllerAdminOp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserAccessRequest {

    @JsonProperty("user")
    private String userEmail;
    private String operation;
}
