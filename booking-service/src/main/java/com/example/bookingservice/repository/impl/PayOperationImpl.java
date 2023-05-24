package com.example.bookingservice.repository.impl;

import com.example.bookingservice.model.BookingPlace;
import com.example.bookingservice.model.PayOperation;
import com.example.bookingservice.model.OperationStats;
import com.example.bookingservice.repository.PayOperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PayOperationImpl implements PayOperationRepository {
    private final DatabaseClient databaseClient;

    @Override
    public Mono<String> save(PayOperation payOperation) {
        final String sql = "INSERT INTO pay_operation (operation_key, amount, date, user_email) VALUES (:operationKey, :amount, :date, :userEmail) RETURNING operation_key";
        final String key = UUID.randomUUID().toString();
        return databaseClient.sql(sql)
                .bind("operationKey", key)
                .bind("amount", payOperation.getAmount())
                .bind("date", payOperation.getDate())
                .bind("userEmail", payOperation.getUserEmail())
                .map(m -> key)
                .one();
    }

    @Override
    public Mono<OperationStats> save(OperationStats operationStats) {
        final boolean emptyBooking = operationStats.getBookingPlace() != null && operationStats.getBookingPlace().getId() == null;
        final boolean emptyBought = operationStats.getBoughtPlace() != null && operationStats.getBoughtPlace().getId() == null;

        if (emptyBooking && emptyBought)
            return saveWithoutId(operationStats);
        if (emptyBooking)
            return saveWithoutBookingPlaceId(operationStats);
        if (emptyBought)
            return saveWithoutBoughtPlaceId(operationStats);

        final String sql = "INSERT INTO operation_stats (complete, operation_key, booking_place_id, bought_place_id) " +
                "VALUES (:bool, :operationKey, :bookingPlaceId, :boughtPlaceId) RETURNING id";

        return databaseClient.sql(sql)
                .bind("bool", operationStats.isComplete())
                .bind("operationKey", operationStats.getOperationKey())
                .bind("bookingPlaceId", operationStats.getBookingPlace().getId())
                .bind("boughtPlaceId", operationStats.getBoughtPlace().getId())
                .map(m -> {
                    final OperationStats res = operationStats;
                    res.setId((Long) m.get(0));
                    return res;
                })
                .one();
    }

    private Mono<OperationStats> saveWithoutBookingPlaceId(OperationStats operationStats) {
        final String sql = "INSERT INTO operation_stats (complete, operation_key, booking_place_id, bought_place_id) " +
                "VALUES (:bool, :operationKey, (SELECT id FROM booking_place WHERE union_key = :unionKey), :boughtPlaceId) RETURNING id";
        return databaseClient.sql(sql)
                .bind("bool", operationStats.isComplete())
                .bind("operationKey", operationStats.getOperationKey())
                .bind("unionKey", operationStats.getBookingPlace().getUnionKey())
                .bind("boughtPlaceId", operationStats.getBoughtPlace().getId())
                .map(m -> {
                    final OperationStats res = operationStats;
                    res.setId((Long) m.get(0));
                    return res;
                })
                .one();
    }

    private Mono<OperationStats> saveWithoutBoughtPlaceId(OperationStats operationStats) {
        final String sql = "INSERT INTO operation_stats (complete, operation_key, booking_place_id, bought_place_id) " +
                "VALUES (:bool, :operationKey, :bookingPlaceId, (SELECT id FROM bought_place WHERE union_key = :unionKey)) RETURNING id";
        return databaseClient.sql(sql)
                .bind("bool", operationStats.isComplete())
                .bind("operationKey", operationStats.getOperationKey())
                .bind("bookingPlaceId", operationStats.getBookingPlace().getId())
                .bind("unionKey", operationStats.getBoughtPlace().getUnionKey())
                .map(m -> {
                    final OperationStats res = operationStats;
                    res.setId((Long) m.get(0));
                    return res;
                })
                .one();
    }

    private Mono<OperationStats> saveWithoutId(OperationStats operationStats) {
        final String sql = "INSERT INTO operation_stats (complete, operation_key, booking_place_id, bought_place_id) VALUES (:bool, :operationKey, " +
                "(SELECT id FROM booking_place WHERE union_key = :bookingUnionKey), (SELECT id FROM bought_place WHERE union_key = :boughtUnionKey)) RETURNING id";
        return databaseClient.sql(sql)
                .bind("bool", operationStats.isComplete())
                .bind("operationKey", operationStats.getOperationKey())
                .bind("bookingUnionKey", operationStats.getBookingPlace().getUnionKey())
                .bind("boughtUnionKey", operationStats.getBoughtPlace().getUnionKey())
                .map(m -> {
                    final OperationStats res = operationStats;
                    res.setId((Long) m.get(0));
                    return res;
                })
                .one();
    }

}
