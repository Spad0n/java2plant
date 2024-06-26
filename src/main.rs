use logos::Logos;
use std::{fs::File, io::{BufReader, Read}};

mod tokens;
mod parser;
mod uml;
use tokens::TokenType;

const FILE_PATH: &str = "samples/mondeIG/ArcIG.java";

fn main() -> Result<(), String> {
    let file = File::open(FILE_PATH).map_err(|err| {
        return format!("Could not open '{FILE_PATH}': {err}");
    })?;
    let mut buf_reader = BufReader::new(file);
    let mut contents = String::new();
    buf_reader.read_to_string(&mut contents).map_err(|err| {
        return format!("Could not read the content of the file '{FILE_PATH}': {err}");
    })?;

    let lexer = TokenType::lexer(&contents)
        .map(|t| t.unwrap_or(TokenType::Unknown))
        .collect::<Vec<TokenType>>();

    let class = parser::parse(lexer);

    println!("{}", class);

    Ok(())
}
