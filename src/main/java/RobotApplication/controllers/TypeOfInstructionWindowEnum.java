package RobotApplication.controllers;

public enum TypeOfInstructionWindowEnum {
    ADDING {
        @Override
        public String toString() {
            return "Dodaj instrukcję:";
        }
    },
    EDITING {
        @Override
        public String toString() {
            return "Edytuj instrukcję:";
        }
    }
}
