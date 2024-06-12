use logos::Logos;

#[derive(Debug, Logos, PartialEq)]
#[logos(skip r"[ \t\n\f]+", skip r"\/\/.*")]
pub enum TokenType {
    #[token("class")]
    Class,

    #[token("static")]
    Static,

    #[token("import")]
    Import,

    #[token("package")]
    Package,

    #[token("public")]
    Public,

    #[token("abstract")]
    Abstract,

    #[token("private")]
    Private,

    #[token("protected")]
    Protected,

    #[token("extends")]
    Extends,

    #[token("implements")]
    Implements,

    #[token("(")]
    ParenOpen,

    #[token(")")]
    ParenClose,

    #[token("{")]
    BraceOpen,

    #[token("}")]
    BraceClose,

    #[token(",")]
    Comma,

    #[token(";")]
    SemiColon,

    #[token("=")]
    Equal,

    #[token("!")]
    Not,

    #[regex("[a-zA-Z.1-9<>]+", |lex| lex.slice().to_owned())]
    Value(String),

    Unknown
}
