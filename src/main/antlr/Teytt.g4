grammar Teytt;

program: stat* EOF;

stat: decl | call;

decl: ID '(' ID? (',' ID)* ')' '=' expr;
call: ID '(' expr? ( ',' expr )* ')';

// Parser rules
expr:   expr ('*'|'/') expr      # MulDiv
    |   expr ('+'|'-') expr      # AddSub
    |   INT                      # Int
    |   ID                       # ID
    |   '(' expr ')'             # Parens
    ;

// Lexer rules
INT:    [0-9]+ ;
ID:     [a-zA-Z_][a-zA-Z0-9_]*;
WS:     [ \t\r\n]+ -> skip ;
