package handlers;

import dataAccess.DataAccess;
import service.AdminService;
import service.ChessServerException;

public class ClearHandler extends HttpHandler<Void>{

    public ClearHandler(DataAccess dataAccess) {
        super(dataAccess);
    }


    @Override
    protected Class<Void> getRequestClass() {
        return null;
    }


    @Override
    protected Object getResult(DataAccess dataAccess, Void request, String authtoken) throws ChessServerException {
        new AdminService(dataAccess).clear();
        return null;
    }

}
