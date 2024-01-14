package me.blog.controller;

import lombok.RequiredArgsConstructor;
import me.blog.dto.AddArticleRequest;
import me.blog.domain.Article;
import me.blog.dto.ArticleResponse;
import me.blog.dto.UpdateArticleRequest;
import me.blog.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BlogApiController {

    private final BlogService blogService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/articles")
    public Article addArticle(@RequestBody AddArticleRequest request) {
        Article savedArticle = blogService.save(request);

        return savedArticle; //todo entity를 반환함 dto로 반환하도록 나중에 고쳐야 할듯
    }

    @GetMapping("/api/articles")
    public List<ArticleResponse> findAllArticles() {
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();
        return articles;
    }

    @GetMapping("/api/articles/{id}")
    public ArticleResponse findArticle(@PathVariable long id) {
        Article article = blogService.findById(id);
        return new ArticleResponse(article);
    }

    @DeleteMapping("/api/articles/{id}")  //todo entity private key로 지워도 되나?
    public void deleteArticle(@PathVariable("id") long id) {
        blogService.delete(id);
    }

    @PutMapping("/api/articles/{id}") //todo dto로 반환
    public Article updateArticle(@PathVariable long id, @RequestBody UpdateArticleRequest request) {
        Article updatedArticle = blogService.update(id, request);
        return updatedArticle; 
    }
}
