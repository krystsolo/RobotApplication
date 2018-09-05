package RobotApplication.controllers;

public enum TypeOfProgramActionEnum {
DELETE {
    @Override
    public String toString() {
        return "Usuwanie programu";
    }
},
CREATE {
    @Override
    public String toString() {
        return "Tworzenie nowego programu";
    }
},
LOAD {
    @Override
    public String toString() {
        return "Ładowanie programu";
    }
},
SAVE_AS {
    @Override
    public String toString() {
        return "Zapisz program jako";
    }
},
CHANGE_NAME {
    @Override
    public String toString() {
        return "Zmiana nazwy programu";
    }
},

}
