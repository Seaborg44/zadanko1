package ww.Github.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BranchDetailDto {
    private String repoName;
    private String branchName;
    private String commitSha;
}
