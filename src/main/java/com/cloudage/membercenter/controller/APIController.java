
package com.cloudage.membercenter.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudage.membercenter.entity.Article;
import com.cloudage.membercenter.entity.Comment;
import com.cloudage.membercenter.entity.User;
import com.cloudage.membercenter.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.cloudage.membercenter.service.DefaultUserService;
import com.cloudage.membercenter.service.IArticleService;
import com.cloudage.membercenter.service.ICommentService;
import com.cloudage.membercenter.service.ILikesService;
@RestController
@RequestMapping("/api")
public class APIController {
	@Autowired
	IUserService userService;
	
	@Autowired
	IArticleService articleService;

	@Autowired
	ICommentService commentSv;
	
	@Autowired
	ILikesService likesService;
	@RequestMapping(value = "/hello", method=RequestMethod.GET)
	public @ResponseBody String hello(){
		return "HELLO WORLD";
	}
	
	@RequestMapping(value = "/register", method=RequestMethod.POST)
	public @ResponseBody User register(
			@RequestParam String name,
			@RequestParam String account,
			@RequestParam String email,
			@RequestParam String passwordHash,
			MultipartFile avatar,
			HttpServletRequest request
			){
		
		User user = new User();
		
		user.setName(name);
		user.setAccount(account);
		user.setEmail(email);
		user.setPasswordHash(passwordHash);
		
		
		
		if(avatar != null){
			String realpath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");
			try {
				FileUtils.copyInputStreamToFile(avatar.getInputStream(), new File(realpath,avatar+".png"));
				user.setAvatar("upload/"+avatar+".png");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return userService.save(user);
		
	}
	
	@RequestMapping(value = "/login", method=RequestMethod.POST)
	public @ResponseBody User login(
			@RequestParam String account,
			@RequestParam String passwordHash,
			HttpServletRequest request){
			 User obj = userService.findByAccount(account);
			 if( obj != null && obj.getPasswordHash().equals(passwordHash)){
				 
				 HttpSession session = request.getSession(true);
				 session.setAttribute("uid", obj.getId());
				 return obj;
			 }else{
				 return null;
			 }
		
		
		
	}
	
	@RequestMapping(value = "/me", method=RequestMethod.GET)
	public @ResponseBody User getCurrentUser(HttpServletRequest request){
		 HttpSession session = request.getSession();
		 Integer uid = (Integer) session.getAttribute("uid");
			return userService.findById(uid);
	}
	
	@RequestMapping(value = "/passwordrecover", method=RequestMethod.POST)
	public @ResponseBody User passwordrecover(HttpServletRequest request){
		 HttpSession session = request.getSession();
		 String email = (String) session.getAttribute("email");
			return userService.findByEmail(email);
	}
	
	@RequestMapping(value="/article/{userId}")
	public List <Article> getArticlesByUserID(@PathVariable Integer userId){
		return (List<Article>) articleService.findByUserId(userId);
	}
	@RequestMapping(value = "/article", method=RequestMethod.POST)
	public @ResponseBody Article addArticle(
			@RequestParam String title,
			@RequestParam String text,
		
			HttpServletRequest request){
			User user = getCurrentUser(request);
			Article article = new Article();
			article.setAuthor(user);
			article.setTitle(title);
			article.setText(text);
			return articleService.save(article);
	}



	
	@RequestMapping(value="/feeds/{page}")
	public Page<Article> getFeeds(@PathVariable int page){
		return articleService.getFeeds(page);
	}
	
	@RequestMapping("/feeds")
	public Page <Article> getFeeds(){
		return getFeeds(0);
	}
	
	@RequestMapping(value="/article/{article_id}/comments/{page}")
	public Page<Comment> getCommentsOfArticle(@PathVariable int article_id,@PathVariable int page){
		return commentSv.findCommentsOfArticle(article_id,page);
	}
	
	@RequestMapping(value="/article/{article_id}/comments/count")
	public int getCommentsCount(@PathVariable int article_id){
		return commentSv.getCommentsCount(article_id);
	}
	
	@RequestMapping(value="/article/{article_id}/comments",method=RequestMethod.GET)
	public Page<Comment> getComments(@PathVariable int article_id){
		return commentSv.findCommentsOfArticle(article_id,0);
	}
	
	@RequestMapping(value="/article/{article_id}/comments",method=RequestMethod.POST)
	public Comment postComments(
			@PathVariable int article_id,
			@RequestParam String text,
			HttpServletRequest request	
			){
		User me = getCurrentUser(request);
		Article atc = articleService.findOne(article_id);
		
		Comment commment = new Comment();
		commment.setText(text);
		commment.setAuthor(me);
		commment.setArticle(atc);
		return commentSv.save(commment);
	}
	
	
	@RequestMapping(value="article/{article_id}/likes", method = RequestMethod.POST)
	public int changeLikes(
			@PathVariable  int article_id,
			@RequestParam  boolean likes,
			HttpServletRequest request){
		User user = getCurrentUser(request);
		Article article = articleService.findOne(article_id);
		
		if(likes){
			likesService.addLike(user, article);
		}else{
			likesService.removeLike(user, article);
		}
		return likesService.countLike(article_id);
	}
	
	@RequestMapping(value="article/{article_id}/isLiked", method = RequestMethod.GET)
	public boolean checkLikes(
			@PathVariable  int article_id,
			HttpServletRequest request){
			User user= getCurrentUser(request);
			Article article  = articleService.findOne(article_id);
		return likesService.checkLikesExit(user, article);
		
	}
	
	@RequestMapping("/comments")
	public Page<Comment> getAllMyComments(){
		return getAllMyComments(0);
	}
	
	@RequestMapping(value="/comments/{page}")
	public Page<Comment> getAllMyComments(
			@PathVariable  int page
			){
	
		return commentSv.findAllMyComments(page);
	}
	
	@RequestMapping(value="/search", method=RequestMethod.POST)
	public Page<Comment> searchComment(
			@RequestParam  String text
			){
		
		return searchComment(text,0);
		
	}
	
	@RequestMapping(value="/search/{page}", method=RequestMethod.POST)
	public Page<Comment> searchComment(
			@RequestParam  String text,
			@PathVariable  int page
			){
		return commentSv.searchComment(text,page);
		
	}
	
}
