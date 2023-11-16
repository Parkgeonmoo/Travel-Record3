package toy.five.triprecord.global.security.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    private Long id;

    //토큰 자체로는 만료 기한 외의 정보를 가지고 있지 않기 때문에 해당 멤버의 토큰을 식별하기 위해 refresh token 테이블에 token만 저장하는 것이 아니라 회원 id column을 추가
    @Column(name = "email")
    private String email;

    @Column(name = "token")
    private String token;

    protected RefreshToken() {
    }

    public RefreshToken(String email, String token) {
        this.email = email;
        this.token = token;
    }



}
