package org.hrms.manager;

import org.hrms.dto.request.AuthIdAndCompanyNameCheckerRequestDto;
import org.hrms.repository.enums.EUserType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static org.hrms.constant.EndPoints.ISDAYOFFREQUESTVALID;

@FeignClient(url = "http://localhost:9091/api/v1/user",decode404 = true,name = "permission-userservice")
public interface IUserManager {

    @GetMapping("getUserType")
    ResponseEntity<EUserType> getUserType(@RequestParam Long authid);

    @GetMapping("getUsername")
    ResponseEntity<String> getUsername(@RequestParam Long authid);

    @PostMapping(ISDAYOFFREQUESTVALID)
    ResponseEntity<Boolean> isDayOffRequestValid(@RequestBody AuthIdAndCompanyNameCheckerRequestDto dto);


}
