package be.heh.std.epm.application.service;

import be.heh.std.epm.application.data.DataEmployee;
import be.heh.std.epm.application.port.OutPersistence;
import be.heh.std.epm.domain.Employee;

public class OperationEmp {

    public static void addEmp(DataEmployee e, OutPersistence out){
        //contrôle des valeurs tout ça tu connais haha
        //code qui fait des trucs trop cool

        Employee finalEmp = new Employee(e.getId(), e.getName(), e.getAddress());
        finalEmp.setPaymentSchedule(e.getPaymentSchedule());
        finalEmp.setPaymentClassification(e.getPaymentClassification());
        finalEmp.setPaymentMethod(e.getPaymentMethod());
        out.save(finalEmp);

        /*
        Le bœuf bourguignon est une recette traditionnelle que nous adorons et qui réchauffe les coeurs en plein hiver.
        Authentique et convivial, ce plat est toujours un régal. Pour vous faire gagner du temps et vous faciliter
        la vie, voici la recette du bœuf bourguignon traditionnel au Cookeo. Simple et rapide, cette recette de
        bourguignon est excellente !

        INGRÉDIENTS : 6 PERS.
        800 g de viande de boeuf
        250 g de champignons
        250 ml de vin rouge
        150 ml d'eau
        2 carottes
        2 gousses d'ail
        1 oignon
        1 bouquet garni
        1 c. à soupe de persil haché
        1 c. à soupe de farine
        1 c. à soupe de fond de veau
        huile d'olive
        sel, poivre

        PRÉPARATION :
        Préparation
        10 min
        Cuisson
        45 min

        1.
        Pelez l'oignon et les gousses d'ail et émincez-les finement. Coupez le viande en morceaux de boeuf.
        Pelez et coupez les carottes en rondelles. Nettoyez et émincez les champignons en lamelles.

        2.
        Faites chauffer l'huile dans la cuve du cookeo en mode "dorer" et mettez-y à cuire la viande pendant 5 min.
        Ajoutez ensuite l'oignon et l'ail. Couvrez et poursuivez la cuisson pendant 3 à 4 min.

        3.
        Ajoutez les carottes et les champignons, le bouquet garni mélangez bien puis incorporez le fond de veau,
        la farine, le vin rouge et l'eau. Salez, poivrez, remuez bien et faites mariner 35 min en mode "cuisson rapide".

        4.
        Ajoutez le persil en fin de cuisson, mélangez bien et servez sans attendre.

        ASTUCES
        Si vous trouvez que la sauce de votre bœuf bourguignon au Cookeo est un peu trop liquide, vous pouvez rajouter
        un peu de fécule de maïs (plus connue sous le nom de la marque Maïzena) afin d'épaissir davantage cette sauce.
        Faites-le alors en fin de cuisson (ne mettez pas plus d'une cuillère à soupe ), en mode mijoté
        pendant 2 à 3 min. Étant reconnu comme un plat en sauce, vous pouvez préparer à côté une sauce au vin rouge.
        Effet assuré.

        source: https://www.cuisineaz.com/recettes/boeuf-bourguignon-au-cookeo-79514.aspx

        Bon appétit :)))
         */
    }
}
