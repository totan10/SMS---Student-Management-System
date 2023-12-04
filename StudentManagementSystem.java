import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Student implements Serializable {
    String name;
    int rollNo;
    // Add more attributes as needed

    public Student(String name, int rollNo) {
        this.name = name;
        this.rollNo = rollNo;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Roll No: " + rollNo;
    }
}

public class StudentManagementSystem {
    private static Map<Integer, Student> studentMap = new HashMap<>();

    public static void main(String[] args) {
        loadDataFromFile(); // Load existing data from file

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Student");
            System.out.println("2. Fetch Student Details");
            System.out.println("3. Update Student Details");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    fetchStudentDetails();
                    break;
                case 3:
                    updateStudentDetails();
                    break;
                case 4:
                    deleteStudent();
                    break;
                case 5:
                    saveDataToFile();
                    System.out.println("Exiting program. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addStudent() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter student roll number: ");
        int rollNo = scanner.nextInt();

        Student student = new Student(name, rollNo);
        studentMap.put(rollNo, student);

        System.out.println("Student added successfully!");
    }

    private static void fetchStudentDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter roll number to fetch details: ");
        int rollNo = scanner.nextInt();

        Student student = studentMap.get(rollNo);

        if (student != null) {
            System.out.println("Student details: " + student);
        } else {
            System.out.println("Student not found with roll number: " + rollNo);
        }
    }

    private static void updateStudentDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter roll number to update details: ");
        int rollNo = scanner.nextInt();

        if (studentMap.containsKey(rollNo)) {
            System.out.print("Enter new name: ");
            String newName = scanner.nextLine();
            studentMap.get(rollNo).name = newName;
            System.out.println("Student details updated successfully!");
        } else {
            System.out.println("Student not found with roll number: " + rollNo);
        }
    }

    private static void deleteStudent() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter roll number to delete student: ");
        int rollNo = scanner.nextInt();

        if (studentMap.containsKey(rollNo)) {
            studentMap.remove(rollNo);
            System.out.println("Student deleted successfully!");
        } else {
            System.out.println("Student not found with roll number: " + rollNo);
        }
    }

    private static void saveDataToFile() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("student_data.ser"))) {
            objectOutputStream.writeObject(studentMap);
            System.out.println("Data saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadDataFromFile() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("student_data.ser"))) {
            Object object = objectInputStream.readObject();
            if (object instanceof Map) {
                studentMap = (Map<Integer, Student>) object;
                System.out.println("Data loaded from file.");
            }
        } catch (IOException | ClassNotFoundException e) {
            // Ignore if the file doesn't exist or is corrupted
        }
    }
}
