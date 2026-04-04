import java.util.*;
public class CaesarPlayfair {
    public String CaesarCipher(String plainText, int shift, boolean encrypt) {
        plainText = plainText.toUpperCase();
        StringBuilder cipherText = new StringBuilder();
        for (char ch : plainText.toCharArray()) {
            if (Character.isLetter(ch)) {
                if (encrypt)
                    cipherText.append((char) ('A' + (ch - 'A' + shift) % 26));
                else {
                    int c_shift = ch - 'A' - shift;
                    int c_decrypt = c_shift < 0 ? 26 + c_shift : c_shift;
                    cipherText.append((char) ('A' + c_decrypt));
                }
            }
            else
                cipherText.append(ch);
        }
        return cipherText.toString();
    }

    public String PlayFairCipher(String plainText, String encryptionKey, boolean encrypt) {
        plainText = plainText.toUpperCase();
        plainText = plainText.replace('J', 'I');
        encryptionKey = encryptionKey.toUpperCase();
        encryptionKey = encryptionKey.replace('J', 'I');
        StringBuilder cipherText = new StringBuilder();
        Set<Character> encryptionSet = new LinkedHashSet<>();
        HashMap<Character, Integer> encryptionMatrix = new LinkedHashMap<>();
        HashMap<Integer, Character> reverseEncryptionMatrix = new LinkedHashMap<>();
        int iter = 0;
        for (char ch : encryptionKey.toCharArray())
            if (Character.isLetter(ch))
                if (!encryptionSet.contains(ch)) {
                    encryptionMatrix.put(ch, iter);
                    reverseEncryptionMatrix.put(iter++, ch);
                    encryptionSet.add(ch);
                }
        for (char c = 'A'; c <= 'Z'; c++){
            if (c == 'J') continue;
            else
                if(!encryptionSet.contains(c)) {
                    encryptionMatrix.put(c, iter);
                    reverseEncryptionMatrix.put(iter++, c);
                }
        }
        StringTokenizer st = new StringTokenizer(plainText, " ");
        while (st.hasMoreTokens()){
            List<char[]> pairs = new ArrayList<>();
            String token = st.nextToken();
            int i = 0;
            while(i+1 < token.length()){
                if (token.charAt(i) == token.charAt(i+1)) {
                    pairs.add(new char[]{token.charAt(i), 'X'});
                    i++;
                }
                else {
                    pairs.add(new char[]{token.charAt(i), token.charAt(i + 1)});
                    i+=2;
                }
            }
            if (i == token.length()-1)
                pairs.add(new char[]{token.charAt(i), 'X'});
            StringBuilder finalToken = new StringBuilder();
            for (char[] pair : pairs){
                int pos1 = encryptionMatrix.get(pair[0]);
                int pos2 = encryptionMatrix.get(pair[1]);
                int row1 = pos1/5;
                int row2 = pos2/5;
                int col1 = pos1%5;
                int col2 = pos2%5;
                int nextPos1, nextPos2;
                if (row1 == row2){
                    if (encrypt) {
                        nextPos1 = row1 * 5 + (col1 + 1) % 5;
                        nextPos2 = row2 * 5 + (col2 + 1) % 5;
                    }
                    else{
                        nextPos1 = col1 == 0 ? row1 * 5 + 4 : row1 * 5 + col1 - 1;
                        nextPos2 = col2 == 0 ? row2 * 5 + 4 : row2 * 5 + col2 - 1;
                    }
                    finalToken.append(reverseEncryptionMatrix.get(nextPos1));
                    finalToken.append(reverseEncryptionMatrix.get(nextPos2));
                }
                else if (col1 == col2){
                    if (encrypt) {
                        nextPos1 = ((row1 + 1) % 5) * 5 + col1;
                        nextPos2 = ((row2 + 1) % 5) * 5 + col2;
                    }
                    else{
                        nextPos1 = row1 == 0 ? 20 + col1 : (row1 - 1) * 5 + col1;
                        nextPos2 = row2 == 0 ? 20 + col2 : (row2 - 1) * 5 + col2;
                    }
                    finalToken.append(reverseEncryptionMatrix.get(nextPos1));
                    finalToken.append(reverseEncryptionMatrix.get(nextPos2));
                }
                else{
                    nextPos1 = 5 * row1 + col2;
                    nextPos2 = 5 * row2 + col1;
                    finalToken.append(reverseEncryptionMatrix.get(nextPos1));
                    finalToken.append(reverseEncryptionMatrix.get(nextPos2));
                }
            }
            if(encrypt) {
                cipherText.append(finalToken);
            }
            else{
                int l = finalToken.length();
                for (int j = 1; j < l-1; j++)
                    if (finalToken.charAt(j) == 'X' && finalToken.charAt(j-1) == finalToken.charAt(j+1))
                        finalToken.deleteCharAt(j);
                if (finalToken.charAt(l-1) == 'X')
                    finalToken.deleteCharAt(l-1);
                cipherText.append(finalToken);
            }
            cipherText.append(' ');
        }

        return cipherText.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CaesarPlayfair cp = new CaesarPlayfair();
        System.out.print("Enter the text to be encrypted/decrypted: ");
        String text = sc.nextLine();
        System.out.println("\nEnter 1 for Caesar Cipher\n2 for PlayFair Cipher\n");
        int choice = Integer.parseInt(sc.next());
        switch (choice) {
            case 1:
                System.out.print("\nEnter right shift key: ");
                int shift = sc.nextInt();
                System.out.print("\nPress E to Encrypt, D to Decrypt: ");
                char encrypt = sc.next().charAt(0);
                if (encrypt == 'E' || encrypt == 'e')
                    System.out.println("The Encrypted text is : "+cp.CaesarCipher(text, shift, true)+"\n");
                else if (encrypt == 'D' || encrypt == 'd')
                    System.out.println("The Decrypted text is : "+cp.CaesarCipher(text, shift, false)+"\n");
                else
                    System.out.println("ERROR");
                break;
            case 2:
                System.out.print("\nEnter the Encrytion key: ");
                String encryptionKey = sc.next();
                System.out.print("\nPress E to Encrypt, D to Decrypt: ");
                char flag = sc.next().charAt(0);
                if (flag == 'E' || flag == 'e')
                    System.out.println("The Encrypted text is : "+cp.PlayFairCipher(text, encryptionKey, true)+"\n");
                else if (flag == 'D' || flag == 'd')
                    System.out.println("The Decrypted text is : "+cp.PlayFairCipher(text, encryptionKey, false)+"\n");
                else
                    System.out.println("ERROR");
                break;
            default:
                System.out.println("Invalid choice");
        }
    }
}
