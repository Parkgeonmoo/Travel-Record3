package toy.five.triprecord.domain.jouney.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
//@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class JourneyCreateResponse {

    private List<MoveJourneyCreateResponse> moves;
    private List<VisitJourneyCreateResponse> visits;
    private List<LodgmentJourneyCreateResponse> lodgments;

    public static JourneyCreateResponse of(List<MoveJourneyCreateResponse> moves,
                                           List<VisitJourneyCreateResponse> visits,
                                           List<LodgmentJourneyCreateResponse> lodgments)
    {
        return JourneyCreateResponse.builder()
                .moves(moves)
                .visits(visits)
                .lodgments(lodgments)
                .build();
    }

}
