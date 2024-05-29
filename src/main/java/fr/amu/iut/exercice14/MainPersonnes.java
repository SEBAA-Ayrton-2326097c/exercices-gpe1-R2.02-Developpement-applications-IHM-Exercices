package fr.amu.iut.exercice14;

import javafx.beans.Observable;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;



@SuppressWarnings("Duplicates")
public class MainPersonnes {




    private static SimpleListProperty<Personne> lesPersonnes;
    private static IntegerProperty ageMoyen;
    private static IntegerProperty nbParisiens;
    private static IntegerBinding calculAgeMoyen;
    private static IntegerBinding calculnbParisiens;

    public static void main(String[] args) {

        lesPersonnes = new SimpleListProperty<>(FXCollections.observableArrayList(personne -> new Observable[]{
                personne.getAge(), personne.getVilleDeNaissance()
        }));
        ageMoyen = new SimpleIntegerProperty(0);
        nbParisiens = new SimpleIntegerProperty(0);



        calculnbParisiens = new IntegerBinding() {
            {
                this.bind(lesPersonnes);
            }
            protected int computeValue() {
                int cmp = 0;
                if (lesPersonnes.getSize() == 0) {
                    return 0;
                }
                for (Personne p : lesPersonnes) {
                    if (p.getVilleDeNaissance().get()=="Paris") ++ cmp;
                }
                return cmp;
            }
        };


        nbParisiens.bind(calculnbParisiens);
        calculAgeMoyen = new IntegerBinding() {
            {
                this.bind(lesPersonnes);
            }



            @Override
            protected int computeValue() {
                int moy = 0;
                int cmp = 0;
                if (lesPersonnes.getSize() == 0) {
                    return 0;
                }
                for (Personne p : lesPersonnes) {
                    moy += p.getAge().get();
                    ++cmp;
                }
                return moy/cmp;
            }
        };
        ageMoyen.bind(calculAgeMoyen);
        question1();
        question2();
    }


    public static void question1() {
        System.out.println("1 - L'âge moyen est de " + ageMoyen.getValue() + " ans");
        Personne pierre = new Personne("Pierre", 20);
        lesPersonnes.add(pierre);
        System.out.println("2 - L'âge moyen est de " + ageMoyen.getValue() + " ans");
        Personne paul = new Personne("Paul", 40);
        lesPersonnes.add(paul);
        System.out.println("3 - L'âge moyen est de " + ageMoyen.getValue() + " ans");
        Personne jacques = new Personne("Jacques", 60);
        lesPersonnes.add(jacques);
        System.out.println("4 - L'âge moyen est de " + ageMoyen.getValue() + " ans");
        paul.setAge(100);
        System.out.println("5 - L'âge moyen est de " + ageMoyen.getValue() + " ans");
        lesPersonnes.remove(paul);
        System.out.println("6 - L'âge moyen est de " + ageMoyen.getValue() + " ans");
    }

    public static void question2() {
        System.out.println("Il y a " + nbParisiens.getValue() + " parisiens");
        lesPersonnes.get(0).setVilleDeNaissance("Marseille");
        System.out.println("Il y a " + nbParisiens.getValue() + " parisiens");
        lesPersonnes.get(1).setVilleDeNaissance("Montpellier");
        System.out.println("Il y a " + nbParisiens.getValue() + " parisien");
        for (Personne p : lesPersonnes)
            p.setVilleDeNaissance("Paris");
        System.out.println("Il y a " + nbParisiens.getValue() + " parisiens");
    }

}
