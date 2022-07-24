import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        // Поехали!
        Scanner scanner = new Scanner(System.in);
        ReadReport readReport = new ReadReport();
        MonthlyReport monthlyReport = new MonthlyReport();
        YearlyReport yearlyReport = new YearlyReport();
        HashMap<String, String> readFileMonthly = null;
        HashMap<String, String> readFileYearly = null;
        String reportingYear;
        boolean readMonthly = false;
        boolean readYearly = false;
        boolean run = true;
        while (run) {
            printMenu();
            String command = scanner.nextLine().toLowerCase();

            switch (command){
                case "1":
                    System.out.println("\nВведите отчетный год(на данный момент доступны отчеты за 2021год):");
                    reportingYear = scanner.nextLine().toLowerCase();
                    readFileMonthly = readReport.readFile("месяц", reportingYear);
                    readMonthly = true;
                    break;
                case "2":
                    System.out.println("\nВведите отчетный год(на данный момент доступны отчеты за 2021год):");
                    reportingYear = scanner.nextLine().toLowerCase();
                    readFileYearly = readReport.readFile("год", reportingYear);
                    readYearly = true;
                    break;
                case "3":
                    if(readMonthly && readYearly){
                        HashMap<Integer, Integer[]> yearlyData = yearlyReport.contentReport(readFileYearly);
                        Set<Integer> keysMonth = yearlyData.keySet();
                        boolean success = true;
                        for (Integer monthNum : keysMonth){
                            Set<String> nameFilesMonthly = readFileMonthly.keySet();
                            for (String file : nameFilesMonthly) {
                                Integer month = Integer.parseInt(file.substring(6, 8));
                                if (monthNum.equals(month)) {
                                    int sum[] = monthlyReport.sumLoseAndProfit(readFileMonthly.get(file));
                                    if(!(sum[0] == yearlyData.get(monthNum)[0]) || !(sum[1] == yearlyData.get(monthNum)[1])){
                                        success = false;
                                        System.out.println(String.format("\nДанные отчета за %s не соответствуют данным в годовом отчете\n",
                                                monthlyReport.converterMonth(monthNum)));
                                    }
                                }
                            }
                        }
                        if(success){
                            System.out.println("\nОперация успешно завершена.\n");
                        }
                    }
                    else
                    {
                        System.out.println("\nСначала необходимо считать отчеты.\n");
                    }

                    break;
                case "4":
                    if(readMonthly){
                        monthlyReport.printReport(readFileMonthly);
                    }
                    else
                    {
                        System.out.println("\nСначала необходимо считать отчеты.\n");
                    }
                    break;
                case "5":
                    if(readYearly){
                    Set<String> nameFilesYearly = readFileYearly.keySet();
                    for(String nameFile : nameFilesYearly){
                        String year = nameFile.substring(2,6);
                        System.out.println(String.format("\nРассматриваемый год: %s\n", year));
                    }
                    yearlyReport.printReport(readFileYearly);

                    }
                    else
                    {
                        System.out.println("\nСначала необходимо считать отчеты.\n");
                    }
                    break;
                case "выход":
                case "ds[jl":
                case "0":
                    run = false;
                    scanner.close();
                    break;
                default:
                    System.out.println("\nНе известная команда, введите команду из списка!\n");
                    break;

            }
        }
    }

    public static void printMenu() {
        System.out.println("Что вы хотите сделать?");
        System.out.println("1 - Считать все месячные отчёты");
        System.out.println("2 - Считать годовой отчёт");
        System.out.println("3 - Сверить отчёты");
        System.out.println("4 - Вывести информацию о всех месячных отчётах");
        System.out.println("5 - Вывести информацию о годовом отчёте");
        System.out.println("Выход(0) - Выход");
    }
}

