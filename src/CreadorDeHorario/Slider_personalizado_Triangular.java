package CreadorDeHorario;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

/**
 * 
 * @Origen https://stackoverflow.com/questions/62609789/change-thumb-and-color-of-jslider
 *
 */

public class Slider_personalizado_Triangular extends BasicSliderUI {

	
    private static final int TRACK_HEIGHT = 8;
    private static final int TRACK_WIDTH = 8;
    private static final int TRACK_ARC = 5;
    private final Dimension THUMB_SIZE = new Dimension(20, 20);
    private final RoundRectangle2D.Float trackShape = new RoundRectangle2D.Float();

	
      
    
    
    public Slider_personalizado_Triangular(final JSlider b) {
        super(b);
    }

    @Override
    protected void calculateTrackRect() {
        super.calculateTrackRect();
        if (isHorizontal()) {
            trackRect.y = trackRect.y + (trackRect.height - TRACK_HEIGHT) / 2;
            trackRect.height = TRACK_HEIGHT;
        } else {
            trackRect.x = trackRect.x + (trackRect.width - TRACK_WIDTH) / 2;
            trackRect.width = TRACK_WIDTH;
        }
        trackShape.setRoundRect(trackRect.x, trackRect.y, trackRect.width, trackRect.height, TRACK_ARC, TRACK_ARC);
    }

    @Override
    protected void calculateThumbLocation() {
        super.calculateThumbLocation();
        if (isHorizontal()) {
            thumbRect.y = trackRect.y + (trackRect.height - thumbRect.height) / 2;
        } else {
            thumbRect.x = trackRect.x + (trackRect.width - thumbRect.width) / 2;
        }
    }

    @Override
    protected Dimension getThumbSize() {
        return THUMB_SIZE;
    }

    private boolean isHorizontal() {
        return slider.getOrientation() == JSlider.HORIZONTAL;
    }

    @Override
    public void paint(final Graphics g, final JComponent c) {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paint(g, c);
    }

    @Override
    public void paintTrack(final Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Shape clip = g2.getClip();

        boolean horizontal = isHorizontal();
        boolean inverted = slider.getInverted();

        // Paint shadow.
        g2.setColor(new Color(170, 170 ,170));
        g2.fill(trackShape);

        // Paint track background.
        g2.setColor(new Color(200, 200 ,200));
        g2.setClip(trackShape);
        trackShape.y += 1;
        g2.fill(trackShape);
        trackShape.y = trackRect.y;

        g2.setClip(clip);

        // Paint selected track.
        if (horizontal) {
            boolean ltr = slider.getComponentOrientation().isLeftToRight();
            if (ltr) inverted = !inverted;
            int thumbPos = thumbRect.x + thumbRect.width / 2;
            if (inverted) {
                g2.clipRect(0, 0, thumbPos, slider.getHeight());
            } else {
                g2.clipRect(thumbPos, 0, slider.getWidth() - thumbPos, slider.getHeight());
            }

        } else {
            int thumbPos = thumbRect.y + thumbRect.height / 2;
            if (inverted) {
                g2.clipRect(0, 0, slider.getHeight(), thumbPos);
            } else {
                g2.clipRect(0, thumbPos, slider.getWidth(), slider.getHeight() - thumbPos);
            }
        }
        g2.setColor(new Color(198,197,228,255));
        g2.fill(trackShape);
        g2.setClip(clip);
    }

    @Override
    public void paintThumb(final Graphics g) {
//        g.setColor(new Color(246, 146, 36));
//        g.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
        
        Graphics2D g2d = (Graphics2D) g; 
        int x1 = thumbRect.x + 2; 
        int x2 = thumbRect.x + thumbRect.width - 2; 
        int width = thumbRect.width - 4; 
        int topY = thumbRect.y + thumbRect.height / 2 - thumbRect.width / 3; 
        GeneralPath shape = new GeneralPath(GeneralPath.WIND_EVEN_ODD); 
        shape.moveTo(x1, topY ); 
        shape.lineTo(x2, topY ); 
        shape.lineTo((x1 + x2)/2, topY + width); 
        shape.closePath(); 
        g2d.setPaint(new Color(81, 83, 186)); 
        g2d.fill(shape); 
        Stroke old = g2d.getStroke(); 
        g2d.setStroke(new BasicStroke(2f)); 
        g2d.setPaint(new Color(131, 127, 211)); 
        g2d.draw(shape); 
        g2d.setStroke(old); 
    }

    @Override
    public void paintFocus(final Graphics g) {}
}
