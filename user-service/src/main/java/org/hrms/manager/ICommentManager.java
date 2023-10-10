package org.hrms.manager;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.hrms.constant.EndPoints.APPROVECOMMENT;
import static org.hrms.constant.EndPoints.DELETECOMMENT;

@FeignClient(url = "${feign.comment}",decode404 = true,name = "user-commentservice")
public interface ICommentManager {

    @PutMapping(APPROVECOMMENT)
    ResponseEntity<Boolean> approveComment(@RequestParam Long id);

    @DeleteMapping(DELETECOMMENT)
    ResponseEntity<Boolean> deleteCommentById(@RequestParam Long id);
}
