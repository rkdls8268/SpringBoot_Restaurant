package kr.co.fastcampus.eatgo.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RegionRepostiory extends CrudRepository<Region, Long> {
    List<Region> findAll();
}
