package com.project.taeil.mantomen.firebase;

import java.util.HashMap;
import java.util.Map;

public class ChatModel {

    public Map<String,Boolean> users = new HashMap<>(); //내아이디

    public Map<String,Comment> comments = new HashMap<>();

    public static class Comment{
        public String uid;
        public String message;
        public Object timestamp;
    }
}