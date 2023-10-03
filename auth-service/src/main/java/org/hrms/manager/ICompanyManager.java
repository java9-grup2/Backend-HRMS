package org.hrms.manager;

import org.hrms.dto.request.IsCompanyRequestValidDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static org.hrms.constant.EndPoints.COMPANYREQUESTCHECKER;
import static org.hrms.constant.EndPoints.ISCOMPANYEXISTS;

@FeignClient(url = "http://localhost:9092/api/v1/company",decode404 = true,name = "auth-companyservice")
public interface ICompanyManager {

    @PostMapping(ISCOMPANYEXISTS)
    ResponseEntity<Boolean> isCompanyExists(@RequestParam String companyName);

    @PostMapping(COMPANYREQUESTCHECKER)
     ResponseEntity<Boolean> isCompanyRequestValid(@RequestBody IsCompanyRequestValidDto dto);
}
