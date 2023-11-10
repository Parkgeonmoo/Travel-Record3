package toy.five.triprecord.domain.trip.dto.request;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.five.triprecord.domain.trip.entity.Domestic;
import toy.five.triprecord.domain.trip.validation.common.CommonTimeConstraint;
import toy.five.triprecord.global.util.BaseTimeRequest;



@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@CommonTimeConstraint
public class TripUpdateRequest extends BaseTimeRequest {

    @NotNull(message ="필드가 전부 채워져야 합니다.")
    @NotEmpty(message ="필드가 전부 채워져야 합니다.")
    @NotBlank(message ="필드가 전부 채워져야 합니다.")
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull(message ="필드가 전부 채워져야 합니다.")
    private Domestic domestic;




}