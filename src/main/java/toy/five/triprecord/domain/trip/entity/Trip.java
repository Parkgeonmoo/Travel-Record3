package toy.five.triprecord.domain.trip.entity;


import jakarta.persistence.*;
import lombok.*;
import toy.five.triprecord.domain.jouney.entity.LodgmentJourney;
import toy.five.triprecord.domain.jouney.entity.MoveJourney;
import toy.five.triprecord.domain.jouney.entity.VisitJourney;
import toy.five.triprecord.domain.trip.dto.request.TripPatchRequest;
import toy.five.triprecord.domain.trip.dto.request.TripUpdateRequest;
import toy.five.triprecord.global.common.BaseTimeEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Trip extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(length = 30)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "trip", cascade = CascadeType.ALL)
    private List<MoveJourney> moveJourneys;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "trip", cascade = CascadeType.ALL)
    private List<VisitJourney> visitJourneys;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "trip", cascade = CascadeType.ALL)
    private List<LodgmentJourney> lodgmentJourneys;

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column
    private Domestic domestic;

    private void updateName(String name) {
        if (!name.isEmpty()) {
            this.name = name;
        }
    }

    private void updateStartTime(LocalDateTime startTime) {
        if (startTime != null) {
            this.startTime = startTime;
        }
    }

    private void updateEndTime(LocalDateTime endTime) {
        if (endTime != null) {
            this.endTime = endTime;
        }
    }

    private void updateIsDomestic(Domestic domestic) {
        if (domestic != null) {
            this.domestic = domestic;
        }
    }


    public void updateColumns(TripPatchRequest tripPatchRequest) {
        updateName(tripPatchRequest.getName());
        updateStartTime(tripPatchRequest.getStartTime());
        updateEndTime(tripPatchRequest.getEndTime());
        updateIsDomestic(tripPatchRequest.getDomestic());
    }

    public void updateAllColumns(TripUpdateRequest tripUpdateRequest) {
        updateName(tripUpdateRequest.getName());
        updateStartTime(tripUpdateRequest.getStartTime());
        updateEndTime(tripUpdateRequest.getEndTime());
        updateIsDomestic(tripUpdateRequest.getDomestic());
    }










}