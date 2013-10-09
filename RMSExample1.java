package jedi;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
//import javax.microedition.location.QualifiedCoordinates;
import javax.microedition.rms.*;

public class RmsExample1 extends MIDlet implements CommandListener{


    Display display;
    RecordStore recStore = null;
    int count = 0;
    Command exitCommand = new Command("Exit", Command.EXIT, 0);
    Command newCommand = new Command("New Record",Command.OK,0);
    ChoiceGroup items = new ChoiceGroup("Items",Choice.MULTIPLE);




    public void startApp() 
    {
        if(display == null){
            display = Display.getDisplay(this);
            display.setCurrent(new RmsForm());
            loadStore();
        }
    }


    public void pauseApp() {
    }


    public void destroyApp(boolean unconditional) {


    }


    public void loadStore(){
        byte[] data = new byte[16];
        int recLength;
        items.deleteAll();


        try{
            recStore = RecordStore.openRecordStore("RmsExample1", true);


            for(int recID=1; recID <=recStore.getNumRecords(); recID++){
                recLength = recStore.getRecord(recID, data, 0);
                String item = new String(data, 0,recLength);
                items.append(item, null);


            }


        }catch(Exception e){


        }
    }


    class RmsForm extends Form{
        RmsForm(){
          super("RmsExample");
          addCommand(exitCommand);
          addCommand(newCommand);
          setCommandListener(RmsExample1.this);
           append(items);
        
        }
    }


    public void commandAction(Command c, Displayable d){
        if(c == exitCommand)
        {
            Quit();
        }
        if(c == newCommand)
        {
            try{
                String newItem = "Record #" + recStore.getNextRecordID();


                byte[] bytes = newItem.getBytes();


                recStore.addRecord(bytes, 0, bytes.length);


                items.append(newItem, null);


            }catch(Exception e){


            }
        }
    }
    public void Quit(){
        if(recStore != null){
            try{
                recStore.closeRecordStore();
            }catch(RecordStoreNotOpenException e)
            {
                //
            }catch(Exception e){
                //
            }
        }
        notifyDestroyed();
    }
}
