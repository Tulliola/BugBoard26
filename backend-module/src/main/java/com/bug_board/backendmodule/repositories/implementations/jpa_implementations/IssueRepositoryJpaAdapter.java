package com.bug_board.backendmodule.repositories.implementations.jpa_implementations;

import com.bug_board.backendmodule.entity.Issue;
import com.bug_board.backendmodule.repositories.interfaces.IIssueRepository;
import com.bug_board.dto.IssueFiltersDTO;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IssueRepositoryJpaAdapter implements IIssueRepository {
    private final IIssueRepositoryJPA issueRepositoryJPA;

    public IssueRepositoryJpaAdapter(IIssueRepositoryJPA issueRepositoryJPA) {
        this.issueRepositoryJPA = issueRepositoryJPA;
    }

    @Override
    public List<Issue> retrieveAllUsersIssues(String username, IssueFiltersDTO filters) {
        Specification<Issue> userScope = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("creator").get("username"), username);

        Specification<Issue> filterSpecification = this.buildSpecificationFromFilters(filters);

        return issueRepositoryJPA.findAll(userScope.and(filterSpecification));
    }

    @Override
    public List<Issue> retrieveAllProjectsIssues(Integer idProject, IssueFiltersDTO filters) {
        Specification<Issue> projectScope = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("project").get("idProject"), idProject);

        Specification<Issue> filterSpecification = this.buildSpecificationFromFilters(filters);

        return issueRepositoryJPA.findAll(projectScope.and(filterSpecification));
    }

    @Override
    public Issue getIssue(Integer idIssue) {
        return issueRepositoryJPA.findById(idIssue).orElse(null);
    }

    @Override
    public Issue createANewIssueToProject(Issue issueToPublish) {
        return issueRepositoryJPA.save(issueToPublish);
    }

    private Specification<Issue> buildSpecificationFromFilters(IssueFiltersDTO filters) {
        Specification<Issue> specification = (root, query, cb) -> cb.conjunction();

        if(filters.getIssueStates() != null && !filters.getIssueStates().isEmpty())
            specification = specification.and((root, query, criteriaBuilder) ->
                root.get("state").in(filters.getIssueStates())
            );

        if(filters.getIssueTipologies() != null && !filters.getIssueTipologies().isEmpty())
            specification = specification.and((root, query, criteriaBuilder) ->
                    root.get("tipology").in(filters.getIssueTipologies())
            );

        if(filters.getIssuePriorities() != null && !filters.getIssuePriorities().isEmpty())
            specification = specification.and((root, query, criteriaBuilder) ->
                    root.get("priority").in(filters.getIssuePriorities())
            );

        return specification;
    }
}
