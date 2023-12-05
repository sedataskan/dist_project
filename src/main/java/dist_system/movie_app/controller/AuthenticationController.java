package dist_system.movie_app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    // login
    @RequestMapping("/login")
    public String login() {
        return "Login";
    }

    // register
    @RequestMapping("/register")
    public String register() {
        return "Register";
    }
}
