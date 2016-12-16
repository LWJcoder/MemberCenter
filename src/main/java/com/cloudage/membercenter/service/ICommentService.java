package com.cloudage.membercenter.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cloudage.membercenter.entity.Comment;
import com.cloudage.membercenter.entity.User;

public interface ICommentService {

	Page<Comment> findAllArticleId(int id, Pageable page);


	Comment save(Comment commment);

	Page<Comment> findCommentsOfArticle(int atc_id, int i);


	int getCommentsCount(int atc_id);


	Page<Comment> findAllMyComments(int page);


	Page<Comment> searchComment(String text, int page);





	
}
