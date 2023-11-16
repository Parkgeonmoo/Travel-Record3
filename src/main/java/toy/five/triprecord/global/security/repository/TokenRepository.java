package toy.five.triprecord.global.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import toy.five.triprecord.domain.user.dto.request.UserLoginRequest;
import toy.five.triprecord.domain.user.entity.User;
import toy.five.triprecord.global.security.entity.RefreshToken;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<RefreshToken,Long> {
    public Optional<RefreshToken> findByEmail(String email);


}
