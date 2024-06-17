use std::{iter::Peekable, vec::IntoIter};

use crate::tokens::TokenType;

#[allow(unused)]
#[derive(Debug)]
pub enum ParseError {
    UnexpectedToken,
    MissingClassName,
    MissingAttributeName,
    MissingAttributeType,
}

#[derive(Debug)]
pub enum Visibility {
    Private,
    Protected,
    Public,
}

#[derive(Debug)]
pub struct Class {
    pub name: Box<str>,
    pub attributes: Vec<Attribute>,
    pub methods: Vec<Method>,
}

#[allow(dead_code)]
#[derive(Debug)]
pub struct Attribute {
    pub visibility: Visibility,
    pub name: Box<str>,
    pub typ: Box<str>,
}

#[allow(dead_code)]
#[derive(Debug)]
pub struct Method {
    pub visibility: Visibility,
    pub name: Option<Box<str>>,
    pub return_type: Box<str>,
    pub parameters: Vec<(Box<str>, Box<str>)>,
}

fn parse_parameters(iter: &mut Peekable<IntoIter<TokenType>>) -> Vec<(Box<str>, Box<str>)> {
    let mut parameters = Vec::new();
    while let (Some(TokenType::Value(param_type)), Some(TokenType::Value(param_name))) = (iter.next(), iter.next()) {
        parameters.push((param_type, param_name));
        if Some(TokenType::Comma) == iter.next() {
            continue;
        } else {
            break;
        }
    }

    // skip the content of the methods
    while let Some(token) = iter.next() {
        if TokenType::BraceClose == token {
            break;
        }
    }

    return parameters;
}

pub fn parse(tokens: Vec<TokenType>) -> Class {
    let mut iter = tokens.into_iter().peekable();
    let mut class_name = None;
    let mut attributes = Vec::new();
    let mut methods = Vec::new();

    while let Some(token) = iter.next() {
        match token {
            TokenType::Class => {
                if let Some(TokenType::Value(name)) = iter.next() {
                    class_name = Some(name);
                }
            },
            TokenType::Private | TokenType::Protected | TokenType::Public => {
                if Some(&TokenType::Class) == iter.peek() {
                    continue;
                }

                // skip final and static
                while let Some(param) = iter.peek() {
                    if &TokenType::Final == param || &TokenType::Static == param {
                        iter.next();
                    } else {
                        break;
                    }
                }

                let visibility = match token {
                    TokenType::Private => Visibility::Private,
                    TokenType::Protected => Visibility::Protected,
                    TokenType::Public => Visibility::Public,
                    _ => unreachable!()
                };

                if let Some(TokenType::Value(typ)) = iter.next() {
                    if Some(&TokenType::ParenOpen) != iter.peek() {
                        if let Some(TokenType::Value(name)) = iter.next() {
                            match iter.next() {
                                Some(TokenType::SemiColon) | Some(TokenType::Equal) => {
                                    attributes.push(Attribute {
                                        visibility: visibility,
                                        name: name,
                                        typ: typ,
                                    });
                                },
                                Some(TokenType::ParenOpen) => {
                                    let parameters = parse_parameters(&mut iter);
                                    methods.push(Method {
                                        visibility: visibility,
                                        name: Some(name),
                                        return_type: typ,
                                        parameters: parameters,
                                    });
                                },
                                _ => {},
                            }
                        } else {
                            panic!("Unexpected token");
                        }
                    } else {
                        iter.next(); // skip the open parenthesis
                        let parameters = parse_parameters(&mut iter);
                        methods.push(Method {
                            visibility: visibility,
                            name: None,
                            return_type: typ,
                            parameters: parameters,
                        });
                    }
                }
            }
            _ => {}
        }
    }

    Class {
        name: class_name.unwrap_or(Box::from("unknown")),
        attributes,
        methods,
    }
}

#[cfg(test)]
mod test {
    use std::{fs::File, io::{BufReader, Read}};

    use logos::Logos;

    use crate::tokens::TokenType;
    use super::{parse, Class};

    #[allow(unused)]
    macro_rules! vues {
        () => ("samples/vues/")
    }

    #[allow(unused)]
    macro_rules! monde_ig {
        () => ("samples/mondeIG/")
    }

    #[allow(unused)]
    macro_rules! outils {
        () => ("samples/outils/")
    }

    fn parse_file(path: &str) -> Class {
        let file = File::open(path).expect(format!("could not load '{path}'").as_str());
        let mut buf_reader = BufReader::new(file);
        let mut contents = String::new();
        buf_reader.read_to_string(&mut contents).expect(format!("could not read to string from the file '{path}'").as_str());

        let lexer = TokenType::lexer(&contents)
            .map(|t| t.unwrap_or(TokenType::Unknown))
            .collect::<Vec<TokenType>>();
        let class = parse(lexer);
        return class;
    }

    #[test]
    fn fabrique_identifiant() {
        let class = parse_file(concat!(outils!(), "FabriqueIdentifiant.java"));
        assert_eq!(class.attributes.len(), 2, "number of attributes");
        assert_eq!(class.methods.len(), 2, "number of methods");
    }

    #[test]
    fn taille_composant() {
        let class = parse_file(concat!(outils!(), "TailleComposants.java"));
        assert_eq!(class.attributes.len(), 8, "number of attributes");
        assert_eq!(class.methods.len(), 12, "number of methods");
    }

    #[test]
    fn vue_activite_ig() {
        let class = parse_file(concat!(vues!(), "VueActiviteIG.java"));
        assert_eq!(class.attributes.len(), 1, "number of attributes");
        assert_eq!(class.methods.len(), 2, "number of methods");
    }

    #[test]
    fn vue_arc_ig() {
        let class = parse_file(concat!(vues!(), "VueArcIG.java"));
        assert_eq!(class.attributes.len(), 0, "number of attributes");
        assert_eq!(class.methods.len(), 4, "number of methods");
    }

    #[test]
    fn vue_etape_ig() {
        let class = parse_file(concat!(vues!(), "VueEtapeIG.java"));
        assert_eq!(class.attributes.len(), 3, "number of attributes");
        assert_eq!(class.methods.len(), 1, "number of methods");
    }

    #[test]
    fn vue_menu() {
        let class = parse_file(concat!(vues!(), "VueMenu.java"));
        assert_eq!(class.attributes.len(), 1, "number of attributes");
        assert_eq!(class.methods.len(), 2, "number of methods");
    }

    #[test]
    fn vue_monde_ig() {
        let class = parse_file(concat!(vues!(), "VueMondeIG.java"));
        assert_eq!(class.attributes.len(), 1, "number of attributes");
        assert_eq!(class.methods.len(), 2, "number of methods");
    }

    #[test]
    fn vue_outils() {
        let class = parse_file(concat!(vues!(), "VueOutils.java"));
        assert_eq!(class.attributes.len(), 1, "number of attributes");
        assert_eq!(class.methods.len(), 2, "number of methods");
    }

    #[test]
    fn vue_point_de_controle_ig() {
        let class = parse_file(concat!(vues!(), "VuePointDeControleIG.java"));
        assert_eq!(class.attributes.len(), 2, "number of attributes");
        assert_eq!(class.methods.len(), 2, "number of methods");
    }
}
