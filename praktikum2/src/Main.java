import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private String usernameAdmin = "admin";
    private String passworsAdmin = "adm1n";
    public static Book[] bookList;
    private List<Student> userStudent;
    private Scanner scanner;

    public Main() {
        scanner = new Scanner(System.in);
        // Inisialisasi daftar buku
        bookList = new Book[]{
                new Book("1", "Seni Hidup Minimalis", "Francine Jay", "Author1", 1),
                new Book("2", "Bahasa Indonesia", "Tim Dosen UMM", "Author2", 2),
                new Book("3", "Filosofi Teras", "Henry Manampiring", "Author3", 3),
                new Book("4", "Atomic Habits", "James Clear", "Author3", 4)
        };

        // Inisialisasi daftar user student
        userStudent = new ArrayList<>();
        userStudent.add(new Student("Taufiq Ramadhan", "282210378311288", "Teknik", "Informatika"));
        userStudent.add(new Student("arif munandar", "200510370310521", "Teknik", "Informatika"));
        userStudent.add(new Student("Sutrisno Adit Pratama", "282210378311283", "Teknik", "Informatika"));
    }

    public void menu() {
        int choice;
        do {
            System.out.println("Library System");
            System.out.println("1. Masuk Sebagai Admin");
            System.out.println("2. Masuk Sebagai Mahasiswa");
            System.out.println("3. Keluar");
            System.out.print("Silahkan Masukan Pilihan: ");
            choice = cekTipeInputanUser();

            switch (choice) {
                case 1:
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("Masukkan username: ");
                    String username = scanner.nextLine();
                    System.out.print("Masukkan password: ");
                    String password = scanner.nextLine();

                    if (usernameAdmin.equals(username)){
                        if (passworsAdmin.equals(password)){
                            System.out.println("Berhasil Masuk");
                            menuAdmin();
                        } else {
                            System.out.println("Username Atau Password Anda Salah. Silahkan Coba lagi....");
                            menu();
                        }
                    } else {
                        System.out.println("Username Atau Password Anda Salah. Silahkan Coba lagi....");
                        menu();
                    }

                    break;
                case 2:
                    inputNim();
                    break;
                case 3:
                    System.out.println("Keluar Dari Program...");
                    break;
                default:
                    System.out.println("Pilihan Tidak Tersedia. Silahkan Pilih Ulang");
            }
        } while (choice != 3);
    }

    public int cekTipeInputanUser() {
        while (!scanner.hasNextInt()) {
            System.out.println("Yang Anda Input Bukan Angka");
            scanner.next();
        }
        return scanner.nextInt();
    }

    public void inputNim() {
        System.out.print("Masukan NIM: ");
        scanner.nextLine();
        String nim = scanner.nextLine();
        checkNim(nim);
    }

    public void checkNim(String nim) {
        boolean found = false;
        for (Student student : userStudent) {
            if (student.getNim().equals(nim)) {
                found = true;
                System.out.println("Berhasil Masuk sebagai Mahasiswa");
                student.menuStudent();
                break;
            }
        }
        if (!found) {
            System.out.println("Mahasiswa Dengan NIM " + nim + " Tidak Ditemukan.");
        }
    }

    public void menuAdmin() {
        Admin admin = new Admin(userStudent);
        int choice;
        do {
            System.out.println("Admin Menu");
            System.out.println("1. Tambah Mahasiswa");
            System.out.println("2. Tampilkan Mahasiswa Terdaftar");
            System.out.println("3. Keluar");
            System.out.print("Masukan Pilihan: ");
            choice = cekTipeInputanUser();

            switch (choice) {
                case 1:
                    admin.tambahMahasiswa();
                    break;
                case 2:
                    admin.displayStudent();
                    break;
                case 3:
                    System.out.println("Keluar dari akun admin...");
                    break;
                default:
                    System.out.println("Pilihan Tidak Tersedia. Silahkan Coba Lagi");
            }
        } while (choice != 3);
    }

    public static void main(String[] args) {
        Main library = new Main();
        library.menu();
    }
}

class Book {
    private String id;
    private String title;
    private String author;
    private String category;
    private int stock;

    public Book(String id, String title, String author, String category, int stock) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.stock = stock;
    }

    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}

class Student {
    private String name;
    private String nim;
    private String faculty;
    private String studyProgram;
    private List<Book> meminjamBuku;
    private Scanner scanner;

