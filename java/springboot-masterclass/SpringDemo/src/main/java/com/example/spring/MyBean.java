package com.example.spring;

public class MyBean {
    public String message;

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MyBean{" +
                "message='" + message + '\'' +
                '}';
    }
}
