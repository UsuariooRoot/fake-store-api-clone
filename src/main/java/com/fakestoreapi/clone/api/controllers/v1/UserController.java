package com.fakestoreapi.clone.api.controllers.v1;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fakestoreapi.clone.application.dto.request.user.UserSaveDTO;
import com.fakestoreapi.clone.application.dto.response.user.UserResponse;
import com.fakestoreapi.clone.application.mapper.UserMapper;
import com.fakestoreapi.clone.application.service.interfaces.IUserService;
import com.fakestoreapi.clone.domain.entity.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    private final UserMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getProduct(@PathVariable Long id) {
        User user = userService.getUser(id);
        return ResponseEntity.ok().body(mapper.toResponse(user));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllProducts() {
        return ResponseEntity.ok().body(userService.getAllUsers()
                .stream()
                .map(mapper::toResponse)
                .toList());
    }

    @PostMapping
    public ResponseEntity<UserResponse> createProduct(@Valid @RequestBody UserSaveDTO request) {
        var user = mapper.toDomain(request);
        var created = userService.createUser(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toResponse(created));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> delete(@PathVariable Long id) {
        User user = userService.deleteUser(id);

        return ResponseEntity.ok()
                .body(mapper.toResponse(user));
    }

}
