package com.prokopchyk.webApp.repositories;

import com.prokopchyk.webApp.Models.Articles;
import org.springframework.data.repository.CrudRepository;

public interface ArticlesRepository extends CrudRepository<Articles,Long> {
}
