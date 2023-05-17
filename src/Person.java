import java.time.LocalDate;

public class Person {
    private String lastName; private String firstName; private String middleName; private LocalDate birthDate; private long phoneNumber; private String gender;

    public Person(String lastName, String firstName, String middleName, LocalDate birthDate, long phoneNumber, String gender) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public LocalDate getDateOfBirth() {
        return birthDate;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public String getGender() {
        return gender;
    }
}
