CREATE SCHEMA `notebook_hib_pr` ;

CREATE TABLE `notebook_hib_pr`.`notes` (
                                           `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
                                           `my_text` VARCHAR(255) NOT NULL,
                                           `this_date` DATETIME NOT NULL,
                                           PRIMARY KEY (`id`));

CREATE TABLE `notebook_hib_pr`.`topics` (
                                            `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
                                            `name_topic` VARCHAR(32) NOT NULL,
                                            PRIMARY KEY (`id`));

CREATE TABLE `notebook_hib_pr`.`note_topic` (
                                                `note_id` BIGINT(20) NOT NULL,
                                                `topic_id` BIGINT(20) NOT NULL,
                                                PRIMARY KEY (`note_id`, `topic_id`));

ALTER TABLE `notebook_hib_pr`.`note_topic`
    ADD INDEX `fk_topic_idx` (`topic_id` ASC);
;
ALTER TABLE `notebook_hib_pr`.`note_topic`
    ADD CONSTRAINT `fk_note`
        FOREIGN KEY (`note_id`)
            REFERENCES `notebook_hib_pr`.`notes` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    ADD CONSTRAINT `fk_topic`
        FOREIGN KEY (`topic_id`)
            REFERENCES `notebook_hib_pr`.`topics` (`id`)
            ON DELETE RESTRICT
            ON UPDATE RESTRICT;

INSERT INTO `notebook_hib_pr`.`topics` (`name_topic`) VALUES ('Topic_1');
INSERT INTO `notebook_hib_pr`.`topics` (`name_topic`) VALUES ('Topic_2');
INSERT INTO `notebook_hib_pr`.`topics` (`name_topic`) VALUES ('Topic_3');
INSERT INTO `notebook_hib_pr`.`topics` (`name_topic`) VALUES ('Topic_4');
INSERT INTO `notebook_hib_pr`.`topics` (`name_topic`) VALUES ('Topic_5');


INSERT INTO `notebook_hib_pr`.`notes` (`my_text`, `this_date`) VALUES ('Text_1', '2020-10-01');
INSERT INTO `notebook_hib_pr`.`notes` (`my_text`, `this_date`) VALUES ('Text_2', '2020-11-01');
INSERT INTO `notebook_hib_pr`.`notes` (`my_text`, `this_date`) VALUES ('Text_3', '2020-12-01');
INSERT INTO `notebook_hib_pr`.`notes` (`my_text`, `this_date`) VALUES ('Text_4', '2020-12-02');
INSERT INTO `notebook_hib_pr`.`notes` (`my_text`, `this_date`) VALUES ('Text_5', '2020-12-25');
INSERT INTO `notebook_hib_pr`.`notes` (`my_text`, `this_date`) VALUES ('Text_6', '2021-01-13');
INSERT INTO `notebook_hib_pr`.`notes` (`my_text`, `this_date`) VALUES ('Text_7', '2021-05-06');
INSERT INTO `notebook_hib_pr`.`notes` (`my_text`, `this_date`) VALUES ('Text_8', '2021-08-08');

INSERT INTO `notebook_hib_pr`.`note_topic` (`note_id`, `topic_id`) VALUES ('1', '1');
INSERT INTO `notebook_hib_pr`.`note_topic` (`note_id`, `topic_id`) VALUES ('1', '3');
INSERT INTO `notebook_hib_pr`.`note_topic` (`note_id`, `topic_id`) VALUES ('1', '5');
INSERT INTO `notebook_hib_pr`.`note_topic` (`note_id`, `topic_id`) VALUES ('2', '2');
INSERT INTO `notebook_hib_pr`.`note_topic` (`note_id`, `topic_id`) VALUES ('2', '3');
INSERT INTO `notebook_hib_pr`.`note_topic` (`note_id`, `topic_id`) VALUES ('2', '4');
INSERT INTO `notebook_hib_pr`.`note_topic` (`note_id`, `topic_id`) VALUES ('3', '5');
INSERT INTO `notebook_hib_pr`.`note_topic` (`note_id`, `topic_id`) VALUES ('4', '1');
INSERT INTO `notebook_hib_pr`.`note_topic` (`note_id`, `topic_id`) VALUES ('4', '2');
INSERT INTO `notebook_hib_pr`.`note_topic` (`note_id`, `topic_id`) VALUES ('4', '5');
INSERT INTO `notebook_hib_pr`.`note_topic` (`note_id`, `topic_id`) VALUES ('5', '4');
INSERT INTO `notebook_hib_pr`.`note_topic` (`note_id`, `topic_id`) VALUES ('6', '3');
INSERT INTO `notebook_hib_pr`.`note_topic` (`note_id`, `topic_id`) VALUES ('6', '4');
INSERT INTO `notebook_hib_pr`.`note_topic` (`note_id`, `topic_id`) VALUES ('6', '5');
INSERT INTO `notebook_hib_pr`.`note_topic` (`note_id`, `topic_id`) VALUES ('7', '2');
INSERT INTO `notebook_hib_pr`.`note_topic` (`note_id`, `topic_id`) VALUES ('7', '5');
INSERT INTO `notebook_hib_pr`.`note_topic` (`note_id`, `topic_id`) VALUES ('8', '1');
INSERT INTO `notebook_hib_pr`.`note_topic` (`note_id`, `topic_id`) VALUES ('8', '2');
INSERT INTO `notebook_hib_pr`.`note_topic` (`note_id`, `topic_id`) VALUES ('8', '4');
