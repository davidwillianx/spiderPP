
package socket;

import javax.websocket.Session;

public class Participant {

    private int idProjeto;
    private int idPerfil;
    private String hashRoom;
    private Session session;
    
    static final int SM = 1;
    static final int PO = 2;
    static final int TM = 3;

    //Need pass userID/Perfil/HashRoom
    public Participant(String hashRoom,String hashPerfil, Session session) {
        this.setIdProjeto(this.getParticipantDataConnection(hashRoom));
        this.setIdPerfil(this.getParticipantDataConnection(hashPerfil));
        this.setHasRoom(hashRoom);
        this.setSession(session);
        
    }
    
    public void setIdProjeto(int idProjeto)
    {
        this.idProjeto = idProjeto;
    }
    
    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }

    public void setHasRoom(String hashRoom)
    {
        this.hashRoom = hashRoom;
    }
    
    public void setSession(Session session)
    {
        this.session = session;
    }
    
    public int getIdProjeto()
    {
        return this.idProjeto;
    }
    
    public int getIdPerfil() {
        return this.idPerfil;
    }

    public String getHashRoom()
    {
       return this.hashRoom;
    }

    public boolean  isScrumMaster()
    {
        return this.idPerfil == this.SM;
    }
    
    public Session getSession()
    {
        return this.session;
    }

    private int getParticipantDataConnection(String hashConnection) {
        String data = hashConnection.substring(hashConnection.length() -1);
        return Integer.parseInt(data);
    }
}
