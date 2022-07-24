import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadReport {

    /**
     * Считываем отчеты из директории rescources, и формируем мапу
     * @param timePeriod период отчетов('месяц' - помесячные отчеты, 'год' - за год)
     * @return Мапа с названием файла и его содержанием
     */
    public HashMap<String, String> readFile(String timePeriod, String reportingYear){
        List<String> nameFileReport = listReport(timePeriod, reportingYear);
        HashMap<String, String> contentFile = new HashMap<>();
        String content;
        for (String file : nameFileReport) {
            content = readFileContentsOrNull(String.format("resources/%s", file));
            contentFile.put(file, content);
        }
        return contentFile;
    }

    /**
     * Формируем список с необходимыми файлами отчетов из директории rescources
     * @param timePeriod период отчетов('месяц' - помесячные отчеты, 'год' - за год)
     * @return список имен файлов с отчетами
     */
    private List<String> listReport(String timePeriod, String reportingYear){
        String templateRegex = "";
        List<String> nameFileReport;
        if(timePeriod.equals("месяц")){
            templateRegex = String.format("\\b[m]\\.%s(0[1-9]|[1][0-2])\\.csv\\b", reportingYear);
        }
        else if (timePeriod.equals("год")){
            templateRegex =  String.format("\\b[y]\\.%s\\.csv\\b", reportingYear);
        }
        nameFileReport = printDirectories(templateRegex);
        if(nameFileReport.size() > 0){
            System.out.println("\nСчитывание прошло успешно!\n");
        }
        else {
            System.out.println("\nНе удалось считать файлы!\n");
        }
        return nameFileReport;
    }

    /**
     * Считываем нужные файлы в директории rescources
     * @param templateRegex шаблон названия необходимого файла
     * @return список имен подходящих файлов
     */
    private List<String> printDirectories(String templateRegex){
        File dir = new File("resources");
        List<String> nameFileReport = new ArrayList<>();
        Pattern pattern = Pattern.compile(templateRegex);
        Matcher matcher;
        for(File item : dir.listFiles()) {
            if (item.isFile()) {
                matcher = pattern.matcher(item.getName());
                if (matcher.find()) {
                    nameFileReport.add(item.getName());
                }
            }
        }
        return nameFileReport;
    }

    /**
     * Считываем файл
     * @param path путь до необходимого файла
     * @return Содержание файла
     */
    private String readFileContentsOrNull(String path)
    {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }
}
