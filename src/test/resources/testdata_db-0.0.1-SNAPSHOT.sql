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
     'cc9b92ae-873f-440e-884e-6d07300b45d6',
     20
    )
;

insert into item_tag_xref
    (tag_name, item_id)
values
    ('pink', 'cc9b92ae-873f-440e-884e-6d07300b45d6'),
    ('black', 'cc9b92ae-873f-440e-884e-6d07300b45d7')
;
