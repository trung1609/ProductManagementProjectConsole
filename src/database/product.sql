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
    return query select p.id, p.name, p.brand, p.price, p.stock from product p order by p.id;
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
    return query select p.id, p.name, p.brand, p.price, p.stock from product p where p.brand ilike brand_search;
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
                 where p.price between price_search_from and price_search_to
                 order by p.price;
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


