package com.base.admin.application.service.user;

public interface IUserDirectory {
    public Object search(String username);

    public Boolean check(String username, String password);
}
