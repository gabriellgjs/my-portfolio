package com.myportfolio.controllers;

import com.myportfolio.domain.user.AutheticationDTO;
import com.myportfolio.domain.user.LoginResponseDTO;
import com.myportfolio.domain.user.RegisterDTO;
import com.myportfolio.domain.user.User;
import com.myportfolio.infra.security.TokenService;
import com.myportfolio.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private UserRepository repository;
  @Autowired
  private TokenService tokenService;


  @PostMapping("login")
  public ResponseEntity login(@RequestBody @Valid AutheticationDTO data) {
    var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());

    var auth = this.authenticationManager.authenticate(usernamePassword);

    var token = tokenService.generateToken((User) auth.getPrincipal());

    return  ResponseEntity.ok(new LoginResponseDTO(token));
  }

  @PostMapping("/register")
  public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
    if(this.repository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

    String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
    User newUser = new User(data.email(), encryptedPassword, data.role());

    this.repository.save(newUser);

    return ResponseEntity.ok().build();
  }
}
