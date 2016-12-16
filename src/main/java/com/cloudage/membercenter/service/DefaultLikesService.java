package com.cloudage.membercenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudage.membercenter.entity.Article;
import com.cloudage.membercenter.entity.Likes;
import com.cloudage.membercenter.entity.Likes.Key;
import com.cloudage.membercenter.entity.User;
import com.cloudage.membercenter.repository.ILikesRepository;
@Configuration
@Component
@Service
@Transactional
public class DefaultLikesService implements ILikesService{
	@Autowired
	ILikesRepository likeRepo;
	@Override
	public void addLike(User user, Article article) {
			 Likes.Key l = new Key();
			 l.setArticle(article);
			 l.setUser(user);
			
			 Likes lk = new Likes();
			 lk.setId(l);
			 likeRepo.save(lk);
		
	}

	@Override
	public void removeLike(User user, Article article) {
		
		Likes.Key key = new Key();
		
		key.setUser(user);
		key.setArticle(article);
		
		likeRepo.delete(key);
		
	}

	@Override
	public int countLike(int article_id) {
		
		return likeRepo.likeCount(article_id);
	}

	public boolean checkLikesExit(User user,Article article){
		return  likeRepo.checkLikesExit(user, article)>0;
	}

}
