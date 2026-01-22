create table admin
(
    id       serial primary key,
    username varchar(50)  not null unique,
    password varchar(255) not null,
    role     varchar(20)  not null default 'STAFF' check (role in ('ADMIN', 'STAFF'))
);


create table product
(
    id    serial primary key,
    name  varchar(100)   not null,
    brand varchar(50)    not null,
    price decimal(12, 2) not null,
    stock int            not null
);


create table customer
(
    id      serial primary key,
    name    varchar(100) not null,
    phone   varchar(20),
    email   varchar(100) unique,
    address varchar(255)
);


create table invoice
(
    id           serial primary key,
    customer_id  int references customer (id),
    created_at   timestamp default current_timestamp,
    total_amount decimal(12, 2) not null
);


create table invoice_details
(
    id         serial primary key,
    invoice_id int references invoice (id),
    product_id int references product (id),
    quantity   int            not null,
    unit_price decimal(12, 2) not null
);

insert into "user" (username, password, role)
values ('admin', '123456', 'ADMIN');

insert into "user" (username, password)
values ('staff', '123456');
