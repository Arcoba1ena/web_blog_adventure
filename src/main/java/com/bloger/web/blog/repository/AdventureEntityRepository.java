package com.bloger.web.blog.repository;

import com.bloger.web.blog.models.AdventureEntity;
import org.springframework.data.repository.CrudRepository;

public interface AdventureEntityRepository extends CrudRepository<AdventureEntity, Long> {
}