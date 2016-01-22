package com.crossover.article.dao;

import java.util.HashMap;
import java.util.Map;

import com.crossover.article.model.Article;

public enum ArticleDAO {
  instance;
  
  private Map<String, Article> contentProvider = new HashMap<>();
  
  private ArticleDAO() {
    
    Article article = new Article("1", "file1.pdf");
    article.setDescription("My first PDF");
    contentProvider.put("1", article);
    article = new Article("2", "file2.pdf");
    article.setDescription("My second PDF");
    contentProvider.put("2", article);
    
  }
  public Map<String, Article> getModel(){
    return contentProvider;
  }
  
} 

