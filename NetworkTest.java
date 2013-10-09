package networkTest;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Form;
import javax.microedition.midlet.*;

/**
 * @author mark
 */
public class NetworkTest extends MIDlet {

    public String getData(){
        HttpConnection httpConn = null;
        InputStream is = null;
        String dataRead = "";
        String serverUrl = "http://google.com";
        Form resultForm = new Form("Form");
        Display display = Display.getDisplay(this);

        try{

            httpConn = (HttpConnection)Connector.open(serverUrl);
            if((httpConn.getResponseCode() == HttpConnection.HTTP_OK)){
                int length = (int)httpConn.getLength();
                is = httpConn.openInputStream();
                if(length == -1){ //length unknown
                    int chunkSize = 1500;
                    byte [] data = new byte[chunkSize];
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    int dataSizeRead = 0;

                    while((dataSizeRead = is.read(data)) != -1){
                        baos.write(data, 0, dataSizeRead);
                        System.out.println("Data Size Read = "+dataSizeRead);
                    }
                    dataRead = new String(baos.toByteArray());
                    baos.close();
                }else{ //length known
                    DataInputStream dis = new DataInputStream(is);
                    byte [] data = new byte[length];
                    dis.readFully(data);
                    dataRead = new String(data);
                }

                resultForm.deleteAll();
                resultForm.append("Data Read : " + dataRead);
                System.out.println("Data Read : " + dataRead);

            }else{
                resultForm.append("Server returned : " + httpConn.getResponseCode());
            }

            display.setCurrent(resultForm);

        }catch(Exception e){

        }finally{

            try{
                httpConn.close();
            }catch(Throwable t){
                System.out.println(t.toString());
            }

        }
        return dataRead;
    }


    public void startApp() {

        getData();
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }
}
