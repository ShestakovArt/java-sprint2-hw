import java.util.HashMap;
import java.util.Set;

public class MonthlyReport{
    /**
     * Конвертируем цифровое представление месяца в буквенное
     * @param numMonth номер месяца в формате хх
     * @return название месяца
     */
    public String converterMonth(int numMonth){
        HashMap<Integer, String> month = new HashMap<>();
        month.put(1, "Январь");
        month.put(2, "Февраль");
        month.put(3, "Март");
        month.put(4, "Апрель");
        month.put(5, "Май");
        month.put(6, "Июнь");
        month.put(7, "Июль");
        month.put(8, "Август");
        month.put(9, "Сентябрь");
        month.put(10, "Октябрь");
        month.put(11, "Ноябрь");
        month.put(12, "Декабрь");
        return month.get(numMonth);
    }

    /**
     * Выводим данные отчета
     * @param readFile Мапа с названием файла и его содержанием
     */
    public void printReport(HashMap<String, String> readFile){
        Set<String> nameFiles = readFile.keySet();
        for (String file : nameFiles){
            Integer month = Integer.parseInt(file.substring(6, 8));
            System.out.println(String.format("Месяц: %s", converterMonth(month)));
            contentReport(readFile.get(file));
            System.out.println();
        }
    }

    /**
     * Вывод самого прибыльного товара и самой большой траты месяца
     * @param contentFile содержание файла отчета за месяц
     */
    private void contentReport(String contentFile){
        String[] lines = contentFile.split(System.lineSeparator());
        String profitProductName = null;
        int profitProduct = 0;
        String bigLoseName = null;
        int bigLose = 0;

        for(int i = 1; i < lines.length; i++){
            String[] lineContents = lines[i].split(",");
            if(lineContents[1].equals("TRUE")){
                Integer totalCost = totalCost(Integer.parseInt(lineContents[2]), Integer.parseInt(lineContents[3]));
                if(totalCost > bigLose){
                    bigLose = totalCost;
                    bigLoseName = lineContents[0];
                }
            }

            else if(lineContents[1].equals("FALSE")){
                Integer totalCost = totalCost(Integer.parseInt(lineContents[2]), Integer.parseInt(lineContents[3]));
                if(totalCost > profitProduct){
                    profitProduct = totalCost;
                    profitProductName = lineContents[0];
                }
            }
        }

        System.out.println(String.format("Самый прибыльный товар: '%s' сумма прибыли %d", profitProductName, profitProduct));
        System.out.println(String.format("Самая большая трата: '%s' на сумму %d", bigLoseName, bigLose));
    }

    public int[] sumLoseAndProfit(String contentFile){
        String[] lines = contentFile.split(System.lineSeparator());
        int profitSum = 0;
        int loseSum = 0;

        for(int i = 1; i < lines.length; i++){
            String[] lineContents = lines[i].split(",");
            if(lineContents[1].equals("TRUE")){
                Integer totalCost = totalCost(Integer.parseInt(lineContents[2]), Integer.parseInt(lineContents[3]));
                loseSum+=totalCost;
            }

            else if(lineContents[1].equals("FALSE")){
                Integer totalCost = totalCost(Integer.parseInt(lineContents[2]), Integer.parseInt(lineContents[3]));
                profitSum+=totalCost;
            }
        }
        return new int[]{ loseSum, profitSum };
    }

    private Integer totalCost(Integer count, Integer cost){
        return count * cost;
    }
}
