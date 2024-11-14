package stanl_2.final_backend.domain.member.query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;

@RestController(value = "queryAuthController")
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthQueryService authQueryService;

    @Autowired
    public AuthController(AuthQueryService authQueryService) {
        this.authQueryService = authQueryService;
    }


}
