package toy.five.triprecord.global.util;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class BaseTimeRequest {

    @NotNull(message ="시작 시간이  필요합니다.")
    private LocalDateTime startTime;

    @NotNull(message ="필드가 전부 채워져야 합니다.")
    private LocalDateTime endTime;

}
