package pz.recipes.recipes.find.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("search")
public class SearchController {


    @GetMapping("/recipes")
    public void findRecipeByQuery(@RequestParam(value = "query", defaultValue = "") String query) {


    }

}