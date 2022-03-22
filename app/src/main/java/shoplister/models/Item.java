package shoplister.models;

import shoplister.core.IColors;
import shoplister.core.Instance;

public class Item implements IColors
{
    private String name;
    private String icon;
    private String description;
    private String category;
    public  String checked;

    public Item() { }

    public String getName()                  { return name; }
    public String getIcon()                  { return icon; }
    public String getDescription()           { return description; }
    public String getCategory()              { return category; }
    public String getChecked()               { return checked; }

    public void setCategory(String category) { this.category = category; }

    public int iconAsResource()
    {
        return Instance.context.getResources().getIdentifier(icon, "drawable", Instance.context.getPackageName());
    }
}
