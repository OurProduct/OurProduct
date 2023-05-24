package com.example.bookingservice;

import com.example.bookingservice.model.BookingPlace;
import com.example.bookingservice.model.BoughtPlace;
import com.example.bookingservice.model.PlaceBookingPrice;
import com.example.bookingservice.model.User;
import com.example.bookingservice.model.dto.BuyPlaceRequest;
import com.example.bookingservice.model.OperationStats;
import com.example.bookingservice.model.dto.PaymentCredentials;
import com.example.bookingservice.repository.BookingRepository;
import com.example.bookingservice.repository.PBPRepository;
import com.example.bookingservice.repository.impl.BoughtPlaceRepositoryImpl;
import com.example.bookingservice.service.BoughtService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class BookingServiceApplication implements CommandLineRunner {
	private final BookingRepository bookingRepository;
	private final PBPRepository pbpRepository;
	private final BoughtPlaceRepositoryImpl boughtPlaceRepository;
	private final BoughtService boughtService;
	private final Gson gson;
	private final WebClient webClient;

	public static void main(String[] args) {
		SpringApplication.run(BookingServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		bookingRepository.save(BookingPlace.builder().unionKey("example").geoPlace("geoPlace").title("title").description("decription").build())
//				.subscribe();
//		System.out.println(Thread.currentThread().getName());
//		pbpRepository.save(PlaceBookingPrice.builder().price(1000).dateStart("05.06.08 01.01.2023").dateEnd("05.06.08 02.02.2023").build()).subscribe();
//
//		bookingRepository.addPlaceBookingPrice("example", PlaceBookingPrice.builder().id(1L).dateStart("2023-01-01").dateEnd("2023-02-02").build())
//				.subscribe(s -> System.out.println(s));
//		BoughtPlace boughtPlace = new BoughtPlace();boughtPlace.setPrice(1000);
//		boughtPlace.setDateStart(boughtPlace.convertDate("02.06.08 01.01.2023"));boughtPlace.setDateEnd(boughtPlace.convertDate("03.06.08 12.01.2023"));
//		boughtPlaceRepository.save(boughtPlace).subscribe();
//		BoughtPlace boughtPlace1 = new BoughtPlace();boughtPlace1.setPrice(2000);
//		boughtPlace1.setDateStart(boughtPlace.convertDate("02.06.08 03.01.2023"));boughtPlace1.setDateEnd(boughtPlace1.convertDate("03.06.08 04.01.2023"));
//		boughtPlaceRepository.save(boughtPlace1).subscribe();
//		boughtPlaceRepository.findCountBoughtPlaceBetweenDate(LocalDateTime.parse("02.06.08 01.01.2023", DateTimeFormatter.ofPattern("ss.mm.HH dd.MM.yyyy")),
//				LocalDateTime.parse("03.06.08 12.01.2023", DateTimeFormatter.ofPattern("ss.mm.HH dd.MM.yyyy")))
//				.subscribe(System.out::println);
		PlaceBookingPrice placeBookingPrice = PlaceBookingPrice.builder().price(1000)
				.dateStart("00.00.12 01.01.2023").dateEnd("00.00.14 27.04.2024").build();
//		pbpRepository.save(placeBookingPrice).block();
		BookingPlace bookingPlace = bookingRepository.findByUnionKey("example").block();
		BoughtPlace boughtPlace = BoughtPlace.builder().dateStart(LocalDateTime.now().minusDays(3L))
				.dateEnd(LocalDateTime.now().minusDays(2L)).price(1000).unionKey("example").build();
//		boughtPlaceRepository.save(boughtPlace).block();
		PaymentCredentials paymentCredentials = PaymentCredentials.builder().amount(1000)
				.card("2202 2305 4554 5439").userEmail("email").build();
		User user = User.builder().email("email").isBlock(false).build();
		BuyPlaceRequest buyPlaceRequest = BuyPlaceRequest.builder().bookingPlace(bookingPlace)
				.boughtPlace(boughtPlace).user(user).paymentCredentials(paymentCredentials).build();
		BuyPlaceRequest buyPlaceRequest1 = gson.fromJson(gson.toJson(buyPlaceRequest), BuyPlaceRequest.class);
		log.info(buyPlaceRequest1.toString());
		log.info(gson.toJson(buyPlaceRequest));
//		bookingRepository.save(BookingPlace.builder().description("desc").build()).block();
//		bookingRepository.findByLikeDescription("desc").subscribe(s -> System.out.println(s));
//		boughtService.buyPlace(BuyPlaceRequest.builder().bookingPlace(bookingPlace)
//				.boughtPlace(boughtPlace).paymentCredentials(paymentCredentials).user(user).build())
//				.switchIfEmpty(Mono.defer(() -> {
//					log.info("wwww");
//					return Mono.just(OperationStats.builder().build());
//				}))
//				.subscribe(s -> System.out.println(s));
//		pbpRepository.findByPrice(500, 1500).subscribe(s -> System.out.println(s));
	}
}
