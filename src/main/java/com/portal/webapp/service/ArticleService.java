package com.portal.webapp.service;

import java.util.List;

import com.portal.webapp.entity.Article;
import org.springframework.data.domain.Pageable;

public interface ArticleService {

	public Iterable<Article> getAll();
	
	public List<Article> getByDescription(String description);
	
	public List<Article> getByDescription(String description, Pageable pageable);
	
	public Article getByCodArt(String codArt);
	
	public void deleteArticle(Article article);
	
	public void insertArticle(Article article);
}
