package ww.Github.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ww.Github.mapper.GithubMapper;
import ww.Github.service.dto.GithubDetailDto;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GithubService {

    private final GithubMapper githubMapper;


    public GithubDetailDto getUser(String userName) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.github.com/users/" + userName + "/events/public";

        return githubMapper.mapResponseEntityToDto(
                restTemplate.getForEntity(url, String.class),
                getRepoNames(userName),
                userName
        );
    }

    private List<String> getRepoNames(String userName) throws JsonProcessingException {
        String reposUrl = "https://api.github.com/users/" + userName + "/repos";
        return githubMapper.extractNamesFromJson(reposUrl);
    }
}
