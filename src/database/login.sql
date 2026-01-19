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

create or replace procedure check_ex_username(user_in varchar(50), out is_true boolean)
    language plpgsql
as
$$
begin
    select exists(select 1
                  from admin a
                  where username = user_in)
    into is_true;
end;
$$;
create or replace function get_pass_by_username(user_in varchar(50))
    returns varchar(255)
    language plpgsql
as
$$
declare
    pass_out varchar(255);
begin
    select password
    into pass_out
    from admin a
    where username = user_in;
    return pass_out;
end;
$$;