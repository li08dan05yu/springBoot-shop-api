package com.fh.test.model;

import java.util.Collections;
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
        Users user1 = new Users(12,"王二小");
        Users user2 = new Users(13,"王二小");
        Users user3 = new Users(14,"王二小");
        map.put(user,"啊啊啊啊啊");
        map.put(user1,"哈哈哈哈");
        map.put(user2,"嘿嘿嘿嘿嘿");
        map.put(user3,"呵呵呵呵呵");

        Map<Users, String> usersStringMap = Collections.synchronizedMap(map);

        System.out.println(map.get(new Users(11,"王二小")));
        System.out.println(map.get(user1));
        System.out.println(map.get(user2));
        System.out.println(map.get(user3));
        System.out.println(usersStringMap.get(new Users(11,"王二小")));
        System.out.println(usersStringMap.get(user1));
        System.out.println(usersStringMap.get(user2));
        System.out.println(usersStringMap.get(user3));

    }
}
