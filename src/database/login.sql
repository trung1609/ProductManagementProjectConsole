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