import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        String admin = "admin";
        String sandi = "adm1n";
        boolean exit = true;

        while (exit) {
            System.out.println("=== Library System ===");
            System.out.println("1. Login as Student");
            System.out.println("2. Login as Admin");
            System.out.println("3. Exit");

            //untuk user menginput pilihan
            System.out.print("Choose option (1-3) :");
            Scanner angka = new Scanner(System.in);
            int inputAngka = angka.nextInt();

            switch (inputAngka) {
                case 1:

                    //untuk user menginput nim
                    System.out.print("Enter your NIM :");
                    Scanner nim = new Scanner(System.in);
                    String inputNim = nim.nextLine();

                    //cek panjang nim yang di input
                    if (inputNim.length() == 15) {
                        System.out.println("Successful Login as Student");
                    } else if (inputNim.length() < 15) {
                        System.out.println("User not found");
                    } else {
                        System.out.println("User not found");
                    }
                    break;

                case 2:

                    //untuk user menginput username admin
                    System.out.print("Enter your username(admin) :");
                    Scanner name = new Scanner(System.in);
                    String inputNama = name.nextLine();

                    //untuk user menginput password admin
                    System.out.print("Enter your password(admin) :");
                    Scanner pass = new Scanner(System.in);
                    String password = pass.nextLine();

                    //cek apakah username dan password admin sudah benar
                    if (inputNama.equals(admin)) {
                        if (password.equals(sandi)) {
                            System.out.println("Succesfull Login as Admin");
                        } else {
                            System.out.println("Admin Not Found");
                        }
                    }  else {
                        System.out.println("Admin Not Found");
                    }
                    break;

                case 3:
                    exit = false;
                    System.out.println("adios amigos");
                    break;

                default:
                    System.out.println("pilihan tidak tersedia");
                    break;

            }


        }
    }
}