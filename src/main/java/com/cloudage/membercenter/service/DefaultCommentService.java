package com.cloudage.membercenter.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudage.membercenter.entity.Comment;
import com.cloudage.membercenter.entity.User;
import com.cloudage.membercenter.repository.ICommentRepository;
@Component
@Service
@Transactional

public class DefaultCommentService implements ICommentService{
	@Autowired
	ICommentRepository commentRepo;
	
	@Override
	public Page<Comment> findAllArticleId(int id, Pageable page) {
		return commentRepo.findAllArticleId(id, page);
	}

	@Override
	public Page<Comment> findCommentsOfArticle(int atc_id,int page) {
		Sort sort = new Sort(Direction.DESC, "createDate");
		PageRequest request = new PageRequest(atc_id, 10, sort);
		return commentRepo.findAllArticleId(atc_id, request);
	}

	@Override
	public Comment save(Comment commment) {
		// TODO Auto-generated method stub
		return commentRepo.save(commment);
	}

	@Override
	public int getCommentsCount(int atc_id) {
		return commentRepo.getCommentsCount(atc_id);
	}


	@Override
	public Page<Comment> findAllMyComments(int page) {
		Sort sort = new Sort(Direction.DESC, "createDate");
		PageRequest request = new PageRequest(page, 10, sort);
		return commentRepo.findAll(request);
	}

	@Override
	public Page<Comment> searchComment(String text , int page) {
		Sort sort = new Sort(Direction.DESC, "createDate");
		PageRequest request = new PageRequest(page, 10, sort);
		return commentRepo.search(text, request);
	}






	



	
}
