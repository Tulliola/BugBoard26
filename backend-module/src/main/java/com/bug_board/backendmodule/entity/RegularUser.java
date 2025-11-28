package com.bug_board.backendmodule.entity;

import com.bug_board.backendmodule.entity.interfaces.IRegularUserRole;
import com.bug_board.backendmodule.exception.entity.MaximumLabelsException;
import com.bug_board.enum_classes.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@DiscriminatorValue("ROLE_USER")
public class RegularUser extends User implements IRegularUserRole {

    /*Relation RegularUser - Label */
    @OneToMany(mappedBy = "creator")
    private List<Label> personalLabels = new ArrayList<Label>();

    /* Relation RegularUser - Project */
    @ManyToMany(mappedBy = "partecipants")
    private List<Project> partecipatingProjects = new ArrayList<Project>();

    /* Relazione RegularUser - Issue */
    @OneToMany(mappedBy = "creator")
    private List<Issue> personalIssues = new ArrayList<Issue>();

    public RegularUser() {
        super(UserRole.ROLE_USER);
    }

    @Override
    public Collection<String> getRoleNames() {
        return List.of("ROLE_USER");
    }

    @Override
    public void addToProject(Project project) {
        project.addUserWorkingOnProject(this);
    }

    @Override
    public void addIssueToIssueList(Issue newIssue) {
        if(personalIssues != null){
            if(newIssue == null)
                throw  new NullPointerException("You must define the issue to be added to.");

            personalIssues.add(newIssue);
        }
    }

    @Override
    public void addLabelToPersonalLabelList(Label newLabel) throws MaximumLabelsException {
        if(personalLabels != null){
            if(newLabel == null)
                throw  new NullPointerException("You must define the label to be added to.");

            personalLabels.add(newLabel);
        }
    }

    @Override
    public void removeLabelFromPersonalLabelList(Label labelToRemove) {
        if(personalLabels != null && labelToRemove != null)
            personalLabels.remove(labelToRemove);
    }

    @Override
    public void addProjectToPartecipatingProjectList(Project project) {
        if(partecipatingProjects != null) {
            if(project == null)
                throw  new NullPointerException("You must define the project to be added to.");

            partecipatingProjects.add(project);
            project.addUserWorkingOnProject(this);
        }
    }
}
