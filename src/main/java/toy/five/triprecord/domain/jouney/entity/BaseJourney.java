package toy.five.triprecord.domain.jouney.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import toy.five.triprecord.global.common.BaseTimeEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public class BaseJourney extends BaseTimeEntity {

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;
}
