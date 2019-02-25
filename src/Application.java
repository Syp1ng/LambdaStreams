import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Application {
    private List<Record> records = new ArrayList<>();

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
        }
        finally{
            return records;
        }
    }

    // count
    public long executeSQL01() {
        return records.stream().count();
    }

    // count, where
    public long executeSQL02() {
        return records.stream().filter(p -> p.getSource().equals("A"))
                .filter(p -> p.getDestination().equals("D"))
                .count();
    }

    // count, where, in
    public long executeSQL03() {
        return records.stream()
                .filter(p->p.getSize().equals("S"))
                .filter(p->p.getSource().equals("A")|| p.getSource().equals("B") || p.getSource().equals("C"))
                .filter(p->p.getWeight()>=1000&& p.getWeight()<=2500)
                .filter(p->p.isExpress() == true)
                .count();
    }

    // count, where, not in
    public long executeSQL04() {
        return records.stream()
                .filter(p->p.getSize().equals("L"))
                .filter(p-> !p.getSource().equals("A") && !p.getSource().equals("B"))
                .filter(p->p.getWeight()>=1250&& p.getWeight()<=3750)
                .filter(p->p.isExpress() == false && p.isValue() == true)
                .count();
    }

    // id, where, in, order by desc limit
    public long executeSQL05() {
        return records.stream()
                .filter(p->p.getSize().equals("L"))
                .filter(p->p.getSource().equals("A")||p.getSource().equals("B"))
                .filter(p->p.getDestination().equals("D")||p.getDestination().equals("E"))
                .filter(p->p.isValue() == true)
                .mapToInt(i->i.getWeight()).sum();
    }

    // id, where, in, order by desc, order by asc
    public void executeSQL06() {
    }

    // count, group by
    public void executeSQL07() {
    }

    // count, where, group by
    public void executeSQL08() {
    }

    // count, where, in, group by
    public void executeSQL09() {
    }

    // count, where, not in, group by
    public void executeSQL10() {
    }

    // sum, where, not in, in, group by
    public void executeSQL11() {
    }

    // avg, where, in, in, group by
    public void executeSQL12() {
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
    }

    public static void main(String... args) {
        Application ap = new Application();
        ap.execute();
    }
}