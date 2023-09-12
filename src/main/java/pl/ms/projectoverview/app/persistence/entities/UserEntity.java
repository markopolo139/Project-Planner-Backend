package pl.ms.projectoverview.app.persistence.entities;

import jakarta.persistence.*;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "app_users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "username", unique = true, nullable = false, length = 64)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", unique = true, nullable = false, length = 127)
    private String email;

    @Column(name = "notification_token")
    private String notificationToken;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "app_users_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role", nullable = false, length = 64)
    private Set<String> roles;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<ProjectEntity> projects;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<ProjectPlanEntity> projectPlans;

    public UserEntity(
            Integer userId, String username, String password, String email, String notificationToken, Set<String> roles
    ) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.notificationToken = notificationToken;
        this.roles = roles;
        projects = Collections.emptySet();
        projectPlans = Collections.emptySet();
    }

    public UserEntity(
            Integer userId, String username, String password, String email, String notificationToken,
            Set<String> roles, Set<ProjectEntity> projects, Set<ProjectPlanEntity> projectPlans
    ) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.notificationToken = notificationToken;
        this.roles = roles;
        this.projects = projects;
        this.projectPlans = projectPlans;
    }

    protected UserEntity() { }

    public void addProject(ProjectEntity project) {
        projects.add(project);
        project.setUser(this);
    }

    public void addProjectPlan(ProjectPlanEntity projectPlan) {
        projectPlans.add(projectPlan);
        projectPlan.setUser(this);
    }

    public Integer getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public Set<ProjectEntity> getProjects() {
        return projects;
    }

    public Set<ProjectPlanEntity> getProjectPlans() {
        return projectPlans;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNotificationToken() {
        return notificationToken;
    }

    public void setNotificationToken(String notificationToken) {
        this.notificationToken = notificationToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(userId, that.userId)
                && Objects.equals(username, that.username)
                && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, email);
    }
}
