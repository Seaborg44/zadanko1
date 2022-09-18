package ww.Github.service.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class GithubDetailDto {
    private String owner;
    private List<String> repositories;
    private List<BranchDetailDto> branchDetailDtos;
}
