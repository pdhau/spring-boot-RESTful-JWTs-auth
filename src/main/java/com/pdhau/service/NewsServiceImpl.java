package com.pdhau.service;

import java.util.ArrayList;
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
		if(null != n) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteNews(News news) {
		News n = null;
		n = newsRepository.findOne(news.getId());
		if (n != null) {
			newsRepository.delete(news);
			return true;
		}

		return false;
	}

	@Override
	public News deleteAllNews() {
		// TODO Auto-generated method stub
		return null;
	}

}
