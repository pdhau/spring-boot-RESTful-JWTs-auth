package com.pdhau.repository;
import org.springframework.data.repository.CrudRepository;

import com.pdhau.model.News;

public interface NewsRepository extends CrudRepository<News, Long>{
	public News findByTitle(String title);
}
