package com.example.gogo;

public class Getters {

    public Getters() {
    }

        String name, email, user, password;

        public Getters(String email, String name, String user, String password)
        {
            this.email = email;
            this.password = password;
            this.user = user;
            this.name = name;
        }


        public String getEmail() {
            return email;
        }

        public String getUser() {
            return user;
        }

        public String getPassword() {
            return password;
        }
        public String getName() {
            return name;
        }

    }



