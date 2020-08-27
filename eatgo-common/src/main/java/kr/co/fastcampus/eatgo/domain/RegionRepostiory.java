package kr.co.fastcampus.eatgo.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RegionRepostiory extends CrudRepository<Region, Long> {
    List<Region> findAll();

    // 만들어줘도 되지만 CrudRepository 에 기본으로 내장되어 있음.
//    Region save(Region region);
}
