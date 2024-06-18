insert into item
    (id, name, description, photo_url, type, owner, price)
values
    (
     'cc9b92ae-873f-440e-884e-6d07300b45d6',
     'Samsung Galaxy Phone',
     'A Samsung Android OS mobile phone.',
     null,
     'phone',
     '1233ba9c-8403-492e-9f4c-5b3b5464c3af',
     50
    ),
    (
     'cc9b92ae-873f-440e-884e-6d07300b45d7',
     'iPhone SE',
     'An Apple iOS mobile phone',
     null,
     'phone',
     '04a7bf6a-f317-4eea-9311-fa4d711f860f',
     20
    ),
    (
     'cc9b92ae-873f-440e-884e-6d07300b45d8',
     'KA-BAR Space-Bar Knife',
     'KA-BAR Space-Bar Knife - United States Space Force utility knife.',
     'https://www.kabar.com/img/1313sf-hero-1.jpg',
     'knife',
     '04a7bf6a-f317-4eea-9311-fa4d711f860d',
     40
    )
;

insert into item_tag_xref
    (tag_name, item_id)
values
    ('pink', 'cc9b92ae-873f-440e-884e-6d07300b45d6'),
    ('black', 'cc9b92ae-873f-440e-884e-6d07300b45d7'),
    ('blue', 'cc9b92ae-873f-440e-884e-6d07300b45d8'),
    ('gray', 'cc9b92ae-873f-440e-884e-6d07300b45d8')
;
