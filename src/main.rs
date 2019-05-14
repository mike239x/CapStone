//use termion::raw::RawTerminal;
use termion::raw::IntoRawMode;
use termion::cursor::Goto;
use termion::color::Fg;
//use termion::color::Bg;
use termion::color::Rgb;

use termion::clear;

use std::io::Write;
//use std::ops::Add;

use std::io::{ self, stdout };

use rand::Rng;

const W : usize = 96;
const H : usize = 32;

type World = [[u8; W]; H];

fn infill(w: &World) -> f32 {
    let mut n = 0;
    for l in w {
        for x in l.iter() {
            if x == &0u8 {
                n += 1;
            }
        }
    }
    1.0 - (n as f32) / (H as f32) / (W as f32)
}

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
    fn generate() -> World {
        let mut rng = rand::thread_rng();
        let mut w: World = [[1u8; W]; H];
        let (sx, sy): (usize, usize) = (rng.gen_range(W/3, 2*W/3), rng.gen_range(H/3,2*H/3));
        enum Direction {
            W, A, S, D,
        }
        fn random_dir() -> Direction {
            let mut rng = rand::thread_rng();
            match rng.gen_range(0,4) {
                0 => Direction::W,
                1 => Direction::A,
                2 => Direction::S,
                _ => Direction::D,
            }
        }
        let mut d: Direction = random_dir();
        let p = 0.2; // probability to change directions
        while infill(&w) > 0.40 {
            let (mut x, mut y) = (sx, sy);
            loop {
                w[y][x] = 0;
                if x == 0 || x == W-1 || y == 0 || y == H-1 {
                    break;
                }
                match d {
                    Direction::W => y=y-1,
                    Direction::A => x=x-1,
                    Direction::S => y=y+1,
                    Direction::D => x=x+1,
                };
                if rng.gen::<f32>() < p {
                    d = random_dir();
                };
            }
        }
        w
    }
    let w: World = generate();
    //let mc = Minion { pos : Goto(4,4), color : Rgb(255,0,0), text : "@".to_string() };
    loop {
        write!(stdout, "{}", clear::All)?;
        draw_world(&w, &mut stdout)?;
        //draw_minion(&mc, &mut stdout)?;
        write!(stdout, "{}", Goto(1, H as u16 +1))?;
        // TODO make a proper loop
        break;
    }
    write!(stdout, "{}", infill(&w))?;
    //write!(stdout, "{} {}", termion::terminal_size()?.0, termion::terminal_size()?.1)?;
    Ok(())
}
