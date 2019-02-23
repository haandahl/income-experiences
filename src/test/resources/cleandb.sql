-- clear MVP tables and repopulate lookup tables
-- updated 2/23/19 Heidi Aandahl

-- Resources for clearing foreign keys:
-- -- https://dev.mysql.com/doc/refman/5.7/en/create-table-foreign-keys.html#foreign-keys-dropping
-- -- Paula Waite's sample project

SET foreign_key_checks = 0;

truncate table financial_story;

truncate table story_removals;

truncate table survey;

truncate table goals_description;

truncate table goals_unmet;

truncate table income_skew;

truncate table needs_description;

truncate table needs_unmet;

truncate table user;

truncate table role;

SET foreign_key_checks = 1;

/*

INSERT INTO income_experiences.role (id, name) VALUES (1, 'admin');
INSERT INTO income_experiences.role (id, name) VALUES (4, 'advanced user');
INSERT INTO income_experiences.role (id, name) VALUES (3, 'data user');
INSERT INTO income_experiences.role (id, name) VALUES (2, 'new user');

INSERT INTO income_experiences.goals_description (id, description) VALUES (5, 'Income allowed for new or expanding financial goals.');
INSERT INTO income_experiences.goals_description (id, description) VALUES (3, 'Many goals were met.');
INSERT INTO income_experiences.goals_description (id, description) VALUES (4, 'Most or all goals were easily met.');
INSERT INTO income_experiences.goals_description (id, description) VALUES (2, 'Unmet goals caused frustration.');
INSERT INTO income_experiences.goals_description (id, description) VALUES (1, 'Unmet goals caused insecurity or high stress.');

INSERT INTO income_experiences.needs_description (id, description) VALUES (5, 'All needs were comfortably met.');
INSERT INTO income_experiences.needs_description (id, description) VALUES (4, 'Needs were generally met.');
INSERT INTO income_experiences.needs_description (id, description) VALUES (1, 'Severely unmet needs caused permanent harm.');
INSERT INTO income_experiences.needs_description (id, description) VALUES (3, 'Unmet needs caused discomfort.');
INSERT INTO income_experiences.needs_description (id, description) VALUES (2, 'Unmet needs caused illness or decreased ability at work or school.');

INSERT INTO income_experiences.income_skew (id, description) VALUES (1, 'little or no impact');
INSERT INTO income_experiences.income_skew (id, description) VALUES (2, 'some impact');
INSERT INTO income_experiences.income_skew (id, description) VALUES (3, 'strong impact');

*/


INSERT INTO role (id, name) VALUES (1, 'admin');
INSERT INTO role (id, name) VALUES (4, 'advanced user');
INSERT INTO role (id, name) VALUES (3, 'data user');
INSERT INTO role (id, name) VALUES (2, 'new user');

INSERT INTO goals_description (id, description) VALUES (5, 'Income allowed for new or expanding financial goals.');
INSERT INTO goals_description (id, description) VALUES (3, 'Many goals were met.');
INSERT INTO goals_description (id, description) VALUES (4, 'Most or all goals were easily met.');
INSERT INTO goals_description (id, description) VALUES (2, 'Unmet goals caused frustration.');
INSERT INTO goals_description (id, description) VALUES (1, 'Unmet goals caused insecurity or high stress.');

INSERT INTO needs_description (id, description) VALUES (5, 'All needs were comfortably met.');
INSERT INTO needs_description (id, description) VALUES (4, 'Needs were generally met.');
INSERT INTO needs_description (id, description) VALUES (1, 'Severely unmet needs caused permanent harm.');
INSERT INTO needs_description (id, description) VALUES (3, 'Unmet needs caused discomfort.');
INSERT INTO needs_description (id, description) VALUES (2, 'Unmet needs caused illness or decreased ability at work or school.');

INSERT INTO income_skew (id, description) VALUES (1, 'little or no impact');
INSERT INTO income_skew (id, description) VALUES (2, 'some impact');
INSERT INTO income_skew (id, description) VALUES (3, 'strong impact');



-- Insert data where for unit tests built to date (in progress)

INSERT into USER values
  (1, 'admin', 'donthackme', 1),
  (2, 'jean', 'password1', 2),
  (3, 'chris', 'password2', 3),
  (4, 'jen', 'password3', 4),
  (5, 'sam', 'password4', 3),
  (6, 'kj', 'password5', 3),
  (7, 'ryan', 'password6', 4),
  (8, 'mary', 'password7', 4);
