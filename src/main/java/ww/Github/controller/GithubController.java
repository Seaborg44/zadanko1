package ww.Github.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ww.Github.service.GithubService;
import ww.Github.service.dto.GithubDetailDto;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/")
public class GithubController {

    private final GithubService githubService;

    @GetMapping(value = "/name/{name}", produces = "application/json")
    public ResponseEntity<GithubDetailDto> getUser(@PathVariable String name)  {
        try {
            final HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(githubService.getUser(name), httpHeaders, HttpStatus.OK);
        } catch (ResponseStatusException | JsonProcessingException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User Not Found", e);
        }
    }

}
