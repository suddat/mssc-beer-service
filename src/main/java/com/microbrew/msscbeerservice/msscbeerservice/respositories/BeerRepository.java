package com.microbrew.msscbeerservice.msscbeerservice.respositories;

import com.microbrew.msscbeerservice.msscbeerservice.domain.Beer;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {
}
