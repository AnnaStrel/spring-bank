package ru.sgu.springbank.dto;

import javax.validation.constraints.Size;

public class UserDto {

    @Size(min = 4, max = 24, message = "Логин должен быть от 4 д 24 символов")
    private String login;

    @Size(min = 6, max = 100, message = "Пароль должен быть от 6 д 100 символов")
    private String password;

    @Size(min = 6, max = 15, message = "Телефон должен быть от 6 д 15 символов")
    private String phone;

    private String address;

    public UserDto() {
    }

    public UserDto(String login, String password, String phone, String address) {
        this.login = login;
        this.password = password;
        this.phone = phone;
        this.address = address;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
