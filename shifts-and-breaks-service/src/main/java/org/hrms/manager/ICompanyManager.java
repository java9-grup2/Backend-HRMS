package org.hrms.manager;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.hrms.constant.EndPoints.ISCOMPANYEXISTS;

@FeignClient(url = "http://localhost:9092/api/v1/company",decode404 = true,name = "shiftsandbreaks-companyservice")
public interface ICompanyManager {
    @PostMapping(ISCOMPANYEXISTS)
    ResponseEntity<Boolean> isCompanyExists(@RequestParam String companyName);
}
