package com.cts.mfrp.parksmart.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.mfrp.parksmart.service.SearchService;

@RestController
@RequestMapping("api/search")
public class SearchController {
	
	@Autowired
	private SearchService searchService;
	

	@PostMapping("/location/suggest")
    public ResponseEntity<List<String>> suggestLocations(@RequestBody Map<String, String> body) {

        String query = body.get("query");
        if (query == null || query.isBlank()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<String> suggestions = searchService.getLocationSuggestions(query);
        return ResponseEntity.ok(suggestions);
    }


}
