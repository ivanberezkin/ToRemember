package team.dream.ServerSide;

public class SingleServerProtocol {
    private static final SingleServerProtocol serverProtocol = new SingleServerProtocol();

   private SingleServerProtocol(){

   }

   public void processInputFromClient(Object inputFromClient){

   }

   public static SingleServerProtocol getServerProtocol(){
       return serverProtocol;
   }

}
