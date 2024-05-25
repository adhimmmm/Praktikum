package tugas.data;

import tugas.com.main.LibrarySystem;
import tugas.Exception.Custom.IllegalAdminAccess;
import tugas.books.HistoryBook;
import tugas.books.StoryBook;
import tugas.books.TextBook;
import tugas.util.IMenu;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Admin extends User implements IMenu {
    Scanner inputObj = new Scanner(System.in);
    private String adminUserName = "admin", adminPassword = "admin";
    private static ArrayList<Student> studentData = new ArrayList<>();
    private static ArrayList<String> studentList = new ArrayList<>();

    @Override
    public void menu() {
        boolean isRun = true;
        Scanner inputObj = new Scanner(System.in);
        while (isRun) {
            try {
                System.out.print(
                        "===== Admin Menu =====\n1. Tambah Mahasiswa\n2. Tambah Buku \n3. Tampilkan Mahasiswa Terdaftar \n4. Tampilkan Buku Yang Tersedia\n5. Display User\n6. Logout\nPilih Opsi (1-5) : ");
                int choose = inputObj.nextInt();
                inputObj.nextLine();
                switch (choose) {
                    case 1:
                        LibrarySystem.addTempStudent();
                        break;
                    case 2:
                        this.inputBook();
                        break;
                    case 3:
                        this.displayStudent();
                        break;
                    case 4:
                        this.displayBook();
                        break;
                    case 5:
                        this.showUser();
                        break;
                    case 6:
                        isRun = false;
                        break;
                    default:
                        System.out.println("INVALID INPUT");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("INVALID INPUT.");
                inputObj.nextLine();
            }
        }
    }

    public void addStudent(Student student) {
        studentData.add(student);
        studentList.add(student.getNIM());
    }

    public void inputBook() {
        String newTitle, newAuthor, newStock, choose;
        do {
            System.out.print("Pilih Kategori Buku:\n1. Story\n2. History\n3. Text Book\nChoose Category : ");
            choose = inputObj.nextLine();
            if (!choose.matches("[1-3]")) {
                System.out.println("Kategori Tidak ditemukan. Silahkan Pilih 1 to 3.");
            }
        } while (!choose.matches("[1-3]"));
        System.out.print("Masukan judul buku     : ");
        newTitle = inputObj.nextLine();
        System.out.print("Masukan penulis buku    : ");
        newAuthor = inputObj.nextLine();
        do {
            System.out.print("Masukan Stok buku     : ");
            newStock = inputObj.nextLine();

            if (!newStock.matches("\\d+")) {
                System.out.println("Inputan tidak valid. Silahkan masukan nomor.");
            }
        } while (!newStock.matches("\\d+"));
        switch (Integer.parseInt(choose)) {
            case 1:

                StoryBook storybook = new StoryBook(generateId(), newTitle, newAuthor, Integer.parseInt(newStock));
                super.addBook(storybook);
                break;
            case 2:
                HistoryBook historyBook = new HistoryBook(generateId(), newTitle, newAuthor,
                        Integer.parseInt(newStock));
                super.addBook(historyBook);
                break;
            case 3:
                TextBook textbook = new TextBook(generateId(), newTitle, newAuthor, Integer.parseInt(newStock));
                super.addBook(textbook);
                break;
            default:
                break;
        }
        System.out.println("Buku berhasil ditambahkan ke perpustakaan");
    }

    @Override
    public void showUser() {
        System.out.println("USER ADMIN");
    }

    @Override
    public void displayBook() {
        super.displayBook();
    }

    public void displayStudent() {
        for (Student x : studentData) {
            x.displayInfo();
        }
    }

    public boolean isAdmin() throws IllegalAdminAccess {
        String username, pass;
        Scanner inputObj = new Scanner(System.in);
        System.out.print("Input Username : ");
        username = inputObj.nextLine();
        System.out.print("Input Password : ");
        pass = inputObj.nextLine();
        if (username.equals(getAdminUserName()) && pass.equals(getAdminPassword())) {
            return true;
        } else {
            throw new IllegalAdminAccess("Invalid credentials");
        }
    }

    public String generateId() {
        Random random = new Random();

        StringBuilder idTengah = new StringBuilder();
        StringBuilder idAkhir = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            idTengah.append(random.nextInt(10));
            idAkhir.append(random.nextInt(10));
        }
        return ("UMM-" + idTengah + "-" + idAkhir);
    }

    public String getAdminUserName() {
        return adminUserName;
    }

    public static ArrayList<Student> getStudentData() {
        return studentData;
    }

    public static ArrayList<String> getStudentList() {
        return studentList;
    }

    public String getAdminPassword() {
        return adminPassword;
    }
}