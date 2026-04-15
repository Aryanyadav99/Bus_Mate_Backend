package net.busbackend.security;


import lombok.RequiredArgsConstructor;
import net.busbackend.DTO.UserResponseDTO;
import net.busbackend.entites.User;
import net.busbackend.repos.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class SecurityUtil {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return userRepository.findById(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    }

    public UserResponseDTO getCurrentUserDto() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        User user = userRepository.findById(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        return modelMapper.map(user, UserResponseDTO.class);
    }

    public boolean isCurrentUserAdmin() {
        return getCurrentUser().isAdmin();
    }

    public void assertCurrentUserIsAdmin() {
        if (!isCurrentUserAdmin()) {
            throw new AccessDeniedException("You do not have permission to access this resource");
        }
    }

}
