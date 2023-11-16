package toy.five.triprecord.domain.journey.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import toy.five.triprecord.domain.jouney.dto.request.JourneyCreateRequest;
import toy.five.triprecord.domain.jouney.dto.request.MoveJourneyCreateRequest;
import toy.five.triprecord.domain.jouney.dto.response.JourneyCreateResponse;
import toy.five.triprecord.domain.jouney.entity.JourneyType;
import toy.five.triprecord.domain.jouney.entity.MoveJourney;
import toy.five.triprecord.domain.jouney.repository.LodgmentJourneyRepository;
import toy.five.triprecord.domain.jouney.repository.MoveJourneyRepository;
import toy.five.triprecord.domain.jouney.repository.VisitJourneyRepository;
import toy.five.triprecord.domain.jouney.service.JourneyService;
import toy.five.triprecord.domain.trip.entity.Trip;
import toy.five.triprecord.domain.trip.repository.TripRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class JourneyServiceTest {

    @InjectMocks
    private JourneyService journeyService;

    @Mock
    private TripRepository tripRepository;

    @Mock
    private MoveJourneyRepository moveJourneyRepository;

    @Mock
    private LodgmentJourneyRepository lodgmentJourneyRepository;

    @Mock
    private VisitJourneyRepository visitJourneyRepository;

    @Test
    void saveJourneys() {
        // given
        Long tripId = 1L;

        Trip trip = Trip.builder()
                .id(tripId)
                .build();

        String name = "이동01";
        String vehicle = "이동수단01";
        String startPoint = "서울";
        String endPoint = "부산";
        String startTime = "2023-10-26T10:00:00";
        String endTime = "2023-10-26T12:00:00";
        JourneyType type = JourneyType.MOVE;

        MoveJourneyCreateRequest moveJourneyCreateRequest = MoveJourneyCreateRequest.builder()
                .name(name)
                .vehicle(vehicle)
                .startPoint(startPoint)
                .endPoint(endPoint)
                .startTime(LocalDateTime.parse(startTime))
                .endTime(LocalDateTime.parse(endTime))
                .type(type)
                .build();

        JourneyCreateRequest journeyCreateRequest = JourneyCreateRequest.builder()
                .moves(List.of(moveJourneyCreateRequest))
                .build();

        MoveJourney moveJourney = MoveJourney.builder()
                .trip(trip)
                .name(name)
                .vehicle(vehicle)
                .startPoint(startPoint)
                .endPoint(endPoint)
                .startTime(LocalDateTime.parse(startTime))
                .endTime(LocalDateTime.parse(endTime))
                .type(type)
                .build();

        given(moveJourneyRepository.saveAll(any())).willReturn(List.of(moveJourney));
        given(tripRepository.findById(anyLong())).willReturn(Optional.of(trip));

        // when
        JourneyCreateResponse result = journeyService.saveJourneys(tripId, journeyCreateRequest);

        // then
        assertThat(result.getMoves().get(0).getName()).isEqualTo(name);

    }

}
