package com.bug_board.bugboard26.backend.entity;

import com.bug_board.bugboard26.backend.entity.interfaces.IRegularUserRole;
import com.bug_board.bugboard26.exception.entity.MaximumLabelsException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@DiscriminatorValue("ROLE_USER")
public class RegularUser extends User implements IRegularUserRole {
    /*Relation RegularUser - Label */
    @OneToMany
    @JoinColumn(name = "utente_creatore")
    private List<Label> personalLabels = new ArrayList<Label>();

    /* Relation RegularUser - Project */
    @ManyToMany(mappedBy = "partecipants")
    private List<Project> partecipatingProjects = new ArrayList<Project>();

    /* Relazione RegularUser - Issue */
    @OneToMany(mappedBy = "creator")
    private List<Issue> personalIssues = new ArrayList<Issue>();

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
