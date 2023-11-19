create table category
(
    id         int auto_increment
        primary key,
    created_at datetime(6)  null,
    created_by varchar(255) null,
    updated_at datetime(6)  null,
    updated_by varchar(255) null,
    name       varchar(255) null
);

create table employee
(
    id           int auto_increment
        primary key,
    created_at   datetime(6)  null,
    created_by   varchar(255) null,
    updated_at   datetime(6)  null,
    updated_by   varchar(255) null,
    address      varchar(255) null,
    email        varchar(50)  not null,
    job_position varchar(255) null,
    name         varchar(50)  not null,
    phone        varchar(15)  null,
    constraint UK_buf2qp04xpwfp5qq355706h4a
        unique (phone),
    constraint UK_fopic1oh5oln2khj8eat6ino0
        unique (email)
);

create table privilege
(
    id         int auto_increment
        primary key,
    created_at datetime(6)  null,
    created_by varchar(255) null,
    updated_at datetime(6)  null,
    updated_by varchar(255) null,
    name       varchar(255) null
);

create table role
(
    id         int auto_increment
        primary key,
    created_at datetime(6)  null,
    created_by varchar(255) null,
    updated_at datetime(6)  null,
    updated_by varchar(255) null,
    name       varchar(255) null
);

create table role_privilege
(
    role_id      int not null,
    privilege_id int not null,
    primary key (role_id, privilege_id),
    constraint FKdkwbrwb5r8h74m1v7dqmhp99c
        foreign key (privilege_id) references privilege (id),
    constraint FKsykrtrdngu5iexmbti7lu9xa
        foreign key (role_id) references role (id)
);

create table status
(
    id         int auto_increment
        primary key,
    created_at datetime(6)  null,
    created_by varchar(255) null,
    updated_at datetime(6)  null,
    updated_by varchar(255) null,
    name       varchar(255) null
);

create table survey
(
    id                   int auto_increment
        primary key,
    created_at           datetime(6)  null,
    created_by           varchar(255) null,
    updated_at           datetime(6)  null,
    updated_by           varchar(255) null,
    learning_quality     int          null,
    resource             int          null,
    trainer_compotence   int          null,
    training_register_id int          null
);

create table training
(
    id           int auto_increment
        primary key,
    created_at   datetime(6)  null,
    created_by   varchar(255) null,
    updated_at   datetime(6)  null,
    updated_by   varchar(255) null,
    address      varchar(255) null,
    description  text         null,
    duration     int          null,
    end_date     datetime(6)  null,
    is_online    bit          null,
    platform_url varchar(255) null,
    quota        int          null,
    start_date   datetime(6)  null,
    title        varchar(255) null,
    trainer      int          null,
    avail_seat   int          null,
    category     int          null,
    constraint FK4x3vrvhenmym3b4c2uflj8q7h
        foreign key (category) references category (id),
    constraint FKnv4omueear8ajg0i7vmx66gen
        foreign key (trainer) references employee (id)
);

create table training_register
(
    id             int auto_increment
        primary key,
    created_at     datetime(6)  null,
    created_by     varchar(255) null,
    updated_at     datetime(6)  null,
    updated_by     varchar(255) null,
    attachment     longblob     null,
    current_status int          null,
    trainee        int          null,
    training       int          null,
    survey_id      int          null,
    constraint FK3741rv81lhlngf6skhtq1bd7w
        foreign key (survey_id) references survey (id),
    constraint FK4u7u0tudde90uo4qh0xmij388
        foreign key (training) references training (id),
    constraint FK6bbqacratbvac9c73hymxnh8h
        foreign key (trainee) references employee (id),
    constraint FKbfyn9697ykb9gf8qpisk94vxr
        foreign key (current_status) references status (id)
);

create table history
(
    id                int auto_increment
        primary key,
    created_at        datetime(6)  null,
    created_by        varchar(255) null,
    updated_at        datetime(6)  null,
    updated_by        varchar(255) null,
    notes             varchar(255) null,
    status            int          null,
    training_register int          null,
    constraint FKlfp252jx44v0b4hrpe4gpvk8e
        foreign key (training_register) references training_register (id),
    constraint FKs90eitu5esj9wdx64qdhewc6l
        foreign key (status) references status (id)
);

alter table survey
    add constraint FKjed6u75y50myqdvbf8f5x4jtl
        foreign key (training_register_id) references training_register (id);

create table user
(
    employee   int          not null
        primary key,
    created_at datetime(6)  null,
    created_by varchar(255) null,
    updated_at datetime(6)  null,
    updated_by varchar(255) null,
    is_enable  bit          null,
    password   varchar(255) null,
    username   varchar(255) null,
    constraint UK_sb8bbouer5wak8vyiiy4pf2bx
        unique (username),
    constraint FK1b1eatdquaccg8odcmwdbj43e
        foreign key (employee) references employee (id)
);

create table user_role
(
    user_id int not null,
    role_id int not null,
    primary key (user_id, role_id),
    constraint FK859n2jvi8ivhui0rl0esws6o
        foreign key (user_id) references user (employee),
    constraint FKa68196081fvovjhkek5m97n3y
        foreign key (role_id) references role (id)
);


