package myPackage;

public class Passenger {
    String name;
    int age;
    String gender;
    String berthPreference;
    int berthNumber;
    String status;

    public Passenger(String _name, int _age, String _gender, String _berthPreference) {
        name = _name;
        age = _age;
        gender = _gender;
        berthPreference = _berthPreference;
        berthNumber = 0;
        status = "";
    }
}
