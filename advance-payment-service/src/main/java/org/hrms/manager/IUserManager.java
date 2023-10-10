package org.hrms.manager;

import org.hrms.dto.request.AdvancePaymentUserControlDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.hrms.constant.EndPoints.ISADVANCEREQUESTVALID;

@FeignClient(url = "${feign.user}",decode404 = true,name = "advancepayment-userservice")
public interface IUserManager {

    @PostMapping(ISADVANCEREQUESTVALID)
    ResponseEntity<Boolean> isAdvanceRequestValid(@RequestBody AdvancePaymentUserControlDto dto);
}
