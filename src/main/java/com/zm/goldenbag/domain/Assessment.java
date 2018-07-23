package com.zm.goldenbag.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Assessment {
    @Id
    @GeneratedValue
    private Long id;

    private Date assessmentDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "assessment", fetch = FetchType.EAGER)
    private Set<AssessmentProjectScore> assessmentProjectScores;

    @OneToMany(mappedBy = "assessment", fetch = FetchType.EAGER)
    private Set<AssessmentInputContent> assessmentInputContents;

    @ManyToOne
    private AssessmentTemplate assessmentTemplate;

    private String indirectManagerAuditComments;

    private String remarks;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(Date assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<AssessmentProjectScore> getAssessmentProjectScores() {
        return assessmentProjectScores;
    }

    public void setAssessmentProjectScores(Set<AssessmentProjectScore> assessmentProjectScores) {
        this.assessmentProjectScores = assessmentProjectScores;
    }
}
