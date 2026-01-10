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

    if current_stock is null then
        raise exception 'Sản phẩm không tồn tại';
    end if;

    if (quantity_in > current_stock) then
        raise exception 'Không đủ hàng trong kho';
    end if;

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
                id           int,
                customer_id  int,
                created_at   timestamp,
                total_amount decimal(12, 2)
            )
as
$$
begin
    return query select i.id, i.customer_id, i.created_at, i.total_amount from invoice i order by i.created_at desc;
end;
$$ language plpgsql;


/*find invoice by id*/
create or replace function find_invoice_by_id(invoice_id_in int)
    returns table
            (
                id           int,
                customer_id  int,
                created_at   timestamp,
                total_amount decimal(12, 2)
            )
as
$$
begin
    return query select i.id, i.customer_id, i.created_at, i.total_amount from invoice i where i.id = invoice_id_in;
end;
$$ language plpgsql;

/*get_invoice_details_by_customer_name*/
create or replace function get_invoice_details_by_customer_name(customer_name_in varchar(255))
    returns table
            (
                id         int,
                invoice_id int,
                product_id int,
                quantity   int,
                unit_price decimal(12, 2)
            )
as
$$
begin
    return query
        select id.id, id.invoice_id, id.product_id, id.quantity, id.unit_price
        from invoice_details id
                 join invoice i on id.invoice_id = i.id
                 join customer c on i.customer_id = c.id
        where c.name like customer_name_in;
end;
$$ language plpgsql;

create or replace function get_invoice_details_by_invoice_date(date_in timestamp)
    returns table
            (
                id         int,
                invoice_id int,
                product_id int,
                quantity   int,
                unit_price decimal(12, 2)
            )
as
$$
begin
    return query
        select id.id, id.invoice_id, id.product_id, id.quantity, id.unit_price
        from invoice_details id
                 join invoice i on i.id = id.invoice_id
        where i.created_at = date_in;
end;
$$ language plpgsql;