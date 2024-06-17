use std::fmt;

use crate::parser::{Class, Visibility};

impl fmt::Display for Visibility {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        match self {
            Visibility::Public => write!(f, "public"),
            Visibility::Private => write!(f, "private"),
            Visibility::Protected => write!(f, "protected"),
        }
    }
}

impl fmt::Display for Class {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        writeln!(f, "class {} {{", self.name)?;
        for attribute in &self.attributes {
            writeln!(f, "    {} {} {}", attribute.visibility, attribute.typ, attribute.name)?;
        }
        for method in &self.methods {
            if let Some(name) = &method.name {
                write!(f, "    {} {} {}(", method.visibility, method.return_type, name)?;
            } else {
                write!(f, "    {} {}(", method.visibility, method.return_type)?;
            }
            let length = method.parameters.len();
            for (i, (param_type, param_name)) in method.parameters.iter().enumerate() {
                if i < length - 1 {
                    write!(f, "{} {}, ", param_type, param_name)?;
                } else {
                    write!(f, "{} {}", param_type, param_name)?;
                }
            }
            writeln!(f, ")")?;
        }
        writeln!(f, "}}")
    }
}

mod test {
    use std::{fs::File, io::{BufReader, Read}};

    use logos::Logos;

    use crate::{parser::{parse, Class}, tokens::TokenType};

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
    fn print_arc_ig() {
        let class = parse_file("samples/mondeIG/ArcIG.java");

        let expected = r#"
class ArcIG {
    private PointDeControleIG pdc1
    private PointDeControleIG pdc2
    private boolean affiche
    private boolean selectionne
    public ArcIG(PointDeControleIG pdc1, PointDeControleIG pdc2)
    public PointDeControleIG getDebut()
    public PointDeControleIG getFin()
    public EtapeIG getEtapeDebut()
    public EtapeIG getEtapeFin()
    public void setSelectionne()
    public boolean getSelectionne()
    public boolean getAffiche()
    public void setAffiche(boolean b)
    public void setEstSelectionne()
    public boolean aCommeDebut(EtapeIG etapeFin)
    public boolean aCommeFin(EtapeIG etapeDebut)
}
"#;

        assert_eq!(format!("\n{}", class), expected, "result string do not match with the expected one");
    }

    #[test]
    fn print_activite_ig() {
        let class = parse_file("samples/mondeIG/ActiviteIG.java");
        let expected = r#"
class ActiviteIG {
    public ActiviteIG(String nom, int larg, int haut)
}
"#;

        assert_eq!(format!("\n{}", class), expected, "result string do not match with the expecred one");
    }
}
