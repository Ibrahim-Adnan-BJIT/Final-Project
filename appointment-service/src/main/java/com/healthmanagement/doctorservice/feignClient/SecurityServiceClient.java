package com.healthmanagement.doctorservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "security-service")
public interface SecurityServiceClient {

    @GetMapping("/api/v2/user/getDoctor/{id}")
    public Long getDoctor(@PathVariable long id);
}
