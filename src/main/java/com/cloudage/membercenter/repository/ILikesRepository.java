package com.cloudage.membercenter.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cloudage.membercenter.entity.Article;
import com.cloudage.membercenter.entity.Likes;
import com.cloudage.membercenter.entity.User;
@Repository
public interface ILikesRepository extends PagingAndSortingRepository<Likes, Likes.Key>{


	@Query("select count(*) from Likes likes where likes.id.article.id = ?1")
	 int likeCount(int article_id);
	

	@Query("select count(*) from Likes likes where likes.id.user.id = ?1 and likes.id.article.id= ?2")
	int checkLikesExit( User user, Article article);
}
