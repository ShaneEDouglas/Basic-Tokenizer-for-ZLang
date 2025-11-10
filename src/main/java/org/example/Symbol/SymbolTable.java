package org.example.Symbol;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

    Map<String, Symbol> symbolTable = new HashMap<String, Symbol>();


    public void  declare(String name, String type, String value) {
        symbolTable.put(name, new Symbol(name, type, value));
    }

    public void assign(String name , String newValue) {
        // Update the current vlaue of a variable after being declared

        Symbol symbol = symbolTable.get(name);
        symbol.setValue(newValue);

    }

    public Symbol contains(String name) {
        if (symbolTable.containsKey(name)) {
            return symbolTable.get(name);
        } else{
            return null;
        }

    }

    public void printInitial() {
        System.out.println("=============================================================");
        System.out.println("                  Initial Symbol Table                       ");
        System.out.println("=============================================================");

        for (Symbol symbol : symbolTable.values()) {
            System.out.println(symbol.toString());
        }
    }

    public void printUpdated() {
        System.out.println("=============================================================");
        System.out.println("                  Updated Symbol Table                       ");
        System.out.println("=============================================================");

        for (Symbol symbol : symbolTable.values()) {
            System.out.println(symbol.toString());
        }
    }

}
