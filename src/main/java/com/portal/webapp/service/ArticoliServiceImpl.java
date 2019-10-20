package com.portal.webapp.service;

import com.portal.webapp.entity.Article;
import org.springframework.transaction.annotation.Transactional;

import com.portal.webapp.repository.ArticoliRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional(readOnly = true)
public class ArticoliServiceImpl implements ArticleService
{
	@Autowired
	ArticoliRepository articoliRepository;
	
	@Override
	public Iterable<Article> getAll()
	{
		return articoliRepository.findAll();
	}

	@Override
	public List<Article> getByDescription(String description, Pageable pageable) {
		return articoliRepository.findByDescrizioneLike(description, pageable);
	}

	@Override
	public List<Article> getByDescription(String description) {

		return articoliRepository.getByDescription(description);
	}
	
	@Override
	public Article getByCodArt(String codArt)
	{
		return articoliRepository.findByCodArt(codArt);
	}

	@Override
	@Transactional
	public void deleteArticle(Article article)
	{
		articoliRepository.delete(article);
	}

	@Override
	@Transactional
	public void insertArticle(Article article)
	{
		articoliRepository.save(article);
	}
}
