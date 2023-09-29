package org.hrms.manager;

import org.hrms.repository.enums.EUserType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "http://localhost:9091/api/v1/user",decode404 = true,name = "permission-userservice")
public interface IUserManager {

    @GetMapping("getUserType")
    ResponseEntity<EUserType> getUserType(@RequestParam Long authid);

    @GetMapping("getUsername")
    ResponseEntity<String> getUsername(@RequestParam Long authid);

}
