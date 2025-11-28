package com.bug_board.backendmodule.entity;

import com.bug_board.backendmodule.entity.interfaces.IAdminRole;
import com.bug_board.enum_classes.UserRole;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@DiscriminatorValue("ROLE_ADMIN")
public class Admin extends User implements IAdminRole {

    /* Relation Admin - Project */

    @ManyToMany(mappedBy = "admins")
    private List<Project> overviewedProjects = new ArrayList<Project>();


    public Admin(){
        super(UserRole.ROLE_ADMIN);
    }

    protected Admin(UserRole role) {
        super(role);
    }

    @Override
    public Collection<String> getRoleNames() {
        return List.of("ROLE_ADMIN");
    }

    @Override
    public void addToProject(Project project) {
        project.addAdminOverviewingProject(this);
    }

    @Override
    public void addProjectToOverviewedProjectList(Project project) {
        if(overviewedProjects != null){
            if(project == null)
                throw  new NullPointerException("You must define the project to be added to.");

            overviewedProjects.add(project);
        }
    }
}
