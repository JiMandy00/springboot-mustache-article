package com.mustache.article.controller;

import com.mustache.article.domain.Article;
import com.mustache.article.domain.dto.ArticleDto;
import com.mustache.article.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/articles")
@Slf4j
public class ArticleController {

    ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/{id}")
    public String selectSingle(@PathVariable Long id, Model model) {
        Optional<Article> optArticle = articleRepository.findById(id);
        if (!optArticle.isEmpty()) { // 비어있지 않으면
            model.addAttribute("aritlce", optArticle.get());
            return "show";
        } else {
            return "error";
        }
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Article> articles = articleRepository.findAll();
        model.addAttribute("articles", articles);
        return "list";
    }

    @GetMapping("")
    public String index() {
        return "redirect:/articles/list";
    }

    @PostMapping("/new") // create post
    public String add(ArticleDto articleDto) {
        log.info(articleDto.getTitle()); // title을 log로 기록
        Article saveArticle = articleRepository.save(articleDto.toEntity());
        log.info("generatedId:{}", saveArticle.getId());
        return "";
    }
}
