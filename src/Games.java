public class Games {
    private String name;
    private String platform;
    private String year;
    private String genre;
    private String global_Sales;

    public Games(String name, String platform, String year, String genre, String global_Sales) {
        this.name = name;
        this.platform = platform;
        this.year = year;
        this.genre = genre;
        this.global_Sales = global_Sales;
    }

    public String getName() {
        return name;
    }

    public String getPlatform() {
        return platform;
    }

    public String getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public String getGlobal_Sales() {
        return global_Sales;
    }
}
