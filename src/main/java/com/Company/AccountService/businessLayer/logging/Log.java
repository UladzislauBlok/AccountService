package com.Company.AccountService.businessLayer.logging;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Log {

    @Id
    @GeneratedValue
    private long id;
    private Date date;
    private String action;
    private String subject;
    private String object;
    private String path;
}
