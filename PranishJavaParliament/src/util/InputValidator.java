package src.util;

import javax.swing.*;

public class InputValidator {

    //is not empty static
    public static boolean isNotEmpty(String name){
        if(name == null || name.trim().isEmpty()){
            return false;
        }
        return true;
    }

    //is long static
    public static boolean isLong(String populationText){
        try{
            long value = Long.parseLong(populationText);
            if(value < 0){
                return false;
            }
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }

    //is numeric static
    public static boolean isNumeric(String areaText){
        try{
            double value = Double.parseDouble(areaText);
            if(value < 0){
                return false;
            }
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }
    

    public static boolean validateString(String input, String fieldName) {
        if (input == null || input.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, fieldName + " cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (input.length() > 100) {
            JOptionPane.showMessageDialog(null, fieldName + " should not exceed 100 characters.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public static boolean validateLong(String input, String fieldName) {
        try {
            long value = Long.parseLong(input);
            if (value < 0) {
                JOptionPane.showMessageDialog(null, fieldName + " must be a positive number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, fieldName + " must be a valid number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static boolean validateDouble(String input, String fieldName) {
        try {
            double value = Double.parseDouble(input);
            if (value < 0) {
                JOptionPane.showMessageDialog(null, fieldName + " must be a positive number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, fieldName + " must be a valid decimal number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static boolean validateSelection(int selectedRow, String actionName) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a row to " + actionName + ".", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
