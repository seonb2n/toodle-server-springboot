package com.example.toodle_server_springboot.util;

import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class TmpPasswordGenerator {

    private final String capitalCaseLetters;
    private final String lowerCaseLetters;
    private final String specialCharacters;
    private final String numbers;
    private final String combinedChars;

    public TmpPasswordGenerator() {
        capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        specialCharacters = "!@#$";
        numbers = "1234567890";
        combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
    }

    public String generatePassword(int length) {
        Random random = new Random();
        char[] password = new char[length];

        password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
        password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
        password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
        password[3] = numbers.charAt(random.nextInt(numbers.length()));

        for (int i = 4; i < length; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }

        return new String(password);
    }
}
