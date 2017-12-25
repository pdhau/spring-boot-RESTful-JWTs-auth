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

	@RequestMapping(value = "/news", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<News> createNews(@RequestBody News news) {
		if(!newsService.createNews(news)) {
			return new ResponseEntity<News>(HttpStatus.NOT_IMPLEMENTED);
		}
		return new ResponseEntity<News>(news, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/news", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<News> delNews(@RequestBody News news){
		boolean status = newsService.deleteNews(news);
		if(!status) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/news/all", method = RequestMethod.DELETE)
	public ResponseEntity<News> delNews(){
		newsService.deleteAllNews();
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/news", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<News> updateNews(@RequestBody News news) {
		News n = newsService.updateNews(news);
		if(n == null) {
			return new ResponseEntity<News>(HttpStatus.NOT_IMPLEMENTED);
		}
		return new ResponseEntity<News>(n, HttpStatus.OK);
	}
	
}
