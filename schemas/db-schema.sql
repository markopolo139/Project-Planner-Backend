create table if not exists app_users (
    user_id int primary key not null auto_increment,
    username varchar(64) unique not null,
    password varchar(255) not null,
    email varchar(127) unique not null,
    notification_token varchar(255) null
);

create table if not exists app_users_roles (
    user_id int not null,
    role varchar(64) not null,
    constraint role_to_app_users foreign key(user_id) references app_users(user_id)
        on delete CASCADE
        on update CASCADE
);

create table if not exists projects(
    project_id int not null primary key auto_increment,
    github_link varchar(255) unique not null,
    title varchar(255) unique not null,
    description text,
    language varchar(127) not null,
    deadline datetime null,
    date_of_start datetime not null,
    is_current_project bool not null default false,
    project_status enum('ACTIVE', 'BREAK', 'CANCELED', 'UPCOMING', 'PENDING') not null,
    user_id int not null,
    constraint projects_to_user foreign key(user_id) references app_users(user_id)
        on delete CASCADE
        on update CASCADE
);

create table if not exists projects_technologies (
    project_id int not null,
    technology varchar(127) not null,
    constraint projects_technologies_to_projects foreign key(project_id) references projects(project_id)
        on delete CASCADE
        on update CASCADE
);

create table if not exists projects_features (
    project_id int not null,
    feature varchar(127) not null,
    constraint projects_features_to_projects foreign key(project_id) references projects(project_id)
        on delete CASCADE
        on update CASCADE
);

create table if not exists projects_goals (
    project_id int not null,
    goal varchar(255) not null,
    constraint projects_goals_to_projects foreign key(project_id) references projects(project_id)
        on delete CASCADE
        on update CASCADE
);

create table if not exists project_plans(
    project_plan_id int not null primary key auto_increment,
    title varchar(255) unique not null,
    language varchar(127) not null,
    user_id int not null,
    constraint project_plans_to_user foreign key(user_id) references app_users(user_id)
        on delete CASCADE
        on update CASCADE
);

create table if not exists project_plans_points (
    project_plan_id int not null,
    points varchar(255) not null,
    constraint project_plans_points_to_project_plans foreign key(project_plan_id) references project_plans(project_plan_id)
        on delete CASCADE
        on update CASCADE
);

create table if not exists project_plans_features (
    project_plan_id int not null,
    feature varchar(127) not null,
    constraint project_plans_features_to_project_plans foreign key(project_plan_id) references project_plans(project_plan_id)
        on delete CASCADE
        on update CASCADE
);

create table if not exists project_plans_goals (
    project_plan_id int not null,
    goal varchar(255) not null,
    constraint project_plans_goals_to_project_plans foreign key(project_plan_id) references project_plans(project_plan_id)
       on delete CASCADE
       on update CASCADE
);