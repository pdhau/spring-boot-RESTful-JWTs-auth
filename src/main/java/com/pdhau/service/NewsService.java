package com.pdhau.service;

import java.util.List;

import com.pdhau.model.News;

public interface NewsService {
	public List<News> getAllNews();

	public News getNews();

	public boolean createNews(News news);

	public boolean deleteNews(News news);

	public void deleteAllNews();
	
	public News updateNews(News news);
}
