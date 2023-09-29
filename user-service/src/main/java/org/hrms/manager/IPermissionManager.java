package org.hrms.manager;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(url = "http://localhost:9011/api/v1/permission",decode404 = true,name = "user-permissionservice")
public interface IPermissionManager {

}
