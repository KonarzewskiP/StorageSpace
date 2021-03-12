INSERT INTO users (id, first_name, last_name, email, role, gender)
VALUES (1, 'Rick', 'Sanchez', 'rick@sanchez.com', 'ADMIN','MALE'),
       (2, 'Morty', 'Smith', 'morty@smith.com', 'CUSTOMER','MALE'),
       (3, 'Summer', 'Smith', 'summer@smith.com', 'CUSTOMER','FEMALE');

INSERT INTO warehouses (id, name, city, street, post_code)
VALUES (1, 'Byfleet', 'Surrey', '113-115 Oyster Lane', 'KT14 7JZ'),
       (2, 'Bromley ', 'Kent', '12 Farwig Lane', 'BR1 3RB'),
       (3, 'Bow ', 'London', '400 Wick Lane', 'E3 2JG'),
       (4, 'Beckenham ', 'Kent', '1 Croydon Road', 'BR3 4AA'),
       (5, 'Battersea ', 'London', '55 Lombard Road', 'SW11 3RX'),
       (6, 'Barking ', 'London', 'Hertford Road', 'IG11 8BL'),
       (7, 'Balham ', 'London', '7 Weir Road', 'SW12 0LT'),
       (8, 'Croydon ', 'London', '202 - 216 Thornton Road', 'CR0 3EU'),
       (9, 'Chiswick ', 'Middlesex', '961 Great West Road', 'TW8 9FX'),
       (10, 'Camberwell', 'London', '49-65 Southampton Way', 'SE5 7SW'),
       (11, 'Enfield ', 'London', '1 Progress Way', 'EN1 1FT'),
       (12, 'Eltham ', 'London', '400 Westhorne Avenue', 'SE9 5LT'),
       (13, 'Edmonton ', 'London', '8 Advent Way', 'N18 3AF'),
       (14, 'Ealing ', 'London', '399 Uxbridge Road', 'UB1 3EW'),
       (15, 'Dagenham ', 'Barking', '25 Alfreds Way', 'IG11 0AG'),
       (16, 'Ilford ', 'Essex', '374 Eastern Avenue', 'IG2 6NW'),
       (17, 'Hounslow ', 'Middlesex', '179 - 191 Staines Road', 'TW3 3LL'),
       (18, 'Hanger ', 'London', 'Quill Street', 'W5 1DN'),
       (19, 'Gypsy Corner', 'London', '12 Jenner Avenue', 'W3 6EQ'),
       (20, 'Fulham ', 'London', '71 Townmead Road', 'SW6 2ST'),
       (21, 'Finchley North ', 'London', '447 High Road', 'N12 0AF'),
       (22, 'Finchley East  ', 'London', '401 High Road', 'N2 8HS'),
       (23, 'Sheen  ', 'Surrey', '197 Lower Richmond Road', 'TW9 4LN'),
       (24, 'Romford  ', 'Essex', 'Ashton Road', 'RM3 8NF'),
       (25, 'Richmond  ', 'Surrey', 'Lower Mortlake Road', 'TW9 2JX'),
       (26, 'Orpington  ', 'Orpington', 'Cray Avenue', 'BR5 3PX'),
       (27, 'North Kensington  ', 'London', '149 Scrubs Lane', 'NW10 6RH'),
       (28, 'Nine Elms ', 'London', '120 - 170 Stewarts Road', 'SW8 4UB'),
       (29, 'New Malden ', 'New Malden', 'Beverley Way', 'KT3 4PH'),
       (30, 'New Cross ', 'London', '155 Lewisham Way', 'SE14 6QP'),
       (31, 'Merton  ', 'London', '61 Morden Road', 'SW19 3BE'),
       (32, 'Kingston  ', 'Surrey', '163 London Road', 'KT2 6NU'),
       (33, 'Kennington  ', 'London', '289 Kennington Lane', 'SE11 5QY'),
       (34, 'West Norwood  ', 'London', '107 Knights Hill', 'SE27 0SP'),
       (35, 'West Molesey ', 'West Molesey', '36 Central Avenue', 'KT8 2QZ'),
       (36, 'Watford  ', 'Hertfordshire', '1 Ascot Road', 'WD18 8AL'),
       (37, 'Wapping ', 'London', '100 The Highway ', 'E1W 2BX'),
       (38, 'Wandsworth  ', 'London', '100 Garratt Lane', 'SW18 4DJ'),
       (39, 'Twickenham  ', 'Middlesex', '1A Rugby Road ', 'TW1 1DG'),
       (40, 'Tolworth  ', 'Surrey', '225 Hook Rise South', 'KT6 7LD'),
       (41, 'Sutton  ', 'Surrey', '12 Kimpton Park Way', 'SM3 9QS'),
       (42, 'Staples Corner  ', 'London', '1000 North Circular Road', 'NW2 7JP');

