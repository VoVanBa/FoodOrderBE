package com.example.foodApp.controller;

import com.example.foodApp.Exception.TokenRefreshException;
import com.example.foodApp.Request.LoginRequest;
import com.example.foodApp.Request.TokenRefreshRequest;
import com.example.foodApp.Service.CustomerUserDetailsSertvice;
import com.example.foodApp.Service.RefreshTokenService;
import com.example.foodApp.config.JwtProvider;
import com.example.foodApp.model.Cart;
import com.example.foodApp.model.RefeshToken;
import com.example.foodApp.model.USER_ROLE;
import com.example.foodApp.model.User;
import com.example.foodApp.reponsitory.CartRepository;
import com.example.foodApp.reponsitory.UserRepository;
import com.example.foodApp.response.AuthResponse;
import com.example.foodApp.response.TokenRefreshResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private CustomerUserDetailsSertvice customerUserDetailsSertvice;
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    RefreshTokenService refreshTokenService;


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
        User isEmailExits = userRepository.findByEmail(user.getEmail());
        if (isEmailExits != null) {
            throw new Exception("Email is already used another account");
        }

        User createUser = new User();

        createUser.setEmail(user.getEmail());
        createUser.setFullname(user.getFullname());
        createUser.setRole(user.getRole());
        createUser.setPassword(passwordEncoder.encode(user.getPassword()));

        User saveUser = userRepository.save(createUser);

        Cart cart = new Cart();
        cart.setCustomer(saveUser);
        cartRepository.save(cart);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.gennerateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("register Susscess");
        authResponse.setRole(saveUser.getRole());
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@RequestBody TokenRefreshRequest request) {
        // Lấy refresh token từ request body
        String requestRefreshToken = request.getRefreshToken();

        // Tìm refresh token trong cơ sở dữ liệu bằng refreshTokenService
        return refreshTokenService.findByToken(requestRefreshToken)
                // Nếu tìm thấy token, kiểm tra xem token có hết hạn không
                .map(refreshTokenService::verifyExpiration)
                // Lấy thông tin người dùng liên kết với refresh token
                .map(RefeshToken::getUser)
                // Tạo mới access token dựa trên thông tin của người dùng (email)
                .map(user -> {
                    String token = jwtProvider.generateTokenFromUsername(user.getEmail());
                    // Trả về Access Token mới cùng với Refresh Token cho client
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                // Nếu không tìm thấy token, ném ra ngoại lệ với thông báo lỗi
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }


    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> singning(@RequestBody LoginRequest req){
        String userName=req.getEmail();
        String password=req.getPassword();
        User user=userRepository.findByEmail(userName);
        Authentication authentication=authenticate(userName,password);
        Collection<? extends GrantedAuthority> authorities= authentication.getAuthorities();
        String role=authorities.isEmpty()?null:authorities.iterator().next().getAuthority();
        String jwt = jwtProvider.gennerateToken(authentication);


        RefeshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("register Susscess");
        authResponse.setRole(USER_ROLE.valueOf(role));
        authResponse.setRefreshToken(refreshToken.getToken());
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }



    private Authentication authenticate(String userName, String password) {
        UserDetails userDetails= customerUserDetailsSertvice.loadUserByUsername(userName);

        if(userDetails ==null){
            throw new BadCredentialsException("In valid username..");
        }

        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

    }
}
