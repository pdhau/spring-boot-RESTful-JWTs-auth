package com.pdhau.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.pdhau.model.News;
import com.pdhau.service.NewsService;

@RestController
@RequestMapping("/api")
public class NewsController {

	@Autowired
	NewsService newsService;

	@RequestMapping(value = "/news", method = RequestMethod.GET)
	public ResponseEntity<List<News>> getListNews() {
		List<News> news_list = newsService.getAllNews();

		if (news_list.isEmpty()) {
			return new ResponseEntity<List<News>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<News>>(news_list, HttpStatus.OK);
	}

	@RequestMapping(value = "/news", method = RequestMethod.POST)
	public ResponseEntity<News> createNews(@RequestBody News news) {
		if(!newsService.createNews(news)) {
			return new ResponseEntity<News>(HttpStatus.NOT_IMPLEMENTED);
		}
		return new ResponseEntity<News>(news, HttpStatus.CREATED);
	}
	
	
}
