package toy.five.triprecord.domain.jouney.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import toy.five.triprecord.domain.jouney.dto.request.VisitJourneyUpdateRequest;
import toy.five.triprecord.domain.trip.entity.Trip;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class VisitJourney extends BaseJourney {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne  // mappedBy는 주인 쪽만
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JourneyType type;

    public void updateEntity(VisitJourneyUpdateRequest request) {
        this.name = request.getName();
        this.location = request.getLocation();
    }


}
