package net.busbackend.services.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import net.busbackend.DTO.UserRequestDTO;
import net.busbackend.DTO.UserResponseDTO;
import net.busbackend.entites.RefreshToken;
import net.busbackend.entites.User;
import net.busbackend.repos.RefreshRepo;
import net.busbackend.repos.UserRepository;
import net.busbackend.security.AuthCookieUtil;
import net.busbackend.security.JwtUtil;
import net.busbackend.services.EmailService;
import net.busbackend.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtUtil jwtUtil;
    private final RefreshRepo refreshRepo;
    private final AuthCookieUtil authCookieUtil;

    @Value("${collab.admin.email}")
    private String adminEmail;

    @Override
    public UserResponseDTO getUserById(String id) {
        return userRepository.findById(id).map(user -> modelMapper.map(user, UserResponseDTO.class)).orElse(null);
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(user -> modelMapper.map(user, UserResponseDTO.class)).orElse(null);
    }

    @Override
    public UserResponseDTO saveUser(User user) {
        if(user.getEmail()==null || user.getEmail().isEmpty()){
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        else if(user.getPassword()==null || user.getPassword().isEmpty()){
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserResponseDTO.class);
    }

    @Override
    public boolean verifyPassword(String email, String password) {
        Optional<User> userOptional=userRepository.findByEmail(email);
        return userOptional.filter(user -> passwordEncoder.matches(password, user.getPassword())).isPresent();
    }

    @Override
    public UserResponseDTO resetPassword(Map<String, String> request){
        String email = request.get("email");
        String otp = request.get("otp");
        String newPassword=request.get("newPassword");
        if (email == null || otp == null || newPassword == null) {
            throw new ValidationException("Missing email, OTP or new password");
        }
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new ValidationException("User with given email does not exist");
        }
        User user = userOptional.get();
        if(!user.getOtp().equals(otp)){
            return null;
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setOtp(null);
        user.setTokenVersion(user.getTokenVersion()+1);
        return saveUser(user);
    }

    @Override
    public void generateAndSendOtp(String email) {
        Optional<User>user=userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new ValidationException("User with given email does not exist");
        }
        User existingUser=user.get();
        String otp=String.valueOf((int)(Math.random()*900000)+100000);
        existingUser.setOtp(otp);
        userRepository.save(existingUser);
        // Here you should implement the logic to send the OTP to the user's email.
        emailService.sendOtp(email, otp);
    }

    @Override
    public boolean verifyOtp(UserRequestDTO userRequest) throws IOException {
        String email = userRequest.getEmail();
        String otp = userRequest.getOtp();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new ValidationException("User not found"));

        if (otp != null && otp.equals(user.getOtp())) {
            user.setVerified(true);
            saveUser(user);
            return true;
        }
        return false;
    }

    @Override
    public void refresh(String rt, HttpServletResponse resp) {
        try{
            Claims claims=jwtUtil.parseClaims(rt);
            String tid=claims.get("tid", String.class);
            if(tid==null){
                throw new ValidationException("Invalid refresh token");
            }
            Integer tokenVer=claims.get("ver", Integer.class);

            Optional<RefreshToken> existingRt=refreshRepo.findById(tid);
            if(existingRt.isEmpty()){
                throw new ValidationException("Invalid refresh token");
            }
            User user=existingRt.get().getUser();

            if(user.getTokenVersion()!=tokenVer){
                throw new ValidationException("Refresh token has been invalidated");
            }
            refreshRepo.deleteAllByUser(user);
            generateAndSetTokens(resp,user.getEmail());

        }
        catch (JwtException e){
            throw new ValidationException("Invalid or expired refresh token");
        }
    }

    @Override
    public void generateAndSetTokens(HttpServletResponse resp, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ValidationException("User not found"));

        // Generate tokens
        String accessToken = jwtUtil.generateAccessToken(user);
        String tokenId = UUID.randomUUID().toString();
        String refreshToken = jwtUtil.generateRefreshToken(user, tokenId);

        // Save refresh token (consider hashing in production)
        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setId(tokenId);
        refreshTokenEntity.setUser(user);
        refreshTokenEntity.setToken(refreshToken);
        refreshRepo.save(refreshTokenEntity);

        authCookieUtil.setAccessTokenCookie(resp, accessToken);
        authCookieUtil.setRefreshTokenCookie(resp, refreshToken);
    }

    @Override
    public void registerUser(UserRequestDTO userRequestDTO) {
        String email = userRequestDTO.getEmail();

        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("Email cannot be null or empty");
        }

        email = email.trim().toLowerCase();

        //  Check if user already exists
        if (userRepository.findByEmail(email).isPresent()) {
            throw new ValidationException("User already exists with this email");
        }

        User userEntity = new User();
        userEntity.setEmail(email);

        if (email.equalsIgnoreCase(adminEmail)) {
            userEntity.setAdmin(true);
        }

        String otp = String.valueOf((int) (Math.random() * 900000) + 100000);
        userEntity.setOtp(otp);
        userEntity.setVerified(false);
        userEntity.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));

        emailService.sendOtp(email, otp);

        saveUser(userEntity);
    }
    private String getUsernameFromEmail(String email) {
        if (email == null || !email.contains("@")) {
            return "Bus_Mate User";
        }
        return email.split("@")[0]; // Returns the part before @
    }
}
