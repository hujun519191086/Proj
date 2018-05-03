package com.qc188.com.bean;

public class CarTypeTitleBean
{
    private String structure;
    private String drive;
    private String drive_gearbox;
    private String guild_sale;
    private String the_lowest;

    private String img;

    public String getImg()
    {
        return img;
    }

    public void setImg(String img)
    {
        this.img = img;
    }

    public String getStructure()
    {
        return structure;
    }

    public void setStructure(String structure)
    {
        this.structure = structure;
    }

    public String getDrive()
    {
        return drive;
    }

    public void setDrive(String drive)
    {
        this.drive = drive;
    }

    public String getDrive_gearbox()
    {
        return drive_gearbox;
    }

    public void setDrive_gearbox(String drive_gearbox)
    {
        this.drive_gearbox = drive_gearbox;
    }

    public String getGuild_sale()
    {
        return guild_sale;
    }

    public void setGuild_sale(String guild_sale)
    {
        this.guild_sale = guild_sale;
    }

    public String getThe_lowest()
    {
        return the_lowest;
    }

    public void setThe_lowest(String the_lowest)
    {
        this.the_lowest = the_lowest;
    }

    @Override
    public String toString()
    {
        return "CarTypeTitleBean [structure=" + structure + ", drive=" + drive + ", drive_gearbox=" + drive_gearbox + ", guild_sale=" + guild_sale + ", the_lowest=" + the_lowest + "]";
    }

}
