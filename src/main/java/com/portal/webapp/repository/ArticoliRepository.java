package com.portal.webapp.repository;

import java.util.List;

import com.portal.webapp.entity.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ArticoliRepository extends PagingAndSortingRepository<Article, String> {
	
	@Query(value = "SELECT * FROM ARTICOLI WHERE DESCRIZIONE LIKE :desArt", nativeQuery=true)
	List<Article> getByDescription(@Param("desArt") String description);

	List<Article> findByDescrizioneLike(String description, Pageable pageable);
	
	Article findByCodArt(String codArt);
	
}
