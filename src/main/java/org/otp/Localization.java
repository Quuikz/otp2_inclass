package org.otp;

import java.util.*;

public class Localization {
    static Scanner scanner = new Scanner(System.in);

    static String getLanguage(ResourceBundle bundle){
        String sel;
        String language = null;

        while(language == null){
            System.out.println(bundle.getString("selectLanguage"));
            sel = scanner.nextLine().trim();

            if (Set.of("en", "fi", "sv", "ja").contains(sel)){
                language = sel;
            }
        }
        return language;
    }

    static String getCountry(ResourceBundle bundle){
        String sel;
        String country = null;

        while(country == null){
            System.out.println(bundle.getString("selectCountry"));
            sel = scanner.nextLine().trim();

            if (Set.of("gb", "fi", "se", "jp").contains(sel.toLowerCase())){
                country = sel.toUpperCase();
            }
        }
        return country;
    }

    static int setItems(ResourceBundle bundle){
        String sel;
        int items = 0;

        while (items <= 0) {
            System.out.println(bundle.getString("howManyItems"));
            sel = scanner.nextLine().trim();
            try {
                items = Integer.parseInt(sel);
                if (items <= 0) {
                    System.out.println(bundle.getString("errorGreaterThanZero"));
                }
            } catch (NumberFormatException e) {
                System.out.println(bundle.getString("errorWholeNumber"));
            }
        }
        return items;
    }

    static String setName(ResourceBundle bundle){
        String sel;

        String item = null;
        while (item == null) {
            System.out.println(bundle.getString("itemName"));
            sel = scanner.nextLine().trim();
            if (!sel.isEmpty()) {
                item = sel;
            } else {
                System.out.println(bundle.getString("errorItemNameEmpty"));
            }
        }
        return item;
    }

    static int setQuantity(ResourceBundle bundle){
        String sel;

        int quantity = 0;
        while (quantity <= 0) {
            System.out.println(bundle.getString("itemQuantity"));
            sel = scanner.nextLine().trim();
            try {
                quantity = Integer.parseInt(sel);
                if (quantity <= 0) {
                    System.out.println(bundle.getString("errorGreaterThanZero"));
                }
            } catch (NumberFormatException e) {
                System.out.println(bundle.getString("errorWholeNumber"));
            }
        }
        return quantity;
    }

    static double setPrice(ResourceBundle bundle){
        String sel;

        double price = -1;
        while (price < 0) {
            System.out.println(bundle.getString("itemPrice"));
            sel = scanner.nextLine().trim();
            try {
                price = Double.parseDouble(sel);
                if (price < 0) {
                    System.out.println(bundle.getString("errorPriceNonNegative"));
                }
            } catch (NumberFormatException e) {
                System.out.println(bundle.getString("errorValidNumber"));
            }
        }
        return price;
    }

    public static void main(String[] args) {
        Locale locale = Locale.getDefault();
        ResourceBundle bundle = ResourceBundle.getBundle("MessagesBundle" , locale);

        Map<String, Double> shoppingCart = new HashMap<>();
        double cartTotal = 0.00;

        String language = getLanguage(bundle);
        String country = getCountry(bundle);

        locale = Locale.of(language,country);
        bundle = ResourceBundle.getBundle("MessagesBundle" , locale);

        int items = setItems(bundle);

        for(int i = 0; i < items; i++){
            String item = setName(bundle);
            int quantity = setQuantity(bundle);
            double price = setPrice(bundle);
            double total = price * quantity;

            shoppingCart.put((quantity + " " + item), total);
        }

        for(double val : shoppingCart.values()){
            cartTotal += val;
        }

        System.out.printf(bundle.getString("cartTotal")+ "%.2f", cartTotal);
    }
}
