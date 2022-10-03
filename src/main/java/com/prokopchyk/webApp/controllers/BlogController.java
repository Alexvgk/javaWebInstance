package com.prokopchyk.webApp.controllers;

import com.prokopchyk.webApp.Models.Articles;
import com.prokopchyk.webApp.repositories.ArticlesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private ArticlesRepository ArticlesRepository;

    @GetMapping("/blog")
    public String blogMain(Model model){
        Iterable<Articles> articals = ArticlesRepository.findAll();
        model.addAttribute("articals",articals);
        return "blog-main";
    }
    @GetMapping("/blog/add")
    public String blogAdd(Model model){
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title,
                              @RequestParam String anons,
                              @RequestParam String fullText, Model model){
        Articles articles = new Articles(title,anons,fullText);
        ArticlesRepository.save(articles);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id,  Model model){
        if(!ArticlesRepository.existsById(id)){
            return "redirect:/blog";
        }
       Optional<Articles> articles =  ArticlesRepository.findById(id);
        ArrayList<Articles> res = new ArrayList<>();
        articles.ifPresent(res::add);
        model.addAttribute("post",res);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id,  Model model){
        if(!ArticlesRepository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Articles> articles =  ArticlesRepository.findById(id);
        ArrayList<Articles> res = new ArrayList<>();
        articles.ifPresent(res::add);
        model.addAttribute("post",res);
        return "blog-edit";
    }
    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@RequestParam String title,
                              @RequestParam String anons,
                              @RequestParam String fullText,
                                 Model model,
                                 @PathVariable(value = "id") long id){
        Articles art = ArticlesRepository.findById(id).orElseThrow();
        art.setTitle(title);
        art.setAnons(anons);
        art.setFullText(fullText);
        ArticlesRepository.save(art);
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogPostRemove(Model model, @PathVariable(value = "id") long id){
        Articles art = ArticlesRepository.findById(id).orElseThrow();
        ArticlesRepository.delete(art);
        return "redirect:/blog";
    }





}
