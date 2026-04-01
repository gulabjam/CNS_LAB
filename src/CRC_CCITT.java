import java.util.*;

public class CRC_CCITT {

    public int MSB(int n){
        for (int i = 31; i >= 0; i--)
            if ((n & (1 << i)) != 0)
                return i;
        return -1;
    }

    public int checkSum(int data, int poly){
        int dividend = data;
        while (true){
            int msbDividend = MSB(dividend);
            int msbPoly = MSB(poly);
            if (msbDividend >= msbPoly)
                dividend = dividend ^ (poly << (msbDividend-msbPoly));
            else
                break;
        }
        return dividend;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter data: ");
        CRC_CCITT crc = new CRC_CCITT();
        try {
            int data = Integer.parseInt(sc.next(), 2);
            System.out.print("Generating polynomial: ");
            int polynomial = Integer.parseInt(sc.next(), 2);
            int dividend = data << crc.MSB(polynomial);
            System.out.println("Modified data is: "+Integer.toBinaryString(dividend));
            int checkSum = crc.checkSum(dividend, polynomial);
            System.out.println("Checksum is: "+Integer.toBinaryString(checkSum));
            int codeWord = data << (crc.MSB(checkSum)+1) | checkSum;
            System.out.println("Final code word is: "+Integer.toBinaryString(codeWord));
            System.out.print("Test error detection 0(yes) 1(no) ?: ");
            int op = sc.nextInt();
            if (op == 1) {
                if(crc.checkSum(codeWord, polynomial)==0)
                    System.out.println("No error detected") ;
                else System.out.println("Error detected") ;
            }
            else if (op == 0){
                System.out.print("Enter the position where error is to be inserted: ") ;
                int pos = sc.nextInt();
                int errData = codeWord ^ (1 << (crc.MSB(codeWord)-pos+1));
                System.out.println("Erroneous data: "+Integer.toBinaryString(errData));
                if(crc.checkSum(errData, polynomial)==0)
                    System.out.println("No error detected") ;
                else System.out.println("Error detected") ;
            }
        }catch(NumberFormatException e){
            System.out.println("ERROR ! Enter string of 0's and 1's");
        }

    }
}
