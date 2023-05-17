import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите данные в следующем формате через пробелы: " +
                "ФИО(полностью) дата_рождения(дд.мм.гггг) номер_телефона(только цифры) пол(f или m) \n");
        String input = scanner.nextLine();
        String[] data = input.split(" ");

        if (data.length != 6) {
            System.out.println("Ошибка ввода! Введено " + data.length + " значений вместо 6");
            return;
        }

        String lastName = data[0];
        String firstName = data[1];
        String middleName = data[2];
        String gender = data[5];

        long phoneNumber;
        try {
            phoneNumber = Long.parseLong(data[4]);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка! Неверный формат номера телефона");
            return;
        }

        LocalDate birthDate;
        try {
            birthDate = LocalDate.parse(data[3], DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (DateTimeParseException e) {
            System.out.println("Ошибка! Неверный формат даты рождения");
            return;
        }

        if (!gender.equals("m") && !gender.equals("f")) {
            System.out.println("Ошибка! Неверный формат пола");
            return;
        }

        Person person = new Person(lastName, firstName, middleName, birthDate, phoneNumber, gender);
        Map<String, Person> personMap = new HashMap<>();
        personMap.put(lastName, person);
        FileCreator.createFiles(personMap);
    }

    public static class FileCreator {
        public static final String FILE_EXTENSION = ".txt";

        public static void createFiles(Map<String, Person> personMap) {
            Map<String, BufferedWriter> writerMap = new HashMap<>();

            try {
                for (Person person : personMap.values()) {
                    String fileName = person.getLastName() + FILE_EXTENSION;

                    if (!writerMap.containsKey(fileName)) {
                        // Проверяем, существует ли уже файл с такой фамилией
                        File file = new File(fileName);
                        boolean fileExists = file.exists();

                        // Создаем новый файл или открываем существующий для дополнения
                        FileWriter fileWriter = new FileWriter(file, true);
                        BufferedWriter writer = new BufferedWriter(fileWriter);

                        // Если файл уже существует, переходим на новую строку перед записью новых данных
                        if (fileExists) {
                            writer.newLine();
                        }

                        writerMap.put(fileName, writer);
                    }

                    BufferedWriter writer = writerMap.get(fileName);

                    writer.write(person.getLastName() + " " + person.getFirstName() + " " + person.getMiddleName()
                            + " " + formatDate(person.getDateOfBirth()) + " " + person.getPhoneNumber()
                            + " " + person.getGender());
                    writer.newLine();
                }

                for (BufferedWriter writer : writerMap.values()) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static String formatDate(LocalDate dateOfBirth) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            return dateOfBirth.format(formatter);
        }
    }
}