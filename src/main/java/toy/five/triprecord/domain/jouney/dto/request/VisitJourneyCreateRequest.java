package toy.five.triprecord.domain.jouney.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.five.triprecord.domain.jouney.entity.JourneyType;


import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class VisitJourneyCreateRequest {

    @NotNull
    private String name;
    @NotNull
    private String location;
    @NotNull
    private JourneyType type;
    @NotNull
    private LocalDateTime startTime;
    @NotNull
    private LocalDateTime endTime;

}
