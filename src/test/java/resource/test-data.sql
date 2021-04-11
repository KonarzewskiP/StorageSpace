INSERT INTO users (id, first_name, last_name, email, role, gender)
VALUES (1, 'Rick', 'Sanchez', 'rick@sanchez.com', 'ADMIN', 'MALE'),
       (2, 'Morty', 'Smith', 'morty@smith.com', 'CUSTOMER', 'MALE'),
       (3, 'Summer', 'Smith', 'summer@smith.com', 'CUSTOMER', 'FEMALE');

INSERT INTO warehouses (id, name, type_of_storage)
VALUES (2, 'Bromley ','REGULAR'),
       (6, 'Barking ','PREMIUM'),
       (13, 'Edmonton ','PREMIUM'),
       (24, 'Romford','REGULAR');

INSERT INTO address (id, city, street, postcode, warehouse_id)
VALUES
       (2, 'Kent', '12 Farwig Lane', 'BR1 3RB',2),
       (6,  'London', 'Hertford Road', 'IG11 8BL',6),
       (13,  'London', '8 Advent Way', 'N18 3AF',13),
       (24,  'Essex', 'Ashton Road', 'RM3 8NF',24);

INSERT INTO storage_rooms (id, size, reserved, warehouse_id)
VALUES
       (13, 'TELEPHONE_BOX', FALSE, 2),
       (14, 'LARGE_WALK_IN_WARDROBE', FALSE, 2),
       (15, 'GARDEN_SHED', FALSE, 2),
       (16, 'LARGE_GARDEN_SHED', FALSE, 2),
       (17, 'LUTON_VAN', FALSE, 2),
       (18, 'LARGE_SINGLE_GARAGE', FALSE, 2),
       (19, 'ONE_AND_HALF_GARAGES', FALSE, 2),
       (20, 'STANDARD_DOUBLE_GARAGE', FALSE, 2),
       (21, 'LARGE_DOUBLE_GARAGE', FALSE, 2),
       (22, 'THREE_SINGLE_GARAGES', FALSE, 2),
       (23, 'TWO_DOUBLE_GARAGES', FALSE, 2),
       (24, 'TWO_SHIPPING_CONTAINERS', FALSE, 2),

       (60, 'TELEPHONE_BOX', FALSE, 6),
       (61, 'LARGE_WALK_IN_WARDROBE', FALSE, 6),
       (62, 'GARDEN_SHED', FALSE, 6),
       (63, 'LARGE_GARDEN_SHED', FALSE, 6),
       (64, 'LUTON_VAN', FALSE, 6),
       (65, 'LARGE_SINGLE_GARAGE', FALSE, 6),
       (66, 'ONE_AND_HALF_GARAGES', FALSE, 6),
       (67, 'STANDARD_DOUBLE_GARAGE', FALSE, 6),
       (68, 'LARGE_DOUBLE_GARAGE', FALSE, 6),
       (69, 'THREE_SINGLE_GARAGES', FALSE, 6),
       (70, 'TWO_DOUBLE_GARAGES', FALSE, 6),
       (71, 'TWO_SHIPPING_CONTAINERS', FALSE, 6),

       (144, 'TELEPHONE_BOX', FALSE, 13),
       (145, 'LARGE_WALK_IN_WARDROBE', FALSE, 13),
       (146, 'GARDEN_SHED', FALSE, 13),
       (147, 'LARGE_GARDEN_SHED', FALSE, 13),
       (148, 'LUTON_VAN', FALSE, 13),
       (149, 'LARGE_SINGLE_GARAGE', FALSE, 13),
       (150, 'ONE_AND_HALF_GARAGES', FALSE, 13),
       (151, 'STANDARD_DOUBLE_GARAGE', FALSE, 13),
       (152, 'LARGE_DOUBLE_GARAGE', FALSE, 13),
       (153, 'THREE_SINGLE_GARAGES', FALSE, 13),
       (154, 'TWO_DOUBLE_GARAGES', FALSE, 13),
       (155, 'TWO_SHIPPING_CONTAINERS', FALSE, 13),

       (276, 'TELEPHONE_BOX', FALSE, 24),
       (277, 'LARGE_WALK_IN_WARDROBE', FALSE, 24),
       (278, 'GARDEN_SHED', FALSE, 24),
       (279, 'LARGE_GARDEN_SHED', FALSE, 24),
       (280, 'LUTON_VAN', FALSE, 24),
       (281, 'LARGE_SINGLE_GARAGE', FALSE, 24),
       (282, 'ONE_AND_HALF_GARAGES', FALSE, 24),
       (283, 'STANDARD_DOUBLE_GARAGE', FALSE, 24),
       (284, 'LARGE_DOUBLE_GARAGE', FALSE, 24),
       (285, 'THREE_SINGLE_GARAGES', FALSE, 24),
       (286, 'TWO_DOUBLE_GARAGES', FALSE, 24),
       (287, 'TWO_SHIPPING_CONTAINERS', FALSE, 24);

