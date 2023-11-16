package toy.five.triprecord.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toy.five.triprecord.domain.user.dto.request.UserGetRequest;
import toy.five.triprecord.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    public Optional<User> findByEmail(String email);

    public boolean existsByEmail (String email);

    public User findByName(String name);


}
