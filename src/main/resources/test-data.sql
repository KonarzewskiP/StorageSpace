INSERT INTO USERS (uuid, created_at, updated_at, first_name, last_name, email, role, gender, enabled)
VALUES ('U1000', NOW(), NOW(), 'Rick', 'Sanchez', 'rick@sanchez.com', 'ADMIN', 'MALE', true),
       ('U1001', NOW(), NOW(), 'Morty', 'Smith', 'morty@smith.com', 'CUSTOMER', 'MALE', true),
       ('U1002', NOW(), NOW(), 'Summer', 'Smith', 'summer@smith.com', 'CUSTOMER', 'FEMALE', true);

INSERT INTO warehouses (id, uuid, created_at, updated_at, name, city, postcode, street, lat, lng,
                        total_rental_area_in_m2)
VALUES (1, 'W1000', NOW(), NOW(), 'Orange', 'London', 'SE11 4UB', '57 Walcot Square', '51.4943', '-0.1089',
        '10000'),
       (2, 'W1001', NOW(), NOW(), 'Blue', 'London', 'SW11 1PL', '3C Severus Rd', '51.4627', '-0.169',
        '20000');

INSERT INTO storage_rooms (uuid, created_at, updated_at, storage_size, status, warehouse_id)
VALUES ('SR1000', NOW(), NOW(), 'TELEPHONE_BOX', 'AVAILABLE', 1),
--        ('SR1001', NOW(), NOW(), 'LARGE_WALK_IN_WARDROBE', 'AVAILABLE', 1),
--        ('SR1002', NOW(), NOW(), 'GARDEN_SHED', 'AVAILABLE', 1),
       ('SR1003', NOW(), NOW(), 'LARGE_GARDEN_SHED', 'AVAILABLE', 1),
--        ('SR1004', NOW(), NOW(), 'LUTON_VAN', 'AVAILABLE', 1),
--        ('SR1005', NOW(), NOW(), 'LARGE_SINGLE_GARAGE', 'AVAILABLE', 1),
       ('SR1006', NOW(), NOW(), 'ONE_AND_HALF_GARAGES', 'AVAILABLE', 1),
--        ('SR1007', NOW(), NOW(), 'STANDARD_DOUBLE_GARAGE', 'AVAILABLE', 1),
--        ('SR1008', NOW(), NOW(), 'LARGE_DOUBLE_GARAGE', 'AVAILABLE', 1),
       ('SR1009', NOW(), NOW(), 'THREE_SINGLE_GARAGES', 'AVAILABLE', 1),
--        ('SR1010', NOW(), NOW(), 'TWO_DOUBLE_GARAGES', 'AVAILABLE', 1),
--        ('SR1011', NOW(), NOW(), 'TWO_SHIPPING_CONTAINERS', 'AVAILABLE', 1),

       ('SR1051', NOW(), NOW(), 'TELEPHONE_BOX', 'AVAILABLE', 1),
--        ('SR1052', NOW(), NOW(), 'LARGE_WALK_IN_WARDROBE', 'AVAILABLE', 1),
--        ('SR1053', NOW(), NOW(), 'GARDEN_SHED', 'AVAILABLE', 1),
       ('SR1054', NOW(), NOW(), 'LARGE_GARDEN_SHED', 'AVAILABLE', 1),
--        ('SR1055', NOW(), NOW(), 'LUTON_VAN', 'AVAILABLE', 1),
--        ('SR1056', NOW(), NOW(), 'LARGE_SINGLE_GARAGE', 'AVAILABLE', 1),
       ('SR1057', NOW(), NOW(), 'ONE_AND_HALF_GARAGES', 'AVAILABLE', 1),
--        ('SR1058', NOW(), NOW(), 'STANDARD_DOUBLE_GARAGE', 'AVAILABLE', 1),
--        ('SR1059', NOW(), NOW(), 'LARGE_DOUBLE_GARAGE', 'AVAILABLE', 1),
       ('SR1050', NOW(), NOW(), 'THREE_SINGLE_GARAGES', 'AVAILABLE', 1),
--        ('SR1061', NOW(), NOW(), 'TWO_DOUBLE_GARAGES', 'AVAILABLE', 1),
--        ('SR1062', NOW(), NOW(), 'TWO_SHIPPING_CONTAINERS', 'AVAILABLE', 1),

       ('SR1023', NOW(), NOW(), 'TELEPHONE_BOX', 'AVAILABLE', 2),
--        ('SR1024', NOW(), NOW(), 'LARGE_WALK_IN_WARDROBE', 'AVAILABLE', 2),
--        ('SR1025', NOW(), NOW(), 'GARDEN_SHED', 'AVAILABLE', 2),
       ('SR1026', NOW(), NOW(), 'LARGE_GARDEN_SHED', 'AVAILABLE', 2),
--        ('SR1027', NOW(), NOW(), 'LUTON_VAN', 'AVAILABLE', 2),
--        ('SR1028', NOW(), NOW(), 'LARGE_SINGLE_GARAGE', 'AVAILABLE', 2),
       ('SR1029', NOW(), NOW(), 'ONE_AND_HALF_GARAGES', 'AVAILABLE', 2),
--        ('SR1030', NOW(), NOW(), 'STANDARD_DOUBLE_GARAGE', 'AVAILABLE', 2),
--        ('SR1031', NOW(), NOW(), 'LARGE_DOUBLE_GARAGE', 'AVAILABLE', 2),
       ('SR1032', NOW(), NOW(), 'THREE_SINGLE_GARAGES', 'AVAILABLE', 2),
--        ('SR1033', NOW(), NOW(), 'TWO_DOUBLE_GARAGES', 'AVAILABLE', 2),
--        ('SR1034', NOW(), NOW(), 'TWO_SHIPPING_CONTAINERS', 'AVAILABLE', 2),

       ('SR1035', NOW(), NOW(), 'TELEPHONE_BOX', 'AVAILABLE', 2),
--        ('SR1036', NOW(), NOW(), 'LARGE_WALK_IN_WARDROBE', 'AVAILABLE', 2),
--        ('SR1037', NOW(), NOW(), 'GARDEN_SHED', 'AVAILABLE', 2),
       ('SR1038', NOW(), NOW(), 'LARGE_GARDEN_SHED', 'AVAILABLE', 2),
--        ('SR1039', NOW(), NOW(), 'LUTON_VAN', 'AVAILABLE', 2),
--        ('SR1040', NOW(), NOW(), 'LARGE_SINGLE_GARAGE', 'AVAILABLE', 2),
       ('SR1041', NOW(), NOW(), 'ONE_AND_HALF_GARAGES', 'AVAILABLE', 2),
--        ('SR1042', NOW(), NOW(), 'STANDARD_DOUBLE_GARAGE', 'AVAILABLE', 2),
--        ('SR1043', NOW(), NOW(), 'LARGE_DOUBLE_GARAGE', 'AVAILABLE', 2),
       ('SR1044', NOW(), NOW(), 'THREE_SINGLE_GARAGES', 'AVAILABLE', 2);
--        ('SR1045', NOW(), NOW(), 'TWO_DOUBLE_GARAGES', 'AVAILABLE', 2),
--        ('SR1046', NOW(), NOW(), 'TWO_SHIPPING_CONTAINERS', 'AVAILABLE', 2);

