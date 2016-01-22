package com.crossover.article.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.crossover.article.dao.ArticleDAO;
import com.crossover.article.model.Article;

// Will map the resource to the URL article
@Path("/article")
public class ArticlesResource {

	// Allows to insert contextual objects into the class,
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	// Return the list of article to the user in the browser
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Article> getArticleBrowser() {
		List<Article> article = new ArrayList<Article>();
		article.addAll(ArticleDAO.instance.getModel().values());
		return article;
	}

	// Return the list of article for applications
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Article> getArticle() {
		List<Article> article = new ArrayList<Article>();
		article.addAll(ArticleDAO.instance.getModel().values());
		return article;
	}

	//returns the number of article
	//Use http://localhost:8080/com.crossover.article/rest/article/count
	//to get the total number of records
	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		int count = ArticleDAO.instance.getModel().size();
		return String.valueOf(count);
	}

	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newArticle(@FormParam("id") String id, @FormParam("file") String file, @FormParam("description") String description,	@Context HttpServletResponse servletResponse) throws IOException {
		Article article = new Article(id, file);
		
		if (description != null) {
			article.setDescription(description);
		}
		
		ArticleDAO.instance.getModel().put(id, article);
		
		servletResponse.sendRedirect("../add_update_article.html");
	}

	//Defines that the next path parameter after article is
	//treated as a parameter and passed to the TodoResources
	//Allows to type http://localhost:8080/com.vogella.jersey.todo/rest/article/1
	//1 will be treaded as parameter article and passed to TodoResource
	@Path("{article}")
	public ArticleResource getArticle(@PathParam("article") String id) {
		return new ArticleResource(uriInfo, request, id);
	}

}