INSERT INTO storage_rooms (id, size, reserved, warehouse_id)
VALUES  (1,'TELEPHONE_BOX',FALSE,1),
        (2,'LARGE_WALK_IN_WARDROBE',FALSE,1),
        (3,'GARDEN_SHED',FALSE,1),
        (4,'LARGE_GARDEN_SHED',FALSE,1),
        (5,'LUTON_VAN',FALSE,1),
        (6,'LARGE_SINGLE_GARAGE',FALSE,1),
        (7,'ONE_AND_HALF_GARAGES',FALSE,1),
        (8,'STANDARD_DOUBLE_GARAGE',FALSE,1),
        (9,'LARGE_DOUBLE_GARAGE',FALSE,1),
        (10,'THREE_SINGLE_GARAGES',FALSE,1),
        (11,'TWO_DOUBLE_GARAGES',FALSE,1),
        (12,'TWO_SHIPPING_CONTAINERS',FALSE,1),

        (13,'TELEPHONE_BOX',FALSE,2),
        (14,'LARGE_WALK_IN_WARDROBE',FALSE,2),
        (15,'GARDEN_SHED',FALSE,2),
        (16,'LARGE_GARDEN_SHED',FALSE,2),
        (17,'LUTON_VAN',FALSE,2),
        (18,'LARGE_SINGLE_GARAGE',FALSE,2),
        (19,'ONE_AND_HALF_GARAGES',FALSE,2),
        (20,'STANDARD_DOUBLE_GARAGE',FALSE,2),
        (21,'LARGE_DOUBLE_GARAGE',FALSE,2),
        (22,'THREE_SINGLE_GARAGES',FALSE,2),
        (23,'TWO_DOUBLE_GARAGES',FALSE,2),
        (24,'TWO_SHIPPING_CONTAINERS',FALSE,2),

        (25,'LARGE_WALK_IN_WARDROBE',FALSE,3),
        (26,'GARDEN_SHED',FALSE,3),
        (27,'LARGE_GARDEN_SHED',FALSE,3),
        (28,'LUTON_VAN',FALSE,3),
        (29,'LARGE_SINGLE_GARAGE',FALSE,3),
        (30,'ONE_AND_HALF_GARAGES',FALSE,3),
        (31,'STANDARD_DOUBLE_GARAGE',FALSE,3),
        (32,'LARGE_DOUBLE_GARAGE',FALSE,3),
        (33,'THREE_SINGLE_GARAGES',FALSE,3),
        (34,'TWO_DOUBLE_GARAGES',FALSE,3),
        (35,'TWO_SHIPPING_CONTAINERS',FALSE,3),

        (36,'TELEPHONE_BOX',FALSE,4),
        (37,'LARGE_WALK_IN_WARDROBE',FALSE,4),
        (38,'GARDEN_SHED',FALSE,4),
        (39,'LARGE_GARDEN_SHED',FALSE,4),
        (40,'LUTON_VAN',FALSE,4),
        (41,'LARGE_SINGLE_GARAGE',FALSE,4),
        (42,'ONE_AND_HALF_GARAGES',FALSE,4),
        (43,'STANDARD_DOUBLE_GARAGE',FALSE,4),
        (44,'LARGE_DOUBLE_GARAGE',FALSE,4),
        (45,'THREE_SINGLE_GARAGES',FALSE,4),
        (46,'TWO_DOUBLE_GARAGES',FALSE,4),
        (47,'TWO_SHIPPING_CONTAINERS',FALSE,4),

        (48,'TELEPHONE_BOX',FALSE,5),
        (49,'LARGE_WALK_IN_WARDROBE',FALSE,5),
        (50,'GARDEN_SHED',FALSE,5),
        (51,'LARGE_GARDEN_SHED',FALSE,5),
        (52,'LUTON_VAN',FALSE,5),
        (53,'LARGE_SINGLE_GARAGE',FALSE,5),
        (54,'ONE_AND_HALF_GARAGES',FALSE,5),
        (55,'STANDARD_DOUBLE_GARAGE',FALSE,5),
        (56,'LARGE_DOUBLE_GARAGE',FALSE,5),
        (57,'THREE_SINGLE_GARAGES',FALSE,5),
        (58,'TWO_DOUBLE_GARAGES',FALSE,5),
        (59,'TWO_SHIPPING_CONTAINERS',FALSE,5),

        (60,'TELEPHONE_BOX',FALSE,6),
        (61,'LARGE_WALK_IN_WARDROBE',FALSE,6),
        (62,'GARDEN_SHED',FALSE,6),
        (63,'LARGE_GARDEN_SHED',FALSE,6),
        (64,'LUTON_VAN',FALSE,6),
        (65,'LARGE_SINGLE_GARAGE',FALSE,6),
        (66,'ONE_AND_HALF_GARAGES',FALSE,6),
        (67,'STANDARD_DOUBLE_GARAGE',FALSE,6),
        (68,'LARGE_DOUBLE_GARAGE',FALSE,6),
        (69,'THREE_SINGLE_GARAGES',FALSE,6),
        (70,'TWO_DOUBLE_GARAGES',FALSE,6),
        (71,'TWO_SHIPPING_CONTAINERS',FALSE,6),

        (72,'TELEPHONE_BOX',FALSE,7),
        (73,'LARGE_WALK_IN_WARDROBE',FALSE,7),
        (74,'GARDEN_SHED',FALSE,7),
        (75,'LARGE_GARDEN_SHED',FALSE,7),
        (76,'LUTON_VAN',FALSE,7),
        (77,'LARGE_SINGLE_GARAGE',FALSE,7),
        (78,'ONE_AND_HALF_GARAGES',FALSE,7),
        (79,'STANDARD_DOUBLE_GARAGE',FALSE,7),
        (80,'LARGE_DOUBLE_GARAGE',FALSE,7),
        (81,'THREE_SINGLE_GARAGES',FALSE,7),
        (82,'TWO_DOUBLE_GARAGES',FALSE,7),
        (83,'TWO_SHIPPING_CONTAINERS',FALSE,7),

        (84,'TELEPHONE_BOX',FALSE,8),
        (85,'LARGE_WALK_IN_WARDROBE',FALSE,8),
        (86,'GARDEN_SHED',FALSE,8),
        (87,'LARGE_GARDEN_SHED',FALSE,8),
        (88,'LUTON_VAN',FALSE,8),
        (89,'LARGE_SINGLE_GARAGE',FALSE,8),
        (90,'ONE_AND_HALF_GARAGES',FALSE,8),
        (91,'STANDARD_DOUBLE_GARAGE',FALSE,8),
        (92,'LARGE_DOUBLE_GARAGE',FALSE,8),
        (93,'THREE_SINGLE_GARAGES',FALSE,8),
        (94,'TWO_DOUBLE_GARAGES',FALSE,8),
        (95,'TWO_SHIPPING_CONTAINERS',FALSE,8),

        (96,'TELEPHONE_BOX',FALSE,9),
        (97,'LARGE_WALK_IN_WARDROBE',FALSE,9),
        (98,'GARDEN_SHED',FALSE,9),
        (99,'LARGE_GARDEN_SHED',FALSE,9),
        (100,'LUTON_VAN',FALSE,9),
        (101,'LARGE_SINGLE_GARAGE',FALSE,9),
        (102,'ONE_AND_HALF_GARAGES',FALSE,9),
        (103,'STANDARD_DOUBLE_GARAGE',FALSE,9),
        (104,'LARGE_DOUBLE_GARAGE',FALSE,9),
        (105,'THREE_SINGLE_GARAGES',FALSE,9),
        (106,'TWO_DOUBLE_GARAGES',FALSE,9),
        (107,'TWO_SHIPPING_CONTAINERS',FALSE,9),

        (108,'TELEPHONE_BOX',FALSE,10),
        (109,'LARGE_WALK_IN_WARDROBE',FALSE,10),
        (110,'GARDEN_SHED',FALSE,10),
        (111,'LARGE_GARDEN_SHED',FALSE,10),
        (112,'LUTON_VAN',FALSE,10),
        (113,'LARGE_SINGLE_GARAGE',FALSE,10),
        (114,'ONE_AND_HALF_GARAGES',FALSE,10),
        (115,'STANDARD_DOUBLE_GARAGE',FALSE,10),
        (116,'LARGE_DOUBLE_GARAGE',FALSE,10),
        (117,'THREE_SINGLE_GARAGES',FALSE,10),
        (118,'TWO_DOUBLE_GARAGES',FALSE,10),
        (119,'TWO_SHIPPING_CONTAINERS',FALSE,10),

        (120,'TELEPHONE_BOX',FALSE,11),
        (121,'LARGE_WALK_IN_WARDROBE',FALSE,11),
        (122,'GARDEN_SHED',FALSE,11),
        (123,'LARGE_GARDEN_SHED',FALSE,11),
        (124,'LUTON_VAN',FALSE,11),
        (125,'LARGE_SINGLE_GARAGE',FALSE,11),
        (126,'ONE_AND_HALF_GARAGES',FALSE,11),
        (127,'STANDARD_DOUBLE_GARAGE',FALSE,11),
        (128,'LARGE_DOUBLE_GARAGE',FALSE,11),
        (129,'THREE_SINGLE_GARAGES',FALSE,11),
        (130,'TWO_DOUBLE_GARAGES',FALSE,11),
        (131,'TWO_SHIPPING_CONTAINERS',FALSE,11),

        (132,'TELEPHONE_BOX',FALSE,12),
        (133,'LARGE_WALK_IN_WARDROBE',FALSE,12),
        (134,'GARDEN_SHED',FALSE,12),
        (135,'LARGE_GARDEN_SHED',FALSE,12),
        (136,'LUTON_VAN',FALSE,12),
        (137,'LARGE_SINGLE_GARAGE',FALSE,12),
        (138,'ONE_AND_HALF_GARAGES',FALSE,12),
        (139,'STANDARD_DOUBLE_GARAGE',FALSE,12),
        (140,'LARGE_DOUBLE_GARAGE',FALSE,12),
        (141,'THREE_SINGLE_GARAGES',FALSE,12),
        (142,'TWO_DOUBLE_GARAGES',FALSE,12),
        (143,'TWO_SHIPPING_CONTAINERS',FALSE,12),

        (144,'TELEPHONE_BOX',FALSE,13),
        (145,'LARGE_WALK_IN_WARDROBE',FALSE,13),
        (146,'GARDEN_SHED',FALSE,13),
        (147,'LARGE_GARDEN_SHED',FALSE,13),
        (148,'LUTON_VAN',FALSE,13),
        (149,'LARGE_SINGLE_GARAGE',FALSE,13),
        (150,'ONE_AND_HALF_GARAGES',FALSE,13),
        (151,'STANDARD_DOUBLE_GARAGE',FALSE,13),
        (152,'LARGE_DOUBLE_GARAGE',FALSE,13),
        (153,'THREE_SINGLE_GARAGES',FALSE,13),
        (154,'TWO_DOUBLE_GARAGES',FALSE,13),
        (155,'TWO_SHIPPING_CONTAINERS',FALSE,13),

        (156,'TELEPHONE_BOX',FALSE,14),
        (157,'LARGE_WALK_IN_WARDROBE',FALSE,14),
        (158,'GARDEN_SHED',FALSE,14),
        (159,'LARGE_GARDEN_SHED',FALSE,14),
        (160,'LUTON_VAN',FALSE,14),
        (161,'LARGE_SINGLE_GARAGE',FALSE,14),
        (162,'ONE_AND_HALF_GARAGES',FALSE,14),
        (163,'STANDARD_DOUBLE_GARAGE',FALSE,14),
        (164,'LARGE_DOUBLE_GARAGE',FALSE,14),
        (165,'THREE_SINGLE_GARAGES',FALSE,14),
        (166,'TWO_DOUBLE_GARAGES',FALSE,14),
        (167,'TWO_SHIPPING_CONTAINERS',FALSE,14),

        (168,'TELEPHONE_BOX',FALSE,15),
        (169,'LARGE_WALK_IN_WARDROBE',FALSE,15),
        (170,'GARDEN_SHED',FALSE,15),
        (171,'LARGE_GARDEN_SHED',FALSE,15),
        (172,'LUTON_VAN',FALSE,15),
        (173,'LARGE_SINGLE_GARAGE',FALSE,15),
        (174,'ONE_AND_HALF_GARAGES',FALSE,15),
        (175,'STANDARD_DOUBLE_GARAGE',FALSE,15),
        (176,'LARGE_DOUBLE_GARAGE',FALSE,15),
        (177,'THREE_SINGLE_GARAGES',FALSE,15),
        (178,'TWO_DOUBLE_GARAGES',FALSE,15),
        (179,'TWO_SHIPPING_CONTAINERS',FALSE,15),

        (180,'TELEPHONE_BOX',FALSE,16),
        (181,'LARGE_WALK_IN_WARDROBE',FALSE,16),
        (182,'GARDEN_SHED',FALSE,16),
        (183,'LARGE_GARDEN_SHED',FALSE,16),
        (184,'LUTON_VAN',FALSE,16),
        (185,'LARGE_SINGLE_GARAGE',FALSE,16),
        (186,'ONE_AND_HALF_GARAGES',FALSE,16),
        (187,'STANDARD_DOUBLE_GARAGE',FALSE,16),
        (188,'LARGE_DOUBLE_GARAGE',FALSE,16),
        (189,'THREE_SINGLE_GARAGES',FALSE,16),
        (190,'TWO_DOUBLE_GARAGES',FALSE,16),
        (191,'TWO_SHIPPING_CONTAINERS',FALSE,16),

        (192,'TELEPHONE_BOX',FALSE,17),
        (193,'LARGE_WALK_IN_WARDROBE',FALSE,17),
        (194,'GARDEN_SHED',FALSE,17),
        (195,'LARGE_GARDEN_SHED',FALSE,17),
        (196,'LUTON_VAN',FALSE,17),
        (197,'LARGE_SINGLE_GARAGE',FALSE,17),
        (198,'ONE_AND_HALF_GARAGES',FALSE,17),
        (199,'STANDARD_DOUBLE_GARAGE',FALSE,17),
        (200,'LARGE_DOUBLE_GARAGE',FALSE,17),
        (201,'THREE_SINGLE_GARAGES',FALSE,17),
        (202,'TWO_DOUBLE_GARAGES',FALSE,17),
        (203,'TWO_SHIPPING_CONTAINERS',FALSE,17),

        (204,'TELEPHONE_BOX',FALSE,18),
        (205,'LARGE_WALK_IN_WARDROBE',FALSE,18),
        (206,'GARDEN_SHED',FALSE,18),
        (207,'LARGE_GARDEN_SHED',FALSE,18),
        (208,'LUTON_VAN',FALSE,18),
        (209,'LARGE_SINGLE_GARAGE',FALSE,18),
        (210,'ONE_AND_HALF_GARAGES',FALSE,18),
        (211,'STANDARD_DOUBLE_GARAGE',FALSE,18),
        (212,'LARGE_DOUBLE_GARAGE',FALSE,18),
        (213,'THREE_SINGLE_GARAGES',FALSE,18),
        (214,'TWO_DOUBLE_GARAGES',FALSE,18),
        (215,'TWO_SHIPPING_CONTAINERS',FALSE,18),

        (216,'TELEPHONE_BOX',FALSE,19),
        (217,'LARGE_WALK_IN_WARDROBE',FALSE,19),
        (218,'GARDEN_SHED',FALSE,19),
        (219,'LARGE_GARDEN_SHED',FALSE,19),
        (220,'LUTON_VAN',FALSE,19),
        (221,'LARGE_SINGLE_GARAGE',FALSE,19),
        (222,'ONE_AND_HALF_GARAGES',FALSE,19),
        (223,'STANDARD_DOUBLE_GARAGE',FALSE,19),
        (224,'LARGE_DOUBLE_GARAGE',FALSE,19),
        (225,'THREE_SINGLE_GARAGES',FALSE,19),
        (226,'TWO_DOUBLE_GARAGES',FALSE,19),
        (227,'TWO_SHIPPING_CONTAINERS',FALSE,19),

        (228,'TELEPHONE_BOX',FALSE,20),
        (229,'LARGE_WALK_IN_WARDROBE',FALSE,20),
        (230,'GARDEN_SHED',FALSE,20),
        (231,'LARGE_GARDEN_SHED',FALSE,20),
        (232,'LUTON_VAN',FALSE,20),
        (233,'LARGE_SINGLE_GARAGE',FALSE,20),
        (234,'ONE_AND_HALF_GARAGES',FALSE,20),
        (235,'STANDARD_DOUBLE_GARAGE',FALSE,20),
        (236,'LARGE_DOUBLE_GARAGE',FALSE,20),
        (237,'THREE_SINGLE_GARAGES',FALSE,20),
        (238,'TWO_DOUBLE_GARAGES',FALSE,20),
        (239,'TWO_SHIPPING_CONTAINERS',FALSE,20),

        (240,'TELEPHONE_BOX',FALSE,21),
        (241,'LARGE_WALK_IN_WARDROBE',FALSE,21),
        (242,'GARDEN_SHED',FALSE,21),
        (243,'LARGE_GARDEN_SHED',FALSE,21),
        (244,'LUTON_VAN',FALSE,21),
        (245,'LARGE_SINGLE_GARAGE',FALSE,21),
        (246,'ONE_AND_HALF_GARAGES',FALSE,21),
        (247,'STANDARD_DOUBLE_GARAGE',FALSE,21),
        (248,'LARGE_DOUBLE_GARAGE',FALSE,21),
        (249,'THREE_SINGLE_GARAGES',FALSE,21),
        (250,'TWO_DOUBLE_GARAGES',FALSE,21),
        (251,'TWO_SHIPPING_CONTAINERS',FALSE,21),

        (252,'TELEPHONE_BOX',FALSE,22),
        (253,'LARGE_WALK_IN_WARDROBE',FALSE,22),
        (254,'GARDEN_SHED',FALSE,22),
        (255,'LARGE_GARDEN_SHED',FALSE,22),
        (256,'LUTON_VAN',FALSE,22),
        (257,'LARGE_SINGLE_GARAGE',FALSE,22),
        (258,'ONE_AND_HALF_GARAGES',FALSE,22),
        (259,'STANDARD_DOUBLE_GARAGE',FALSE,22),
        (260,'LARGE_DOUBLE_GARAGE',FALSE,22),
        (261,'THREE_SINGLE_GARAGES',FALSE,22),
        (262,'TWO_DOUBLE_GARAGES',FALSE,22),
        (263,'TWO_SHIPPING_CONTAINERS',FALSE,22),

        (264,'TELEPHONE_BOX',FALSE,23),
        (265,'LARGE_WALK_IN_WARDROBE',FALSE,23),
        (266,'GARDEN_SHED',FALSE,23),
        (267,'LARGE_GARDEN_SHED',FALSE,23),
        (268,'LUTON_VAN',FALSE,23),
        (269,'LARGE_SINGLE_GARAGE',FALSE,23),
        (270,'ONE_AND_HALF_GARAGES',FALSE,23),
        (271,'STANDARD_DOUBLE_GARAGE',FALSE,23),
        (272,'LARGE_DOUBLE_GARAGE',FALSE,23),
        (273,'THREE_SINGLE_GARAGES',FALSE,23),
        (274,'TWO_DOUBLE_GARAGES',FALSE,23),
        (275,'TWO_SHIPPING_CONTAINERS',FALSE,23),

        (276,'TELEPHONE_BOX',FALSE,24),
        (277,'LARGE_WALK_IN_WARDROBE',FALSE,24),
        (278,'GARDEN_SHED',FALSE,24),
        (279,'LARGE_GARDEN_SHED',FALSE,24),
        (280,'LUTON_VAN',FALSE,24),
        (281,'LARGE_SINGLE_GARAGE',FALSE,24),
        (282,'ONE_AND_HALF_GARAGES',FALSE,24),
        (283,'STANDARD_DOUBLE_GARAGE',FALSE,24),
        (284,'LARGE_DOUBLE_GARAGE',FALSE,24),
        (285,'THREE_SINGLE_GARAGES',FALSE,24),
        (286,'TWO_DOUBLE_GARAGES',FALSE,24),
        (287,'TWO_SHIPPING_CONTAINERS',FALSE,24),

        (288,'TELEPHONE_BOX',FALSE,25),
        (289,'LARGE_WALK_IN_WARDROBE',FALSE,25),
        (290,'GARDEN_SHED',FALSE,25),
        (291,'LARGE_GARDEN_SHED',FALSE,25),
        (292,'LUTON_VAN',FALSE,25),
        (293,'LARGE_SINGLE_GARAGE',FALSE,25),
        (294,'ONE_AND_HALF_GARAGES',FALSE,25),
        (295,'STANDARD_DOUBLE_GARAGE',FALSE,25),
        (296,'LARGE_DOUBLE_GARAGE',FALSE,25),
        (297,'THREE_SINGLE_GARAGES',FALSE,25),
        (298,'TWO_DOUBLE_GARAGES',FALSE,25),
        (299,'TWO_SHIPPING_CONTAINERS',FALSE,25),

        (300,'TELEPHONE_BOX',FALSE,26),
        (301,'LARGE_WALK_IN_WARDROBE',FALSE,26),
        (302,'GARDEN_SHED',FALSE,26),
        (303,'LARGE_GARDEN_SHED',FALSE,26),
        (304,'LUTON_VAN',FALSE,26),
        (305,'LARGE_SINGLE_GARAGE',FALSE,26),
        (306,'ONE_AND_HALF_GARAGES',FALSE,26),
        (307,'STANDARD_DOUBLE_GARAGE',FALSE,26),
        (308,'LARGE_DOUBLE_GARAGE',FALSE,26),
        (309,'THREE_SINGLE_GARAGES',FALSE,26),
        (310,'TWO_DOUBLE_GARAGES',FALSE,26),
        (311,'TWO_SHIPPING_CONTAINERS',FALSE,26),

        (312,'TELEPHONE_BOX',FALSE,27),
        (313,'LARGE_WALK_IN_WARDROBE',FALSE,27),
        (314,'GARDEN_SHED',FALSE,27),
        (315,'LARGE_GARDEN_SHED',FALSE,27),
        (316,'LUTON_VAN',FALSE,27),
        (317,'LARGE_SINGLE_GARAGE',FALSE,27),
        (318,'ONE_AND_HALF_GARAGES',FALSE,27),
        (319,'STANDARD_DOUBLE_GARAGE',FALSE,27),
        (320,'LARGE_DOUBLE_GARAGE',FALSE,27),
        (321,'THREE_SINGLE_GARAGES',FALSE,27),
        (322,'TWO_DOUBLE_GARAGES',FALSE,27),
        (323,'TWO_SHIPPING_CONTAINERS',FALSE,27),

        (324,'TELEPHONE_BOX',FALSE,28),
        (325,'LARGE_WALK_IN_WARDROBE',FALSE,28),
        (326,'GARDEN_SHED',FALSE,28),
        (327,'LARGE_GARDEN_SHED',FALSE,28),
        (328,'LUTON_VAN',FALSE,28),
        (329,'LARGE_SINGLE_GARAGE',FALSE,28),
        (330,'ONE_AND_HALF_GARAGES',FALSE,28),
        (331,'STANDARD_DOUBLE_GARAGE',FALSE,28),
        (332,'LARGE_DOUBLE_GARAGE',FALSE,28),
        (333,'THREE_SINGLE_GARAGES',FALSE,28),
        (334,'TWO_DOUBLE_GARAGES',FALSE,28),
        (335,'TWO_SHIPPING_CONTAINERS',FALSE,28),

        (336,'TELEPHONE_BOX',FALSE,29),
        (337,'LARGE_WALK_IN_WARDROBE',FALSE,29),
        (338,'GARDEN_SHED',FALSE,29),
        (339,'LARGE_GARDEN_SHED',FALSE,29),
        (340,'LUTON_VAN',FALSE,29),
        (341,'LARGE_SINGLE_GARAGE',FALSE,29),
        (342,'ONE_AND_HALF_GARAGES',FALSE,29),
        (343,'STANDARD_DOUBLE_GARAGE',FALSE,29),
        (344,'LARGE_DOUBLE_GARAGE',FALSE,29),
        (345,'THREE_SINGLE_GARAGES',FALSE,29),
        (346,'TWO_DOUBLE_GARAGES',FALSE,29),
        (347,'TWO_SHIPPING_CONTAINERS',FALSE,29),

        (348,'TELEPHONE_BOX',FALSE,30),
        (349,'LARGE_WALK_IN_WARDROBE',FALSE,30),
        (350,'GARDEN_SHED',FALSE,30),
        (351,'LARGE_GARDEN_SHED',FALSE,30),
        (352,'LUTON_VAN',FALSE,30),
        (353,'LARGE_SINGLE_GARAGE',FALSE,30),
        (354,'ONE_AND_HALF_GARAGES',FALSE,30),
        (355,'STANDARD_DOUBLE_GARAGE',FALSE,30),
        (356,'LARGE_DOUBLE_GARAGE',FALSE,30),
        (357,'THREE_SINGLE_GARAGES',FALSE,30),
        (358,'TWO_DOUBLE_GARAGES',FALSE,30),
        (359,'TWO_SHIPPING_CONTAINERS',FALSE,30),

        (360,'TELEPHONE_BOX',FALSE,31),
        (361,'LARGE_WALK_IN_WARDROBE',FALSE,31),
        (362,'GARDEN_SHED',FALSE,31),
        (363,'LARGE_GARDEN_SHED',FALSE,31),
        (364,'LUTON_VAN',FALSE,31),
        (365,'LARGE_SINGLE_GARAGE',FALSE,31),
        (366,'ONE_AND_HALF_GARAGES',FALSE,31),
        (367,'STANDARD_DOUBLE_GARAGE',FALSE,31),
        (368,'LARGE_DOUBLE_GARAGE',FALSE,31),
        (369,'THREE_SINGLE_GARAGES',FALSE,31),
        (370,'TWO_DOUBLE_GARAGES',FALSE,31),
        (371,'TWO_SHIPPING_CONTAINERS',FALSE,31),

        (372,'TELEPHONE_BOX',FALSE,32),
        (373,'LARGE_WALK_IN_WARDROBE',FALSE,32),
        (374,'GARDEN_SHED',FALSE,32),
        (375,'LARGE_GARDEN_SHED',FALSE,32),
        (376,'LUTON_VAN',FALSE,32),
        (377,'LARGE_SINGLE_GARAGE',FALSE,32),
        (378,'ONE_AND_HALF_GARAGES',FALSE,32),
        (379,'STANDARD_DOUBLE_GARAGE',FALSE,32),
        (380,'LARGE_DOUBLE_GARAGE',FALSE,32),
        (381,'THREE_SINGLE_GARAGES',FALSE,32),
        (382,'TWO_DOUBLE_GARAGES',FALSE,32),
        (383,'TWO_SHIPPING_CONTAINERS',FALSE,32),

        (384,'TELEPHONE_BOX',FALSE,33),
        (385,'LARGE_WALK_IN_WARDROBE',FALSE,33),
        (386,'GARDEN_SHED',FALSE,33),
        (387,'LARGE_GARDEN_SHED',FALSE,33),
        (389,'LUTON_VAN',FALSE,33),
        (390,'LARGE_SINGLE_GARAGE',FALSE,33),
        (391,'ONE_AND_HALF_GARAGES',FALSE,33),
        (392,'STANDARD_DOUBLE_GARAGE',FALSE,33),
        (393,'LARGE_DOUBLE_GARAGE',FALSE,33),
        (394,'THREE_SINGLE_GARAGES',FALSE,33),
        (395,'TWO_DOUBLE_GARAGES',FALSE,33),
        (396,'TWO_SHIPPING_CONTAINERS',FALSE,33),

        (397,'TELEPHONE_BOX',FALSE,34),
        (398,'LARGE_WALK_IN_WARDROBE',FALSE,34),
        (399,'GARDEN_SHED',FALSE,34),
        (400,'LARGE_GARDEN_SHED',FALSE,34),
        (401,'LUTON_VAN',FALSE,34),
        (402,'LARGE_SINGLE_GARAGE',FALSE,34),
        (403,'ONE_AND_HALF_GARAGES',FALSE,34),
        (404,'STANDARD_DOUBLE_GARAGE',FALSE,34),
        (405,'LARGE_DOUBLE_GARAGE',FALSE,34),
        (406,'THREE_SINGLE_GARAGES',FALSE,34),
        (407,'TWO_DOUBLE_GARAGES',FALSE,34),
        (408,'TWO_SHIPPING_CONTAINERS',FALSE,34),

        (409,'TELEPHONE_BOX',FALSE,35),
        (410,'LARGE_WALK_IN_WARDROBE',FALSE,35),
        (411,'GARDEN_SHED',FALSE,35),
        (412,'LARGE_GARDEN_SHED',FALSE,35),
        (413,'LUTON_VAN',FALSE,35),
        (414,'LARGE_SINGLE_GARAGE',FALSE,35),
        (415,'ONE_AND_HALF_GARAGES',FALSE,35),
        (416,'STANDARD_DOUBLE_GARAGE',FALSE,35),
        (417,'LARGE_DOUBLE_GARAGE',FALSE,35),
        (418,'THREE_SINGLE_GARAGES',FALSE,35),
        (419,'TWO_DOUBLE_GARAGES',FALSE,35),
        (420,'TWO_SHIPPING_CONTAINERS',FALSE,35),

        (421,'TELEPHONE_BOX',FALSE,36),
        (422,'LARGE_WALK_IN_WARDROBE',FALSE,36),
        (423,'GARDEN_SHED',FALSE,36),
        (424,'LARGE_GARDEN_SHED',FALSE,36),
        (425,'LUTON_VAN',FALSE,36),
        (426,'LARGE_SINGLE_GARAGE',FALSE,36),
        (427,'ONE_AND_HALF_GARAGES',FALSE,36),
        (428,'STANDARD_DOUBLE_GARAGE',FALSE,36),
        (429,'LARGE_DOUBLE_GARAGE',FALSE,36),
        (430,'THREE_SINGLE_GARAGES',FALSE,36),
        (431,'TWO_DOUBLE_GARAGES',FALSE,36),
        (432,'TWO_SHIPPING_CONTAINERS',FALSE,36),

        (433,'TELEPHONE_BOX',FALSE,37),
        (434,'LARGE_WALK_IN_WARDROBE',FALSE,37),
        (435,'GARDEN_SHED',FALSE,37),
        (436,'LARGE_GARDEN_SHED',FALSE,37),
        (437,'LUTON_VAN',FALSE,37),
        (438,'LARGE_SINGLE_GARAGE',FALSE,37),
        (439,'ONE_AND_HALF_GARAGES',FALSE,37),
        (440,'STANDARD_DOUBLE_GARAGE',FALSE,37),
        (441,'LARGE_DOUBLE_GARAGE',FALSE,37),
        (442,'THREE_SINGLE_GARAGES',FALSE,37),
        (443,'TWO_DOUBLE_GARAGES',FALSE,37),
        (444,'TWO_SHIPPING_CONTAINERS',FALSE,37),

        (445,'TELEPHONE_BOX',FALSE,38),
        (446,'LARGE_WALK_IN_WARDROBE',FALSE,38),
        (447,'GARDEN_SHED',FALSE,38),
        (448,'LARGE_GARDEN_SHED',FALSE,38),
        (449,'LUTON_VAN',FALSE,38),
        (450,'LARGE_SINGLE_GARAGE',FALSE,38),
        (451,'ONE_AND_HALF_GARAGES',FALSE,38),
        (452,'STANDARD_DOUBLE_GARAGE',FALSE,38),
        (453,'LARGE_DOUBLE_GARAGE',FALSE,38),
        (454,'THREE_SINGLE_GARAGES',FALSE,38),
        (455,'TWO_DOUBLE_GARAGES',FALSE,38),
        (456,'TWO_SHIPPING_CONTAINERS',FALSE,38),

        (457,'TELEPHONE_BOX',FALSE,39),
        (458,'LARGE_WALK_IN_WARDROBE',FALSE,39),
        (459,'GARDEN_SHED',FALSE,39),
        (460,'LARGE_GARDEN_SHED',FALSE,39),
        (461,'LUTON_VAN',FALSE,39),
        (462,'LARGE_SINGLE_GARAGE',FALSE,39),
        (463,'ONE_AND_HALF_GARAGES',FALSE,39),
        (464,'STANDARD_DOUBLE_GARAGE',FALSE,39),
        (465,'LARGE_DOUBLE_GARAGE',FALSE,39),
        (466,'THREE_SINGLE_GARAGES',FALSE,39),
        (467,'TWO_DOUBLE_GARAGES',FALSE,39),
        (468,'TWO_SHIPPING_CONTAINERS',FALSE,39),

        (469,'TELEPHONE_BOX',FALSE,40),
        (470,'LARGE_WALK_IN_WARDROBE',FALSE,40),
        (471,'GARDEN_SHED',FALSE,40),
        (472,'LARGE_GARDEN_SHED',FALSE,40),
        (473,'LUTON_VAN',FALSE,40),
        (474,'LARGE_SINGLE_GARAGE',FALSE,40),
        (475,'ONE_AND_HALF_GARAGES',FALSE,40),
        (476,'STANDARD_DOUBLE_GARAGE',FALSE,40),
        (477,'LARGE_DOUBLE_GARAGE',FALSE,40),
        (478,'THREE_SINGLE_GARAGES',FALSE,40),
        (479,'TWO_DOUBLE_GARAGES',FALSE,40),
        (480,'TWO_SHIPPING_CONTAINERS',FALSE,40),

        (481,'TELEPHONE_BOX',FALSE,41),
        (482,'LARGE_WALK_IN_WARDROBE',FALSE,41),
        (483,'GARDEN_SHED',FALSE,41),
        (484,'LARGE_GARDEN_SHED',FALSE,41),
        (485,'LUTON_VAN',FALSE,41),
        (486,'LARGE_SINGLE_GARAGE',FALSE,41),
        (487,'ONE_AND_HALF_GARAGES',FALSE,41),
        (488,'STANDARD_DOUBLE_GARAGE',FALSE,41),
        (489,'LARGE_DOUBLE_GARAGE',FALSE,41),
        (490,'THREE_SINGLE_GARAGES',FALSE,41),
        (491,'TWO_DOUBLE_GARAGES',FALSE,41),
        (492,'TWO_SHIPPING_CONTAINERS',FALSE,41),

        (493,'TELEPHONE_BOX',FALSE,42),
        (494,'LARGE_WALK_IN_WARDROBE',FALSE,42),
        (495,'GARDEN_SHED',FALSE,42),
        (496,'LARGE_GARDEN_SHED',FALSE,42),
        (497,'LUTON_VAN',FALSE,42),
        (498,'LARGE_SINGLE_GARAGE',FALSE,42),
        (499,'ONE_AND_HALF_GARAGES',FALSE,42),
        (500,'STANDARD_DOUBLE_GARAGE',FALSE,42),
        (501,'LARGE_DOUBLE_GARAGE',FALSE,42),
        (502,'THREE_SINGLE_GARAGES',FALSE,42),
        (503,'TWO_DOUBLE_GARAGES',FALSE,42),
        (504,'TWO_SHIPPING_CONTAINERS',FALSE,42);
