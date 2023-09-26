package org.hrms.manager;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.hrms.constant.EndPoints.APPROVECOMMENT;

@FeignClient(url = "http://localhost:9094/api/v1/comment",decode404 = true,name = "user-commentservice")
public interface ICommentManager {

    @PutMapping(APPROVECOMMENT)
    ResponseEntity<Boolean> approveComment(@RequestParam Long id);
}
