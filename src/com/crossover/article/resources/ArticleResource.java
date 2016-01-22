package com.crossover.article.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import com.crossover.article.dao.ArticleDAO;
import com.crossover.article.model.Article;

public class ArticleResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String id;

	public ArticleResource(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}

	//Application integration     
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Article getArticle() {
		Article article = ArticleDAO.instance.getModel().get(id);
		
		if(article==null)
			throw new RuntimeException("Get: Article with " + id +  " not found");
		
		return article;
	}

	// for the browser
	@GET
	@Produces(MediaType.TEXT_XML)
	public Article getArticleHTML() {
		Article article = ArticleDAO.instance.getModel().get(id);
		
		if(article==null)
			throw new RuntimeException("Get: Article with " + id +  " not found");
		
		return article;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response putArticle(JAXBElement<Article> article) {
		Article a = article.getValue();
		
		return putAndGetResponse(a);
	}

	@DELETE
	public void deleteArticle() {
		Article a = ArticleDAO.instance.getModel().remove(id);
		
		if(a==null)
			throw new RuntimeException("Delete: Article with " + id +  " not found");
	}

	private Response putAndGetResponse(Article article) {
		Response res;
		
		if(ArticleDAO.instance.getModel().containsKey(article.getId())) {
			res = Response.noContent().build();
		} else {
			res = Response.created(uriInfo.getAbsolutePath()).build();
		}
		
		ArticleDAO.instance.getModel().put(article.getId(), article);
		
		return res;
	}
}