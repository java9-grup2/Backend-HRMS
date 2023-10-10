package org.hrms.manager;


import org.hrms.dto.request.AuthIdAndCompanyNameCheckerRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.hrms.constant.EndPoints.ISEXPENSEREQUESTVALID;

@FeignClient(url = "${feign.user}",decode404 = true,name = "expense-userservice")
public interface IUserManager {
    @PostMapping(ISEXPENSEREQUESTVALID)
    ResponseEntity<Boolean> isExpenseRequestValid(@RequestBody AuthIdAndCompanyNameCheckerRequestDto dto);
}