    public Student(String name, String nim, String faculty, String studyProgram) {
        this.name = name;
        this.nim = nim;
        this.faculty = faculty;
        this.studyProgram = studyProgram;
        this.meminjamBuku = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void menuStudent() {
        int Pilihan;
        do {
            System.out.println("Student Menu");
            System.out.println("1. Tampilan Buku");
            System.out.println("2. Buku Yang Dipinjam");
            System.out.println("3. Meminjam Buku");
            System.out.println("4. Keluar");
            System.out.print("Masukan Pilihan: ");
            Pilihan = cektipeDataInputanUser();

            switch (Pilihan) {
                case 1:
                    tampilanBuku(Main.bookList);
                    break;
                case 2:
                    menampilkanBukuyangDipinjam();
                    break;
                case 3:
                    meminjamBuku();
                    break;
                case 4:
                    System.out.println("Keluar dari akun Mahasiswa...");
                    break;
                default:
                    System.out.println("Pilihan tidak tersedia. silahkan pilih ulang");
            }
        } while (Pilihan != 4);
    }

    public int cektipeDataInputanUser() {
        while (!scanner.hasNextInt()) {
            System.out.println("Yang anda input bukan angka.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    public void tampilanBuku(Book[] books) {
        System.out.println("Buku yang Tersedia:");
        for (Book book : books) {
            System.out.println("========================================================================");
            System.out.println("Id: " + book.getId() + " || " + " Judul: " + book.getTitle() + " || " + "Penulis: " + book.getAuthor() + " || " + " Stok Buku: " + book.getStock() + " Stok Tersedia" + " ||");
            System.out.println("========================================================================");
        }
    }

    private void menampilkanBukuyangDipinjam() {
        if (meminjamBuku.isEmpty()) {
            System.out.println("Anda belum meminjam buku apa pun.");
        } else {
            System.out.println("Buku yang Dipinjam:");
            for (Book book : meminjamBuku) {
                System.out.println(book.getTitle() + " (" + book.getAuthor() + ")");
            }
        }
    }

    private void meminjamBuku() {
        System.out.print("Masukkan ID buku yang ingin Anda pinjam (input 99 untuk kemblai) : ");
        scanner.nextLine();
        String idBuku = scanner.nextLine();
        Book bukuUntukdiPinjam = null;

        //cek user input angka 99
        if (idBuku.equals("99")){
            menuStudent();
        }
        for (Book book : Main.bookList) {
            if (book.getId().equals(idBuku)) {
                bukuUntukdiPinjam = book;
                break;
            }
        }
        if (bukuUntukdiPinjam != null) {
            if (bukuUntukdiPinjam.getStock() > 0) {
                meminjamBuku.add(bukuUntukdiPinjam);
                bukuUntukdiPinjam.setStock(bukuUntukdiPinjam.getStock() - 1);
                System.out.println("\n" + "Buku berhasil dipinjam.");
            } else {
                System.out.println("Maaf, stok buku sedang habis.");
            }
        } else {
            System.out.println("Buku Tidak Ditemukan.");
        }
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getStudyProgram() {
        return studyProgram;
    }

    public void setStudyProgram(String studyProgram) {
        this.studyProgram = studyProgram;
    }
}

class Admin{
    private List<Student> userStudent;

    public Admin(List<Student> students) {
        this.userStudent = students;
    }
    public void tambahMahasiswa() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukan Nama: ");
        String name = scanner.nextLine();
        System.out.print("Masukan NIM: ");
        String nim;
        do {
            nim = scanner.nextLine();
            if (nim.length() != 15) {
                System.out.println("NIM Tidak Valid. Masukan 15 Angka");
                System.out.print("Masukan Ulang NIM: ");
            }
        } while (nim.length() != 15);

        System.out.print("Masukan Fakultas: ");
        String faculty = scanner.nextLine();
        System.out.print("Masukan Program Studi: ");
        String studyProgram = scanner.nextLine();

        userStudent.add(new Student(name, nim, faculty, studyProgram));
        System.out.println("Data Mahasiswa Berhasil Di Tambah");
    }

    public void displayStudent() {
        System.out.println("Mahasiswa Terdaftar:");
        for (Student student : userStudent) {
            System.out.println("Nama: " + student.getName() + " ||" + " Faklutas: " + student.getFaculty() + " ||" + " Program Studi: " + student.getStudyProgram() + " ||" + " Nim: " + student.getNim() + " ||");
        }
    }
}
