


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

    public static int width = 1200;
    public static int height = 900;

    public static mandelbrot m;

    public static double r = 0;
    public static double c = 0;


    public static double xOff = 0.0;
    public static double yOff = 0.0;
    public static double zoom = 1;

    public static BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

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



        JLabel xL = new JLabel("X OFFSET : 2");

        JSlider x_slider = new JSlider(-3000, 3000);
        x_slider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                xOff = ((double) x_slider.getValue()/1000);
                xL.setText("X OFFSET : " + xOff);
                m.repaint();
            }

        });

         JLabel yL = new JLabel("Y OFFSET : 0");

        JSlider y_slider = new JSlider(-2000, 2000);
        y_slider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                yOff = ((double) y_slider.getValue() / 1000);
                yL.setText("Y OFFSET : " + yOff);
                m.repaint();
            }

        });


        JLabel zL = new JLabel("Zoom : 200");

        JSlider z_slider = new JSlider(1, 100000);
        z_slider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                zoom = ((double) z_slider.getValue() / 100000);
                zL.setText("ZOOM : " + zoom);
                m.repaint();
            }

        });

        container.add(m);

        sideBar.add(rL);
        sideBar.add(real);
        sideBar.add(cL);
        sideBar.add(complex);

        sideBar.add(xL);
        sideBar.add(x_slider);

        sideBar.add(yL);
        sideBar.add(y_slider);

        sideBar.add(zL);
        sideBar.add(z_slider);

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


    // x range: (-2.1 to 0.6)
    // y range: (-1.2 to 1.2)

    //Real ranges: x: -2 to 1, y: -1 to 1

    
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D m = (Graphics2D) g;
        int depth = 150;


        double xmin = -3.0 * zoom;
        double xmax = 1.0 * zoom;
        double ymin = -1.0 * zoom;
        double ymax = 1.0 * zoom;

        System.out.println(xmin + " " + xmax);

        double xStep = (Math.abs(xmin) + Math.abs(xmax)) / width ;
        double yStep = (Math.abs(ymin) + Math.abs(ymax)) / height ;

        xmin -= xOff;
        xmax -= xOff;

        ymin -= yOff;
        ymax -= yOff;

        m.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        

        // for (int i =0; i < width;i++){
        //     for (int j = 0; j < height; j++) {

        //         int z = Zn(depth, new Complex(r, c), new Complex((Double.valueOf(i) - xOff)/ zoom, (Double.valueOf(j) - yOff) / zoom));
        //         image.setRGB(i, j, new Color(z,z,z).getRGB());
        //     }

        // }


        for (int i =0; i < width;i++){
            for (int j = 0; j < height; j++) {

                int z = Zn(depth, new Complex(r, c), new Complex(xmin  + Double.valueOf(i) * xStep, ymin + Double.valueOf(j) * yStep));
                image.setRGB(i, j, new Color(z,z,z).getRGB());

                if( xmin + Double.valueOf(i) * xStep + 0.01 >= 0 &&  xmin + Double.valueOf(i) * xStep  <= 0){
                    if( ymin + Double.valueOf(j) * yStep + 0.01 >= 0 &&  ymin + Double.valueOf(j) * yStep  <= 0){
                        image.setRGB(i, j, new Color(255,z,z).getRGB());
                    }

                }

            }

        }


        m.drawImage(image, null, 0, 0);
    }
}
