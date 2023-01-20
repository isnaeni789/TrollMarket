package com.trollmarket.rest;

import com.trollmarket.JwtToken;
import com.trollmarket.dto.account.RequestTokenDTO;
import com.trollmarket.dto.account.ResponseTokenDTO;
import com.trollmarket.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/authenticate")
public class AccountRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtToken jwtToken;

    @PostMapping
    public ResponseEntity<Object> post(@RequestBody RequestTokenDTO dto){
        try {
            /*Token ini bukan JWT, ini adalah token gaya spring security (sama seperti yang di cookies)*/
            var token = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());

            /*Menggunakan spring security melakukan check apakah username dan password valid*/
            authenticationManager.authenticate(token);

        } catch (Exception exception){
            return ResponseEntity.status(403).body("Authentication gagal. Username dan password not found.");
        }
        /*Dapatkan role untuk tambahan informasi*/

        /*Dapatkan jwt token dari method generate token yang sudah kita buat di class JwtToken*/
        String token = jwtToken.generateToken(dto.getUsername(), dto.getSecretKey(),
                dto.getAudience(), dto.getSubject());

        ResponseTokenDTO responseTokenDTO = new ResponseTokenDTO(dto.getUsername(), token);
        return ResponseEntity.status(200).body(responseTokenDTO);
    }
}
