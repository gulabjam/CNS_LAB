import java.util.*;
import java.util.concurrent.TimeUnit;

public class LeakyBucket {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Output Rate: ");
        int rate = sc.nextInt();
        System.out.print("Bucket size: ");
        int size = sc.nextInt();
        int timer = 0;
        int packet;
        int bucketSpaceOccupied = 0;

        while (true){
            if (timer == 0){
                System.out.print("Incoming packet: ");
                packet = sc.nextInt();
                if ((packet+bucketSpaceOccupied) > size){
                    System.out.println("Bucket overflow---------Packet discarded");
                }
                else {
                    bucketSpaceOccupied += packet;
                    System.out.println("Transmission left: " + bucketSpaceOccupied);
                    System.out.print("Next packet will come at (Enter -1 to quit) ");
                    timer = sc.nextInt();
                }
            }
            if (timer == -1) {
                System.out.println("Packets transmitted successfully");
                break;
            }
            for (; timer > 0; timer--){
                bucketSpaceOccupied = Math.max(bucketSpaceOccupied - 2, 0);
                System.out.println("Time left "+timer+" ----------------- No packets to transmit!! Transmitted");
                if(bucketSpaceOccupied > 0)
                    System.out.println("Bytes Remaining: "+bucketSpaceOccupied);
                else
                    System.out.println("Bytes Remaining: 0!!  Waiting for next packet to arrive");
                try{
                    TimeUnit.SECONDS.sleep(1);
                }catch(InterruptedException e){
                    System.out.println(e.getMessage());
                }
            }
            System.out.println("------------------------------------------------------------------------------------");

        }
    }

}
