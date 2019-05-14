//use termion::raw::RawTerminal;
use termion::raw::IntoRawMode;
use termion::cursor::Goto;
use termion::color::{Fg, Bg, Rgb};

use std::io::Write;
//use std::ops::Add;

use std::io::{ self, stdout };

const W : usize = 13;
const H : usize = 8;

type World = [[u8; W]; H];

fn draw_world <W:Write> (w : &World, stdout: &mut W) -> Result<(), io::Error> {
    for y in 0..H {
        write!(stdout, "{}", Goto(1, y as u16 + 1))?;
        for x in 0..W {
            if w[y][x] != 0u8 {
                write!(stdout, "X")?;
            } else {
                write!(stdout, " ")?;
            }
        }
    }
    Ok(())
}

struct Minion {
    pos : Goto,
    color : Rgb,
    text : String,
}

fn draw_minion <W:Write> (m : &Minion, stdout: &mut W) -> Result<(), io::Error> {
    write!(stdout, "{}{}{}", m.pos, Fg(m.color), m.text)?;
    Ok(())
}

fn main() -> Result<(), io::Error> {
    let mut stdout = stdout().into_raw_mode()?;
    let mut w: World = [[0u8; W]; H];
    w[4][4] = 1;
    for x in 0..W {
        w[0][x] = 1;
        w[H-1][x] = 1;
    }
    for y in 0..H {
        w[y][0] = 1;
        w[y][W-1] = 1;
    }
    draw_world(&w, &mut stdout)?;
    let mc = Minion { pos : Goto(4,4), color : Rgb(255,0,0), text : "@".to_string() };
    draw_minion(&mc, &mut stdout)?;
    Ok(())
}
