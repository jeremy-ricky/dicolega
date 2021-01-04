package com.getsoft.dicolega;

public class DB {

    public static String[] getData(int id){
        if (id == R.id.action_fr_kil) {
            return getFrKil();
        }else if (id == R.id.action_kil_fr) {
            return getKilFr();
        }else if (id == R.id.action_ang_kil){
            return getAngKil();
        }
        return new String[0];
    }

    public static String[] getFrKil(){
        String[] source = new String[]{

                "a"
                ,"abandon"
                ,"abandoned"
                ,"ability"
                ,"able"
                ,"about"
                ,"above"
                ,"abroad"
                ,"absence"
                ,"absent"
                ,"absolute"
                ,"absolutely"
                ,"ansorb"
                ,"abuse"
                ,"abise"
                ,"academique"
                ,"accent"
                ,"accept"
                ,"acceptable"
                ,"access"
                ,"accident"
                ,"accomodation"
                ,"accompany"

        };

        return source;
    }

    public static String[] getKilFr(){
        String[] source = new String[]{

                "samba"
                ,"misagu"
                ,"misoga"
                ,"buni"
                ,"walya"
                ,"lozé"
                ,"kiziki"
                ,"itwe"
                ,"kalema"
                ,"kiko"
                ,"kyambutu"
                ,"nkensale"
                ,"itwe"
                ,"mosé"
                ,"mwino"
                ,"mbala"
                ,"ikaga"
                ,"loso"
                ,"mpene"
                ,"nkoko"
                ,"kabaga"
                ,"nzogu"
                ,"kwito"

        };

        return source;
    }

    public static String[] getAngKil(){
        String[] source = new String[]{

                "A"
                ,"a la mode"
                ,"A level"
                ,"AGE"
                ,"A (1)"
                ,"a (2)"
                ,"A, D"
                ,"A, a"
                ,"A-bomb"
                ,"A-OK"
                ,"a-peak"
                ,"a-riot"
                ,"a-ripple"
                ,"A-road"
                ,"A.c. (also ac)"
                ,"a.m"
                ,"AA"
                ,"AAA"
                ,"aback"
                ,"abactor"

        };

        return source;
    }

}
