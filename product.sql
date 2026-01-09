create table admin
(
    id       serial primary key,
    username varchar(50)  not null unique,
    password varchar(255) not null
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

insert into admin (username, password)
values ('admin', '123456');


/* Check login */
create or replace procedure check_login(user_in varchar(50), password_in varchar(255), out is_true boolean)
    language plpgsql
as
$$
begin
    select exists(select 1
                  from admin a
                  where username = user_in
                    and password = password_in)
    into is_true;
end;
$$;

/* Check product name */
create or replace function check_product_name(in name_in varchar(100))
    returns boolean
as
$$
begin
    return exists(select 1
                  from product p
                  where p.name = name_in);
end;
$$ language plpgsql;

/* Add product*/
create or replace procedure add_product(
    name_in varchar(100), brand_in varchar(50), price_in decimal(12, 2), stock_in int)
    language plpgsql
as
$$
begin
    insert into product (name, brand, price, stock) values (name_in, brand_in, price_in, stock_in);
end;
$$;

/* Display product*/
create or replace function get_all_product()
    returns table
            (
                id    int,
                name  varchar(100),
                brand varchar(50),
                price decimal(12, 2),
                stock int
            )
as
$$
begin
    return query select p.id, p.name, p.brand, p.price, p.stock from product p;
end;
$$ language plpgsql;

/* find_product_by_id */
create or replace function find_product_by_id(id_in int)
    returns table
            (
                id    int,
                name  varchar(100),
                brand varchar(50),
                price decimal(12, 2),
                stock int
            )
as
$$
begin
    return query select p.id, p.name, p.brand, p.price, p.stock from product p where p.id = id_in;
end;
$$ language plpgsql;

/* update product*/
create or replace procedure update_product(id_in int, name_in varchar(100), brand_in varchar(50),
                                           price_in decimal(12, 2), stock_in int)
    language plpgsql
as
$$
begin
    update product p
    set name  = name_in,
        brand = brand_in,
        price = price_in,
        stock = stock_in
    where p.id = id_in;
end;
$$;

/* delete product */
create or replace procedure delete_product(id_in int)
    language plpgsql
as
$$
begin
    delete from product p where p.id = id_in;
end;
$$;

/* search by brand*/
create or replace function search_product_by_brand(brand_in varchar(50))
    returns table
            (
                id    int,
                name  varchar(100),
                brand varchar(50),
                price decimal(12, 2),
                stock int
            )
as
$$
declare
    brand_search varchar(50);
begin
    brand_search := concat('%', brand_in, '%');
    return query select p.id, p.name, p.brand, p.price, p.stock from product p where p.brand like brand_search;
end;
$$
    language plpgsql;

/* search by price */
create or replace function search_product_by_price(price_search_from decimal(12, 2),
                                                   price_search_to decimal(12, 2))
    returns table
            (
                id    int,
                name  varchar(100),
                brand varchar(50),
                price decimal(12, 2),
                stock int
            )
as
$$
begin
    return query select p.id, p.name, p.brand, p.price, p.stock
                 from product p
                 where p.price between price_search_from and price_search_to;
end;
$$
    language plpgsql;

/*search by stock */
create or replace function search_product_by_stock(stock_in int)
    returns table
            (
                id    int,
                name  varchar(100),
                brand varchar(50),
                price decimal(12, 2),
                stock int
            )
as
$$
begin
    return query select p.id, p.name, p.brand, p.price, p.stock from product p where p.stock = stock_in;
end;
$$
    language plpgsql;


