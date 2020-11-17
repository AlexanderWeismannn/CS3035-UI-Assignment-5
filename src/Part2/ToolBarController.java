package Part2;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;

public class ToolBarController {

    @FXML ToggleGroup colorGroup;
    @FXML ToggleGroup actionGroup;

    @FXML ToggleButton redButton;
    @FXML ToggleButton greenButton;
    @FXML ToggleButton blueButton;
    @FXML ToggleButton createButton;
    @FXML ToggleButton deleteButton;

    public ToolBarController() {
        initialize();
    }

    public void redButtonClicked() { colorButtonClicked("RED"); }

    public void greenButtonClicked() { colorButtonClicked("GREEN"); }

    public void blueButtonClicked() { colorButtonClicked("BLUE"); }

    public void createButtonClicked() {
//        System.out.println("Creation mode");
        Main.getScene().setCursor(Cursor.DEFAULT);
        Main.interactionModel.setIsCreating();
        createButton.setSelected(true);
        deleteButton.setSelected(false);
        actionGroup.selectToggle(createButton);
    }
    public void deleteButtonClicked() {
//        System.out.println("Deletion mode");
        Main.interactionModel.setIsDeleting();
        createButton.setSelected(false);
        deleteButton.setSelected(true);
        actionGroup.selectToggle(deleteButton);
    }

    private void colorButtonClicked(String colour){
        switch(colour) {
            case    "RED" :    redButton.setSelected(true);
                System.out.println("Red Pressed");
                greenButton.setSelected(false);
                blueButton.setSelected(false);
                redButton.setTextFill(Color.BLACK);
                colorGroup.selectToggle(redButton);
                Main.interactionModel.setSelectedColor(Color.valueOf("#ff3333"));
                Main.interactionModel.redButtonSelected();
                Main.interactionModel.unSelectGreenButton();
                Main.interactionModel.unSelectBlueButton();
                break;

            case    "GREEN" :    greenButton.setSelected(true);
                System.out.println("Green Pressed");
                redButton.setSelected(false);
                blueButton.setSelected(false);
                greenButton.setTextFill(Color.BLACK);
                colorGroup.selectToggle(greenButton);
                Main.interactionModel.setSelectedColor(Color.GREEN);
                Main.interactionModel.greenButtonSelected();
                Main.interactionModel.unSelectRedButton();
                Main.interactionModel.unSelectBlueButton();
                break;

            case    "BLUE" :    blueButton.setSelected(true);
                System.out.println("Blue Pressed");
                redButton.setSelected(false);
                greenButton.setSelected(false);
                blueButton.setTextFill(Color.BLACK);
                colorGroup.selectToggle(blueButton);
                Main.interactionModel.setSelectedColor(Color.BLUE);
                Main.interactionModel.blueButtonSelected();
                Main.interactionModel.unSelectRedButton();
                Main.interactionModel.unSelectGreenButton();
                break;

            default : break;
        }
    }

    public void initialize() {
        colorGroup = new ToggleGroup();
        actionGroup = new ToggleGroup();
        redButton = new ToggleButton();
        greenButton = new ToggleButton();
        blueButton = new ToggleButton();
        createButton = new ToggleButton();
        deleteButton = new ToggleButton();
        redButton.setToggleGroup(colorGroup);
        greenButton.setToggleGroup(colorGroup);
        blueButton.setToggleGroup(colorGroup);
        createButton.setToggleGroup(actionGroup);
        deleteButton.setToggleGroup(actionGroup);
    }
}
