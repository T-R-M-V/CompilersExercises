package org.example;

public enum Type {
    Error, // T: type of node used in the case where there is an error
    Integer,
    Boolean,
    Double,
    String,
    Char,
    Void, // T: this type is used for 'notype' in documentation
}
