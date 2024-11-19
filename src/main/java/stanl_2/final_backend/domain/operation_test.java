package stanl_2.final_backend.domain;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class operation_test {
    @GetMapping("health")
    public String healthCheck() {
        return "CI/CD 배포 성공!!";
    }
}
