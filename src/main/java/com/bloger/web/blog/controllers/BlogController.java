package com.bloger.web.blog.controllers;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import com.bloger.web.blog.models.AdventureEntity;
import com.bloger.web.blog.services.RegistrationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import com.bloger.web.blog.repository.AdventureEntityRepository;

@Controller
public class BlogController {
    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private AdventureEntityRepository adventureEntityRepository;

    //просмотр путешествий
    @GetMapping("/blogs/adventure")
    public String blogPageAdventures(Model model) {
        Iterable<AdventureEntity> adventureEntities = adventureEntityRepository.findAll();
        model.addAttribute("adventures", adventureEntities);
        return "blogs-adventure";
    }

    @GetMapping("/blog/add/adventure")
    public String blogAddAdventureGetPage(Model model) {
        model.addAttribute("page", "Написать статью");
        System.out.println("/blog/add" + registrationService.getAllCredentials());
        if (!registrationService.getAllCredentials().isEmpty()) {
            for (var cred : registrationService.getAllCredentials()) {
                if (cred.get("isAuthentication").getAsBoolean()) {
                    return "blog-add-adventure";
                }
            }
        }
        return "auth";
    }

    @PostMapping("/blog/add/adventure")
    public String blogAddPostAdventurePage(
            @RequestParam String city,
            @RequestParam String price,
            @RequestParam String culturePlace,
            @RequestParam String visitPlace,
            @RequestParam String rating,
            Model model) {
        AdventureEntity adventureEntity = new AdventureEntity(city, price, culturePlace, visitPlace, rating);
        adventureEntityRepository.save(adventureEntity);
        return "redirect:/blogs/adventure";
    }

    @GetMapping("/blog/{id}/adventure")
    public String blogUserDetailsPageAdventure(@PathVariable(value = "id") long userId, Model model) {
        if (!registrationService.getAllCredentials().isEmpty()) {
            for (var cred : registrationService.getAllCredentials()) {
                if (cred.get("isAuthentication").getAsBoolean()) {
                    if (!adventureEntityRepository.existsById(userId)) {
                        return "redirect:/blogs";
                    }
                    Optional<AdventureEntity> adventureEntity = adventureEntityRepository.findById(userId);
                    List<AdventureEntity> resultList = new ArrayList<>();
                    adventureEntity.ifPresent(resultList::add);
                    model.addAttribute("adventures", resultList);
                    return "blog-details-adventure";
                }
            }
        }
        return "auth";
    }

    //редактирование путешествия
    @GetMapping("/blog/{id}/edit/adventure")
    public String blogEditPageAdventure(@PathVariable(value = "id") long userId, Model model) {
        if (!adventureEntityRepository.existsById(userId)) {
            return "redirect:/blogs-adventure";
        }
        Optional<AdventureEntity> adventureEntity = adventureEntityRepository.findById(userId);
        List<AdventureEntity> resultList = new ArrayList<>();
        adventureEntity.ifPresent(resultList::add);
        model.addAttribute("adventures", resultList);
        return "blog-edit-adventure";
    }

    @PostMapping("/blog/{id}/edit/adventure")
    public String blogAddPostUpdatePageAdventure(
            @PathVariable(value = "id") long id,
            @RequestParam String city,
            @RequestParam String price,
            @RequestParam String culturePlace,
            @RequestParam String visitPlace,
            @RequestParam String rating,
            Model model) {
        AdventureEntity adventureEntity = adventureEntityRepository.findById(id).orElseThrow();
        adventureEntity.setCity(city);
        adventureEntity.setPrice(price);
        adventureEntity.setCulturePlace(culturePlace);
        adventureEntity.setVisitPlace(visitPlace);
        adventureEntity.setRating(rating);
        adventureEntityRepository.save(adventureEntity);
        return "redirect:/blogs/adventure";
    }

    //удаление путешествия
    @PostMapping("/blog/{id}/remove/adventure")
    public String blogAddPostDeletePageAdventure(@PathVariable(value = "id") long id, Model model) {
        AdventureEntity adventureEntity = adventureEntityRepository.findById(id).orElseThrow();
        adventureEntityRepository.delete(adventureEntity);
        return "redirect:/blogs/adventure";
    }
}