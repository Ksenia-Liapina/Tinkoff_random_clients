package ru.tinkof.lyapina.utils;

public class TransliterateUtils {

    private TransliterateUtils(){}

    public static String transliterate(String message){
        char[] abcCyr =   {' ','а','б','в','г','д','е','ё', 'ж','з','и','й','к','л','м','н','о','п','р','с','т','у','ф','х', 'ц','ч', 'ш','щ','ъ','ы','ь','э', 'ю','я','А','Б','В','Г','Д','Е','Ё', 'Ж','З','И','Й','К','Л','М','Н','О','П'
                ,'Р','С','Т','У','Ф','Х', 'Ц', 'Ч','Ш', 'Щ','Ъ','Ы','Ь','Э','Ю','Я','a','b','c','d','e','f','g','h','i'
                ,'j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I'
                ,'J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z', '0', '1', '2', '3', '4', '5',
                           '6', '7', '8', '9'
        };
        String[] abcLat = {" ","a","b","v","g","d","e","e","zh","z","i","y","k","l","m","n",
                           "o","p","r","s","t","u","f","h","ts","ch","sh","sch", "","i", "","e",
                           "ju","ja","A","B","V","G","D","E","E","Zh","Z","I","Y","K","L","M","N","O",
                           "P","R","S","T","U","F","H","Ts","Ch","Sh","Sch", "","I", "","E","Ju","Ja","a","b","c",
                           "d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y",
                           "z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z", "0", "1", "2", "3", "4", "5",
                           "6", "7", "8", "9"};
        StringBuilder builder = new StringBuilder();
        int messageLength = message.length();
        int cyrillicLength = abcCyr.length;
        for (int i = 0; i < messageLength; i++) {
            for (int x = 0; x < cyrillicLength; x++ ) {
                if (message.charAt(i) == abcCyr[x]) {
                    builder.append(abcLat[x]);
                }
            }
        }
        return builder.toString();
    }
}
