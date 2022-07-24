import java.util.HashMap;
import java.util.Set;

public class YearlyReport {
    MonthlyReport monthlyReport = new MonthlyReport();

    /**
     * Выводим данные отчета
     * @param readFile Мапа с названием файла и его содержанием
     */
    public void printReport(HashMap<String, String> readFile){
        HashMap<Integer, Integer[]> contentReport  = contentReport(readFile);
        double sumExpenses = 0;
        double sumIncome = 0;
        int countReport = 0;
        Set<Integer> keysMonth = contentReport.keySet();

        for (Integer month : keysMonth){
            System.out.println(String.format("Месяц: %s", monthlyReport.converterMonth(month)));
            Integer profit = contentReport.get(month)[1] - contentReport.get(month)[0];
            sumExpenses =contentReport.get(month)[0];
            sumIncome =contentReport.get(month)[1];
            System.out.println(String.format("Прибыль: %d\n", profit));
            countReport++;
        }

        double averageCountReportExpenses = sumExpenses / countReport;
        double averageCountReportIncome = sumIncome / countReport;
        System.out.println(String.format("Количество отчетных месяцев: %d", countReport));
        System.out.println(String.format("Средний расход за отчетные месяцы в году: %f", averageCountReportExpenses));
        System.out.println(String.format("Средний доход за отчетные месяцы в году: %f", averageCountReportIncome));
        System.out.println();

        double averageExpenses = sumExpenses / 12;
        double averageIncome = sumIncome / 12;
        System.out.println(String.format("Средний расход за все месяцы в году: %f", averageExpenses));
        System.out.println(String.format("Средний доход за все месяцы в году: %f", averageIncome));
        System.out.println();
    }

    /**
     * Формируем мапу с данными по месяцам: 'Номер месяца': '[сумма расхода, сумма дохода]'
     * @param readFile Мапа с названием файла и его содержанием
     * @return данные по месяцам
     */
    public HashMap<Integer, Integer[]> contentReport(HashMap<String, String> readFile){
        HashMap<Integer, Integer[]> contentReport = new HashMap<>();
        Set<String> nameFiles = readFile.keySet();

        for (String file : nameFiles){
            String contentFile = readFile.get(file);
            String[] lines = contentFile.split(System.lineSeparator());

            for (int i = 1; i < 13; i++){
                Integer[] profit = new Integer[2];

                for(int j = 1; j < lines.length; j++){
                    String[] lineContents = lines[j].split(",");

                    if(Integer.parseInt(lineContents[0]) == i){

                        if(lineContents[2].equals("true")){
                            profit[0] = Integer.parseInt(lineContents[1]);
                        }
                        else if (lineContents[2].equals("false")){
                            profit[1] = Integer.parseInt(lineContents[1]);
                        }

                        contentReport.put(i, profit);
                    }
                }
            }
        }
        return contentReport;
    }
}
