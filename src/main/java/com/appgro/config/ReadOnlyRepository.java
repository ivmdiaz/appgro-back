package com.appgro.config;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface ReadOnlyRepository<T> extends JpaRepository<T, Long> {

    @Override
    @NonNull
    default <S extends T> S save(@NonNull S entity) {
        throw new RuntimeException("Action not allowed");
    }

    @Override
    @NonNull
    default <S extends T> List<S> saveAll(@NonNull Iterable<S> iterable) {
        throw new RuntimeException("Action not allowed.");
    }

    @Override
    @NonNull
    default <S extends T> S saveAndFlush(@NonNull S s) {
        throw new RuntimeException("Action not allowed.");
    }

    @Override
    default void delete(@NonNull T entity) {
        throw new RuntimeException("Action not allowed.");
    }

    @Override
    default void deleteAll() {
        throw new RuntimeException("Action not allowed.");
    }

    @Override
    default void deleteAll(@NonNull Iterable<? extends T> entities) {
        throw new RuntimeException("Action not allowed.");
    }

    @Override
    default void deleteAllInBatch() {
        throw new RuntimeException("Action not allowed.");
    }

    @Override
    default void deleteById(@NonNull Long id) {
        throw new RuntimeException("Action not allowed.");
    }

    @Override
    default void deleteInBatch(@NonNull Iterable<T> iterable) {
        throw new RuntimeException("Action not allowed.");
    }
}
