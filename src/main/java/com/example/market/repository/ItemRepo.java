package com.example.market.repository;

import com.example.market.entity.ItemEntity;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepo extends CrudRepository<ItemEntity, String> {}
