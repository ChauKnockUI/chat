/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author ADMIN
 */
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Processing extends Thread {

    Chatter chatter;

    public Processing(Chatter chatter) {
        this.chatter = chatter;
    }

    @Override
    public void run() {
        String action;
        while (true) {
            try {
                action = chatter.getIn().readUTF();
                if (action.equals("Cancel")) {
                    chatter.getSocket().close();
                    Server.list.remove(chatter);
                    for (Chatter c : Server.list) {
                        c.getOut().writeUTF("remove");
                        c.getOut().writeUTF(chatter.getName());
                    }
                    break;
                } else {
                    switch (action) {
                        case "Send":
                            String mess = chatter.getIn().readUTF();

                            String name = chatter.getIn().readUTF();

                            for (Chatter c : Server.list) {
                                c.getOut().writeUTF("Send");
                                c.getOut().writeUTF("[" + name + "] " + mess);
                            }
                            break;
                        case "SendPM":
                            String mess1 = chatter.getIn().readUTF();
                            int j = chatter.getIn().readInt();
                            String name1 = chatter.getIn().readUTF();

                            Server.list.get(j).getOut().writeUTF("SendPM");
                            Server.list.get(j).getOut().writeUTF("[PM from " + name1 + "] " + mess1);

                            break;
                        default:
                            throw new AssertionError();
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Processing.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

