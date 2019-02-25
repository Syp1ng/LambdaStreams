public class Record {
    private int id;

    public int getId() {
        return id;
    }

    public String getSize() {
        return size;
    }

    public int getWeight() {
        return weight;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public boolean isExpress() {
        return isExpress;
    }

    public boolean isValue() {
        return isValue;
    }

    public boolean isFragile() {
        return isFragile;
    }

    private String size;
    private int weight;
    private String source;
    private String destination;
    private boolean isExpress;
    private boolean isValue;
    private boolean isFragile;

    public Record(int id, String size, int weight, String source, String destination,
                   boolean isExpress, boolean isValue, boolean isFragile) {
                    this.id = id;
                   this.size = size;
                   this.weight = weight;
                   this.source = source;
                   this.destination = destination;
                   this.isExpress = isExpress;
                   this.isValue = isValue;
                   this.isFragile = isFragile;
               }

               public String toString() {
                   StringBuilder stringBuilder = new StringBuilder();
                   stringBuilder.append(id).append(";").append(size).append(";").append(weight).append(";");
                   stringBuilder.append(source).append(";").append(destination).append(";");
                   stringBuilder.append(isExpress).append(";").append(isValue).append(";").append(isFragile);
                   return stringBuilder.toString();
               }
}