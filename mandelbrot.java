
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.BoxLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.image.BufferedImage;

class mandelbrot extends JPanel {

    public static double width = 531;
    public static double height = 472;

    public static mandelbrot m;

    public static double r = 0;
    public static double c = 0;

    public static BufferedImage image = new BufferedImage(531, 472, BufferedImage.TYPE_INT_RGB);

    public static void main(String[] args) {
        JFrame f = new JFrame();

        m = new mandelbrot();

        // f.add(m);
        m.setPreferredSize(new Dimension((int) width, (int) height));

        JPanel container = new JPanel();

        JPanel sideBar = new JPanel();
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));

        JLabel rL = new JLabel("REAL : 0");

        JSlider real = new JSlider(-50, 50);
        real.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                r = ((double) real.getValue()) / 25.0;
                rL.setText("REAL : " + r);
                m.repaint();
            }

        });

        JLabel cL = new JLabel("COMPLEX : 0");

        JSlider complex = new JSlider(-50, 50);
        complex.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                c = ((double) complex.getValue()) / 25.0;
                cL.setText("COMPLEX : " + c);
                m.repaint();
            }

        });

        container.add(m);

        sideBar.add(rL);
        sideBar.add(real);
        sideBar.add(cL);
        sideBar.add(complex);

        container.add(sideBar);

       
        f.add(container);
        f.pack();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setVisible(true);

    }

    static public int Zn(int depth, Complex z, Complex c) {
        z.square();
        z.add(c);

        if (z.abs() > 2 || depth == 0) {
            return depth;
        }

        return Zn(depth - 1, z, c);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D m = (Graphics2D) g;
        int depth = 50;
        double density = 600.0;

        double xScale = 2.7 / density;
        double yScale = 2.4 / density;

      

        m.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (double y = -1.2; y <= 1.2; y += yScale) {
            for (double x = -2.1; x <= 0.6; x += xScale) {

                
                // m.setColor(new Color((int) (255 / depth) * z, (int) (255 / depth) * z, (int) (255 / depth) * z));
                // m.fillRect((int) ((x + 2.1) * width / 2.7), (int) ((y + 1.2) * height / 2.4),
                //         (int) (width / density) + 1, (int) (height / density) + 1);


                int z = Zn(depth, new Complex(r, c), new Complex(x, y));
                image.setRGB((int) ((x + 2.1) * width / 2.7), (int) ((y + 1.2) * height / 2.4), new Color((int) (255 / depth) * z, (int) (255 / depth) * z, (int) (255 / depth) * z).getRGB());
            }
        }

        // BufferedImage i2 = new BufferedImage(531, 472, BufferedImage.TYPE_INT_RGB);
        // for(int x = 1; x < width - 1; x++) {
        //     for(int y = 1; y < height - 1; y++) {
        //         int r = 0;
        //         int gr = 0;
        //         int b = 0;

        //         for (int u = x-1; u< x + 1; u++) {
        //             for (int v = y-1; v< y + 1; v++) {
        //                 Color tmp = new Color(image.getRGB(u, v));

        //                 r += tmp.getRed();
        //                 gr += tmp.getGreen();
        //                 b += tmp.getBlue();
        //             }
        //         }
                
        //         r /= 9;
        //         gr /= 9;
        //         b /= 9;

        //         i2.setRGB(x, y, new Color(r,gr,b).brighter().getRGB());


        //     }
        // }

        // image = i2;


        m.drawImage(image, null, 0, 0);
    }
}
