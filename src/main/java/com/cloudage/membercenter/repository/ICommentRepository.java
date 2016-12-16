

package com.cloudage.membercenter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cloudage.membercenter.entity.Comment;


@Repository
public interface ICommentRepository extends PagingAndSortingRepository<Comment, Integer> {
	
	@Query("from Comment comment where comment.article.id = ?1")
	Page<Comment> findAllArticleId(int articleId, Pageable page);

	@Query("select count(*) from Comment comment where comment.article.id = ?1")
	int getCommentsCount(int atc_id);

	@Query("from Comment comment where comment.text like %?1%")
	Page<Comment> search(String text, Pageable page);

	

}
