package com.pdhau.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdhau.model.News;
import com.pdhau.repository.NewsRepository;

@Service
public class NewsServiceImpl implements NewsService {

	@Autowired
	NewsRepository newsRepository;

	@Override
	public List<News> getAllNews() {
		List<News> news_list = (List<News>) newsRepository.findAll();
		return news_list;
	}

	@Override
	public News getNews() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createNews(News news) {
		News n = null;
		n = newsRepository.save(news);
		if (null != n) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteNews(News news) {
		News n = null;
		n = newsRepository.findByTitle(news.getTitle());
		if (n == null) {
			return false;
		}
		newsRepository.delete(n);
		return true;
	}

	@Override
	public void deleteAllNews() {
		newsRepository.deleteAll();
	}

	@Override
	public News updateNews(News news) {
		News n = null;
		n = newsRepository.findByTitle(news.getTitle());
		if (n == null) {
			return null;
		}
		n.setContent(news.getContent());
		n.setTitle(news.getTitle());
		newsRepository.save(n);
		return n;
	}

}
