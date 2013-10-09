package KeyCodesMIDlet;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 * KeyCodesMIDlet
 * @author Ma. Jeanylyn Bas
 * @author Sinag Abello
 * @author Marj Cerrero
 */
 
public class KeyCodesMIDlet extends MIDlet {
    private Display display;
    KeyCodes canvas;


    public void startApp() {
        if (display == null){
            canvas = new KeyCodes(this);
            display = Display.getDisplay(this);

        }
        display.setCurrent(canvas);
    }

    public void pauseApp() {
    }

       public void destroyApp(boolean unconditional) {
    }

    protected void Quit(){
        destroyApp(true);
        notifyDestroyed();
    }
}

class KeyCodes extends Canvas implements CommandListener {
    private Command exitCommand = new Command("Exit", Command.EXIT, 0);
    private KeyCodesMIDlet midlet;
    int code = 0;
    String name = null;

    public KeyCodes(KeyCodesMIDlet midlet) {
        this.midlet = midlet;
        addCommand(exitCommand);
        setCommandListener(this);
    }

    protected void paint(Graphics g) {
        // clear the screen by filling the whole screen w/ white color
        g.setColor(255, 255, 255 );
        g.fillRect(0, 0, getWidth(), getHeight());

        // set the pen to black
        g.setColor(0, 0, 0);
        // and draw the text

        g.drawString("code: " + code + ", name: " + name,
        getWidth()/2, getHeight()/2, Graphics.TOP | Graphics.HCENTER);

    }

    public void commandAction(Command c, Displayable d) {
        if (c == exitCommand){
            midlet.Quit();
        }
    }

    protected void keyPressed(int keyCode){
       this.code = keyCode;
       this.name = getKeyName(keyCode);
        repaint();
    }
}
