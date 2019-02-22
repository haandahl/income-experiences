-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2019-02-22 18:24:36.975
-- Continued updates by Heidi Aandahl as needed

-- tables
-- Table: financial_story
CREATE TABLE financial_story (
    id int NOT NULL AUTO_INCREMENT,
    content varchar(2000) NOT NULL,
    date date NOT NULL,
    visible bool NOT NULL,
    profile_user int NOT NULL,
    editor int NOT NULL,
    CONSTRAINT financial_story_pk PRIMARY KEY (id)
);

-- Table: goals_description
CREATE TABLE goals_description (
    id int NOT NULL AUTO_INCREMENT,
    description varchar(255) NOT NULL UNIQUE,
    CONSTRAINT goals_description_pk PRIMARY KEY (id)
);

-- Table: goals_unmet
CREATE TABLE goals_unmet (
    id int NOT NULL AUTO_INCREMENT,
    savings bool NULL,
    career_ed bool NULL,
    needs_quality bool NULL,
    donations bool NULL,
    recreation bool NULL,
    travel bool NULL,
    services bool NULL,
    other bool NULL,
    CONSTRAINT goals_unmet_pk PRIMARY KEY (id)
);

-- Table: income_skew
CREATE TABLE income_skew (
    id int NOT NULL AUTO_INCREMENT,
    description varchar(255) NOT NULL UNIQUE,
    CONSTRAINT income_skew_pk PRIMARY KEY (id)
);

-- Table: needs_description
CREATE TABLE needs_description (
    id int NOT NULL AUTO_INCREMENT,
    description varchar(255) NOT NULL UNIQUE,
    CONSTRAINT needs_description_pk PRIMARY KEY (id)
);

-- Table: needs_unmet
CREATE TABLE needs_unmet (
    id int NOT NULL AUTO_INCREMENT,
    food bool NULL,
    housing bool NULL,
    utilities bool NULL,
    health_care bool NULL,
    clothing bool NULL,
    transportation bool NULL,
    child_care bool NULL,
    other bool NULL,
    CONSTRAINT needs_unmet_pk PRIMARY KEY (id)
);

-- Table: role
CREATE TABLE role (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(100) NOT NULL UNIQUE,
    CONSTRAINT role_pk PRIMARY KEY (id)
);

-- Table: story_removals
CREATE TABLE story_removals (
    id int NOT NULL AUTO_INCREMENT,
    date date NOT NULL,
    profile_user int NOT NULL,
    editor int NOT NULL,
    CONSTRAINT story_removals_pk PRIMARY KEY (id)
);

-- Table: survey
CREATE TABLE survey (
    id int NOT NULL AUTO_INCREMENT,
    date date NOT NULL,
    family_size int NOT NULL,
    income int NOT NULL,
    user_id int NOT NULL,
    needs_unmet_id int NOT NULL,
    goals_unmet_id int NOT NULL,
    needs_description_id int NOT NULL,
    goals_description_id int NOT NULL,
    income_skew_id int NOT NULL,
    CONSTRAINT survey_pk PRIMARY KEY (id)
);

-- Table: user
CREATE TABLE user (
    id int NOT NULL AUTO_INCREMENT,
    username varchar(50) NOT NULL UNIQUE,
    password varchar(150) NOT NULL,
    role_id int NOT NULL,
    CONSTRAINT user_pk PRIMARY KEY (id)
);

-- foreign keys
-- Reference: financial_story_editor (table: financial_story)
ALTER TABLE financial_story ADD CONSTRAINT financial_story_editor FOREIGN KEY financial_story_editor (editor)
    REFERENCES user (id);

-- Reference: financial_story_user (table: financial_story)
ALTER TABLE financial_story ADD CONSTRAINT financial_story_user FOREIGN KEY financial_story_user (profile_user)
    REFERENCES user (id);

-- Reference: story_removals_editor (table: story_removals)
ALTER TABLE story_removals ADD CONSTRAINT story_removals_editor FOREIGN KEY story_removals_editor (editor)
    REFERENCES user (id);

-- Reference: story_removals_profile_user (table: story_removals)
ALTER TABLE story_removals ADD CONSTRAINT story_removals_profile_user FOREIGN KEY story_removals_profile_user (profile_user)
    REFERENCES user (id);

-- Reference: survey_goals_description (table: survey)
ALTER TABLE survey ADD CONSTRAINT survey_goals_description FOREIGN KEY survey_goals_description (goals_description_id)
    REFERENCES goals_description (id);

-- Reference: survey_goals_unmet (table: survey)
ALTER TABLE survey ADD CONSTRAINT survey_goals_unmet FOREIGN KEY survey_goals_unmet (goals_unmet_id)
    REFERENCES goals_unmet (id);

-- Reference: survey_income_skew (table: survey)
ALTER TABLE survey ADD CONSTRAINT survey_income_skew FOREIGN KEY survey_income_skew (income_skew_id)
    REFERENCES income_skew (id);

-- Reference: survey_needs_description (table: survey)
ALTER TABLE survey ADD CONSTRAINT survey_needs_description FOREIGN KEY survey_needs_description (needs_description_id)
    REFERENCES needs_description (id);

-- Reference: survey_needs_unmet (table: survey)
ALTER TABLE survey ADD CONSTRAINT survey_needs_unmet FOREIGN KEY survey_needs_unmet (needs_unmet_id)
    REFERENCES needs_unmet (id);

-- Reference: survey_user (table: survey)
ALTER TABLE survey ADD CONSTRAINT survey_user FOREIGN KEY survey_user (user_id)
    REFERENCES user (id);

-- Reference: user_role (table: user)
ALTER TABLE user ADD CONSTRAINT user_role FOREIGN KEY user_role (role_id)
    REFERENCES role (id);

-- End of file.

