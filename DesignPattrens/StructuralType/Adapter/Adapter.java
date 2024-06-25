public class RoundHole {
    private double radius;

    public RoundHole(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public boolean fits(RoundPage page) {
        boolean result;
        result = (this.getRadius() >= page.getRadius());
        return result;
    }
}
//RoundHoles are compatible with RoundPegs.
public class RoundPage {
    private double radius;

    public RoundPage(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }
}



/**
 * SquarePegs are not compatible with RoundHoles (they were implemented by
 * previous development team). But we have to integrate them into our program.
 */
public class SquarePage {
    private double width;

    public SquarePage(double width) {
        this.width = width;
    }

    public double getWidth() {
        return width;
    }

    public double getSquare(){
        double result;
        result = Math.pow(this.width, 2);
        return result;
    }
}

/**
 * Adapter allows fitting square pegs into round holes.
 */
public class SquarePageAdapter extends RoundPage {
    private SquarePage page;

    public SquarePageAdapter(SquarePage page){
        this.page = page;
    }

    @Override
    public double getRadius(){
        double result;
        // Calculate a minimum circle radius, which can fit this peg.
        result = (Math.sqrt(Math.pow((peg.getWidth() / 2), 2) * 2));
        return result;
    }
}

public class Demo(){
    public static void main(String[] args) {
        RoundHole hole = new RoundHole(5);
        RoundPage rpage = new RoundPage(5);

        if (hole.fits(rpage)) {
            System.out.println("Round peg r5 fits round hole r5.");
        }

        SquarePage smallPage = new SquarePage(2);
        SquarePage largePage = new SquarePage(20);
        // hole.fits(smallSqPeg); // Won't compile.

        // Adapter solves the problem.
        SquarePageAdapter smallPageAdapter = new SquarePageAdapter(smallPage);
        SquarePageAdapter largePageAdapter = new SquarePageAdapter(largePage);
        if (hole.fits(smallPageAdapter)) {
            System.out.println("Square peg w2 fits round hole r5.");
        }
        if (!hole.fits(largePageAdapter)) {
            System.out.println("Square peg w20 does not fit into round hole r5.");
        }
    }
}


//Output
// Round peg r5 fits round hole r5.
// Square peg w2 fits round hole r5.
// Square peg w20 does not fit into round hole r5.

// +---------------------+
// |     RoundHole       |
// +---------------------+
// | - radius: double    |
// +---------------------+
// | + RoundHole(double) |
// | + getRadius(): double |
// | + fits(page: RoundPage): boolean |
// +---------------------+

//          RoundHole uses RoundPage          SquarePage is adapted to RoundPage
//                    |                                 |
//                    |                                 |
//                    v                                 v
// +---------------------+                      +-----------------------+
// |     RoundPage       |                      |     SquarePage        |
// +---------------------+                      +-----------------------+
// | - radius: double    |                      | - width: double       |
// +---------------------+                      +-----------------------+
// | + RoundPage(double) |                      | + SquarePage(double)  |
// | + getRadius(): double|                      | + getWidth(): double  |
// +---------------------+                      | + getSquare(): double |
//           inherts                            +-----------------------+
//            |                                            ^
//             |                                           |
//              |                                          |
//               |                           +----------------------------+
//                |                          |     SquarePageAdapter      |
//                 |                         +----------------------------+
//                  |||||||||||||||||||||||| | - page: SquarePage         |
//                                          +----------------------------+
//                                          | + SquarePageAdapter(page: SquarePage) |
//                                          | + getRadius(): double      |
//                                          +----------------------------+
