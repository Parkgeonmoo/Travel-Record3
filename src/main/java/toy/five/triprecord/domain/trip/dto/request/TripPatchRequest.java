package toy.five.triprecord.domain.trip.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.five.triprecord.domain.trip.entity.Domestic;
import toy.five.triprecord.domain.trip.validation.patch.TripPatchTimeConstraint;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@TripPatchTimeConstraint
public class TripPatchRequest {

    @Nullable
    private String name;
    @Nullable
    private LocalDateTime startTime;
    @Nullable
    private LocalDateTime endTime;
    @Nullable
    private Domestic domestic;


    public void PatchFromTripPatchRequest(TripPatchRequest tripPatchRequest) {
        if (!tripPatchRequest.getName().isEmpty()) {
            this.name = tripPatchRequest.getName();
        }
        if (tripPatchRequest.getStartTime() != null) {
            this.startTime = tripPatchRequest.getStartTime();
        }
        if (tripPatchRequest.getEndTime() != null) {
            this.endTime = tripPatchRequest.getEndTime();
        }
        if (tripPatchRequest.getDomestic() != null) {
            this.domestic = tripPatchRequest.getDomestic();
        }
    }



}
