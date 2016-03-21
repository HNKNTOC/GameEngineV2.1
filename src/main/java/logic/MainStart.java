package logic;

import gui.Display;
import gui.JDisplay;
import logic.action.command.ReceiverAction;
import logic.action.command.gObject.ReceiverGObject;
import logic.action.command.gObject.command.CommandMove;
import logic.gameComponents.boardComponents.gBoard.FactoryGBoard;
import logic.gameComponents.boardComponents.gBoard.FactoryGBoardDefault;
import logic.gameComponents.boardComponents.gBoard.GBoard;
import logic.gameComponents.boardComponents.gCell.FactoryGCell;
import logic.gameComponents.boardComponents.gCell.FactoryGCellDefault;
import logic.gameComponents.boardComponents.gCell.GCell;
import logic.gameComponents.boardComponents.gCell.list.HashMapPanelGCell;
import logic.gameComponents.boardComponents.gCell.list.ListPanelGCell;
import logic.gameComponents.boardComponents.gObject.GObject;
import logic.gameComponents.boardComponents.gObject.list.ArrayListGObject;
import logic.gameComponents.boardComponents.gObject.list.ListGObject;
import logic.listeners.keyboard.KeyListenerMainPlayer;
import logic.listeners.mouse.MouseListenerDefault;
import logic.resources.loader.image.ImageLoader;
import logic.resources.manager.ResManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Nikita on 16.02.2016.
 */
public class MainStart {
    public static void main(String[] args) {
        int x = 10;
        int y = 10;


        //Загрузка Image
        ResManager resManager = ResManager.getResManager();
        resManager.putImageIcon(ImageLoader.getImage("null.jpg"));
        resManager.putImageIcon(ImageLoader.getImage("grass.jpg"));
        resManager.putImageIcon(ImageLoader.getImage("Player.png"));

        //Создание и заполнение listGCell
        MouseListenerDefault listener = new MouseListenerDefault();
        FactoryGCell factoryGCell = new FactoryGCellDefault();
        ListPanelGCell<GCell> listGCell = new HashMapPanelGCell(x,y);
        for (GCell gCell : factoryGCell.createGCell(x * y)) {
            gCell.getGPanel().addMouseListener(listener);
            listGCell.add(gCell);
        }

        //Создание и заполнение listGObject
        ListGObject<GObject> listGObject = new ArrayListGObject();




        FactoryGBoard factoryGBoard = new FactoryGBoardDefault();
        GBoard gBoard = factoryGBoard.createGBoard(listGObject,listGCell);


        GObject player = new GObject();
        listGObject.add(player);

        //Создание и настройка команды.
        CommandMove commandMove = new CommandMove(player,gBoard);
        commandMove.setParameters(CommandMove.NAME_PARAMETER_MAX_X_MOVE,gBoard.getListGCell().getMaxX()+"");
        commandMove.setParameters(CommandMove.NAME_PARAMETER_MAX_Y_MOVE,gBoard.getListGCell().getMaxY()+"");

        ReceiverAction receiver = new ReceiverGObject();
        receiver.addActionCommand(commandMove,0);
        player.setReceiverAction(receiver);
        gBoard.getListGCell().get(3, 3).setGObject(player);

        Display display = new JDisplay();

        display.addKeyListener(new KeyListenerMainPlayer(player));
        display.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==32){
                    gBoard.updateGCell();
                }
                if(e.getKeyCode()==66){
                    gBoard.updateGObject();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        display.showPanel(gBoard.getGPanel());
        display.start();
        gBoard.update();
    }
}
