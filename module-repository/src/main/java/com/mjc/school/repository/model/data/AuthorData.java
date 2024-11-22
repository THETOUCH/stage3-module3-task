package com.mjc.school.repository.model.data;

import com.mjc.school.repository.utils.*;
import com.mjc.school.repository.model.AuthorModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mjc.school.repository.utils.Utils.getRandomContentByFilePath;
import static com.mjc.school.repository.utils.Utils.getRandomDate;

public class AuthorData {
    private static final String AUTHORS_FILE_NAME = "authors";
    private static AuthorData authorData;
    private List<AuthorModel> authorList;

    private AuthorData() {
        init();
    }

    public static AuthorData getAuthorData() {
        if (authorData == null) {
            authorData = new AuthorData();
        }
        return authorData;
    }

    private void init() {
        authorList = new ArrayList<>();
        Random random = new Random();
        for (long i = 1; i <= 20; i++) {
            LocalDateTime date = getRandomDate();
            authorList.add(new AuthorModel(
                    i,
                    getRandomContentByFilePath(AUTHORS_FILE_NAME),
                    date,
                    date
            ));
        }
    }

    public List<AuthorModel> getAuthorList() {
        return authorList;
    }
}
