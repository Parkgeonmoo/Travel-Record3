package toy.five.triprecord.domain.user.dto.request;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.five.triprecord.domain.trip.validation.common.CommonTimeConstraint;
import toy.five.triprecord.domain.user.validation.common.PasswordConstraint;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserCreateRequest {



    @NotBlank(message ="이메일을 입력해주세요.")
    @Email(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$", message = "이메일 형식으로 입력해주세요.")
    private String email;


    @NotBlank(message ="비밀번호를 입력해주세요.")
    @PasswordConstraint
    private String password;

    @NotBlank(message ="이름을 입력해주세요.")
    private String name;

}
