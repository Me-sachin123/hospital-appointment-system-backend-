package jsp.hospital_appointment_system.controller;

import jsp.hospital_appointment_system.service.SecurityService;
import jsp.hospital_appointment_system.validator_entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/security")
public class SecurityController {

    @Autowired
    SecurityService securityService;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user)
    {
        securityService.registerUser(user);
        return "registration successfully done";
    }

     @PostMapping("/login")
    public String login(@RequestBody User user)
    {
        return securityService.login(user);
    }
}
