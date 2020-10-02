package services.protocol;

import data.Vector2;
import model.Game;

class ServerProtocolFacade implements IServerProtocol {

    private static ServerProtocolFacade instance = null;

    private final ServerProtocolReader readerProtocol;
    private final ServerProtocolWriter writerProtocol;

    private ServerProtocolFacade() {
        this.readerProtocol = new ServerProtocolReader();
        this.writerProtocol = new ServerProtocolWriter();
    }

    @Override
    public synchronized Command parseMessage(String msg) throws ProtocolException {
        Command cmd = readerProtocol.parseMessage(msg);
        return readerProtocol.parseCommand(cmd);
    }

    @Override
    public synchronized String writeResponse(String msg) {
        return writerProtocol.writeResponse(msg);
    }

    @Override
    public synchronized String writeError(String msg) {
        return writerProtocol.writeError(msg);
    }

    @Override
    public synchronized String writePosition(String name, Vector2 pos, float rotation) {
        return writerProtocol.writePosition(name, pos, rotation);
    }

    @Override
    public synchronized String writeGameInfo(Game game) {
        return writerProtocol.writeGameInfo(game);
    }

    static synchronized ServerProtocolFacade getInstance() {
        if (instance == null) {
            instance = new ServerProtocolFacade();
        }
        return instance;
    }

}
