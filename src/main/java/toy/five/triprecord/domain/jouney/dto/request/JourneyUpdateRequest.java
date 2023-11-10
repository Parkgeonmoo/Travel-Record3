package toy.five.triprecord.domain.jouney.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class JourneyUpdateRequest {

    private List<MoveJourneyUpdateRequest> moves;
    private List<VisitJourneyUpdateRequest> visits;
    private List<LodgmentJourneyUpdateRequest> lodgments;

}
