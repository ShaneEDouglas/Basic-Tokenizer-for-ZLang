package org.example;

import org.example.Token.Token;
import org.example.Token.Tokenizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        String sourceCodeFilePath = Files.readString(Path.of("src/main/java/org/example/Zlangexamplefile.txt"));
        Tokenizer tokenizer = new Tokenizer(sourceCodeFilePath);
        List<Token> tokens = tokenizer.tokenize();

        for (Token token : tokens) {
            System.out.println(token.getType() + ": " + token.getLexeme());
        }

    }
}