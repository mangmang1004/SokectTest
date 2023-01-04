package Project6;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.*;

@SuppressWarnings("serial")
public class JPlaceholderPassword extends JPasswordField {

    private String ph;

    public JPlaceholderPassword(String ph) {
        this.ph = ph;
    }

    public JPlaceholderPassword() {
        this.ph = null;
    }

    /**
     * Gets text, returns placeholder if nothing specified
     */
    @Override
    public String getText() {
        String text = super.getText();

        if (text.trim().length() == 0 && ph != null) {
            text = ph;
        }

        return text;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (super.getText().length() > 0 || ph == null) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(super.getDisabledTextColor());
        g2.drawString(ph, getInsets().left, g.getFontMetrics().getMaxAscent() + getInsets().top);
    }
}