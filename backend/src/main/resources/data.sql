INSERT INTO "PUBLIC"."CATEGORY"
VALUES (-1, 'Møbler'),
       (-2, 'Elektronikk'),
       (-3, 'Klær'),
       (-4, 'Bøker'),
       (-5, 'Diverse');

INSERT INTO "PUBLIC"."IMAGE"
VALUES (-1, 'dev-gallery1.jpg'),
       (-2, 'dev-gallery2.jpg'),
       (-3, 'dev-gallery3.jpg'),
       (-4, 'dev-gallery4.jpg'),
       (-5, 'dev-gallery5.jpg'),
       (-6, 'dev-gallery6.jpg'),
       (-7, 'dev-thumbnail1.jpg');

INSERT INTO "PUBLIC"."USER_"
VALUES (-1, FALSE, 'user@example.org', 'Eksempel', 'Bruker',
        '$2a$10$HgH0RB4RyNoO14oZiwSwPufYNaBRbpSji6l48vqvy4VXJU27sKore'),
       (-2, TRUE, 'admin@example.org', 'Eksempel', 'Admin',
        '$2a$10$hbzDTlQaalh0Xy7cx2/DS.91IueTS0804FcPgZw93AErNN8Zm9GKe');

INSERT INTO "PUBLIC"."LOCATION"
VALUES (-1, 59.6633005, 10.6293392, U&'Dr\00f8bak, Norge'),
       (-2, 59.9479953, 10.9276201, U&'H\00f8ybr\00e5ten, Norge');

INSERT INTO "PUBLIC"."LISTING"
VALUES (-1, TIMESTAMP '2023-03-20 23:45:36.187', 'En fin sofa i god stand', 1000, NULL, 'ACTIVE', 'En fin sofa',
        'Sofa', -1, -1, -1, -7),
       (-2, TIMESTAMP '2023-03-20 23:45:36.187', 'En fin TV i god stand', 2000, NULL, 'ACTIVE', 'En fin TV', 'TV',
        -2, -2, -2, -7),
       (-3, TIMESTAMP '2023-03-20 23:45:36.187', 'En fin stol i god stand!', 3000, NULL, 'ACTIVE', 'En fin stol',
        'Stol', -1, -1, -1, -7),
       (-4, TIMESTAMP '2023-03-20 23:45:36.187', 'En fin stol i god stand!', 4000, NULL, 'ACTIVE', 'En fin stol',
        'Stol', -1, -1, -1, -7),
       (-5, TIMESTAMP '2023-03-20 23:45:36.187', 'En fin stol i god stand!', 5000, NULL, 'ACTIVE', 'En fin stol',
        'Stol', -1, -1, -1, -7),
       (-6, TIMESTAMP '2023-03-20 23:45:36.187', 'En fin stol i god stand!', 6000, NULL, 'ACTIVE', 'En fin stol',
        'Stol', -1, -1, -1, -7),
       (-7, TIMESTAMP '2023-03-20 23:45:36.187', 'En fin stol i god stand!', 7000, NULL, 'ACTIVE', 'En fin stol',
        'Stol', -1, -1, -1, -7),
       (-8, TIMESTAMP '2023-03-20 23:45:36.187', 'En fin stol i god stand!', 8000, NULL, 'ACTIVE', 'En fin stol',
        'Stol', -1, -1, -1, -7),
       (-9, TIMESTAMP '2023-03-20 23:45:36.187', 'En fin stol i god stand!', 9000, NULL, 'ACTIVE', 'En fin stol',
        'Stol', -1, -1, -1, -7),
       (-10, TIMESTAMP '2023-03-20 23:45:36.187', 'En fin stol i god stand!', 10000, NULL, 'ACTIVE', 'En fin stol',
        'Stol', -1, -1, -1, -7),
       (-11, TIMESTAMP '2023-03-20 23:45:36.187', 'En fin stol i god stand!', 11000, NULL, 'ACTIVE', 'En fin stol',
        'Stol', -1, -1, -1, -7);

INSERT INTO "PUBLIC"."LISTING_GALLERY"
VALUES (-1, -1),
       (-1, -2),
       (-1, -3),
       (-1, -4),
       (-1, -5),
       (-1, -6),
       (-1, -7),
       (-2, -1),
       (-2, -2),
       (-2, -3),
       (-2, -4),
       (-2, -5),
       (-2, -6),
       (-2, -7),
       (-3, -1),
       (-3, -2),
       (-3, -3),
       (-3, -4),
       (-3, -5),
       (-3, -6),
       (-3, -7),
       (-4, -1),
       (-4, -2),
       (-4, -3),
       (-4, -4),
       (-4, -5),
       (-4, -6),
       (-4, -7),
       (-5, -1),
       (-5, -2),
       (-5, -3),
       (-5, -4),
       (-5, -5),
       (-5, -6),
       (-5, -7),
       (-6, -1),
       (-6, -2),
       (-6, -3),
       (-6, -4),
       (-6, -5),
       (-6, -6),
       (-6, -7),
       (-7, -1),
       (-7, -2),
       (-7, -3),
       (-7, -4),
       (-7, -5),
       (-7, -6),
       (-7, -7),
       (-8, -1),
       (-8, -2),
       (-8, -3),
       (-8, -4),
       (-8, -5),
       (-8, -6),
       (-8, -7),
       (-9, -1),
       (-9, -2),
       (-9, -3),
       (-9, -4),
       (-9, -5),
       (-9, -6),
       (-9, -7),
       (-10, -1),
       (-10, -2),
       (-10, -3),
       (-10, -4),
       (-10, -5),
       (-10, -6),
       (-10, -7),
       (-11, -1),
       (-11, -2),
       (-11, -3),
       (-11, -4),
       (-11, -5),
       (-11, -6),
       (-11, -7);

INSERT INTO "PUBLIC"."USER__FAVORITES"
VALUES (-1, -2),
       (-1, -3),
       (-1, -9);