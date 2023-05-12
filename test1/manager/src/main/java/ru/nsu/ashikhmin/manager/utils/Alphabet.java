package ru.nsu.ashikhmin.manager.utils;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Alphabet {
    public final static String ALPHABET = "abcdefghijklmnopqrstuvwxyz0123456789";

    public List<String> asStringList() {
        return ALPHABET.chars().mapToObj(c -> String.valueOf((char) c)).toList();
    }

    public int size() {
        return ALPHABET.length();
    }
}
