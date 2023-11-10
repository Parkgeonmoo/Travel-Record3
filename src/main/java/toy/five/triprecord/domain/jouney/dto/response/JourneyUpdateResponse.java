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
public class JourneyUpdateResponse {

    private List<MoveJourneyUpdateResponse> moves;
    private List<VisitJourneyUpdateResponse> visits;
    private List<LodgmentJourneyUpdateResponse> lodgments;

    public static JourneyUpdateResponse of(List<MoveJourneyUpdateResponse> moves,
                                           List<VisitJourneyUpdateResponse> visits,
                                           List<LodgmentJourneyUpdateResponse> lodgments)
    {
        return JourneyUpdateResponse.builder()
                .moves(moves)       //moves 가 null이라면 에러발생, Nullable 처리 예정
                .visits(visits)     //마찬가지
                .lodgments(lodgments)
                .build();
    }

}
