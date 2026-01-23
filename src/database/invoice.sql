/*add invoice*/
create or replace procedure add_invoice(customer_id_in int, out invoice_id_out int)
    language plpgsql
as
$$
begin
    insert into invoice (customer_id, created_at, total_amount)
    values (customer_id_in, current_date, 0)
    returning id into invoice_id_out;
end;
$$;

/*add invoice details*/
create or replace procedure add_invoice_details(invoice_id_in int, product_id_in int, quantity_in int,
                                                price_in decimal(12, 2))
    language plpgsql
as
$$
declare
    current_stock int;
begin
    select stock into current_stock from product p where p.id = product_id_in for update;

    insert into invoice_details (invoice_id, product_id, quantity, unit_price)
    values (invoice_id_in, product_id_in, quantity_in, price_in);
    update invoice
    set total_amount = total_amount + (quantity_in * price_in)
    where id = invoice_id_in;

    update product set stock = stock - quantity_in where id = product_id_in;
end;
$$;

/*get all invoices*/
create or replace function get_all_invoices()
    returns table
            (
                id            int,
                customer_id   int,
                customer_name varchar(255),
                created_at    timestamp,
                total_amount  decimal(12, 2)
            )
as
$$
begin
    return query select i.id, i.customer_id, c.name as customer_name, i.created_at, i.total_amount
                 from invoice i
                          join customer c on i.customer_id = c.id
                 order by i.created_at;
end;
$$ language plpgsql;


/*get_invoice_details_by_customer_name*/
create or replace function get_invoice_details_by_customer_name(customer_name_in varchar(255))
    returns table
            (
                id            int,
                invoice_id    int,
                customer_id   int,
                customer_name varchar(255),
                product_name  varchar(100),
                quantity      int,
                unit_price    decimal(12, 2),
                total_amount  decimal(12, 2)
            )
as
$$
begin
    return query
        select ind.id,
               ind.invoice_id,
               i.customer_id,
               c.name as customer_name,
               p.name as product_name,
               ind.quantity,
               ind.unit_price,
               i.total_amount
        from invoice_details ind
                 join invoice i on ind.invoice_id = i.id
                 join customer c on i.customer_id = c.id
                 join product p on ind.product_id = p.id
        where c.name ilike '%' || customer_name_in || '%'
        order by ind.id;
end;
$$ language plpgsql;

/*get invoice details by invoice date*/
create or replace function get_invoice_details_by_invoice_date(date_in timestamp)
    returns table
            (
                id            int,
                invoice_id    int,
                customer_id   int,
                customer_name varchar(255),
                product_name  varchar(100),
                quantity      int,
                unit_price    decimal(12, 2),
                total_amount  decimal(12, 2)
            )
as
$$
begin
    return query
        select id.id,
               id.invoice_id,
               i.customer_id,
               c.name as customer_name,
               p.name as product_name,
               id.quantity,
               id.unit_price,
               i.total_amount
        from invoice_details id
                 join invoice i on i.id = id.invoice_id
                 join product p on id.product_id = p.id
                 join customer c on i.customer_id = c.id
        where i.created_at = date_in;
end;
$$ language plpgsql;

/*delete customer invoice*/
create or replace procedure delete_customer_invoices(customer_id_in int)
    language plpgsql
as
$$
begin
    delete from invoice_details where invoice_id in (select id from invoice where customer_id = customer_id_in);
    delete from invoice where customer_id = customer_id_in;
end;
$$;

/*get invoice details by id*/
create or replace function get_invoice_details_by_id(invoice_id_in int)
    returns table
            (
                id            int,
                invoice_id    int,
                customer_id   int,
                customer_name varchar(255),
                product_name  varchar(100),
                quantity      int,
                unit_price    decimal(12, 2),
                total_amount  decimal(12, 2)
            )
as
$$
begin
    return query select ind.id,
                        ind.invoice_id,
                        i.customer_id,
                        c.name as customer_name,
                        p.name as product_name,
                        ind.quantity,
                        ind.unit_price,
                        i.total_amount
                 from invoice_details ind
                          join invoice i on i.id = ind.invoice_id
                          join product p on ind.product_id = p.id
                          join customer c on i.customer_id = c.id
                 where ind.invoice_id = invoice_id_in
                 order by i.id;
end;
$$ language plpgsql;
