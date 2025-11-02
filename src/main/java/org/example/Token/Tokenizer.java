package org.example.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tokenizer {

    protected String sourceCode;
    protected int current;
    protected int line;
    protected List<Token> tokens;

    public Tokenizer(String sourceCode) {
        this.sourceCode = sourceCode;
        this.current = 0;
        this.line = 1;
        this.tokens = new ArrayList<Token>();
    }

    // map to point to the correct token type
    private static final Map<String, TokenType> keyWords = new HashMap<String, TokenType>();

    static {
        keyWords.put("vibe", TokenType.VIBE);
        keyWords.put("lit", TokenType.LIT);
        keyWords.put("yap", TokenType.YAP);
        keyWords.put("say", TokenType.SAY);
        keyWords.put("if", TokenType.IF);
        keyWords.put("else", TokenType.ELSE);
        keyWords.put("bet", TokenType.BET);
        keyWords.put("right", TokenType.RIGHT);
        keyWords.put("wrong", TokenType.WRONG);
        keyWords.put("fam", TokenType.FAM);
        keyWords.put("spill", TokenType.SPILL);
        keyWords.put("squad", TokenType.SQUAD);
        keyWords.put("+", TokenType.PLUS);
        keyWords.put("-", TokenType.MINUS);
        keyWords.put("*", TokenType.STAR);
        keyWords.put("(" , TokenType.LEFT_PAREN);
        keyWords.put(")" , TokenType.RIGHT_PAREN);
        keyWords.put("=", TokenType.EQUAL);
        keyWords.put(">", TokenType.GREATER_THAN);
        keyWords.put("<", TokenType.LESS_THAN);
        keyWords.put(">=", TokenType.GREATER_THAN_EQUAL);
        keyWords.put("<=", TokenType.LESS_THAN_EQUAL);
        keyWords.put("==", TokenType.EQUAL_EQUAL);
        keyWords.put("!=", TokenType.NOT_EQUAL);
        keyWords.put("{", TokenType.BRACE_LEFT);
        keyWords.put("}", TokenType.BRACE_RIGHT);
        keyWords.put("End of File", TokenType.EOF);
    }

    public List<Token> tokenize() {
        while (!isAtEnd()) {
            scanTokens();
        }
        // Addign an End of file token if there are no more tokens
        Token eofToken = new Token(TokenType.EOF, "End of File");
        tokens.add(eofToken);
        return tokens;
    }

    private void scanTokens() {
        skipWhiteSpaceAndComments();

        if (isAtEnd()) {
            return;
        }

        char c  = advance();


        if (Character.isLetter(c)) {
            current--;
            readIdentiferOrKeyword();
        } else if (Character.isDigit(c)) {
            current--;
            readNumber();
        } else if (c == '"') {
            current--;
            readString();
        }

        // Handling special operands
        if (c == '=' && peek() == '=') {
            tokens.add(new Token(TokenType.EQUAL_EQUAL,"=="));
            advance();
            return;
        } else if (c == '!' && peek() == '=') {
            tokens.add(new Token(TokenType.NOT_EQUAL,"!="));
            advance();
            return;
        } else if (c == '>' && peek() == '=') {
            tokens.add(new Token(TokenType.GREATER_THAN_EQUAL,"=="));
            advance();
            return;
        } else if (c == '<' && peek() == '=') {
            tokens.add(new Token(TokenType.LESS_THAN_EQUAL,"<"));
            advance();
            return;
        }

        // Directly append tokens for single operators

        switch(c) {
            case '+':
                tokens.add(new Token(TokenType.PLUS, "+"));
                break;
            case '-':
                tokens.add(new Token(TokenType.MINUS, "-"));
                break;

            case '*':
                tokens.add(new Token(TokenType.STAR, "*"));
                break;

            case '(':
                tokens.add(new Token(TokenType.LEFT_PAREN, "("));
                break;

            case ')':
                tokens.add(new Token(TokenType.RIGHT_PAREN, ")"));
                break;
            case ',':
                tokens.add(new Token(TokenType.COMMA, ","));
                break;
            case '>':
                tokens.add(new Token(TokenType.GREATER_THAN, ">"));
                break;
            case '<':
                tokens.add(new Token(TokenType.LESS_THAN, "<"));
                break;
            case '=':
                tokens.add(new Token(TokenType.EQUAL, "="));
                break;
            default:
                // Unidentifed keyword

                System.out.println("Unidentified keyword " + c + " at line " + line);

        }






    }

    private void readString() {
        // Reads and recordsa new string and sets the token for it
        StringBuilder foundWord = new StringBuilder();

        // skip the opening quote
        advance();


        while (peek() != '"' && !isAtEnd()) {

            // Handle new lines for tracking

            if (peek() == '\n') {
                line++;
            }

            foundWord.append(advance());


        }

            if (isAtEnd()) {
                System.out.println("Unterminated string at line " + line);
                return;
            }

            advance();

        Token stringToken = new Token(TokenType.STRING_LITERAL, foundWord.toString());
        // add it to the list of tokens
        tokens.add(stringToken);


    }

    private void readIdentiferOrKeyword() {
        StringBuilder word = new StringBuilder();


            while (!isAtEnd() && Character.isLetterOrDigit(peek()) || peek() == '_') {
                word.append(advance());
            }

            // ocnveert the chracters to a string, set the token type and add to token list
            String text = word.toString();
            TokenType type = keyWords.getOrDefault(text, TokenType.IDENTIFIER);

            tokens.add(new Token(type, text));


    }

    private void readNumber() {
        StringBuilder number = new StringBuilder();
        char c = sourceCode.charAt(current);


        if (peek() == '\n') {
            line++;
        }

        while (!isAtEnd() && Character.isDigit(peek())) {
            number.append(advance());
        }
        tokens.add(new Token(TokenType.NUMBER_LITERAL, number.toString()));
        System.out.println("Number: " + number.toString());
    }

    private void skipWhiteSpaceAndComments() {
        while (!isAtEnd()) {
            char c  = sourceCode.charAt(current);

            if (c == ' ' || c == '\r' || c == '\n') {
                current++;
            } else if (c == '\n') {
                line++;
                current++;
            } else if  (c == '/' && peekNext() == '/') {
                // This will be a comment

                while (peek() != '\n' && !isAtEnd()){
                    current++;
                }
            } else {
                break; // stop when there is no more whitespace or comments
            }
        }
    }

    private char peek() {
        if (isAtEnd()) {
            return '\0';
        }
        return sourceCode.charAt(current);
    }

    private boolean isAtEnd() {
        return this.current == this.sourceCode.length();
    }

    private char peekNext() {
        if (current + 1 >= sourceCode.length()) {
            return '\0';
        }
        return sourceCode.charAt(current + 1);
    }

    private char advance() {
        return sourceCode.charAt(current++);
    }

}
