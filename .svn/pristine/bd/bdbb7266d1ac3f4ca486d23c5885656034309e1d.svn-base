// implimentatino of lamport clock

public class LamportClock {
    public int val; 

    // sets new lampeort clock on recieve data
    public void recieve(int l2){
        val = Math.max(this.val , l2) + 1;
    }
    // increment val when sending data
    public void send(){
        val++;
    }

    public LamportClock(){
        val = 0;
    }
}
