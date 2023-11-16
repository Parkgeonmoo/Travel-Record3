package toy.five.triprecord.domain.trip.entity;


import jakarta.persistence.*;
import lombok.*;
import toy.five.triprecord.domain.comment.entity.Comment;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import toy.five.triprecord.domain.jouney.entity.LodgmentJourney;
import toy.five.triprecord.domain.jouney.entity.MoveJourney;
import toy.five.triprecord.domain.jouney.entity.VisitJourney;
import toy.five.triprecord.domain.trip.dto.request.TripPatchRequest;
import toy.five.triprecord.domain.trip.dto.request.TripUpdateRequest;
import toy.five.triprecord.domain.user.entity.User;
import toy.five.triprecord.global.common.BaseTimeEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@DynamicInsert
public class Trip extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 30)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "trip", cascade = CascadeType.ALL)
    private List<MoveJourney> moveJourneys;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "trip", cascade = CascadeType.ALL)
    private List<VisitJourney> visitJourneys;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "trip", cascade = CascadeType.ALL)
    private List<LodgmentJourney> lodgmentJourneys;

    @OneToMany(mappedBy = "trip", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id asc") // 댓글 정렬
    private List<Comment> comments;

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column
    private Domestic domestic;

    @ColumnDefault("0")
    private Long wishCount;

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

    public long plusWishCount() {
        this.wishCount++;
        return this.wishCount;
    }

    public long minusWishCount() {
        this.wishCount--;
        return this.wishCount;
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

    @PrePersist
    public void prePersist() {
        this.wishCount = this.wishCount == null ? 0 : this.wishCount;
    }

}