INSERT INTO role (id, created_at, created_by, updated_at, updated_by, name) VALUES (1, '2023-11-04 14:09:47.000000', null, '2023-11-04 14:09:51.000000', null, 'ADMIN');
INSERT INTO role (id, created_at, created_by, updated_at, updated_by, name) VALUES (2, null, null, null, null, 'TRAINER');
INSERT INTO role (id, created_at, created_by, updated_at, updated_by, name) VALUES (3, '2023-11-04 14:10:11.000000', null, '2023-11-04 14:10:08.000000', null, 'TRAINEE');

INSERT INTO privilege (id, created_at, created_by, updated_at, updated_by, name) VALUES (1, '2023-11-04 14:34:07.000000', null, '2023-11-04 14:34:09.000000', null, 'CREATE_ADMIN');
INSERT INTO privilege (id, created_at, created_by, updated_at, updated_by, name) VALUES (2, '2023-11-04 14:34:12.000000', null, '2023-11-04 14:34:14.000000', null, 'READ_ADMIN');
INSERT INTO privilege (id, created_at, created_by, updated_at, updated_by, name) VALUES (3, '2023-11-04 14:34:16.000000', null, '2023-11-04 14:34:18.000000', null, 'UPDATE_ADMIN');
INSERT INTO privilege (id, created_at, created_by, updated_at, updated_by, name) VALUES (4, '2023-11-04 14:34:39.000000', null, '2023-11-04 14:34:23.000000', null, 'DELETE_ADMIN');
INSERT INTO privilege (id, created_at, created_by, updated_at, updated_by, name) VALUES (5, '2023-11-04 14:34:41.000000', null, '2023-11-04 14:34:25.000000', null, 'CREATE_TRAINER');
INSERT INTO privilege (id, created_at, created_by, updated_at, updated_by, name) VALUES (6, '2023-11-04 14:34:42.000000', null, '2023-11-04 14:34:26.000000', null, 'READ_TRAINER');
INSERT INTO privilege (id, created_at, created_by, updated_at, updated_by, name) VALUES (7, '2023-11-04 14:34:43.000000', null, '2023-11-04 14:34:28.000000', null, 'UPDATE_TRAINER');
INSERT INTO privilege (id, created_at, created_by, updated_at, updated_by, name) VALUES (8, '2023-11-04 14:34:45.000000', null, '2023-11-04 14:34:30.000000', null, 'DELETE_TRAINER');
INSERT INTO privilege (id, created_at, created_by, updated_at, updated_by, name) VALUES (9, '2023-11-04 14:34:46.000000', null, '2023-11-04 14:34:31.000000', null, 'CREATE_TRAINEE');
INSERT INTO privilege (id, created_at, created_by, updated_at, updated_by, name) VALUES (10, '2023-11-04 14:34:47.000000', null, '2023-11-04 14:34:32.000000', null, 'READ_TRAINEE');
INSERT INTO privilege (id, created_at, created_by, updated_at, updated_by, name) VALUES (11, '2023-11-04 14:34:48.000000', null, '2023-11-04 14:34:34.000000', null, 'UPDATE_TRAINEE');
INSERT INTO privilege (id, created_at, created_by, updated_at, updated_by, name) VALUES (12, '2023-11-04 14:34:50.000000', null, '2023-11-04 14:34:35.000000', null, 'DELETE_TRAINEE');


INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 1);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 2);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 3);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 4);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 5);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 6);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 7);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 8);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (3, 9);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (3, 10);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (3, 11);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (3, 12);


INSERT INTO status (id, created_at, created_by, updated_at, updated_by, name) VALUES (1, '2023-11-06 09:26:52.000000', null, '2023-11-06 09:26:54.000000', null, 'SUCCESS');
INSERT INTO status (id, created_at, created_by, updated_at, updated_by, name) VALUES (2, '2023-11-06 09:26:52.000000', null, null, null, 'PENDING');
INSERT INTO status (id, created_at, created_by, updated_at, updated_by, name) VALUES (3, '2023-11-06 09:26:52.000000', null, null, null, 'REJECTED');
INSERT INTO status (id, created_at, created_by, updated_at, updated_by, name) VALUES (4, '2023-11-06 09:26:52.000000', null, null, null, 'CANCELLED');
INSERT INTO status (id, created_at, created_by, updated_at, updated_by, name) VALUES (5, '2023-11-07 10:22:33.000000', null, null, null, 'REQUEST_CANCEL');

INSERT INTO category (id, created_at, created_by, updated_at, updated_by, name) VALUES (1, '2023-11-16 11:18:59.000000', null, '2023-11-16 11:19:05.000000', null, 'DONE');
INSERT INTO category (id, created_at, created_by, updated_at, updated_by, name) VALUES (2, '2023-11-16 11:18:59.000000', null, '2023-11-16 11:18:59.000000', null, 'ONGOING');
INSERT INTO category (id, created_at, created_by, updated_at, updated_by, name) VALUES (3, '2023-11-16 11:18:59.000000', null, '2023-11-16 11:18:59.000000', null, 'UPCOMING');
