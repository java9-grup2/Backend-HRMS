package org.hrms.manager;

import org.hrms.dto.request.IsCommentMatchesRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.hrms.constant.EndPoints.ISCOMMENTMATCHES;

@FeignClient(url = "http://localhost:9091/api/v1/user",decode404 = true,name = "comment-userservice")
public interface IUserManager {
    @PostMapping(ISCOMMENTMATCHES)
    ResponseEntity<Boolean> isCommentDetailsValid(@RequestBody IsCommentMatchesRequestDto dto);
}
