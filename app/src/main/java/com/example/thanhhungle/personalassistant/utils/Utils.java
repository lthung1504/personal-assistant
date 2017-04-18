package com.example.thanhhungle.personalassistant.utils;

import com.example.thanhhungle.personalassistant.model.ApiUser;
import com.example.thanhhungle.personalassistant.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thanhhung.le on 18/4/17.
 */

public class Utils {
    public static List<ApiUser> getApiUserList() {

        List<ApiUser> apiUserList = new ArrayList<>();

        ApiUser apiUserOne = new ApiUser();
        apiUserOne.firstname = "Amit";
        apiUserOne.lastname = "Shekhar";
        apiUserList.add(apiUserOne);

        ApiUser apiUserTwo = new ApiUser();
        apiUserTwo.firstname = "Manish";
        apiUserTwo.lastname = "Kumar";
        apiUserList.add(apiUserTwo);

        ApiUser apiUserThree = new ApiUser();
        apiUserThree.firstname = "Sumit";
        apiUserThree.lastname = "Kumar";
        apiUserList.add(apiUserThree);

        return apiUserList;
    }

    public static List<User> convertApiUserListToUserList(List<ApiUser> apiUserList) {

        List<User> userList = new ArrayList<>();

        for (ApiUser apiUser : apiUserList) {
            User user = new User();
            user.firstname = apiUser.firstname;
            user.lastname = apiUser.lastname;
            userList.add(user);
        }

        return userList;
    }

}
