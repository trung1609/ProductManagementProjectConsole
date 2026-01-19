/*revenue by date*/
create or replace procedure revenue_by_date(date_in date, out total_revenue numeric)
    language plpgsql
as
$$
begin
    select coalesce(sum(total_amount), 0) into total_revenue from invoice i where date(created_at) = date_in;
end;
$$;

/*revenue all date*/
create or replace procedure revenue_all_date(out total_revenue numeric)
    language plpgsql
as
$$
begin
    select coalesce(sum(total_amount), 0) into total_revenue from invoice i;
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