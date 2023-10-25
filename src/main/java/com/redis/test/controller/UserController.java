package com.redis.test.controller;

import com.redis.test.entity.User;
import com.redis.test.repository.UserRepository;
import com.redis.test.util.EncryptUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @PostMapping("")
    @Operation(summary = "Add New User", description = "Add new data user", operationId = "user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(
                    array = @ArraySchema(
                            schema = @Schema(implementation = User.class))
            )),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public ResponseEntity<User> createUser(@RequestBody User user) throws NoSuchAlgorithmException {
        user.setPassword(EncryptUtil.encrypt(user.getPassword()));
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
    }

    @GetMapping("")
    @Operation(summary = "Get All User", description = "Get all data user", operationId = "user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(
                    array = @ArraySchema(
                            schema = @Schema(implementation = User.class))
            )),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public ResponseEntity<List<User>> getAllUser() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get User by ID", description = "Get data user by id", operationId = "user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(
                    array = @ArraySchema(
                            schema = @Schema(implementation = User.class))
            )),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        return userRepository.findById(id).map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElse(null);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update User by ID", description = "Update data user by id", operationId = "user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(
                    array = @ArraySchema(
                            schema = @Schema(implementation = User.class))
            )),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) throws NoSuchAlgorithmException {
        Optional<User> usr = userRepository.findById(id);
        if (usr.isPresent()) {
            User userData = usr.get();
            userData.setUsername(user.getUsername());
            userData.setNama(user.getNama());
            userData.setPassword(EncryptUtil.encrypt(user.getPassword()));
            userData.setStatus(user.getStatus());
            return new ResponseEntity<>(userRepository.save(userData), HttpStatus.CREATED);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete User by ID", description = "Delete data user by id", operationId = "user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(
                    array = @ArraySchema(
                            schema = @Schema(implementation = User.class))
            )),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>("Success Delete User Data With ID: " + id, HttpStatus.OK);
    }
}
