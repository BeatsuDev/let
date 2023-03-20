INSERT INTO "PUBLIC"."CATEGORY"
VALUES (1, 'Furniture'),
       (2, 'Electronics'),
       (3, 'Clothes'),
       (4, 'Books'),
       (5, 'Other');

INSERT INTO "PUBLIC"."IMAGE"
VALUES (1, 'dev-gallery1.jpg'),
       (2, 'dev-gallery2.jpg'),
       (3, 'dev-gallery3.jpg'),
       (4, 'dev-gallery4.jpg'),
       (5, 'dev-gallery5.jpg'),
       (6, 'dev-gallery6.jpg'),
       (7, 'dev-thumbnail1.jpg');

INSERT INTO "PUBLIC"."USER_"
VALUES (1, FALSE, 'user@example.org', 'Example', 'User',
        '$2a$10$HgH0RB4RyNoO14oZiwSwPufYNaBRbpSji6l48vqvy4VXJU27sKore'),
       (2, TRUE, 'admin@example.org', 'Example', 'Admin',
        '$2a$10$hbzDTlQaalh0Xy7cx2/DS.91IueTS0804FcPgZw93AErNN8Zm9GKe');

INSERT INTO "PUBLIC"."LOCATION"
VALUES (1, 59.6633005, 10.6293392, U&'Dr\00f8bak, Norge'),
       (2, 59.9479953, 10.9276201, U&'H\00f8ybr\00e5ten, Norge');

INSERT INTO "PUBLIC"."LISTING"
VALUES (1, TIMESTAMP '2023-03-20 23:45:36.187', 'A nice sofa, in good condition', 1000, NULL, 'ACTIVE', 'A nice sofa',
        'Sofa', 1, 1, 1, 7),
       (2, TIMESTAMP '2023-03-20 23:45:36.187', 'A nice TV, in good condition', 2000, NULL, 'ACTIVE', 'A nice TV', 'TV',
        2, 2, 2, 7);

INSERT INTO "PUBLIC"."LISTING_GALLERY"
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (2, 1),
       (2, 2),
       (2, 3),
       (2, 4),
       (2, 5),
       (2, 6),
       (2, 7);

