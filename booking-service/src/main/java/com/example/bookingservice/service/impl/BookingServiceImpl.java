package com.example.bookingservice.service.impl;

import com.example.bookingservice.exception.BoughtPlaceAlreadySoldException;
import com.example.bookingservice.exception.DifferenceDataRequestException;
import com.example.bookingservice.model.BookingPlace;
import com.example.bookingservice.model.BoughtPlace;
import com.example.bookingservice.repository.BookingRepository;
import com.example.bookingservice.repository.BoughtPlaceRepository;
import com.example.bookingservice.repository.PBPRepository;
import com.example.bookingservice.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final PBPRepository pbpRepository;
    private final BookingRepository bookingRepository;
    private final BoughtPlaceRepository boughtPlaceRepository;

    @Override
    public Mono<Boolean> check(BookingPlace request, BoughtPlace boughtPlace) {
        return bookingRepository.findByUnionKey(request.getUnionKey())
                .map(b ->
                        b.getUnionKey().equals(request.getUnionKey()) && b.getGeoPlace().equals(request.getGeoPlace())
                                && b.getFilePresentationKey().equals(request.getFilePresentationKey()))
                .flatMap(b -> {
                    if (b)
                        return boughtPlaceRepository.findCountBoughtPlaceBetweenDate(boughtPlace.getDateStart(), boughtPlace.getDateEnd());
                    return Mono.error(new DifferenceDataRequestException("Bought place in request not same in DB"));
                })
                .map(m -> {
                    log.info("COUNT: {}", m);
                    if (m == 0)
                        return true;
                    else
                        throw new BoughtPlaceAlreadySoldException("Place between date already sold");
                })
                .flatMap(m ->  pbpRepository.findByDateAndPrice(boughtPlace.getDateStart(), boughtPlace.getDateEnd(), boughtPlace.getPrice(), boughtPlace.getUnionKey()).collectList())
                .map(m -> {
                    if (m != null && m.size() > 0)
                        return true;
                    throw new DifferenceDataRequestException("Bought place not same in DB");
                });
//                .onErrorResume(throwable -> Mono.just(false));
    }
}
