package com.Company.AccountService.presentationLayer.log;

import com.Company.AccountService.businessLayer.logging.Log;
import com.Company.AccountService.businessLayer.logging.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class logController {

    private final LogService logService;

    @GetMapping("/security/events/")
    public List<Log> getLogList() {
        return logService.getLogList();
    }
}
