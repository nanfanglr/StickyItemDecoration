package me.free.example.edittext_1;


import android.util.Log;

public class DemoBean {

    private int id;
    private String name;
    private String age;

    public DemoBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
        Log.i("TAG", "-------------->DemoBean.setAge=" + age);
    }

    @Override
    public String toString() {
        return "DemoBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
