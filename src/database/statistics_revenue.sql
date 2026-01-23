/*revenue by date*/
create or replace procedure revenue_by_date(date_in date, out total_revenue numeric)
    language plpgsql
as
$$
begin
    select coalesce(sum(total_amount), 0) into total_revenue from invoice i where date(created_at) = date_in;
end;
$$;

/*revenue each date*/
create or replace function revenue_each_date()
    returns table
            (
                revenue_date  date,
                total_revenue numeric
            )
as
$$
begin
    return query select date(created_at) as revenue_date, coalesce(sum(total_amount), 0) as total_revenue
                 from invoice
                 group by revenue_date
                 order by revenue_date;
end
$$ language plpgsql;

/*revenue by month*/
create or replace procedure revenue_by_month(month_in int, year_in int, out total_revenue numeric)
    language plpgsql
as
$$
begin
    select coalesce(sum(i.total_amount), 0)
    into total_revenue
    from invoice i
    where extract(month from created_at) = month_in
      and extract(year from created_at) = year_in;
end;
$$;

/*revenue each month*/
create or replace function revenue_each_month()
    returns table
            (
                f_month       numeric,
                f_year        numeric,
                total_revenue numeric
            )
as
$$
begin
    return query select extract(month from created_at) as f_month,
                        extract(year from created_at)  as f_year,
                        coalesce(sum(total_amount), 0) as total_revenue
                 from invoice
                 group by f_year, f_month
                 order by f_year, f_month;
end;
$$ language plpgsql;


/*revenue by year*/
create or replace procedure revenue_by_year(year_in int, out total_revenue numeric)
    language plpgsql
as
$$
begin
    select coalesce(sum(i.total_amount), 0)
    into total_revenue
    from invoice i
    where extract(year from created_at) = year_in;
end;
$$;

/*revenue each year*/
create or replace function revenue_each_year()
    returns table
            (
                f_year        numeric,
                total_revenue numeric
            )
as
$$
begin
    return query select extract(year from created_at)  as f_year,
                        coalesce(sum(total_amount), 0) as total_revenue
                 from invoice
                 group by f_year
                 order by f_year;
end;
$$ language plpgsql;

/*get top selling product*/
create or replace function get_top_selling_product(limit_in int)
    returns table
            (
                product_id    int,
                product_name  varchar(255),
                total_sold    bigint,
                total_revenue numeric
            )
as
$$
begin
    return query select p.id                             as product_id,
                        p.name                           as product_name,
                        sum(id.quantity)                 as total_sold,
                        sum(id.quantity * id.unit_price) as total_revenue
                 from invoice_details id
                          join invoice i on id.invoice_id = i.id
                          join product p on id.product_id = p.id
                 where i.created_at >= now() - interval '30 days'
                 group by p.id, p.name
                 order by sum(id.quantity) desc
                 limit limit_in;
end;
$$ language plpgsql;

/*top customer VIP*/
create or replace function get_top_customers_vip(limit_in int)
    returns table
            (
                customer_id   int,
                customer_name varchar(255),
                total_orders  bigint,
                total_spent   numeric
            )
as
$$
begin
    return query select c.id                as customer_id,
                        c.name              as customer_name,
                        count(i.id)         as total_orders,
                        sum(i.total_amount) as total_spent
                 from invoice_details id
                          join invoice i on id.invoice_id = i.id
                          join customer c on i.customer_id = c.id
                 group by c.id, c.name
                 order by sum(i.total_amount) desc, count(i.id) desc
                 limit limit_in;
end;
$$ language plpgsql;