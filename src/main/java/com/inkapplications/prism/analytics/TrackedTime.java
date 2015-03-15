package com.inkapplications.prism.analytics;

public class TrackedTime
{
    private String category;
    private String name;
    private long time;
    private String label;

    public TrackedTime(String category, long time)
    {
        this.category = category;
        this.time = time;
    }

    public TrackedTime(String category, long time, String name)
    {
        this.category = category;
        this.time = time;
        this.name = name;
    }

    public TrackedTime(String category, long time, String name, String label)
    {
        this.category = category;
        this.time = time;
        this.name = name;
        this.label = label;
    }

    final public String getCategory()
    {
        return this.category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    final public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    final public long getTime()
    {
        return this.time;
    }

    public void setTime(long time)
    {
        this.time = time;
    }

    final public String getLabel()
    {
        return this.label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    @Override
    public String toString()
    {
        return "TrackedTime{"
            + "category='" + category + '\''
            + ", name='" + name + '\''
            + ", time=" + time
            + ", label='" + label + '\''
            + '}';
    }
}
