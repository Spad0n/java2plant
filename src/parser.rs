use crate::tokens::TokenType;

#[derive(Debug)]
pub struct Class {
    pub name: String,
    pub attributes: Vec<Attribute>,
    pub methods: Vec<Method>,
}

#[allow(dead_code)]
#[derive(Debug)]
pub struct Attribute {
    visibility: Option<String>,
    name: String,
    typ: String,
}

#[allow(dead_code)]
#[derive(Debug)]
pub struct Method {
    visibility: Option<String>,
    name: String,
    return_type: String,
    parameters: Vec<(String, String)>,
}

// TODO: prendre en compte le token 'static'
// TODO: prendre en compte les constructeurs
// TODO: prendre en compte les valeurs par defaut des attributs
// TODO: prendre en compte instance d'un attributs
pub fn parse(tokens: Vec<TokenType>) -> Class {
    let mut iter = tokens.into_iter().peekable();
    let mut class_name = String::new();
    let mut attributes = Vec::new();
    let mut methods = Vec::new();

    while let Some(token) = iter.next() {
        match token {
            TokenType::Class => {
                if let Some(TokenType::Value(name)) = iter.next() {
                    class_name = name;
                }
            },
            TokenType::Private | TokenType::Protected | TokenType::Public => {
                if Some(&TokenType::Class) == iter.peek() {
                    continue;
                }

                let visibility = match token {
                    TokenType::Private => "private",
                    TokenType::Protected => "protected",
                    TokenType::Public => "public",
                    _ => unreachable!()
                }.to_string();

                if let (Some(TokenType::Value(typ)), Some(TokenType::Value(name))) = (iter.next(), iter.next()) {
                    match iter.next() {
                        Some(TokenType::SemiColon) => {
                            attributes.push(Attribute {
                                visibility: Some(visibility),
                                name: name,
                                typ: typ,
                            });
                        },
                        Some(TokenType::ParenOpen) => {
                            let mut parameters = Vec::new();
                            while let (Some(TokenType::Value(param_type)), Some(TokenType::Value(param_name))) = (iter.next(), iter.next()) {
                                parameters.push((param_type, param_name));
                                if Some(TokenType::Comma) == iter.next() {
                                    continue;
                                } else {
                                    break;
                                }
                            }
                            methods.push(Method {
                                visibility: Some(visibility),
                                name: name,
                                return_type: typ,
                                parameters: parameters,
                            });

                            while let Some(token) = iter.next() {
                                if let TokenType::BraceClose = token {
                                    break;
                                }
                            }
                        },
                        _ => {},
                    }
                }
            }
            _ => {}
        }
    }

    Class {
        name: class_name,
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
        () => ("example/vues/")
    }

    #[allow(unused)]
    macro_rules! monde_ig {
        () => ("example/mondeIG/")
    }

    #[allow(unused)]
    macro_rules! outils {
        () => ("example/outils/")
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
        assert_eq!(class.attributes.len(), 0);
        assert_eq!(class.methods.len(), 1);
    }

    #[test]
    fn taille_composant() {
        let class = parse_file(concat!(outils!(), "TailleComposants.java"));
        assert_eq!(class.attributes.len(), 7);
        assert_eq!(class.methods.len(), 10);
    }

    #[test]
    fn vue_activite_ig() {
        let class = parse_file(concat!(vues!(), "VueActiviteIG.java"));
        assert_eq!(class.attributes.len(), 1);
        assert_eq!(class.methods.len(), 1);
    }

    #[test]
    fn vue_arc_ig() {
        let class = parse_file(concat!(vues!(), "VueArcIG.java"));
        assert_eq!(class.attributes.len(), 0);
        assert_eq!(class.methods.len(), 3);
    }

    #[test]
    fn vue_etape_ig() {
        let class = parse_file(concat!(vues!(), "VueEtapeIG.java"));
        assert_eq!(class.attributes.len(), 3);
        assert_eq!(class.methods.len(), 0);
    }

    #[test]
    fn vue_menu() {
        let class = parse_file(concat!(vues!(), "VueMenu.java"));
        assert_eq!(class.attributes.len(), 1);
        assert_eq!(class.methods.len(), 1);
    }

    #[test]
    fn vue_monde_ig() {
        let class = parse_file(concat!(vues!(), "VueMondeIG.java"));
        assert_eq!(class.attributes.len(), 1);
        assert_eq!(class.methods.len(), 1);
    }

    #[test]
    fn vue_outils() {
        let class = parse_file(concat!(vues!(), "VueOutils.java"));
        assert_eq!(class.attributes.len(), 1);
        assert_eq!(class.methods.len(), 1);
    }

    #[test]
    fn vue_point_de_controle_ig() {
        let class = parse_file(concat!(vues!(), "VuePointDeControleIG.java"));
        assert_eq!(class.attributes.len(), 2);
        assert_eq!(class.methods.len(), 1);
    }
}
