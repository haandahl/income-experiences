create schema test_income_experiences collate latin1_swedish_ci;

create table goals_description
(
	id int auto_increment
		primary key,
	description varchar(255) not null,
	constraint description
		unique (description)
);

create table income_skew
(
	id int auto_increment
		primary key,
	description varchar(255) not null,
	constraint description
		unique (description)
);

create table needs_description
(
	id int auto_increment
		primary key,
	description varchar(255) not null,
	constraint description
		unique (description)
);

create table user
(
	id int auto_increment
		primary key,
	username varchar(50) not null,
	password varchar(150) not null,
	constraint username
		unique (username)
);

create table financial_story
(
	id int auto_increment
		primary key,
	content varchar(2000) not null,
	date date not null,
	visible tinyint(1) not null,
	profile_user int not null,
	editor int null,
	unsuitable tinyint(1) null,
	constraint financial_story_editor
		foreign key (editor) references user (id)
			on delete set null,
	constraint financial_story_user
		foreign key (profile_user) references user (id)
);

create table role
(
	id int auto_increment
		primary key,
	name varchar(100) not null,
	username varchar(50) not null,
	constraint role_ibfk_1
		foreign key (username) references user (username)
);

create index username_index
	on role (username);

create table survey
(
	id int auto_increment
		primary key,
	date date not null,
	family_size int not null,
	income int not null,
	user_id int not null,
	needs_description_id int not null,
	goals_description_id int not null,
	income_skew_id int not null,
	constraint survey_goals_description
		foreign key (goals_description_id) references goals_description (id),
	constraint survey_income_skew
		foreign key (income_skew_id) references income_skew (id),
	constraint survey_needs_description
		foreign key (needs_description_id) references needs_description (id),
	constraint survey_user
		foreign key (user_id) references user (id)
);

create table goals_unmet
(
	id int auto_increment
		primary key,
	savings tinyint(1) null,
	career_ed tinyint(1) null,
	needs_quality tinyint(1) null,
	donations tinyint(1) null,
	recreation tinyint(1) null,
	travel tinyint(1) null,
	services tinyint(1) null,
	other tinyint(1) null,
	survey_id int null,
	constraint goals_unmet_survey
		foreign key (survey_id) references survey (id)
);

create table needs_unmet
(
	id int auto_increment
		primary key,
	food tinyint(1) null,
	housing tinyint(1) null,
	utilities tinyint(1) null,
	health_care tinyint(1) null,
	clothing tinyint(1) null,
	transportation tinyint(1) null,
	child_care tinyint(1) null,
	other tinyint(1) null,
	survey_id int null,
	constraint needs_unmet_survey
		foreign key (survey_id) references survey (id)
);

