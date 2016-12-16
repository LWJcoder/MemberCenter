package com.cloudage.membercenter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudage.membercenter.entity.Article;
import com.cloudage.membercenter.entity.User;
import com.cloudage.membercenter.repository.IArticleRepository;
@Configuration
@Component
@Service
@Transactional
public class DefaultArticleService implements IArticleService{
	@Autowired
	IArticleRepository atcRepo;

	@Override
	public List<Article> findByUserId(Integer userId) {
		return atcRepo.findByUserId(userId);
	}

	@Override
	public Article save(Article article) {
		return atcRepo.save(article);
	}

	@Override
	public Page<Article> getFeeds(int page) {
		Sort sort= new Sort(Direction.DESC, "createDate");
		PageRequest request = new PageRequest(page, 10,sort);
		return atcRepo.findAll(request);
	}

	@Override
	public List<Article> findByAuthor(User user) {
		return atcRepo.findByAuthor(user);
	}

	@Override
	public Article findOne(int atc_id) {
		return atcRepo.findOne(atc_id);
	}

	

	
}
