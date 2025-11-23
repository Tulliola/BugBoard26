package com.bug_board.bugboard26.backend.entity;

import com.bug_board.bugboard26.backend.entity.interfaces.ISuperAdminRole;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@DiscriminatorValue("ROLE_SUPERADMIN")
public class SuperAdmin extends Admin implements ISuperAdminRole {

    /* Relation SuperAdmin - Project */
    @OneToMany(mappedBy = "creator")
    private List<Project> createdProjects = new ArrayList<Project>();

    @Override
    public void addProjectToCreatedProjectList(Project project) {
        if(createdProjects != null){
            if(project == null)
                throw  new NullPointerException("You must define the created project.");

            createdProjects.add(project);
        }
    }
}
