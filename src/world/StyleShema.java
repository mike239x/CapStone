package world;

import java.util.Properties;
import world.particles.*;
import java.util.Random;

/**
 * Particle style shema. Used in map and applied to all particles.
 * Than in used in World and applied to player.
 *
 * @author mike239x
 */
public class StyleShema {

    short[][] particlesColors;
    char[][] particlesPictures;
    String name;
    Random random;
    short focusColor;

    public StyleShema(String name, short focusColor,
            short[][] particlesColors, char[][] particlesPictures) {
        this.name = name;
        this.focusColor = focusColor;
        this.particlesColors = particlesColors;
        this.particlesPictures = particlesPictures;
    }

    public void apply(Particle p) {
        if (p == null) {
            return;
        }
        int type = p.getType().getIndex();
        int cLen = particlesColors[type].length;
        int pLen = particlesPictures[type].length;
        p.setMainColor(particlesColors[type][random.nextInt(cLen)]);
        p.setPicture(particlesPictures[type][random.nextInt(pLen)]);
    }

    private static StyleShema hell() {
        short[][] colors = {
            //wall
            {1, 1, 9},
            //enter
            {9},
            //exit
            {9},
            //trap
            {196, 202, 160, 124, 88, 52},
            //moving trap
            {196, 202, 160},
            //key
            {3, 11, 214},
            //person
            {3}
        };
        char[][] pics = {
            //wall
            {'▓', '█', '█'}, //, '▒' ?
            //enter
            {'҈'},//'҈', '□'
            //exit
            {'∏'},//'۝', '̿', '∏'
            //trap
            {'ⱡ', '̈'},//'当', 'ⱡ', '￼', '͏', '̈'
            //moving trap
            {'¤', '۞'},//'¤','Ὠ', '۞', '罗'
            //key
            {'ไ', 'ᄁ', 'ჭ', 'ꜚ', 'ꜛ', 'ꜜ', '', '˽', '˾', '؂', (char) 1763},
            //person
            {'i'}
        };
        return new StyleShema("hell", (short)2, colors, pics);
    }
    public static final StyleShema HELL = hell();

    private static StyleShema forest() {
        short[][] colors = {
            //wall
            {2, 10, 34, 70},
            //enter
            {10},
            //exit
            {10},
            //trap
            {190, 22, 40, 113},
            //moving trap
            {190, 22, 40, 113},
            //key
            {3, 214, 100},
            //person
            {58}
        };
        char[][] pics = {
            //wall
            {'ⱡ', 'Ŧ', 'ф', 'φ'},
            //enter
            {'҈'},//'҈', '□'
            //exit
            {'∩'},//'۝', '̿', '∏'
            //trap
            {'̈'},//'当', 'ⱡ', '￼', '͏', '̈'
            //moving trap
            {'¤', '罗', '当'},//'¤','Ὠ', '۞', '罗'
            //key
            {'ไ', 'ᄁ', 'ჭ', 'ꜚ', 'ꜛ', 'ꜜ', '', '˽', '˾', '؂', (char) 1763},
            //person
            {'i'}
        };
        return new StyleShema("forest", (short)1, colors, pics);
    }
    public static final StyleShema FOREST = forest();

    private static StyleShema matrix() {
        short[][] colors = {
            //wall
            {22, 28, 34, 40, 10},
            //enter
            {3},
            //exit
            {154},
            //trap
            {59, 22, 28, 34, 40, 10},
            //moving trap
            {1, 196, 88},
            //key
            {59},
            //person
            {208}
        };
        char[] wallPics = new char[94];
        for (int i = 0; i < 94; i++) {
            wallPics[i] = (char)(i+33);
        }
        char[][] pics = {
            //wall
            wallPics,
            //enter
            {'0'},//'҈', '□'
            //exit
            {' '},//'۝', '̿', '∏'
            //trap
            {'f', 't', '#'},//'当', 'ⱡ', '￼', '͏', '̈'
            //moving trap
            {'Q', 'O', 'o', '*'},//'¤','Ὠ', '۞', '罗'
            //key
            {'k', '\''},
            //person
            {'i'}
        };
        return new StyleShema("matrix", (short)26, colors, pics);
    }
    public static final StyleShema MATRIX = matrix();

    private static StyleShema standart() {
        short[][] colors = {
            //wall
            {7, 7, 231},
            //enter
            {7},
            //exit
            {7},
            //trap
            {196, 202, 160, 124, 88, 52},
            //moving trap
            {196, 202, 160},
            //key
            {3, 11, 214},
            //person
            {3}
        };
        char[][] pics = {
            //wall
            {'▓', '█', '█'}, //, '▒' ?
            //enter
            {'҈', '□'},//'҈', '□'
            //exit
            {'۝', '̿', '∏'},
            //trap
            {'当', 'ⱡ', '￼', '͏', '̈'},//'当', 'ⱡ', '￼', '͏', '̈'
            //moving trap
            {'¤', 'Ὠ', '۞', '罗'},//'¤','Ὠ', '۞', '罗'
            //key
            {'ไ', 'ᄁ', 'ჭ', 'ꜚ', 'ꜛ', 'ꜜ', '', '˽', '˾', '؂', (char) 1763},
            //person
            {'i'}
        };
        return new StyleShema("standart", (short)2, colors, pics);
    }
    public static final StyleShema STANDART = standart();

    private static StyleShema neon() {
        short[][] colors = {
            //wall
            {196, 21, 201, 46, 51, 226},
            //enter
            {18},
            //exit
            {88},
            //trap
            {196},
            //moving trap
            {196},
            //key
            {226},
            //person
            {208}
        };
        char[][] pics = {
            //wall
            {'█'}, //, '▒' ?
            //enter
            {'□'},//'҈', '□'
            //exit
            {'̿'},
            //trap
            {'当', 'ⱡ', '̈'},//'当', 'ⱡ', '￼', '͏', '̈'
            //moving trap
            {'¤', '۞'},//'¤','Ὠ', '۞', '罗'
            //key
            {'ᄁ', 'ჭ', 'ꜚ', 'ꜛ', 'ꜜ', '˽', '˾'},
            //person
            {'i'}
        };
        return new StyleShema("neon", (short)2, colors, pics);
    }
    public static final StyleShema NEON = neon();

    public void save(Properties p) {
        p.setProperty("style", name);
    }

    public void load(Properties p) {
        String s = p.getProperty("style");
        StyleShema shema = getShema(s);
        this.name = shema.name;
        this.particlesColors = shema.particlesColors;
        this.particlesPictures = shema.particlesPictures;
        this.focusColor = shema.focusColor;
        random = new Random();
    }

    private StyleShema getShema(String s) {
        String[] styles = {
            "standart",
            "standart",
            "standart",
            "standart",
            "hell",
            "hell",
            "forest",
            "forest",
            "matrix",
            "neon"};
        if (s == null) {
            Random r = new Random();
            return getShema(styles[r.nextInt(styles.length)]);
        }
        switch (s) {
            case "hell":
                return HELL;
            case "forest":
                return FOREST;
            case "matrix":
                return MATRIX;
            case "standart":
                return STANDART;
            case "neon":
                return NEON;
            default:
                Random r = new Random();
                return getShema(styles[r.nextInt(styles.length)]);
        }
    }

}
