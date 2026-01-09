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

create or replace procedure add_invoice_details(invoice_id_in int, product_id_in int, quantity_in int, price_in decimal(12,2))
    language plpgsql
as
$$
begin
    insert into invoice_details (invoice_id, product_id, quantity, unit_price)
    values (invoice_id_in, product_id_in, quantity_in, price_in);
    update invoice
    set total_amount = total_amount + (quantity_in * price_in)
    where id = invoice_id_in;
end;
$$;