package ww.Github.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.swagger.models.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.spring.web.json.Json;
import ww.Github.service.dto.BranchDetailDto;
import ww.Github.service.dto.GithubDetailDto;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class GithubMapper {

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate = new RestTemplate();

    public GithubDetailDto mapResponseEntityToDto(ResponseEntity<String> responseEntity, List<String> repoNames, String userName) throws JsonProcessingException {
        JsonNode jsonTree = objectMapper.readTree(responseEntity.getBody());

        return GithubDetailDto
                .builder()
                .owner(jsonTree.findValue("actor").get("login").textValue())
                .repositories(repoNames)
                .branchDetailDtos(getBranchDetailsFromJson(repoNames, userName))
                .build();
    }

    public List<String> extractNamesFromJson(String url) throws JsonProcessingException {
        List<String> repoNames = new ArrayList<>();

        JsonNode jsonTree = objectMapper.readTree(restTemplate.getForObject(url, String.class));
        Iterator<JsonNode> iterator = jsonTree.elements();

        while (iterator.hasNext()) {
            JsonNode nextElement = iterator.next();
            if (!nextElement.path("fork").asBoolean()) {
                repoNames.add(nextElement.path("name").textValue());
            }
        }
        return repoNames;
    }

    public List<BranchDetailDto> getBranchDetailsFromJson(List<String> repoNames, String userName) throws JsonProcessingException {
        List<BranchDetailDto> branchDetailDtoList = new ArrayList<>();
        for (String repoName : repoNames) {
            String url = "https://api.github.com/repos/" + userName + "/" + repoName + "/branches";
            JsonNode jsonTree = objectMapper.readTree(restTemplate.getForObject(url, String.class));
            Iterator<JsonNode> iterator = jsonTree.elements();

            while (iterator.hasNext()) {
                JsonNode nextElement = iterator.next();
                branchDetailDtoList.add(
                        BranchDetailDto.builder()
                                .repoName(repoName)
                                .branchName(nextElement.get("name").textValue())
                                .commitSha(nextElement.findValue("commit").get("sha").textValue())
                                .build());
            }
        }
        return branchDetailDtoList;
    }
}
