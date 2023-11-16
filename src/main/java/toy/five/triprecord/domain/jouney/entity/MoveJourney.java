package toy.five.triprecord.domain.jouney.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import toy.five.triprecord.domain.jouney.dto.request.LocationRequest;
import toy.five.triprecord.domain.jouney.dto.request.MoveJourneyUpdateRequest;
import toy.five.triprecord.domain.trip.entity.Trip;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class MoveJourney extends BaseJourney {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String vehicle;

    @Column(nullable = false)
    private String startPoint;

    @Column(nullable = false)
    private String endPoint;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JourneyType type;

    @Embedded
    @AttributeOverrides( value = {
        @AttributeOverride(name = "placeName", column = @Column(name = "start_place_name")),
        @AttributeOverride(name = "categoryName", column = @Column(name = "start_category_name")),
        @AttributeOverride(name = "addressName", column = @Column(name = "start_address_name")),
        @AttributeOverride(name = "roadAddressName", column = @Column(name = "start_road_address_name")),
        @AttributeOverride(name = "x", column = @Column(name = "start_x")),
        @AttributeOverride(name = "y", column = @Column(name = "start_y"))
    })
    private Location startLocation;

    @Embedded
    @AttributeOverrides( value = {
        @AttributeOverride(name = "placeName", column = @Column(name = "end_place_name")),
        @AttributeOverride(name = "categoryName", column = @Column(name = "end_category_name")),
        @AttributeOverride(name = "addressName", column = @Column(name = "end_address_name")),
        @AttributeOverride(name = "roadAddressName", column = @Column(name = "end_road_address_name")),
        @AttributeOverride(name = "x", column = @Column(name = "end_x")),
        @AttributeOverride(name = "y", column = @Column(name = "end_y"))
    })
    private Location endPointLocation;


    private void setUpdateName(String name) {
        this.name = name;
    }

    private void setUpdateVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    private void setUpdateStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    private void setUpdateEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public void setUpdateStartLocation(LocationRequest startLocation) {
        this.startLocation = Location.builder()
            .x(startLocation.getX())
            .y(startLocation.getY())
            .categoryName(startLocation.getCategoryName())
            .addressName(startLocation.getAddressName())
            .roadAddressName(startLocation.getRoadAddressName())
            .placeName(startLocation.getPlaceName())
            .build();
    }

    public void setUpdateEndPointLocation(LocationRequest endPointLocation) {
        this.endPointLocation = Location.builder()
            .x(endPointLocation.getX())
            .y(endPointLocation.getY())
            .categoryName(endPointLocation.getCategoryName())
            .addressName(endPointLocation.getAddressName())
            .roadAddressName(endPointLocation.getRoadAddressName())
            .placeName(endPointLocation.getPlaceName())
            .build();
    }

    public void setUpdateColumns(MoveJourneyUpdateRequest moveJourneyUpdateRequest) {
        setUpdateName(moveJourneyUpdateRequest.getName());
        setUpdateVehicle(moveJourneyUpdateRequest.getVehicle());
        setUpdateStartPoint(moveJourneyUpdateRequest.getStartPoint());
        setUpdateEndPoint(moveJourneyUpdateRequest.getEndPoint());
        setUpdateStartTime(moveJourneyUpdateRequest.getStartTime());
        setUpdateEndTime(moveJourneyUpdateRequest.getEndTime());
    }



}
