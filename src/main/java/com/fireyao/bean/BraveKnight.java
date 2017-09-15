package com.fireyao.bean;

/**
 * Created by lly on 2017/8/29
 */
public class BraveKnight implements Knights {

    private Quest quest;

    public BraveKnight(Quest quest) {
        this.quest = quest;
    }

    @Override
    public void embarkOnQuest() {
        quest.embark();
    }

    @Override
    public void saySomething(StringBuffer sth) {
        System.out.println(sth.toString());
    }
}
