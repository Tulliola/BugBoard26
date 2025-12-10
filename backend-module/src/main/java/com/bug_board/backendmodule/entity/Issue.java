package com.bug_board.backendmodule.entity;



import com.bug_board.backendmodule.exception.entity.MaximumLabelsException;
import com.bug_board.enum_classes.IssuePriority;
import com.bug_board.enum_classes.IssueState;
import com.bug_board.enum_classes.IssueTipology;
import com.bug_board.enum_classes.converters.IssuePriorityConverter;
import com.bug_board.enum_classes.converters.IssueStateConverter;
import com.bug_board.enum_classes.converters.IssueTipologyConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "issue")
public class Issue {
    /* Issue specific attributes */
    private static int maxAttachableLabels = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idissue", nullable = false)
    private Integer idIssue;

    @Column(name = "titolo", nullable = false, length = 40)
    private String title;

    @Column(name = "descrizione", nullable = false, length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "stato",
            columnDefinition = "statoissueenum NOT NULL DEFAULT 'To-do'")
    @Convert(converter = IssueStateConverter.class)
    private IssueState state;

    @Column(name = "tipologia", columnDefinition = "issueenum not null")
    @Convert(converter = IssueTipologyConverter.class)
    private IssueTipology tipology;

    @Column(name = "priorita", columnDefinition = "issuepriorityenum not null DEFAULT 'No priority'")
    @Convert(converter = IssuePriorityConverter.class)
    private IssuePriority priority;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "immagine",
            joinColumns = @JoinColumn(name = "idissue")
    )
    @Column(name = "file_immagine", columnDefinition = "bytea")
    private List<byte[]> images = new ArrayList<byte[]>();

    @Column(name = "data_creazione",
            nullable = false,
            columnDefinition = "DATE DEFAULT CURRENT_DATE"
    )
    private Date creationDate = new Date(System.currentTimeMillis());

    @Column(name = "data_risoluzione")
    private Date resolutionDate;

    /* Relation Issue - Label */
    @ManyToMany
    @JoinTable(
            name= "etichetta_associata",
            joinColumns = @JoinColumn(name = "idissue"),
            inverseJoinColumns = @JoinColumn(name = "idlabel")
    )
    private List<Label> attachedLabels = new ArrayList<Label>();

    /* Relation Issue - RegularUser */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "utente_creatore")
    private RegularUser creator;

    /* Relation Issue - Project */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idprogetto")
    private Project project;

    public Issue(){

    }

    public Issue(RegularUser creator) {
        if(creator == null)
            throw new NullPointerException("You must define the creator of the issue.");

        this.creator = creator;
        creator.addIssueToIssueList(this);
    }

    public void addLabelAttachedToIssue(Label newLabel) {
        if(this.attachedLabels != null) {
            if (this.maxAttachableLabels == this.attachedLabels.size())
                throw new MaximumLabelsException("You have reached the limit of attachable labels.");

            if (newLabel == null)
                throw new NullPointerException("You must define the label of the issue.");

            this.attachedLabels.add(newLabel);
        }
    }

}