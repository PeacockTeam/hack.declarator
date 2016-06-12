package org.peacockteam.similar;


public class Utils {

    public static void step(int i){
        System.out.println("Step: " + i);
    }

    private static String cyr2lat(char ch){
        switch (ch){
            case 'а': return "a";
            case 'б': return "b";
            case 'в': return "v";
            case 'г': return "g";
            case 'д': return "d";
            case 'е': return "e";
            case 'ё': return "jo";
            case 'ж': return "zh";
            case 'з': return "z";
            case 'и': return "i";
            case 'й': return "y";
            case 'к': return "k";
            case 'л': return "l";
            case 'м': return "m";
            case 'н': return "n";
            case 'о': return "o";
            case 'п': return "p";
            case 'р': return "r";
            case 'с': return "s";
            case 'т': return "t";
            case 'у': return "u";
            case 'ф': return "f";
            case 'х': return "kh";
            case 'ц': return "c";
            case 'ч': return "ch";
            case 'ш': return "sh";
            case 'щ': return "shh";
            case 'ъ': return "jhh";
            case 'ы': return "ih";
            case 'ь': return "jh";
            case 'э': return "eh";
            case 'ю': return "ju";
            case 'я': return "ja";
            default: return String.valueOf(ch);
        }
    }

    public static String cyr2lat(String s){
        StringBuilder sb = new StringBuilder(s.length()*2);
        for(char ch: s.toCharArray()){

            String lat = cyr2lat(ch);

            sb.append(lat);
        }
        return sb.toString();
    }

}
