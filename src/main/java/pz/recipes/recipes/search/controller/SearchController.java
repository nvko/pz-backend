package pz.recipes.recipes.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pz.recipes.recipes.MessageResponse;
import pz.recipes.recipes.recipes.dto.RecipesRequest;
import pz.recipes.recipes.recipes.dto.RecipesResponse;
import pz.recipes.recipes.recipes.service.RecipesService;
import pz.recipes.recipes.search.dto.SearchResponse;
import pz.recipes.recipes.search.service.SearchService;

@RestController
@RequestMapping("search")
public class SearchController {

    @Autowired SearchService searchService;
    @Autowired RecipesService recipesService;

    @GetMapping("/ingredients")
    public ResponseEntity<?> findIngredientsByQuery(@RequestParam(value = "query", defaultValue = "") String query) {
        return new ResponseEntity<>(new SearchResponse<>(searchService.findAllIngredientsByQuery(query)), HttpStatus.OK);
    }

    @GetMapping("/recipes")
    public ResponseEntity<?> getRecipesByQuery(@RequestParam(value = "page", defaultValue = "0") int page,
                                               @RequestParam(value = "limit", defaultValue = "10", required = false) int limit,
                                               @RequestParam(value = "query", defaultValue = "") String query) {
        if (query != null) {
            return new ResponseEntity<>(new RecipesResponse(searchService.findByQuery(query, page, limit)), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("Bad request"), HttpStatus.BAD_REQUEST);
    }

    // TODO: sort
    @PostMapping("/recipes")
    public ResponseEntity<?> getRecipesByIngredients(@RequestParam(value = "page", defaultValue = "0") int page,
                                                     @RequestParam(value = "limit", defaultValue = "10", required = false) int limit,
                                                     @RequestParam(value = "sort", defaultValue = "id") String sort,
                                                     @RequestBody RecipesRequest recipesRequest) {
        if (recipesRequest != null) {
            if (recipesRequest.getIngredients() != null)
                return new ResponseEntity<>(new RecipesResponse(recipesService.findByIngredients(page, limit, sort, recipesRequest.getIngredients())), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("Bad request"), HttpStatus.BAD_REQUEST);
    }
}