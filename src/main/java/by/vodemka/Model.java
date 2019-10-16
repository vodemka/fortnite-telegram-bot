package by.vodemka;

public class Model {
    private String type;
    private String name;
    private String background;
    private String date;

    public Model() {
    }

    @Override
    public String toString() {
        return "\uD83D\uDC51Name: " + getName() + "\n" +
                "\uD83D\uDCADType: " + getType() + "\n" +
                "\uD83D\uDD57Request time: " + getDate();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
