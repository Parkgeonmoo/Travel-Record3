package toy.five.triprecord.domain.user.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import toy.five.triprecord.domain.user.dto.request.UserPatchRequest;
import toy.five.triprecord.domain.user.dto.request.UserUpdateReqeust;
import toy.five.triprecord.global.common.BaseTimeEntity;

import java.util.Collection;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="Users")
public class User extends BaseTimeEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private String email;

    @Column
    private String password;

    @Column
    private String name;
    

    
    private void setUpdatePassword(String password) {

        this.password = password;
    }

    private void setUpdateName(String name) {
        this.name = name;
    }

    private void setPatchPassword(String password) {
        if (!StringUtils.isBlank(password)) {
            this.password = password;
        }
    }

    private void setPatchName(String name) {
        if (!StringUtils.isBlank(name)) {
            this.name = name;
        }
    }

    public void setUpdateColumns(UserUpdateReqeust userUpdateReqeust) {
        setUpdatePassword(userUpdateReqeust.getPassword());
        setUpdateName(userUpdateReqeust.getName());
    }

    public void setPatchColumns(UserPatchRequest userPatchRequest) {
        setPatchPassword(userPatchRequest.getPassword());
        setPatchName(userPatchRequest.getName());
    }


    // TODO: 권한내용 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    // username 반환
    @Override
    public String getUsername() {
        return email;
    }

    // 계정 만료 확인
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠김 확인
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 인증정보 만료 확인
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화 확인
    @Override
    public boolean isEnabled() {
        return true;
    }
}
