package toy.five.triprecord.domain.jouney.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import toy.five.triprecord.domain.jouney.dto.request.LocationRequest;
import toy.five.triprecord.domain.jouney.dto.request.LodgmentJourneyUpdateRequest;
import toy.five.triprecord.domain.trip.entity.Trip;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class LodgmentJourney extends BaseJourney {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne  // mappedBy는 주인 쪽만
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String dormitoryName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JourneyType type;

    @Embedded
    private Location lodgmentLocation;

    private void setUpdateName(String name) {
        this.name = name;
    }

    private void setUpdateDormitoryName(String dormitoryName) {
        this.dormitoryName = dormitoryName;
    }

    public void setUpdateLodgeLocation(LocationRequest lodgmentLocation) {
        this.lodgmentLocation = Location.builder()
            .placeName(lodgmentLocation.getPlaceName())
            .x(lodgmentLocation.getX())
            .y(lodgmentLocation.getY())
            .roadAddressName(lodgmentLocation.getRoadAddressName())
            .addressName(lodgmentLocation.getAddressName())
            .categoryName(lodgmentLocation.getCategoryName())
            .build();
    }

    public void setUpdateColumns(LodgmentJourneyUpdateRequest lodgmentJourneyUpdateRequest) {
        setUpdateName(lodgmentJourneyUpdateRequest.getName());
        setUpdateDormitoryName(lodgmentJourneyUpdateRequest.getDormitoryName());
        setUpdateStartTime(lodgmentJourneyUpdateRequest.getStartTime());
        setUpdateEndTime(lodgmentJourneyUpdateRequest.getEndTime());
    }



}
