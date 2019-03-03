import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Application {
    private List<Record> records = new ArrayList<>();

    public static void main(String... args) {
        Application ap = new Application();

        ap.execute();
    }

    public List<Record> loadRecords() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("data"
                    + System.getProperty("file.separator") + "records.csv"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] splittedLine = line.split(";");
                records.add(new Record(
                        Integer.parseInt(splittedLine[0]),
                        splittedLine[1],
                        Integer.parseInt(splittedLine[2]),
                        splittedLine[3],
                        splittedLine[4],
                        Boolean.parseBoolean(splittedLine[5]),
                        Boolean.parseBoolean(splittedLine[6]),
                        Boolean.parseBoolean(splittedLine[7])));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            return records;
        }
    }

    // SELECT COUNT(*) FROM data
    public long executeSQL01() {
        return records.stream().count();
    }

    //SELECT COUNT(*) FROM data WHERE source = 'A' AND destination = 'D'
    public long executeSQL02() {
        return records.stream().filter(p -> p.getSource().equals("A"))
                .filter(p -> p.getDestination().equals("D"))
                .count();
    }

    //SELECT COUNT(*) FROM data WHERE size = 'S' AND source IN ('A','B','C') AND weight >= 1000 AND weight <= 2500 AND isExpress = 'true'
    public long executeSQL03() {
        return records.stream()
                .filter(p -> p.getSize().equals("S"))
                .filter(p -> p.getSource().equals("A") || p.getSource().equals("B") || p.getSource().equals("C"))
                .filter(p -> p.getWeight() >= 1000 && p.getWeight() <= 2500)
                .filter(p -> p.isExpress())
                .count();
    }

    //SELECT COUNT(*) FROM data WHERE size = 'L' AND source NOT IN ('A','B') AND weight >= 1250 AND weight <= 3750 AND isExpress = 'false' AND isValue = 'true'
    public long executeSQL04() {
        return records.stream()
                .filter(p -> p.getSize().equals("L"))
                .filter(p -> !p.getSource().equals("A") && !p.getSource().equals("B"))
                .filter(p -> p.getWeight() >= 1250 && p.getWeight() <= 3750)
                .filter(p -> !p.isExpress() && p.isValue())
                .count();
    }

    //SELECT SUM(weight) FROM data WHERE size = 'L' AND source IN ('A','B') AND destination IN ('D','E') AND isValue = 'true'
    public long executeSQL05() {
        return records.stream()
                .filter(p -> p.getSize().equals("L"))
                .filter(p -> p.getSource().equals("A") || p.getSource().equals("B"))
                .filter(p -> p.getDestination().equals("D") || p.getDestination().equals("E"))
                .filter(p -> p.isValue())
                .mapToInt(i -> i.getWeight()).sum();
    }

    //SELECT AVG(weight) FROM data WHERE size IN ('S','M') AND source NOT IN ('G','H') AND isExpress ='false' AND isValue = 'true'
    public long executeSQL06() {
        return records.stream()
                .filter(p -> p.getSize().equals("S") || p.getSize().equals("M"))
                .filter(p -> !p.getSource().equals("G") || !p.getSource().equals("H"))
                .filter(p -> !p.isExpress())
                .filter(p -> p.isValue())
                .collect(Collectors.collectingAndThen(Collectors.averagingLong(Record::getWeight), Double::longValue));
    }

    //SELECT id FROM data WHERE size = 'S' AND destination IN ('D','E','F') AND source = 'B'
    // AND weight >= 3500 AND isExpress ='true' AND isValue = 'true' ORDER BY weight DESC LIMIT 3
    public List<Long> executeSQL07() {
        return records.stream()
                .filter(p -> p.getSize().equals("S"))
                .filter(p -> p.getDestination().equals("D") || p.getDestination().equals("E") || p.getDestination().equals("F"))
                .filter(p -> p.getSource().equals("B"))
                .filter(p -> p.getWeight() >= 3500)
                .filter(p -> p.isExpress())
                .filter(p -> p.isValue())
                .sorted(Comparator.comparing(Record::getWeight).reversed())
                .limit(3)
                .mapToLong(p -> p.getId())
                .boxed()
                .collect(Collectors.toList());
    }

    //SELECT id FROM data WHERE size = 'M' AND destination = 'C' AND source IN ('G','H','I','J')
    // AND weight <= 1500 AND isExpress ='true' AND isValue = 'true' ORDER BY weight DESC,destination
    public List<Long> executeSQL08() {
        return records.stream()
                .filter(p -> p.getSize().equals("M"))
                .filter(p -> p.getDestination().equals("C"))
                .filter(p -> p.getSource().equals("G") || p.getSource().equals("H") || p.getSource().equals("I") || p.getSource().equals("J"))
                .filter(p -> p.getWeight() <= 1500)
                .filter(p -> p.isExpress())
                .filter(p -> p.isValue())
                .sorted(Comparator.comparing(Record::getWeight).reversed()
                        .thenComparing(Record::getDestination))
                .mapToLong(p -> p.getId())
                .boxed()
                .collect(Collectors.toList());
    }

    //SELECT isExpress,COUNT(*) FROM data GROUP BY isExpress
    public Map<Boolean, Long> executeSQL09() {
        return records.stream()
                .collect(Collectors.groupingBy(Record::isExpress, Collectors.counting()));
    }

    //SELECT size,COUNT(*) FROM data WHERE isExpress = 'true' AND isValue = 'false' GROUP BY size
    public Map<String, Long> executeSQL10() {
        return records.stream()
                .filter(p -> p.isExpress())
                .filter(p -> !p.isValue())
                .collect(Collectors.groupingBy(Record::getSize, Collectors.counting()));
    }

    //SELECT destination,COUNT(*) FROM data WHERE destination IN ('A','C') AND isValue = 'false' GROUP BY destination
    public Map<String, Long> executeSQL11() {
        return records.stream()
                .filter(p -> p.getDestination().equals("A") || p.getDestination().equals("C"))
                .filter(p -> !p.isValue())
                .collect(Collectors.groupingBy(Record::getDestination, Collectors.counting()));
    }

    //SELECT destination,COUNT(*) FROM data WHERE source NOT IN ('B','C') AND isExpress = 'true' AND isValue = 'true' AND isFragile = 'true' GROUP BY destination
    public Map<String, Long> executeSQL12() {
        return records.stream()
                .filter(p -> !p.getSource().equals("B") || !p.getSource().equals("C"))
                .filter(p -> p.isExpress())
                .filter(p -> p.isValue())
                .filter(p -> p.isFragile())
                .collect(Collectors.groupingBy(Record::getDestination, Collectors.counting()));
    }

    //SELECT isValue,SUM(weight) FROM data WHERE source = 'B' AND destination NOT IN ('D','E') AND weight >= 1000 AND weight <= 2750 GROUP BY isValue
    public Map<Boolean, Long> executeSQL13() {
        return records.stream()
                .filter(p -> p.getSource().equals("B"))
                .filter(p -> !p.getDestination().equals("D") || !p.getDestination().equals("E"))
                .filter(p -> p.getWeight() >= 1000 && p.getWeight() <= 2750)
                .collect(Collectors.groupingBy(Record::isValue, Collectors.summingLong(Record::getWeight)));
    }

    //SELECT destination,AVG(weight) FROM data WHERE source IN ('A','B') AND destination IN ('C','D')
    // AND weight >= 1250 AND isFragile = 'true' GROUP BY destination
    public Map<String, Long> executeSQL14() {
        return records.stream()
                .filter(p -> p.getSource().equals("A") || p.getSource().equals("B"))
                .filter(p -> p.getDestination().equals("C") || p.getDestination().equals("D"))
                .filter(p -> p.getWeight() >= 1250)
                .filter(p -> p.isFragile())
                .collect(Collectors.groupingBy(Record::getDestination, Collectors.collectingAndThen(Collectors.averagingLong(Record::getWeight), Double::longValue)));
    }

    public void execute() {
        loadRecords();
        executeSQL01();
        executeSQL02();
        executeSQL03();
        executeSQL04();
        executeSQL05();
        executeSQL06();
        executeSQL07();
        executeSQL08();
        executeSQL09();
        executeSQL10();
        executeSQL11();
        executeSQL12();
        executeSQL13();
        executeSQL14();
    }
}