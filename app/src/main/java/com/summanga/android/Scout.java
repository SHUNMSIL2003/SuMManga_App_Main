package com.summanga.android;

public class Scout {
    private String Name, Rank, KillCount,SuMExploreAuthor,SuMExploreGerns,MangaAgeRating,SuMExploreURL;

    public Scout(String name, String rank, String killCount,String SuMExploreAuthor0,String SuMExploreGerns0,String MangaAgeRating0,String SuMExploreURL0) {
        Name = name;
        Rank = rank;
        KillCount = killCount;
        SuMExploreAuthor = SuMExploreAuthor0;
        SuMExploreGerns = SuMExploreGerns0;
        MangaAgeRating = MangaAgeRating0;
        SuMExploreURL = SuMExploreURL0;
    }

    public String getSuMExploreURL() { return SuMExploreURL; }
    public String getMangaAgeRating() { return MangaAgeRating; }
    public String getSuMExploreGerns() { return SuMExploreGerns; }
    public String getSuMExploreAuthor() { return SuMExploreAuthor; }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRank() {
        return Rank;
    }

    public void setRank(String rank) {
        Rank = rank;
    }

    public String getKillCount() {
        return KillCount;
    }

    public void setKillCount(String killCount) {
        KillCount = killCount;
    }

    public String getGernString(){ return SuMExploreGerns; }

}

