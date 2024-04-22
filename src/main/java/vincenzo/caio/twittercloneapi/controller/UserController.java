package vincenzo.caio.twittercloneapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vincenzo.caio.twittercloneapi.dto.UserDto;
import vincenzo.caio.twittercloneapi.service.UserService;
import vincenzo.caio.twittercloneapi.model.User;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> signUp(@RequestBody UserDto userDto) {
        User newUser = service.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getUser(@PathVariable String email) {
        User user = service.getUserByEmail(email);
        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with e-mail " + email + " not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
