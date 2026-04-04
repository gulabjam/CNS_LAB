import java.util.*;

public class vigenere {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the text to be encrypted/decrypted: ");
        String text = sc.nextLine();
        text = text.toUpperCase().replaceAll(" ", "");
        System.out.print("\nEnter the key phrase: ");
        String phrase = sc.next();
        System.out.print("\nEnter E to Encrypt, D to Decrypt: ");
        char E = sc.next().charAt(0);
        StringBuilder cipherText = new StringBuilder();
        int i = 0;
        StringBuilder keyStream = new StringBuilder();
        while (i < text.length()) {
            if (text.length() - i >= phrase.length()) {
                keyStream.append(phrase);
                i += phrase.length();
            }
            else{
                    keyStream.append(phrase, 0, text.length() - i);
                    i = text.length();
            }
        }
        if (E == 'E' || E == 'e')
            for (int j = 0; j < text.length(); j++)
                cipherText.append((char) ('A' +  (text.charAt(j) + keyStream.charAt(j) - 2 * 'A') % 26));
        else if (E == 'D' || E == 'd')
            for (int j = 0; j < text.length(); j++){
                int temp = text.charAt(j) - keyStream.charAt(j);
                int shiftVal = temp < 0 ? 26 + temp :  temp;
                cipherText.append((char) ('A' + shiftVal));
            }
        else
            System.out.println("Invalid character");
        System.out.println(cipherText.toString());
    }
}

