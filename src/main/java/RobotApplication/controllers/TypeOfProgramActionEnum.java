package RobotApplication.controllers;

public enum TypeOfProgramActionEnum {

    DELETE("Usuwanie programu"),
    CREATE("Tworzenie nowego programu"),
    LOAD("Ładowanie programu"),
    SAVE_AS("Zapisz program jako"),
    CHANGE_NAME("Zmiana nazwy programu");

    private String content;

    TypeOfProgramActionEnum(String content) {
        this.content = content;
    }

    public String toString(){
        return content;
    }

}
