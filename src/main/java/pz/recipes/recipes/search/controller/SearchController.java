package pz.recipes.recipes.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pz.recipes.recipes.search.dto.SearchResponse;
import pz.recipes.recipes.search.service.SearchService;

@RestController
@RequestMapping("search")
public class SearchController {

    @Autowired SearchService searchService;

    @GetMapping("/recipes")
    public ResponseEntity<?> findRecipesByQuery(@RequestParam(value = "query", defaultValue = "") String query,
                                                @RequestParam(value = "page", defaultValue = "1") int page,
                                                @RequestParam(value = "limit", defaultValue = "10") int limit) {
        return new ResponseEntity<>(new SearchResponse<>(searchService.findAllRecipesByQuery(query, page, limit)), HttpStatus.OK);
    }

}