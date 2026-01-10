create or replace procedure revenue_by_date(date_in date, out total_revenue numeric)
    language plpgsql
as
$$
begin
    select coalesce(sum(total_amount), 0) into total_revenue from invoice i where date(created_at) = date_in;
end;
$$;

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