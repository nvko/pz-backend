package pz.recipes.recipes.search.dto;

import java.util.List;

public class SearchResponse<T> {

    List<T> results;

    public SearchResponse(List<T> results) {
        this.results = results;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
