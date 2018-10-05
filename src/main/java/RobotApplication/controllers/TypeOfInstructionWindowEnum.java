package RobotApplication.controllers;

public enum TypeOfInstructionWindowEnum {
    ADDING("Dodaj instrukcję:"), EDITING("Edytuj instrukcję:");

    private String content;

    TypeOfInstructionWindowEnum(String content) {
        this.content = content;
    }

    public String toString() {
        return content;
    }

}
