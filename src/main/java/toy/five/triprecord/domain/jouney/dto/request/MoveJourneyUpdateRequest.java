package toy.five.triprecord.domain.jouney.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MoveJourneyUpdateRequest {

    @NotNull
    private String name;
    @NotNull
    private String vehicle;
    @NotNull
    private String startPoint;
    @NotNull
    private String endPoint;
    @NotNull
    private LocalDateTime startTime;
    @NotNull
    private LocalDateTime endTime;

}
