package com.fh.test.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Users {

    private Integer id;
    private String name;

    public Users() {
    }

    public Users(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return Objects.equals(id, users.id) &&
                Objects.equals(name, users.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) {

        Map<Users,String> map = new HashMap<>();
        Users user = new Users(11,"王二小");
        map.put(user,"高薪就业");

        System.out.println(map.get(new Users(11,"王二小")));
    }
}
