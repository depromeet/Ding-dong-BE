package com.dingdong.api.example;


import com.dingdong.api.example.dto.HealthCheckResponse;
import com.dingdong.core.exception.ExampleException;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/health")
@Slf4j
public class HealthCheckController {

    @Operation(summary = "테스트용 컨트롤러입니다.")
    @GetMapping
    public HealthCheckResponse healthCheck(@RequestParam(required = false) String check) {
        if (Objects.equals(check, "error")) {
            throw ExampleException.EXCEPTION;
        }
        return HealthCheckResponse.from("health");
    }

    @Operation(summary = "테스트용 컨트롤러입니다.")
    @GetMapping("/error")
    public void healthCheck() {
        log.error("test error");
    }
}
