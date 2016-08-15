package org.hello.rest;

/**
 * Created by Администратор on 15.08.2016.
 */
public class User {
    private String login;
    private String password;

    public User(){};
    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
