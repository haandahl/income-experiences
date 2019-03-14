-- clear tables and repopulate lookup tables
-- last updated 3/14/19 Heidi Aandahl
-- NOTE: This application currently reads this file one line at a time and stops at a blank line.
-- ------------------------------------------------------------------------------------------------
-- Resources for clearing foreign keys:
-- -- https://dev.mysql.com/doc/refman/5.7/en/create-table-foreign-keys.html#foreign-keys-dropping
-- -- Paula Waite's sample project
-- ------------------------------------------------------------------------------------------------
SET foreign_key_checks = 0;
truncate table financial_story;
truncate table survey;
truncate table goals_description;
truncate table goals_unmet;
truncate table income_skew;
truncate table needs_description;
truncate table needs_unmet;
truncate table user;
truncate table role;
SET foreign_key_checks = 1;
-- ------------------------------------------------------------------------------------------------
-- ----------- 3/14 structure change; role is no longer a lookup table -----------
-- ------------------------------------------------------------------------------------------------
-- INSERT INTO role (id, name) VALUES (1, 'admin');
-- INSERT INTO role (id, name) VALUES (4, 'advanced user');
-- INSERT INTO role (id, name) VALUES (3, 'data user');
-- INSERT INTO role (id, name) VALUES (2, 'new user');
-- INSERT INTO role (id, name) VALUES (5, 'blocked user');
-- ------------------------------------------------------------------------------------------------
INSERT INTO goals_description (id, description) VALUES (5, 'Income allowed for new or expanding financial goals.');
INSERT INTO goals_description (id, description) VALUES (3, 'Many goals were met.');
INSERT INTO goals_description (id, description) VALUES (4, 'Most or all goals were easily met.');
INSERT INTO goals_description (id, description) VALUES (2, 'Unmet goals caused frustration.');
INSERT INTO goals_description (id, description) VALUES (1, 'Unmet goals caused insecurity or high stress.');
-- ------------------------------------------------------------------------------------------------
INSERT INTO needs_description (id, description) VALUES (5, 'All needs were comfortably met.');
INSERT INTO needs_description (id, description) VALUES (4, 'Needs were generally met.');
INSERT INTO needs_description (id, description) VALUES (1, 'Severely unmet needs caused permanent harm.');
INSERT INTO needs_description (id, description) VALUES (3, 'Unmet needs caused discomfort.');
INSERT INTO needs_description (id, description) VALUES (2, 'Unmet needs caused illness or decreased ability at work or school.');
-- ------------------------------------------------------------------------------------------------
INSERT INTO income_skew (id, description) VALUES (1, 'little or no impact');
INSERT INTO income_skew (id, description) VALUES (2, 'some impact');
INSERT INTO income_skew (id, description) VALUES (3, 'strong impact');
-- ------------------------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------------------------
-- Insert data for unit tests built to date (in progress)
-- ------------------------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------------------------
-- user (id, username, password)
-- ------------------------------------------------------------------------------------------------
INSERT into USER values (1, 'admin', 'donthackme');
INSERT into USER values  (2, 'jean', 'password1');
INSERT into USER values  (3, 'chris', 'password2');
INSERT into USER values  (4, 'jen', 'password3');
INSERT into USER values  (5, 'sam', 'password4');
INSERT into USER values  (6, 'kj', 'password5');
INSERT into USER values  (7, 'ryan', 'password6');
INSERT into USER values  (8, 'mary', 'password7');
-- ------------------------------------------------------------------------------------------------
-- role (id, name, username) <--name refers to role descriptor
-- ------------------------------------------------------------------------------------------------
-- TODO set up multiple roles for individuals?  Not sure if needed?
-- ------------------------------------------------------------------------------------------------
INSERT into ROLE values (1, 'admin', 'admin');
INSERT into ROLE values (2, 'data user', 'jean');
INSERT into ROLE values (3, 'new user', 'chris');
INSERT into ROLE values (4, 'advanced user', 'jen');
INSERT into ROLE values (5, 'new user', 'sam');
INSERT into ROLE values (6, 'new user', 'kj');
INSERT into ROLE values (7, 'new user', 'ryan');
INSERT into ROLE values (8, 'advanced user', 'mary');
-- -----------------------------------------------------------------------------------------------
-- financial_story (id, content, date, visible, profile_user, editor, unsuitable)
-- -----------------------------------------------------------------------------------------------
INSERT into financial_story values (1, 'We made it work in our 4-generation home.', '2018-01-01', true, 4, 4, false);
INSERT into financial_story values (2, 'It was a great year. Please check out my pyramid scheme.', '2018-03-04', false, 8, 8, true);
INSERT into financial_story values (3, 'It was a great year.', '2018-03-05', true, 8, 1, false);
INSERT into financial_story values (4, 'We had a job loss and a foreclosure on our home.', '2019-02-03', true, 1, 1, false);
-- ------------------------------------------------------------------------------------------------
-- survey (id, date, family size, income, user_id, needs_description_id, goals_description_id, income_skew_id)
-- ------------------------------------------------------------------------------------------------
INSERT into survey values (1, '2018-01-01', 6, 65000, 4, 3, 2, 3);
INSERT into survey values (2, '2018-03-04', 1, 150000, 8, 5, 5, 2);
INSERT into survey values (3, '2019-01-15', 3, 50000, 1, 3, 1, 2);
INSERT into survey values (4, '2017-12-12', 2, 90000, 2, 5, 3, 1);
-- ------------------------------------------------------------------------------------------------
-- needs_unmet(id, food, housing, utilities, health_care, clothing, transportation, child_care, other, survey_id)
-- ------------------------------------------------------------------------------------------------
INSERT into needs_unmet (1, false, true, false, false, false, true, false, true, 1);
INSERT into needs_unmet (2, false, false, false, false, false, false, false, false, 2);
INSERT into needs_unmet (3, true, false, true, true, true, false, false, false, 3);
INSERT into needs_unmet (4, false, false, false, false, false, false, false, false, 4);
-- ------------------------------------------------------------------------------------------------
-- goals_unmet(id, savings, career_ed, needs_quality, donations, recreation, travel, services, other, survey_id)
-- ------------------------------------------------------------------------------------------------
INSERT into goals_unmet (1, true, true, true, true, true, true, true, true, 1);
INSERT into goals_unmet (2, false, false, false, false, false, false, false, false, 2);
INSERT into goals_unmet (3, true, true, true, true, true, true, true, true, 3);
INSERT into goals_unmet (4, false, true, false, false, false, false, true, false, 4);
